package mesko.matus.gui;

import mesko.matus.monster.Monster;
import mesko.matus.player.Player;
import mesko.matus.ui.WoodenButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Panel for battling monsters in the dungeon
 */
public class BattlePanel extends JPanel {
    private Player player;
    private Monster monster;
    private JPanel parentPanel;
    private JLabel playerHealthLabel;
    private JLabel monsterHealthLabel;
    private JTextArea battleLogArea;
    private Random random = new Random();
    private boolean battleEnded = false;

    /**
     * Creates a new battle panel with the player, monster, and parent panel
     * @param player The player
     * @param monster The monster to fight
     * @param parentPanel The parent panel to return to
     */
    public BattlePanel(Player player, Monster monster, JPanel parentPanel) {
        this.player = player;
        this.monster = monster;
        this.parentPanel = parentPanel;
        
        // Create a fresh instance of the monster
        if (monster.getName().equals("Zombie")) {
            this.monster = new mesko.matus.monster.impl.Zombie();
        } else if (monster.getName().equals("Skeleton")) {
            this.monster = new mesko.matus.monster.impl.Skeleton();
        } else if (monster.getName().equals("Dragon")) {
            this.monster = new mesko.matus.monster.impl.Dragon();
        }
        
        // Set up the panel
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));
        
        // Create title
        JLabel titleLabel = new JLabel("Battle: " + player.getHero().getName() + " vs " + monster.getName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Create battle panel
        JPanel battlePanel = new JPanel(new BorderLayout());
        battlePanel.setOpaque(false);
        battlePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        
        // Create stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setOpaque(false);
        
        // Player stats
        JPanel playerStatsPanel = createStatsPanel("Player", true);
        statsPanel.add(playerStatsPanel);
        
        // Monster stats
        JPanel monsterStatsPanel = createStatsPanel("Monster", false);
        statsPanel.add(monsterStatsPanel);
        
        battlePanel.add(statsPanel, BorderLayout.NORTH);
        
        // Create battle log
        battleLogArea = new JTextArea(10, 40);
        battleLogArea.setEditable(false);
        battleLogArea.setLineWrap(true);
        battleLogArea.setWrapStyleWord(true);
        battleLogArea.setBackground(new Color(50, 50, 50));
        battleLogArea.setForeground(Color.WHITE);
        battleLogArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        battleLogArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane logScrollPane = new JScrollPane(battleLogArea);
        logScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        battlePanel.add(logScrollPane, BorderLayout.CENTER);
        
        add(battlePanel, BorderLayout.CENTER);
        
        // Create action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        actionPanel.setOpaque(false);
        
        WoodenButton attackButton = new WoodenButton("Attack");
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!battleEnded) {
                    attackMonster();
                }
            }
        });
        
        WoodenButton fleeButton = new WoodenButton("Flee");
        fleeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fleeBattle();
            }
        });
        
        actionPanel.add(attackButton);
        actionPanel.add(fleeButton);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(actionPanel, BorderLayout.SOUTH);
        
        // Add initial battle log message
        addToBattleLog("Battle started! " + player.getHero().getName() + " vs " + monster.getName() + "!");
        addToBattleLog("Your hero type is " + player.getHero().getName() + ".");
        if (player.getHero().getName().equals("Wizard")) {
            addToBattleLog("As a Wizard, your damage is based on Intelligence.");
        } else {
            addToBattleLog("As a Warrior, your damage is based on Power.");
        }
    }
    
    /**
     * Creates a stats panel for either the player or monster
     * @param title The title of the stats panel
     * @param isPlayer Whether this is for the player or monster
     * @return The stats panel
     */
    private JPanel createStatsPanel(String title, boolean isPlayer) {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBackground(new Color(60, 60, 60));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        
        if (isPlayer) {
            JLabel nameLabel = new JLabel("Name: " + player.getHero().getName(), JLabel.CENTER);
            nameLabel.setForeground(Color.WHITE);
            panel.add(nameLabel);
            
            playerHealthLabel = new JLabel("Health: " + player.getHealth(), JLabel.CENTER);
            playerHealthLabel.setForeground(Color.GREEN);
            panel.add(playerHealthLabel);
            
            String statLabel;
            if (player.getHero().getName().equals("Wizard")) {
                statLabel = "Intelligence: " + player.getIntelligence();
            } else {
                statLabel = "Power: " + player.getPower();
            }
            JLabel powerLabel = new JLabel(statLabel, JLabel.CENTER);
            powerLabel.setForeground(Color.YELLOW);
            panel.add(powerLabel);
        } else {
            JLabel nameLabel = new JLabel("Name: " + monster.getName(), JLabel.CENTER);
            nameLabel.setForeground(Color.WHITE);
            panel.add(nameLabel);
            
            monsterHealthLabel = new JLabel("Health: " + monster.getHealth(), JLabel.CENTER);
            monsterHealthLabel.setForeground(Color.GREEN);
            panel.add(monsterHealthLabel);
            
            JLabel rewardLabel = new JLabel("Reward: " + monster.getRewardCoins() + " coins", JLabel.CENTER);
            rewardLabel.setForeground(Color.YELLOW);
            panel.add(rewardLabel);
        }
        
        return panel;
    }
    
    /**
     * Attacks the monster based on player's hero type
     */
    private void attackMonster() {
        // Calculate player damage based on hero type
        int damage = 0;
        if (player.getHero().getName().equals("Wizard")) {
            // Wizard uses intelligence for damage
            damage = calculateDamage(player.getIntelligence());
            addToBattleLog("You cast a spell for " + damage + " damage!");
        } else {
            // Warrior uses power for damage
            damage = calculateDamage(player.getPower());
            addToBattleLog("You swing your weapon for " + damage + " damage!");
        }
        
        // Apply damage to monster
        monster.setHealth(monster.getHealth() - damage);
        updateMonsterHealth();
        
        // Check if monster is defeated
        if (monster.isDefeated()) {
            monsterDefeated();
            return;
        }
        
        // Monster attacks player
        addToBattleLog(monster.getName() + " attacks you!");
        int playerHealth = player.getHealth();
        monster.damagePlayer(player);
        int damageTaken = playerHealth - player.getHealth();
        addToBattleLog("You take " + damageTaken + " damage!");
        updatePlayerHealth();
        
        // Check if player is defeated
        if (player.getHealth() <= 0) {
            playerDefeated();
        }
    }
    
    /**
     * Calculates damage based on a stat value with some randomness
     * @param statValue The stat value to base damage on
     * @return The calculated damage
     */
    private int calculateDamage(int statValue) {
        // Base damage on stat with some randomness
        return statValue / 2 + random.nextInt(statValue / 2);
    }
    
    /**
     * Updates the player health display
     */
    private void updatePlayerHealth() {
        playerHealthLabel.setText("Health: " + player.getHealth());
        if (player.getHealth() < 50) {
            playerHealthLabel.setForeground(Color.RED);
        }
    }
    
    /**
     * Updates the monster health display
     */
    private void updateMonsterHealth() {
        int health = Math.max(0, monster.getHealth());
        monsterHealthLabel.setText("Health: " + health);
        if (health < 50) {
            monsterHealthLabel.setForeground(Color.RED);
        }
    }
    
    /**
     * Handles monster defeat
     */
    private void monsterDefeated() {
        battleEnded = true;
        addToBattleLog("You defeated the " + monster.getName() + "!");
        
        // Award coins to player
        int coins = monster.getRewardCoins();
        player.setCoins(player.getCoins() + coins);
        addToBattleLog("You earned " + coins + " coins!");
        
        // Show victory message
        JOptionPane.showMessageDialog(this,
                "Victory! You defeated the " + monster.getName() + " and earned " + coins + " coins!",
                "Battle Victory",
                JOptionPane.INFORMATION_MESSAGE);
        
        // Return to dungeon panel
        returnToDungeon();
    }
    
    /**
     * Handles player defeat
     */
    private void playerDefeated() {
        battleEnded = true;
        addToBattleLog("You were defeated by the " + monster.getName() + "!");
        
        // Show defeat message
        JOptionPane.showMessageDialog(this,
                "Defeat! You were beaten by the " + monster.getName() + ".",
                "Battle Defeat",
                JOptionPane.WARNING_MESSAGE);
        
        // Return to dungeon panel
        returnToDungeon();
    }
    
    /**
     * Flees from the battle
     */
    private void fleeBattle() {
        addToBattleLog("You fled from the battle!");
        
        // Show flee message
        JOptionPane.showMessageDialog(this,
                "You fled from the battle with the " + monster.getName() + ".",
                "Battle Fled",
                JOptionPane.INFORMATION_MESSAGE);
        
        // Return to dungeon panel
        returnToDungeon();
    }
    
    /**
     * Adds a message to the battle log
     * @param message The message to add
     */
    private void addToBattleLog(String message) {
        battleLogArea.append(message + "\n");
        // Scroll to the bottom
        battleLogArea.setCaretPosition(battleLogArea.getDocument().getLength());
    }
    
    /**
     * Return to the dungeon panel
     */
    private void returnToDungeon() {
        Container parent = this.getParent();
        
        if (parent != null) {
            parent.remove(this);
            parent.add(parentPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
            
            // Request focus for the dungeon panel
            parentPanel.requestFocusInWindow();
        }
    }
}