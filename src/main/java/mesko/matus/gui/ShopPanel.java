package mesko.matus.gui;

import mesko.matus.items.Item;
import mesko.matus.items.consumable.impl.HealthPotion;
import mesko.matus.items.consumable.impl.PowerPotion;
import mesko.matus.items.consumable.impl.IntelligencePotion;
import mesko.matus.items.wearable.impl.GoldenArmor;
import mesko.matus.items.wearable.impl.MagicCloak;
import mesko.matus.items.wearable.impl.MagicHat;
import mesko.matus.player.Player;
import mesko.matus.ui.WoodenButton;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
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
 * Shop panel displayed when player enters the shop area
 * Allows buying items with coins
 */
public class ShopPanel extends JPanel {
    private Player player;
    private JPanel parentPanel;
    private JLabel coinsLabel;
    private JPanel shopItemsPanel;
    private JPanel coinsPanel;
    private ArrayList<Item> shopItems;

    /**
     * Creates a new shop panel with the player
     * @param player The player
     * @param parentPanel The parent panel to return to
     */
    public ShopPanel(Player player, JPanel parentPanel) {
        this.player = player;
        this.parentPanel = parentPanel;


        this.initializeShopItems();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(139, 69, 19));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(101, 67, 33));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Shop", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JButton backButton = new WoodenButton("Back to game", 150, 30, 15);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShopPanel.this.returnToGame();
            }
        });
        headerPanel.add(backButton, BorderLayout.EAST);

        this.coinsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.coinsPanel.setOpaque(false);

        try {
            BufferedImage coinImage = ImageIO.read(getClass().getResourceAsStream("/coin.png"));
            if (coinImage != null) {
                Image scaledCoin = coinImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                JLabel coinImageLabel = new JLabel(new ImageIcon(scaledCoin));
                this.coinsPanel.add(coinImageLabel);
            }
        } catch (IOException e) {
            System.err.println("Failed to load coin image");
        }

        this.coinsLabel = new JLabel(player.getCoins() + "", JLabel.LEFT);
        this.coinsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.coinsLabel.setForeground(Color.YELLOW);
        this.coinsPanel.add(this.coinsLabel);

        headerPanel.add(this.coinsPanel, BorderLayout.WEST);
        this.add(headerPanel, BorderLayout.NORTH);
        this.createShopItemsPanel();

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(101, 67, 33));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel infoLabel = new JLabel("Click on an item to buy it", JLabel.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoPanel.add(infoLabel);

        this.add(infoPanel, BorderLayout.SOUTH);
    }


    /**
     * Initialize the items available in the shop
     */
    private void initializeShopItems() {
        this.shopItems = new ArrayList<>();
        this.shopItems.add(new HealthPotion());
        this.shopItems.add(new PowerPotion());
        this.shopItems.add(new IntelligencePotion());
        this.shopItems.add(new GoldenArmor());
        this.shopItems.add(new MagicCloak());
        this.shopItems.add(new MagicHat());
    }

    /**
     * Create the panel displaying shop items
     */
    private void createShopItemsPanel() {
        this.shopItemsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        this.shopItemsPanel.setBackground(new Color(139, 69, 19));
        this.shopItemsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Item item : this.shopItems) {
            JPanel itemPanel = this.createItemPanel(item);
            this.shopItemsPanel.add(itemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(this.shopItemsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Create a panel for a single shop item
     * @param item The item to display
     * @return A panel containing the item's image, name, and price
     */
    private JPanel createItemPanel(Item item) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(205, 133, 63));
        panel.setBorder(BorderFactory.createLineBorder(new Color(101, 67, 33), 2));
        JLabel nameLabel = new JLabel(item.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel, BorderLayout.NORTH);
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        if (item.getImagePath() != null) {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream(item.getImagePath()));
                ImageIcon icon = new ImageIcon(image);
                Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                imageLabel.setText("No Image");
                imageLabel.setForeground(Color.WHITE);
            }
        } else {
            imageLabel.setText("No Image");
            imageLabel.setForeground(Color.WHITE);
        }

        panel.add(imageLabel, BorderLayout.CENTER);
        int price = item.getPrize();
        JLabel priceLabel = new JLabel("Price: " + price + " coins", JLabel.CENTER);
        priceLabel.setForeground(Color.YELLOW);
        panel.add(priceLabel, BorderLayout.SOUTH);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    ShopPanel.this.buyItem(item);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    ShopPanel.this.sellItem(item);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JPanel parent = (JPanel)e.getSource();
                parent.setBackground(new Color(204, 112, 24));
                parent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                parent.revalidate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JPanel parent = (JPanel)e.getSource();
                parent.setBackground(new Color(205, 133, 63));
                parent.setCursor(Cursor.getDefaultCursor());
                parent.revalidate();
            }
        });

        return panel;
    }

    /**
     * Buy an item if the player has enough coins
     * @param item The item to buy
     */
    private void buyItem(Item item) {
        int price = item.getPrize();

        if (this.player.getCoins() >= price) {
            if (this.player.getInventory().getItems().size() < 6) {
                this.player.setCoins(this.player.getCoins() - price);
                this.player.getInventory().addItem(item);
                this.coinsLabel.setText(this.player.getCoins() + "");

                JOptionPane.showMessageDialog(this,
                    "You bought " + item.getName() + " for " + price + " coins!",
                    "Purchase Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Your inventory is full! You cannot buy more items.",
                    "Inventory Full", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "You don't have enough coins to buy " + item.getName() + "!",
                "Not Enough Coins", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public void sellItem(Item item) {
        int prize = item.getPrize();

        if (this.player.getInventory().hasItem(item)) {
            this.player.setCoins(this.player.getCoins() + prize / 2);
            this.coinsLabel.setText(this.player.getCoins() + "");
            this.player.getInventory().sellItem(item);
        } else {
            JOptionPane.showMessageDialog(this,
                    "You do not have this item " + item.getName() + "!",
                    "You do not have item",
                    JOptionPane.WARNING_MESSAGE);
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
                ((GamePanel)this.parentPanel).resetShopOpenState();
            }
            this.parentPanel.requestFocusInWindow();
        }
    }
}
