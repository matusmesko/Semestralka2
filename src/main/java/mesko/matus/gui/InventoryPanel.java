package mesko.matus.gui;

import mesko.matus.items.Item;
import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;
import mesko.matus.player.Player;
import mesko.matus.ui.WoodenButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
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
    private JPanel equippedItemsPanel;

    /**
     * Creates a new inventory panel
     * @param player The player whose stats and inventory to display
     * @param parentPanel The parent panel to return to when closed
     */
    public InventoryPanel(Player player, JPanel parentPanel) {
        this.player = player;
        this.parentPanel = parentPanel;

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(200, 180, 150));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Player Inventory");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleLabel, BorderLayout.NORTH);

        // Main content panel with 2 rows
        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        contentPanel.setOpaque(false);

        // Top row with stats and equipped items
        JPanel topRow = new JPanel(new GridLayout(1, 2, 20, 0));
        topRow.setOpaque(false);

        // Stats panel
        this.createStatsPanel();
        topRow.add(this.statsPanel);

        // Equipped items panel
        this.createEquippedItemsPanel();
        topRow.add(this.equippedItemsPanel);

        contentPanel.add(topRow);

        // Inventory panel in bottom row
        this.createInventoryPanel();
        contentPanel.add(this.inventoryPanel);

        this.add(contentPanel, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new WoodenButton("Close Inventory", 300, 50, 15);
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
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the panel displaying player stats
     */
    private void createStatsPanel() {
        this.statsPanel = new JPanel();
        this.statsPanel.setLayout(new BoxLayout(this.statsPanel, BoxLayout.Y_AXIS));
        this.statsPanel.setBorder(BorderFactory.createTitledBorder("Player Stats"));
        this.statsPanel.setOpaque(false);

        // Hero name
        JLabel nameLabel = new JLabel("Hero: " + this.player.getHero().getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.statsPanel.add(nameLabel);
        this.statsPanel.add(Box.createVerticalStrut(10));

        // Stats
        JLabel healthLabel = new JLabel("Health: " + this.player.getHealth());
        JLabel powerLabel = new JLabel("Power: " + this.player.getPower());
        JLabel intelligenceLabel = new JLabel("Intelligence: " + this.player.getIntelligence());
        JLabel luckLabel = new JLabel("Luck: " + this.player.getLuck());
        JLabel coinsLabel = new JLabel("Coins: " + this.player.getCoins());

        this.statsPanel.add(healthLabel);
        this.statsPanel.add(Box.createVerticalStrut(5));
        this.statsPanel.add(powerLabel);
        this.statsPanel.add(Box.createVerticalStrut(5));
        this.statsPanel.add(intelligenceLabel);
        this.statsPanel.add(Box.createVerticalStrut(5));
        this.statsPanel.add(luckLabel);
        this.statsPanel.add(Box.createVerticalStrut(5));
        this.statsPanel.add(coinsLabel);
    }

    /**
     * Creates the panel displaying equipped items
     */
    private void createEquippedItemsPanel() {
        this.equippedItemsPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        this.equippedItemsPanel.setBorder(BorderFactory.createTitledBorder("Equipped Items"));
        this.equippedItemsPanel.setOpaque(false);

        // Create slots for each wearable item type (HEAD, BODY, LEGS)
        for (WearableItemType type : WearableItemType.values()) {
            JPanel slotPanel = new JPanel(new BorderLayout());
            slotPanel.setBorder(BorderFactory.createTitledBorder(type.name()));
            slotPanel.setPreferredSize(new Dimension(100, 100));

            // Get the equipped item for this slot
            WearableItem equippedItem = this.player.getEquippedItem(type);

            if (equippedItem != null) {
                // Display the equipped item
                this.createItemDisplay(slotPanel, equippedItem);

                // Add click listener to unequip
                slotPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        InventoryPanel.this.player.unequipItem(type);
                        InventoryPanel.this.refreshPanels();
                    }
                });
            } else {
                // Empty slot
                slotPanel.setBackground(new Color(220, 220, 220));
                JLabel emptyLabel = new JLabel("Empty");
                emptyLabel.setHorizontalAlignment(JLabel.CENTER);
                slotPanel.add(emptyLabel, BorderLayout.CENTER);
            }

            this.equippedItemsPanel.add(slotPanel);
        }
    }

    /**
     * Creates the panel displaying player inventory
     */
    private void createInventoryPanel() {
        this.inventoryPanel = new JPanel();
        this.inventoryPanel.setLayout(new GridLayout(3, 2, 10, 10));
        this.inventoryPanel.setBorder(BorderFactory.createTitledBorder("Inventory Items"));
        this.inventoryPanel.setOpaque(false);

        // Get player items
        ArrayList<Item> items = this.player.getInventory().getItems();

        // Create item slots (6 max)
        for (int i = 0; i < 6; i++) {
            JPanel itemSlot = new JPanel(new BorderLayout());
            itemSlot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            itemSlot.setPreferredSize(new Dimension(80, 80));

            if (i < items.size() && items.get(i) != null) {
                // Display item
                Item item = items.get(i);
                this.createItemDisplay(itemSlot, item);

                // Add click listener for item use/equip
                itemSlot.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (item instanceof ConsumableItem consumableItem) {
                                consumableItem.useItem(InventoryPanel.this.player);
                                InventoryPanel.this.player.getInventory().removeItem(consumableItem);
                                InventoryPanel.this.refreshPanels();
                            } else if (item instanceof WearableItem wearableItem) {

                                InventoryPanel.this.player.equipItem(wearableItem);
                                InventoryPanel.this.refreshPanels();
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            InventoryPanel.this.player.getInventory().removeItem(item);
                            InventoryPanel.this.refreshPanels();
                        }

                    }
                });
            } else {
                // Empty slot
                itemSlot.setBackground(new Color(220, 220, 220));
            }

            this.inventoryPanel.add(itemSlot);
        }
    }

    /**
     * Helper method to create an item display in a panel
     * @param panel The panel to add the item display to
     * @param item The item to display
     */
    private void createItemDisplay(JPanel panel, Item item) {
        // Try to load and display the item image
        String imagePath = item.getImagePath();
        if (imagePath != null) {
            try {
                BufferedImage itemImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
                if (itemImage != null) {
                    // Scale the image to fill the entire slot
                    Image scaledImage = itemImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    panel.add(imageLabel, BorderLayout.CENTER);

                    // Display item name as overlay at the bottom of the image
                    JLabel itemLabel = new JLabel(item.getName());
                    itemLabel.setHorizontalAlignment(JLabel.CENTER);
                    itemLabel.setFont(new Font("Arial", Font.BOLD, 10));
                    itemLabel.setForeground(Color.WHITE); // White text for better visibility
                    // Add a dark semi-transparent background for better text readability
                    itemLabel.setOpaque(true);
                    itemLabel.setBackground(new Color(0, 0, 0, 150));
                    panel.add(itemLabel, BorderLayout.SOUTH);
                }
            } catch (IOException e) {
                System.err.println("Failed to load image for item: " + item.getName());

                // If image fails to load, just show the name
                JLabel itemLabel = new JLabel(item.getName());
                itemLabel.setHorizontalAlignment(JLabel.CENTER);
                panel.add(itemLabel, BorderLayout.CENTER);
            }
        } else {
            // No image path, just show the name
            JLabel itemLabel = new JLabel(item.getName());
            itemLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(itemLabel, BorderLayout.CENTER);
        }

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                JPanel parent = (JPanel)e.getSource();
                parent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                parent.revalidate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JPanel parent = (JPanel)e.getSource();
                parent.setCursor(Cursor.getDefaultCursor());
                parent.revalidate();
            }
        });
    }

    /**
     * Refreshes all panels to reflect the current state
     */
    private void refreshPanels() {
        // Get the parent container
        Container parent = this.getParent();

        // Remove this panel
        if (parent != null) {
            parent.remove(this);

            // Create a new inventory panel
            InventoryPanel newPanel = new InventoryPanel(this.player, this.parentPanel);

            // Add the new panel
            parent.add(newPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
}
