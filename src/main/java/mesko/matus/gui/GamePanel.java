package mesko.matus.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import mesko.matus.areas.Area;
import mesko.matus.areas.ShopArea;
import mesko.matus.areas.DungeonArea;
import mesko.matus.areas.PubArea;
import mesko.matus.player.Player;
import mesko.matus.gui.ShopPanel;
import mesko.matus.gui.PubPanel;

/**
 * Game panel displayed after hero selection
 * Contains shopkeeper and dungeon entrance
 * Allows character movement and interaction with shop
 */
public class GamePanel extends JPanel implements KeyListener {
    private Player player;

    // Character position and movement
    private int characterX = 400;
    private int characterY = 300;
    private int characterSize = 100;
    private int moveSpeed = 5;

    // Game areas
    private ShopArea shopArea;
    private DungeonArea dungeonArea;
    private PubArea pubArea;

    // Game area dimensions
    private int gameWidth = 800;
    private int gameHeight = 500;


    /**
     * Creates a new game panel with the player
     * @param player The player with the selected hero
     */
    public GamePanel(Player player) {
        this.player = player;
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.setFocusable(true);
        this.addKeyListener(this);

        // Initialize game areas
        this.shopArea = new ShopArea(150, 150, 100, 100, this);
        this.dungeonArea = new DungeonArea(550, 150, 100, 100, this);
        this.pubArea = new PubArea(350, 350, 100, 100, this);

        // Create game area panel (this will be painted in paintComponent)
        JPanel gameAreaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                GamePanel.this.paintGameArea(g);
            }
        };
        gameAreaPanel.setPreferredSize(new Dimension(this.gameWidth, this.gameHeight));
        gameAreaPanel.setOpaque(false);
        add(gameAreaPanel, BorderLayout.CENTER);

        // Timer to ensure smooth animation and continuous collision detection
        Timer gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel.this.repaint();
                GamePanel.this.checkAreaCollisions();
            }
        });
        gameTimer.start();



        // Request focus to receive key events
        this.requestFocusInWindow();
    }

    /**
     * Paint the game area with character, shop, and dungeon
     */
    private void paintGameArea(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        BufferedImage backgroundImage = null;
        BufferedImage playerImage = null;

        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/grass.png"));
            playerImage = ImageIO.read(getClass().getResourceAsStream("/" + this.player.getHero().getName().toLowerCase() + ".png"));
        } catch (IOException e) {
            g2d.setColor(new Color(200, 230, 255));
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            return;
        }

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        } else {
            g2d.setColor(new Color(200, 230, 255));
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        // Draw game areas
        this.shopArea.draw(g2d);
        this.dungeonArea.draw(g2d);
        this.pubArea.draw(g2d);

        this.drawPlayer(g2d);
    }



    private void drawPlayer(Graphics2D g2d) {
        try {
            BufferedImage playerImage = ImageIO.read(getClass().getResourceAsStream("/" + this.player.getHero().getName().toLowerCase() + ".png"));
            Image scaledImage = playerImage.getScaledInstance(this.characterSize, this.characterSize, Image.SCALE_SMOOTH);
            g2d.drawImage(scaledImage, this.characterX - this.characterSize / 2, this.characterY - this.characterSize / 2, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if character is in shop or dungeon area and trigger appropriate actions
     */
    private void checkAreaCollisions() {
        Rectangle characterBounds = new Rectangle(
                this.characterX - this.characterSize/2,
                this.characterY - this.characterSize/2,
                this.characterSize,
                this.characterSize
        );

        // Check for shop, dungeon, and pub collisions
        this.shopArea.checkPlayerCollision(characterBounds);
        this.dungeonArea.checkPlayerCollision(characterBounds);
        this.pubArea.checkPlayerCollision(characterBounds);
    }

    /**
     * Handle key typed events (required by KeyListener)
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    /**
     * Handle key pressed events for character movement
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Move character based on arrow key
        switch (key) {
            case KeyEvent.VK_UP:
                this.characterY = Math.max(this.characterY - this.moveSpeed, this.characterSize/2);
                break;
            case KeyEvent.VK_DOWN:
                this.characterY = Math.min(this.characterY + this.moveSpeed, this.gameHeight - this.characterSize/2);
                break;
            case KeyEvent.VK_LEFT:
                this.characterX = Math.max(this.characterX - this.moveSpeed, this.characterSize/2);
                break;
            case KeyEvent.VK_RIGHT:
                this.characterX = Math.min(this.characterX + this.moveSpeed, this.gameWidth - this.characterSize/2);
                break;
            case KeyEvent.VK_I:
                // Show inventory panel when 'I' is pressed
                this.showInventoryPanel();
                return; // Return early to avoid collision check and repaint
        }

        // Check for collisions with game areas
        this.checkAreaCollisions();

        // Repaint the game area
        this.repaint();
    }

    /**
     * Handle key released events (required by KeyListener)
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Show the inventory panel with player stats and items
     */
    private void showInventoryPanel() {
        // Get the parent container
        Container parent = this.getParent();

        // Create the inventory panel with the player and this panel as parent
        InventoryPanel inventoryPanel = new InventoryPanel(this.player, this);

        // Replace this panel with the inventory panel
        if (parent != null) {
            parent.remove(this);
            parent.add(inventoryPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }

    /**
     * Show the shop panel with items for sale
     */
    public void showShopPanel() {
        // Get the parent container
        Container parent = this.getParent();

        // Create the shop panel with the player and this panel as parent
        ShopPanel shopPanel = new ShopPanel(this.player, this);

        // Replace this panel with the shop panel
        if (parent != null) {
            parent.remove(this);
            parent.add(shopPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }

    /**
     * Reset the shop open state so it can be reopened
     */
    public void resetShopOpenState() {
        this.shopArea.resetShopOpenState();
    }

    /**
     * Reset the dungeon open state so it can be reopened
     */
    public void resetDungeonOpenState() {
        this.dungeonArea.resetDungeonOpenState();
    }

    /**
     * Reset the pub open state so it can be reopened
     */
    public void resetPubOpenState() {
        this.pubArea.resetPubOpenState();
    }

    /**
     * Show the dungeon panel with monsters to fight
     */
    public void showDungeonPanel() {
        // Get the parent container
        Container parent = this.getParent();

        // Create the dungeon panel with the player and this panel as parent
        DungeonPanel dungeonPanel = new DungeonPanel(this.player, this);

        // Replace this panel with the dungeon panel
        if (parent != null) {
            parent.remove(this);
            parent.add(dungeonPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }

    /**
     * Show the pub panel with the cup game
     */
    public void showPubPanel() {
        // Get the parent container
        Container parent = this.getParent();

        // Create the pub panel with the player and this panel as parent
        PubPanel pubPanel = new PubPanel(this.player, this);

        // Replace this panel with the pub panel
        if (parent != null) {
            parent.remove(this);
            parent.add(pubPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
}
