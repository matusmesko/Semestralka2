package mesko.matus.gui;

import mesko.matus.monster.Monster;
import mesko.matus.monster.impl.Zombie;
import mesko.matus.monster.impl.Skeleton;
import mesko.matus.monster.impl.Dragon;
import mesko.matus.player.Player;
import mesko.matus.ui.WoodenButton;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel for selecting monsters to fight in the dungeon
 */
public class DungeonPanel extends JPanel {
    private Player player;
    private JPanel parentPanel;
    private List<Monster> monsters;

    /**
     * Creates a new dungeon panel with the player and parent panel
     * @param player The player
     * @param parentPanel The parent panel to return to
     */
    public DungeonPanel(Player player, JPanel parentPanel) {
        this.player = player;
        this.parentPanel = parentPanel;
        this.monsters = new ArrayList<>();

        this.monsters.add(new Zombie());
        this.monsters.add(new Skeleton());
        this.monsters.add(new Dragon());

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(50, 50, 50));

        JLabel titleLabel = new JLabel("Dungeon", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel monsterPanel = new JPanel(new GridLayout(this.monsters.size(), 1, 10, 10));
        monsterPanel.setOpaque(false);
        monsterPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        for (Monster monster : this.monsters) {
            JPanel monsterItemPanel = this.createMonsterPanel(monster);
            monsterPanel.add(monsterItemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(monsterPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        WoodenButton returnButton = new WoodenButton("Return to Game");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DungeonPanel.this.returnToGame();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(returnButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a panel for a monster with its information and a fight button
     * @param monster The monster to create a panel for
     * @return The monster panel
     */
    private JPanel createMonsterPanel(Monster monster) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(70, 70, 70));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(monster.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);

        JLabel statsLabel = new JLabel("Health: " + monster.getHealth() + " | Reward: " + monster.getRewardCoins() + " coins", JLabel.CENTER);
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statsLabel.setForeground(Color.WHITE);

        infoPanel.add(nameLabel);
        infoPanel.add(statsLabel);
        panel.add(infoPanel, BorderLayout.CENTER);

        boolean isAccessible = this.canFightMonster(monster);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        if (isAccessible) {
            WoodenButton fightButton = new WoodenButton("Fight!", 300, 100, 15);
            fightButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DungeonPanel.this.startBattle(monster);
                }
            });
            buttonPanel.add(fightButton);
        } else {
            JLabel lockedLabel = new JLabel("LOCKED", JLabel.CENTER);
            lockedLabel.setFont(new Font("Arial", Font.BOLD, 16));
            lockedLabel.setForeground(Color.RED);
            buttonPanel.add(lockedLabel);

            String requiredMonster = this.getRequiredMonsterName(monster);
            if (requiredMonster != null) {
                JLabel requirementLabel = new JLabel("Defeat " + requiredMonster + " first", JLabel.CENTER);
                requirementLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                requirementLabel.setForeground(Color.YELLOW);
                buttonPanel.setLayout(new GridLayout(2, 1));
                buttonPanel.add(requirementLabel);
            }
        }

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Determines if a monster can be fought based on progression
     * @param monster The monster to check
     * @return true if the monster can be fought, false otherwise
     */
    private boolean canFightMonster(Monster monster) {
        String monsterName = monster.getName();

        if (monsterName.equals("Zombie")) {
            return true;
        }

        if (monsterName.equals("Skeleton")) {
            return this.player.hasDefeatedMonster("Zombie");
        }

        if (monsterName.equals("Dragon")) {
            return this.player.hasDefeatedMonster("Skeleton");
        }

        return true;
    }

    /**
     * Gets the name of the monster that needs to be defeated before the given monster
     * @param monster The monster to check
     * @return The name of the required monster, or null if none
     */
    private String getRequiredMonsterName(Monster monster) {
        String monsterName = monster.getName();

        if (monsterName.equals("Skeleton")) {
            return "Zombie";
        }

        if (monsterName.equals("Dragon")) {
            return "Skeleton";
        }

        return null;
    }

    /**
     * Starts a battle with the selected monster
     * @param monster The monster to fight
     */
    private void startBattle(Monster monster) {
        Container parent = this.getParent();

        BattlePanel battlePanel = new BattlePanel(this.player, monster, this);
        if (parent != null) {
            parent.remove(this);
            parent.add(battlePanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
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
            if (this.parentPanel instanceof GamePanel) {
                ((GamePanel)this.parentPanel).resetDungeonOpenState();
            }
            this.parentPanel.requestFocusInWindow();
        }
    }

    /**
     * Refreshes all panels to reflect the current state
     */
    public void refreshPanels() {
        Container parent = this.getParent();
        if (parent != null) {
            parent.remove(this);
            DungeonPanel newPanel = new DungeonPanel(this.player, this.parentPanel);
            parent.add(newPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
}
