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
import mesko.matus.player.Player;
import mesko.matus.gui.ShopPanel;

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

    // Game area dimensions
    private int gameWidth = 800;
    private int gameHeight = 500;


    /**
     * Creates a new game panel with the player
     * @param player The player with the selected hero
     */
    public GamePanel(Player player) {
        this.player = player;
        setLayout(new BorderLayout());
        setOpaque(false);
        setFocusable(true);
        addKeyListener(this);

        // Initialize game areas
        shopArea = new ShopArea(150, 150, 100, 100, this);
        dungeonArea = new DungeonArea(550, 150, 100, 100, this);

        // Create game area panel (this will be painted in paintComponent)
        JPanel gameAreaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                GamePanel.this.paintGameArea(g);
            }
        };
        gameAreaPanel.setPreferredSize(new Dimension(gameWidth, gameHeight));
        gameAreaPanel.setOpaque(false);
        add(gameAreaPanel, BorderLayout.CENTER);

        // Timer to ensure smooth animation and continuous collision detection
        Timer gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                checkAreaCollisions();
            }
        });
        gameTimer.start();



        // Request focus to receive key events
        requestFocusInWindow();
    }

    /**
     * Paint the game area with character, shop, and dungeon
     */
    private void paintGameArea(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage backgroundImage = null;
        BufferedImage playerImage = null;

        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/grass.png"));
            playerImage = ImageIO.read(getClass().getResourceAsStream("/" + player.getHero().getName().toLowerCase() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            g2d.setColor(new Color(200, 230, 255));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            return;
        }

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2d.setColor(new Color(200, 230, 255));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw game areas
        shopArea.draw(g2d);
        dungeonArea.draw(g2d);

        drawPlayer(g2d);
    }

//    private void paintGameArea(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//
//        BufferedImage playerImage = null;
//
//        try {
//            playerImage = ImageIO.read(getClass().getResourceAsStream("/" + player.getHero().getName().toLowerCase() + ".png"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Draw background
//        g2d.setColor(new Color(200, 230, 255));
//        g2d.fillRect(0, 0, getWidth(), getHeight());
//
//        // Draw game areas
//        shopArea.draw(g2d);
//        dungeonArea.draw(g2d);
//
//        drawPlayer(g2d);
//    }


    private void drawPlayer(Graphics2D g2d) {
        try {
            BufferedImage playerImage = ImageIO.read(getClass().getResourceAsStream("/" + player.getHero().getName().toLowerCase() + ".png"));
            Image scaledImage = playerImage.getScaledInstance(characterSize, characterSize, Image.SCALE_SMOOTH);
            g2d.drawImage(scaledImage, characterX - characterSize / 2, characterY - characterSize / 2, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if character is in shop or dungeon area and trigger appropriate actions
     */
    private void checkAreaCollisions() {
        Rectangle characterBounds = new Rectangle(
            characterX - characterSize/2, 
            characterY - characterSize/2, 
            characterSize, 
            characterSize
        );

        // Check for shop and dungeon collisions
        shopArea.checkPlayerCollision(characterBounds);
        dungeonArea.checkPlayerCollision(characterBounds);
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
                characterY = Math.max(characterY - moveSpeed, characterSize/2);
                break;
            case KeyEvent.VK_DOWN:
                characterY = Math.min(characterY + moveSpeed, gameHeight - characterSize/2);
                break;
            case KeyEvent.VK_LEFT:
                characterX = Math.max(characterX - moveSpeed, characterSize/2);
                break;
            case KeyEvent.VK_RIGHT:
                characterX = Math.min(characterX + moveSpeed, gameWidth - characterSize/2);
                break;
            case KeyEvent.VK_I:
                // Show inventory panel when 'I' is pressed
                showInventoryPanel();
                return; // Return early to avoid collision check and repaint
        }

        // Check for collisions with game areas
        checkAreaCollisions();

        // Repaint the game area
        repaint();
    }

    /**
     * Handle key released events (required by KeyListener)
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    /**
     * Show the inventory panel with player stats and items
     */
    private void showInventoryPanel() {
        // Get the parent container
        Container parent = this.getParent();

        // Create the inventory panel with the player and this panel as parent
        InventoryPanel inventoryPanel = new InventoryPanel(player, this);

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
        ShopPanel shopPanel = new ShopPanel(player, this);

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
        shopArea.resetShopOpenState();
    }

    /**
     * Reset the dungeon open state so it can be reopened
     */
    public void resetDungeonOpenState() {
        dungeonArea.resetDungeonOpenState();
    }

    /**
     * Show the dungeon panel with monsters to fight
     */
    public void showDungeonPanel() {
        // Get the parent container
        Container parent = this.getParent();

        // Create the dungeon panel with the player and this panel as parent
        DungeonPanel dungeonPanel = new DungeonPanel(player, this);

        // Replace this panel with the dungeon panel
        if (parent != null) {
            parent.remove(this);
            parent.add(dungeonPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
}
