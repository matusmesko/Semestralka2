package mesko.matus.gui;

import mesko.matus.player.Player;
import mesko.matus.ui.WoodenButton;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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

    private int diceUnderCup;
    private boolean gameStarted;
    private int currentBet;

    /**
     * Constructs a new PubPanel instance with the specified player and parent panel.
     * Initializes the GUI components and layout for the cup game.
     *
     * @param player      The player object managing the game state and coins.
     * @param parentPanel The parent panel to return to when exiting the pub game.
     */
    public PubPanel(Player player, JPanel parentPanel) {
        this.player = player;
        this.parentPanel = parentPanel;
        this.currentBet = 0;
        this.gameStarted = false;

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(100, 70, 30));
        this.setPreferredSize(new Dimension(800, 700));

        this.add(this.createTitlePanel(), BorderLayout.NORTH);
        this.add(this.createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(70, 40, 10));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Pub", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(255, 215, 0));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        WoodenButton returnButton = new WoodenButton("Return to Game");
        returnButton.setFont(new Font("Arial", Font.BOLD, 18));
        returnButton.setPreferredSize(new Dimension(200, 50));
        returnButton.addActionListener(e -> PubPanel.this.returnToGame());
        titlePanel.add(returnButton, BorderLayout.EAST);
        return titlePanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        contentPanel.add(this.createPlayerInfoPanel(), BorderLayout.NORTH);
        contentPanel.add(this.createGameAreaPanel(), BorderLayout.CENTER);
        contentPanel.add(this.createBettingPanel(), BorderLayout.SOUTH);

        return contentPanel;
    }

    private JPanel createPlayerInfoPanel() {
        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setOpaque(false);
        playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        this.playerCoinsLabel = new JLabel("Your coins: " + this.player.getCoins(), JLabel.CENTER);
        this.playerCoinsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.playerCoinsLabel.setForeground(Color.YELLOW);

        playerInfoPanel.add(this.playerCoinsLabel);
        return playerInfoPanel;
    }

    private JPanel createGameAreaPanel() {
        JPanel gameAreaPanel = new JPanel(new BorderLayout(0, 20));
        gameAreaPanel.setOpaque(false);

        gameAreaPanel.add(this.createResultLabel(), BorderLayout.NORTH);
        gameAreaPanel.add(this.createCupsPanel(), BorderLayout.CENTER);

        return gameAreaPanel;
    }

    private JLabel createResultLabel() {
        this.resultLabel = new JLabel("Place a bet to start the game", JLabel.CENTER);
        this.resultLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        this.resultLabel.setForeground(Color.WHITE);
        return this.resultLabel;
    }

    private JPanel createCupsPanel() {
        this.cupsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        this.cupsPanel.setOpaque(false);
        this.cupsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.updateCupsPanel(false);
        return this.cupsPanel;
    }

    private JPanel createBettingPanel() {
        JPanel bettingPanel = new JPanel();
        bettingPanel.setOpaque(false);
        bettingPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        bettingPanel.add(this.createBetLabel());
        bettingPanel.add(this.createBetField());
        bettingPanel.add(this.createPlaceBetButton());

        return bettingPanel;
    }

    private JLabel createBetLabel() {
        JLabel betLabel = new JLabel("Your bet: ");
        betLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        betLabel.setForeground(Color.WHITE);
        return betLabel;
    }

    private JTextField createBetField() {
        this.betAmountField = new JTextField(6);
        this.betAmountField.setFont(new Font("Arial", Font.PLAIN, 18));
        this.betAmountField.setText("10");
        this.betAmountField.setHorizontalAlignment(JTextField.CENTER);
        return this.betAmountField;
    }

    private WoodenButton createPlaceBetButton() {
        WoodenButton placeBetButton = new WoodenButton("Place Bet");
        placeBetButton.setFont(new Font("Arial", Font.BOLD, 16));
        placeBetButton.setPreferredSize(new Dimension(150, 40));
        placeBetButton.addActionListener(e -> PubPanel.this.placeBet());
        return placeBetButton;
    }

    /**
     * Handles the placement of a bet by the player.
     * Validates the bet amount, checks if the player has sufficient coins,
     * and initializes the game by randomly placing the dice under one of the cups.
     */
    private void placeBet() {
        try {
            int betAmount = Integer.parseInt(this.betAmountField.getText());

            if (betAmount <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a positive bet amount",
                        "Invalid Bet",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (betAmount > this.player.getCoins()) {
                JOptionPane.showMessageDialog(this,
                        "You don't have enough coins for this bet",
                        "Insufficient Funds",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            this.currentBet = betAmount;
            this.gameStarted = true;
            Random random = new Random();
            this.diceUnderCup = random.nextInt(3);
            this.updateCupsPanel(true);
            this.resultLabel.setText("Select a cup to guess where the dice is");
            this.resultLabel.setForeground(Color.WHITE);
            this.betAmountField.setEnabled(false);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number for your bet",
                    "Invalid Bet",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Updates the display of the cups panel.
     * If the game is in progress, the cups are made selectable by the player.
     * Otherwise, the cups are displayed in a default state.
     *
     * @param selectable True if the cups should be clickable, false otherwise.
     */
    private void updateCupsPanel(boolean selectable) {
        this.cupsPanel.removeAll();

        for (int i = 0; i < 3; i++) {
            final int cupIndex = i;
            JPanel cupPanel = new JPanel(new BorderLayout(0, 10));
            cupPanel.setOpaque(false);

            JLabel cupLabel = new JLabel("Cup " + (i + 1), JLabel.CENTER);
            cupLabel.setFont(new Font("Arial", Font.BOLD, 20));
            cupLabel.setForeground(Color.WHITE);
            cupPanel.add(cupLabel, BorderLayout.NORTH);

            JButton cupButton = new JButton();
            cupButton.setPreferredSize(new Dimension(180, 250));
            cupButton.setBackground(new Color(150, 100, 50));
            cupButton.setBorder(BorderFactory.createRaisedBevelBorder());
            cupButton.setFont(new Font("Arial", Font.BOLD, 18));

            if (selectable) {
                cupButton.addActionListener(e -> PubPanel.this.selectCup(cupIndex));
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
     * Handles the player's selection of a cup.
     * Checks if the selected cup contains the dice, updates the player's coins,
     * displays the result, and reveals the position of the dice.
     *
     * @param cupIndex The index of the selected cup (0-based).
     */
    private void selectCup(int cupIndex) {
        if (!this.gameStarted) {
            return;
        }

        boolean won = (cupIndex == this.diceUnderCup);

        if (won) {
            this.player.setCoins(this.player.getCoins() + this.currentBet * 2);
            this.resultLabel.setText("You won " + this.currentBet * 2 + " coins");
            this.resultLabel.setForeground(Color.GREEN);
        } else {
            this.player.setCoins(this.player.getCoins() - this.currentBet);
            this.resultLabel.setText("You lost " + this.currentBet + " coins");
            this.resultLabel.setForeground(Color.RED);
        }

        this.playerCoinsLabel.setText("Your coins: " + this.player.getCoins());
        this.gameStarted = false;
        this.currentBet = 0;
        this.revealCups();
        this.betAmountField.setEnabled(true);
    }

    /**
     * Reveals the position of the dice under the cups after the game ends.
     * Updates the cups panel to show which cup contained the dice.
     */
    private void revealCups() {
        this.cupsPanel.removeAll();

        for (int i = 0; i < 3; i++) {
            JPanel cupPanel = new JPanel(new BorderLayout(0, 10));
            cupPanel.setOpaque(false);

            JLabel cupLabel = new JLabel("Cup " + (i + 1), JLabel.CENTER);
            cupLabel.setFont(new Font("Arial", Font.BOLD, 20));
            cupLabel.setForeground(Color.WHITE);
            cupPanel.add(cupLabel, BorderLayout.NORTH);

            JButton cupButton = new JButton();
            cupButton.setPreferredSize(new Dimension(180, 250));
            cupButton.setBackground(new Color(150, 100, 50));
            cupButton.setEnabled(false);
            cupButton.setFont(new Font("Arial", Font.BOLD, 24));

            if (i == this.diceUnderCup) {
                cupButton.setText("DICE");
                cupButton.setForeground(Color.YELLOW);
            }

            cupPanel.add(cupButton, BorderLayout.CENTER);
            this.cupsPanel.add(cupPanel);
        }

        this.cupsPanel.revalidate();
        this.cupsPanel.repaint();
    }

    /**
     * Navigates back to the main game panel.
     * Removes this panel from the container and redisplays the parent panel.
     */
    private void returnToGame() {
        Container parent = this.getParent();
        if (parent != null) {
            parent.remove(this);
            parent.add(this.parentPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
            if (this.parentPanel instanceof GamePanel) {
                ((GamePanel)this.parentPanel).resetPubOpenState();
            }
            this.parentPanel.requestFocusInWindow();
        }
    }
}
