package mesko.matus.gui;

import mesko.matus.items.Item;
import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;
import mesko.matus.player.Player;
import mesko.matus.ui.WoodenButton;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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

        JLabel titleLabel = new JLabel("Player Inventory");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        contentPanel.setOpaque(false);

        JPanel topRow = new JPanel(new GridLayout(1, 2, 20, 0));
        topRow.setOpaque(false);
        this.createStatsPanel();
        topRow.add(this.statsPanel);
        this.createEquippedItemsPanel();
        topRow.add(this.equippedItemsPanel);
        contentPanel.add(topRow);
        this.createInventoryPanel();
        contentPanel.add(this.inventoryPanel);
        this.add(contentPanel, BorderLayout.CENTER);

        JButton closeButton = new WoodenButton("Close Inventory", 300, 50, 15);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = InventoryPanel.this.getParent();
                if (parent != null) {
                    parent.remove(InventoryPanel.this);
                    parent.add(parentPanel, BorderLayout.CENTER);
                    parent.revalidate();
                    parent.repaint();

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

        JLabel nameLabel = new JLabel("Hero: " + this.player.getHero().getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.statsPanel.add(nameLabel);
        this.statsPanel.add(Box.createVerticalStrut(10));

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

        for (WearableItemType type : WearableItemType.values()) {
            JPanel slotPanel = new JPanel(new BorderLayout());
            slotPanel.setBorder(BorderFactory.createTitledBorder(type.name()));
            slotPanel.setPreferredSize(new Dimension(100, 100));

            WearableItem equippedItem = this.player.getEquippedItem(type);

            if (equippedItem != null) {
                this.createItemDisplay(slotPanel, equippedItem);

                slotPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        InventoryPanel.this.player.unequipItem(type);
                        InventoryPanel.this.refreshPanels();
                    }
                });
            } else {
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

        ArrayList<Item> items = this.player.getInventory().getItems();

        for (int i = 0; i < 6; i++) {
            JPanel itemSlot = new JPanel(new BorderLayout());
            itemSlot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            itemSlot.setPreferredSize(new Dimension(80, 80));

            if (i < items.size() && items.get(i) != null) {
                Item item = items.get(i);
                this.createItemDisplay(itemSlot, item);

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
        String imagePath = item.getImagePath();
        if (imagePath != null) {
            try {
                BufferedImage itemImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
                if (itemImage != null) {
                    Image scaledImage = itemImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    panel.add(imageLabel, BorderLayout.CENTER);

                    JLabel itemLabel = new JLabel(item.getName());
                    itemLabel.setHorizontalAlignment(JLabel.CENTER);
                    itemLabel.setFont(new Font("Arial", Font.BOLD, 10));
                    itemLabel.setForeground(Color.WHITE);
                    itemLabel.setOpaque(true);
                    itemLabel.setBackground(new Color(0, 0, 0, 150));
                    panel.add(itemLabel, BorderLayout.SOUTH);
                }
            } catch (IOException e) {
                JLabel itemLabel = new JLabel(item.getName());
                itemLabel.setHorizontalAlignment(JLabel.CENTER);
                panel.add(itemLabel, BorderLayout.CENTER);
            }
        } else {
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
        Container parent = this.getParent();
        if (parent != null) {
            parent.remove(this);
            InventoryPanel newPanel = new InventoryPanel(this.player, this.parentPanel);
            parent.add(newPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
}
