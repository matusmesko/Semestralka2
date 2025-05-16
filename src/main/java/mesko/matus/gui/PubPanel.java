package mesko.matus.gui;

import mesko.matus.player.Player;
import mesko.matus.ui.WoodenButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Panel for the pub area where the player can play a cup game
 * The player bets money and tries to guess which cup has a dice under it
 */
public class PubPanel extends JPanel {
    private Player player;
    private JPanel parentPanel;
    private JLabel playerCoinsLabel;
    private JTextField betAmountField;
    private JPanel cupsPanel;
    private JLabel resultLabel;

    private int diceUnderCup; // 0, 1, or 2 (index of the cup with the dice)
    private boolean gameInProgress = false;
    private int currentBet = 0;

    /**
     * Creates a new pub panel with the player and parent panel
     * @param player The player
     * @param parentPanel The parent panel to return to
     */
    public PubPanel(Player player, JPanel parentPanel) {
        this.player = player;
        this.parentPanel = parentPanel;

        // Set up the panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(100, 70, 30));

        // Create title
        JLabel titleLabel = new JLabel("Pub - Cup Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(titleLabel, BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Create a panel for game info (coins and result)
        JPanel gameInfoPanel = new JPanel(new BorderLayout(5, 5));
        gameInfoPanel.setOpaque(false);

        // Create player info panel
        JPanel playerInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerInfoPanel.setOpaque(false);

        this.playerCoinsLabel = new JLabel("Your coins: " + player.getCoins(), JLabel.CENTER);
        this.playerCoinsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.playerCoinsLabel.setForeground(Color.YELLOW);
        playerInfoPanel.add(this.playerCoinsLabel);

        gameInfoPanel.add(playerInfoPanel, BorderLayout.NORTH);

        // Create result label
        this.resultLabel = new JLabel("Place a bet to start the game!", JLabel.CENTER);
        this.resultLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        this.resultLabel.setForeground(Color.WHITE);
        gameInfoPanel.add(this.resultLabel, BorderLayout.CENTER);

        contentPanel.add(gameInfoPanel, BorderLayout.NORTH);

        // Create betting panel
        JPanel bettingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bettingPanel.setOpaque(false);

        JLabel betLabel = new JLabel("Your bet: ");
        betLabel.setForeground(Color.WHITE);
        bettingPanel.add(betLabel);

        this.betAmountField = new JTextField(4);
        this.betAmountField.setText("10"); // Default bet
        bettingPanel.add(this.betAmountField);

        WoodenButton placeBetButton = new WoodenButton("Place Bet");
        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PubPanel.this.placeBet();
            }
        });
        bettingPanel.add(placeBetButton);

        contentPanel.add(bettingPanel, BorderLayout.CENTER);

        // Create cups panel
        this.cupsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        this.cupsPanel.setOpaque(false);
        this.cupsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Initially show empty cups (not selectable until bet is placed)
        this.updateCupsPanel(false);

        contentPanel.add(this.cupsPanel, BorderLayout.SOUTH);

        this.add(contentPanel, BorderLayout.CENTER);

        // Create return button
        WoodenButton returnButton = new WoodenButton("Return to Game");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PubPanel.this.returnToGame();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(returnButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Places a bet and starts the game
     */
    private void placeBet() {
        try {
            int betAmount = Integer.parseInt(this.betAmountField.getText());

            if (betAmount <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a positive bet amount.",
                        "Invalid Bet",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (betAmount > this.player.getCoins()) {
                JOptionPane.showMessageDialog(this,
                        "You don't have enough coins for this bet.",
                        "Insufficient Funds",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Set the current bet and update the game state
            this.currentBet = betAmount;
            this.gameInProgress = true;

            // Randomly determine which cup has the dice
            Random random = new Random();
            this.diceUnderCup = random.nextInt(3); // 0, 1, or 2

            // Update the cups panel to show selectable cups
            this.updateCupsPanel(true);

            // Update the result label
            this.resultLabel.setText("Select a cup to guess where the dice is!");

            // Disable the bet field and button while game is in progress
            this.betAmountField.setEnabled(false);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number for your bet.",
                    "Invalid Bet",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Updates the cups panel with either selectable or non-selectable cups
     * @param selectable Whether the cups should be selectable
     */
    private void updateCupsPanel(boolean selectable) {
        this.cupsPanel.removeAll();

        for (int i = 0; i < 3; i++) {
            final int cupIndex = i;
            JPanel cupPanel = new JPanel(new BorderLayout());
            cupPanel.setOpaque(false);

            JLabel cupLabel = new JLabel("Cup " + (i + 1), JLabel.CENTER);
            cupLabel.setForeground(Color.WHITE);
            cupPanel.add(cupLabel, BorderLayout.NORTH);

            JButton cupButton = new JButton();
            // Use minimum size instead of preferred size for better responsiveness
            cupButton.setMinimumSize(new Dimension(60, 80));
            // Set maximum size to prevent cups from becoming too large
            cupButton.setMaximumSize(new Dimension(80, 100));
            cupButton.setBackground(new Color(150, 100, 50)); // Brown cup color
            cupButton.setBorder(BorderFactory.createRaisedBevelBorder());

            if (selectable) {
                cupButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PubPanel.this.selectCup(cupIndex);
                    }
                });
                cupButton.setEnabled(true);
            } else {
                cupButton.setEnabled(false);
            }

            cupPanel.add(cupButton, BorderLayout.CENTER);
            this.cupsPanel.add(cupPanel);
        }

        this.cupsPanel.revalidate();
        this.cupsPanel.repaint();
    }

    /**
     * Handles the selection of a cup
     * @param cupIndex The index of the selected cup (0, 1, or 2)
     */
    private void selectCup(int cupIndex) {
        if (!this.gameInProgress) {
            return;
        }

        boolean won = (cupIndex == this.diceUnderCup);

        if (won) {
            // Player won - add twice the bet amount
            this.player.setCoins(this.player.getCoins() + this.currentBet);
            this.resultLabel.setText("You won! The dice was under Cup " + (this.diceUnderCup + 1) + "!");
            this.resultLabel.setForeground(Color.GREEN);
        } else {
            // Player lost - subtract the bet amount
            this.player.setCoins(this.player.getCoins() - this.currentBet);
            this.resultLabel.setText("You lost! The dice was under Cup " + (this.diceUnderCup + 1) + ".");
            this.resultLabel.setForeground(Color.RED);
        }

        // Update player coins label
        this.playerCoinsLabel.setText("Your coins: " + this.player.getCoins());

        // Reset game state
        this.gameInProgress = false;
        this.currentBet = 0;

        // Show all cups with the dice revealed
        this.revealCups();

        // Re-enable the bet field
        this.betAmountField.setEnabled(true);
    }

    /**
     * Reveals all cups, showing which one had the dice
     */
    private void revealCups() {
        this.cupsPanel.removeAll();

        for (int i = 0; i < 3; i++) {
            JPanel cupPanel = new JPanel(new BorderLayout());
            cupPanel.setOpaque(false);

            JLabel cupLabel = new JLabel("Cup " + (i + 1), JLabel.CENTER);
            cupLabel.setForeground(Color.WHITE);
            cupPanel.add(cupLabel, BorderLayout.NORTH);

            JButton cupButton = new JButton();
            // Use minimum size instead of preferred size for better responsiveness
            cupButton.setMinimumSize(new Dimension(60, 80));
            // Set maximum size to prevent cups from becoming too large
            cupButton.setMaximumSize(new Dimension(80, 100));
            cupButton.setBackground(new Color(150, 100, 50)); // Brown cup color
            cupButton.setBorder(BorderFactory.createRaisedBevelBorder());
            cupButton.setEnabled(false);

            // Show dice under the correct cup
            if (i == this.diceUnderCup) {
                cupButton.setText("DICE");
                cupButton.setFont(new Font("Arial", Font.BOLD, 14));
            }

            cupPanel.add(cupButton, BorderLayout.CENTER);
            this.cupsPanel.add(cupPanel);
        }

        // Add a "Play Again" button
        JPanel playAgainPanel = new JPanel(new BorderLayout());
        playAgainPanel.setOpaque(false);

        WoodenButton playAgainButton = new WoodenButton("Play Again");
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PubPanel.this.updateCupsPanel(false);
                PubPanel.this.resultLabel.setText("Place a bet to start the game!");
                PubPanel.this.resultLabel.setForeground(Color.WHITE);
            }
        });

        playAgainPanel.add(playAgainButton, BorderLayout.CENTER);
        PubPanel.this.cupsPanel.add(playAgainPanel);

        PubPanel.this.cupsPanel.revalidate();
        PubPanel.this.cupsPanel.repaint();
    }

    /**
     * Return to the game panel
     */
    private void returnToGame() {
        Container parent = this.getParent();

        if (parent != null) {
            parent.remove(this);
            parent.add(this.parentPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();

            // Reset pub open state so it can be reopened
            if (this.parentPanel instanceof GamePanel) {
                ((GamePanel)this.parentPanel).resetPubOpenState();
            }

            // Request focus for the game panel to receive key events
            this.parentPanel.requestFocusInWindow();
        }
    }
}
