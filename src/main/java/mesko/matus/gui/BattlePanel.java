package mesko.matus.gui;

import mesko.matus.monster.Monster;
import mesko.matus.player.Player;
import mesko.matus.ui.WoodenButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BattlePanel extends JPanel {
    private final Player player;
    private final Monster monster;
    private final JPanel parentPanel;

    // UI Components
    private JLabel playerHealthLabel;
    private JLabel monsterHealthLabel;
    private JTextArea battleLogArea;
    private boolean battleEnded = false;

    public BattlePanel(Player player, Monster monster, JPanel parentPanel) {
        this.player = player;
        this.monster = monster;
        this.parentPanel = parentPanel;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40));

        // Title Panel
        JLabel titleLabel = new JLabel("Battle: " + player.getHero().getName() + " vs " + monster.getName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Combatants Panel
        JPanel combatantsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        combatantsPanel.setOpaque(false);

        // Player Panel
        JPanel playerPanel = createCharacterPanel(player, true);
        combatantsPanel.add(playerPanel);

        // Monster Panel
        JPanel monsterPanel = createCharacterPanel(monster, false);
        combatantsPanel.add(monsterPanel);

        add(combatantsPanel, BorderLayout.CENTER);


        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        WoodenButton attackButton = new WoodenButton("Attack");
        attackButton.addActionListener(this::handleAttack);

        WoodenButton fleeButton = new WoodenButton("Flee");
        fleeButton.addActionListener(this::handleFlee);

        buttonPanel.add(attackButton);
        buttonPanel.add(fleeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createCharacterPanel(Object character, boolean isPlayer) {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBackground(new Color(60, 60, 60));
        panel.setBorder(BorderFactory.createLineBorder(isPlayer ? Color.BLUE : Color.RED, 2));

        JLabel nameLabel = new JLabel(isPlayer ? player.getHero().getName() : monster.getName(), JLabel.CENTER);
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);

        JLabel healthLabel = new JLabel("Health: " + (isPlayer ? player.getHealth() : monster.getHealth()), JLabel.CENTER);
        healthLabel.setForeground(Color.GREEN);
        panel.add(healthLabel);

        if (isPlayer) {
            playerHealthLabel = healthLabel;
            String stat = player.getHero().getName().equals("Wizard") ?
                    "Intelligence: " + player.getIntelligence() : "Power: " + player.getPower();
            JLabel statLabel = new JLabel(stat, JLabel.CENTER);
            statLabel.setForeground(Color.YELLOW);
            panel.add(statLabel);
        } else {
            monsterHealthLabel = healthLabel;
            JLabel rewardLabel = new JLabel("Reward: " + monster.getRewardCoins() + " coins", JLabel.CENTER);
            rewardLabel.setForeground(Color.YELLOW);
            panel.add(rewardLabel);
        }

        return panel;
    }


    private void handleAttack(ActionEvent e) {
        if (battleEnded) return;

        // Player attack
        int damage = calculateDamage(player.getHero().getName().equals("Wizard") ?
                player.getIntelligence() : player.getPower());

        String attackDesc = player.getHero().getName().equals("Wizard") ?
                "You cast a spell at the " + monster.getName() + " for " + damage + " damage!" :
                "You strike the " + monster.getName() + " for " + damage + " damage!";

        monster.setHealth(monster.getHealth() - damage);
        updateMonsterHealth();

        if (monster.getHealth() <= 0) {
            monsterDefeated();
            return;
        }

        // Monster counterattack
        int monsterDamage = calculateDamage(monster.getPower());
        player.setHealth(player.getHealth() - monsterDamage);
        updatePlayerHealth();

        if (player.getHealth() <= 0) {
            playerDefeated();
        }
    }

    private void handleFlee(ActionEvent e) {
        if (battleEnded) return;
        fleeBattle();
    }

    private int calculateDamage(int statValue) {
        return statValue / 2 + (int)(Math.random() * (statValue / 2));
    }

    private void updatePlayerHealth() {
        playerHealthLabel.setText("Health: " + player.getHealth());
        if (player.getHealth() < 50) {
            playerHealthLabel.setForeground(Color.RED);
        }
    }

    private void updateMonsterHealth() {
        monsterHealthLabel.setText("Health: " + monster.getHealth());
        if (monster.getHealth() < 50) {
            monsterHealthLabel.setForeground(Color.RED);
        }
    }

    private void monsterDefeated() {
        battleEnded = true;

        int coins = monster.getRewardCoins();
        player.setCoins(player.getCoins() + coins);

        // Mark the monster as defeated
        player.defeatMonster(monster);

        JOptionPane.showMessageDialog(this,
                "Victory! You earned " + coins + " coins!",
                "Battle Won",
                JOptionPane.INFORMATION_MESSAGE);

        returnToDungeon();
    }

    private void playerDefeated() {
        battleEnded = true;

        int choice = JOptionPane.showConfirmDialog(this,
                "You were defeated! Do you want to play again?",
                "Battle Lost",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            // Player chose to play again, restart the game
            restartGame();
        } else {
            // Player chose to end the game
            System.exit(0);
        }
    }

    /**
     * Restarts the game by returning to the hero selection screen
     */
    private void restartGame() {
        // Get the top-level container (JFrame)
        Container topLevelContainer = this.getTopLevelAncestor();

        if (topLevelContainer != null) {
            // Create a new hero selection panel
            HeroSelectionPanel heroSelectionPanel = new HeroSelectionPanel();

            // Remove all components from the top-level container
            if (topLevelContainer instanceof JFrame) {
                JFrame frame = (JFrame) topLevelContainer;
                frame.getContentPane().removeAll();

                // Add the hero selection panel
                frame.getContentPane().add(heroSelectionPanel, BorderLayout.CENTER);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();

                // Request focus for the hero selection panel
                heroSelectionPanel.requestFocusInWindow();
            }
        }
    }

    private void fleeBattle() {


        if (Math.random() < 0.5) {
            JOptionPane.showMessageDialog(this,
                    "You fled from battle!",
                    "Escaped",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {

            // Monster gets a free attack if flee fails
            int damage = calculateDamage(monster.getPower());
            player.setHealth(player.getHealth() - damage);
            updatePlayerHealth();

            if (player.getHealth() <= 0) {
                playerDefeated();
            }
        }
    }


    private void returnToDungeon() {
        Container parent = getParent();
        if (parent != null) {
            parent.remove(this);
            parent.add(parentPanel);
            parent.revalidate();
            parent.repaint();
            parentPanel.requestFocusInWindow();
        }
    }
}
