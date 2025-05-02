package mesko.matus.gui;

import mesko.matus.items.Item;
import mesko.matus.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Panel to display player stats and inventory
 * Shown when player presses 'I' key in game
 */
public class InventoryPanel extends JPanel {
    private Player player;
    private JPanel parentPanel;
    private JPanel statsPanel;
    private JPanel inventoryPanel;

    /**
     * Creates a new inventory panel
     * @param player The player whose stats and inventory to display
     * @param parentPanel The parent panel to return to when closed
     */
    public InventoryPanel(Player player, JPanel parentPanel) {
        this.player = player;
        this.parentPanel = parentPanel;
        
        setLayout(new BorderLayout());
        setBackground(new Color(200, 180, 150));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Player Inventory");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setOpaque(false);
        
        // Stats panel
        createStatsPanel();
        contentPanel.add(statsPanel);
        
        // Inventory panel
        createInventoryPanel();
        contentPanel.add(inventoryPanel);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Close button
        JButton closeButton = new JButton("Close Inventory");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Return to parent panel
                Container parent = InventoryPanel.this.getParent();
                if (parent != null) {
                    parent.remove(InventoryPanel.this);
                    parent.add(parentPanel, BorderLayout.CENTER);
                    parent.revalidate();
                    parent.repaint();
                    
                    // Request focus for the game panel to receive key events
                    parentPanel.requestFocusInWindow();
                }
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Creates the panel displaying player stats
     */
    private void createStatsPanel() {
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Player Stats"));
        statsPanel.setOpaque(false);
        
        // Hero name
        JLabel nameLabel = new JLabel("Hero: " + player.getHero().getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(nameLabel);
        statsPanel.add(Box.createVerticalStrut(10));
        
        // Stats
        JLabel healthLabel = new JLabel("Health: " + player.getHealth());
        JLabel powerLabel = new JLabel("Power: " + player.getPower());
        JLabel intelligenceLabel = new JLabel("Intelligence: " + player.getIntelligence());
        JLabel luckLabel = new JLabel("Luck: " + player.getLuck());
        JLabel coinsLabel = new JLabel("Coins: " + player.getCoins());
        
        statsPanel.add(healthLabel);
        statsPanel.add(Box.createVerticalStrut(5));
        statsPanel.add(powerLabel);
        statsPanel.add(Box.createVerticalStrut(5));
        statsPanel.add(intelligenceLabel);
        statsPanel.add(Box.createVerticalStrut(5));
        statsPanel.add(luckLabel);
        statsPanel.add(Box.createVerticalStrut(5));
        statsPanel.add(coinsLabel);
    }
    
    /**
     * Creates the panel displaying player inventory
     */
    private void createInventoryPanel() {
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Inventory Items"));
        inventoryPanel.setOpaque(false);
        
        // Get player items
        ArrayList<Item> items = player.getInventory().getItems();
        
        // Create item slots (6 max)
        for (int i = 0; i < 6; i++) {
            JPanel itemSlot = new JPanel();
            itemSlot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            itemSlot.setPreferredSize(new Dimension(80, 80));
            
            if (i < items.size() && items.get(i) != null) {
                // Display item
                Item item = items.get(i);
                JLabel itemLabel = new JLabel(item.getName());
                itemLabel.setHorizontalAlignment(JLabel.CENTER);
                itemSlot.add(itemLabel);
            } else {
                // Empty slot
                itemSlot.setBackground(new Color(220, 220, 220));
            }
            
            inventoryPanel.add(itemSlot);
        }
    }
}