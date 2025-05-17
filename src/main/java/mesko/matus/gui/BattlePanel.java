package mesko.matus.gui;

import mesko.matus.monster.Monster;
import mesko.matus.player.Player;
import mesko.matus.ui.WoodenButton;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

/**
 * Panel for battle between player and monster
 * Handles combat mechanics, health updates, and battle outcomes
 */
public class BattlePanel extends JPanel {
    private final Player player;
    private final Monster monster;
    private final JPanel parentPanel;

    private JLabel playerHealthLabel;
    private JLabel monsterHealthLabel;
    private boolean battleEnded = false;

    public BattlePanel(Player player, Monster monster, JPanel parentPanel) {
        this.player = player;
        this.monster = monster;
        this.parentPanel = parentPanel;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(40, 40, 40));

        JLabel titleLabel = new JLabel("Battle: " + this.player.getHero().getName() + " vs " + this.monster.getName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel combatantsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        combatantsPanel.setOpaque(false);


        JPanel playerPanel = this.createCharacterPanel(true);
        combatantsPanel.add(playerPanel);


        JPanel monsterPanel = this.createCharacterPanel(false);
        combatantsPanel.add(monsterPanel);

        this.add(combatantsPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        WoodenButton attackButton = new WoodenButton("Attack");
        attackButton.addActionListener(this::handleAttack);

        WoodenButton fleeButton = new WoodenButton("Flee");
        fleeButton.addActionListener(this::handleFlee);

        buttonPanel.add(attackButton);
        buttonPanel.add(fleeButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a panel for displaying character information (player or monster)
     * 
     * @param isPlayer True if creating panel for player, false for monster
     * @return The created character panel
     */
    private JPanel createCharacterPanel(boolean isPlayer) {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBackground(new Color(60, 60, 60));
        panel.setBorder(BorderFactory.createLineBorder(isPlayer ? Color.BLUE : Color.RED, 2));

        JLabel nameLabel = new JLabel(isPlayer ? this.player.getHero().getName() : this.monster.getName(), JLabel.CENTER);
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);

        JLabel healthLabel = new JLabel("Health: " + (isPlayer ? this.player.getHealth() : this.monster.getHealth()), JLabel.CENTER);
        healthLabel.setForeground(Color.GREEN);
        panel.add(healthLabel);

        if (isPlayer) {
            this.playerHealthLabel = healthLabel;
            String stat = this.player.getHero().getName().equals("Wizard") ?
                    "Intelligence: " + this.player.getIntelligence() : "Power: " + this.player.getPower();
            JLabel statLabel = new JLabel(stat, JLabel.CENTER);
            statLabel.setForeground(Color.YELLOW);
            panel.add(statLabel);
        } else {
            this.monsterHealthLabel = healthLabel;
            JLabel rewardLabel = new JLabel("Reward: " + this.monster.getRewardCoins() + " coins", JLabel.CENTER);
            rewardLabel.setForeground(Color.YELLOW);
            panel.add(rewardLabel);
        }

        return panel;
    }


    /**
     * Handles the attack action when the attack button is clicked
     * 
     * @param e The action event
     */
    private void handleAttack(ActionEvent e) {
        if (this.battleEnded) {
            return;
        }

        this.player.getHero().useAbility(this.player);

        int damage = this.calculateDamage(this.player.getHero().getName().equals("Wizard") ?
                this.player.getIntelligence() : this.player.getPower());

        this.monster.setHealth(this.monster.getHealth() - damage);
        this.updateMonsterHealth();

        if (this.monster.getHealth() <= 0) {
            this.monsterDefeated();
            return;
        }

        int monsterDamage = this.calculateDamage(this.monster.getPower());
        this.player.setHealth(this.player.getHealth() - monsterDamage);
        this.updatePlayerHealth();

        if (this.player.getHealth() <= 0) {
            this.playerDefeated();
        }
    }

    /**
     * Handles the flee action when the flee button is clicked
     * 
     * @param e The action event
     */
    private void handleFlee(ActionEvent e) {
        if (this.battleEnded) {
            return;
        }
        this.fleeBattle();
    }

    /**
     * Calculates damage based on a stat value (power or intelligence)
     * 
     * @param statValue The stat value to base damage on
     * @return The calculated damage amount
     */
    private int calculateDamage(int statValue) {
        return statValue / 2 + (int)(Math.random() * (statValue / 2));
    }

    /**
     * Updates the player's health display
     * Changes color to red if health is low
     */
    private void updatePlayerHealth() {
        this.playerHealthLabel.setText("Health: " + this.player.getHealth());
        if (this.player.getHealth() < 50) {
            this.playerHealthLabel.setForeground(Color.RED);
        }
    }

    /**
     * Updates the monster's health display
     * Changes color to red if health is low
     */
    private void updateMonsterHealth() {
        this.monsterHealthLabel.setText("Health: " + this.monster.getHealth());
        if (this.monster.getHealth() < 50) {
            this.monsterHealthLabel.setForeground(Color.RED);
        }
    }

    /**
     * Handles the monster being defeated
     * Awards coins to the player and marks the monster as defeated
     */
    private void monsterDefeated() {
        this.battleEnded = true;

        int coins = this.monster.getRewardCoins();
        this.player.setCoins(this.player.getCoins() + coins);

        this.player.defeatMonster(this.monster);

        JOptionPane.showMessageDialog(this,
                "Victory! You earned " + coins + " coins!",
                "Battle Won",
                JOptionPane.INFORMATION_MESSAGE);

        this.returnToDungeon();
    }

    /**
     * Handles the player being defeated
     * Shows a dialog asking if the player wants to play again
     */
    private void playerDefeated() {
        this.battleEnded = true;

        int choice = JOptionPane.showConfirmDialog(this,
                "You were defeated! Do you want to play again?",
                "Battle Lost",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            this.restartGame();
        } else {
            System.exit(0);
        }
    }

    /**
     * Restarts the game by returning to the hero selection screen
     */
    private void restartGame() {
        Container topLevelContainer = this.getTopLevelAncestor();

        if (topLevelContainer != null) {
            HeroSelectionPanel heroSelectionPanel = new HeroSelectionPanel();

            if (topLevelContainer instanceof JFrame frame) {
                frame.getContentPane().removeAll();

                frame.getContentPane().add(heroSelectionPanel, BorderLayout.CENTER);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();

                heroSelectionPanel.requestFocusInWindow();
            }
        }
    }

    /**
     * Attempts to flee from battle
     * Has a 50% chance of success
     * If flee fails, monster gets a free attack
     */
    private void fleeBattle() {
        if (Math.random() < 0.5) {
            JOptionPane.showMessageDialog(this,
                    "You fled from battle!",
                    "Escaped",
                    JOptionPane.INFORMATION_MESSAGE);
            this.returnToDungeon();
        } else {
            int damage = this.calculateDamage(this.monster.getPower());
            this.player.setHealth(this.player.getHealth() - damage);
            this.updatePlayerHealth();

            if (this.player.getHealth() <= 0) {
                this.playerDefeated();
            }
        }
    }

    /**
     * Returns to the dungeon panel after battle
     */
    private void returnToDungeon() {
        Container parent = this.getParent();
        if (parent != null) {
            parent.remove(this);
            parent.add(this.parentPanel);
            parent.revalidate();
            parent.repaint();
            this.parentPanel.requestFocusInWindow();
        }
    }
}
