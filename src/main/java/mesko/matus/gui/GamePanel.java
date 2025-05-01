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

import mesko.matus.player.Player;

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

    // Shop area
    private Rectangle shopArea = new Rectangle(150, 150, 100, 100);
    private boolean inShopArea = false;
    private boolean shopOpen = false;

    // Dungeon area
    private Rectangle dungeonArea = new Rectangle(550, 150, 100, 100);

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
                checkShopCollision();
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

        BufferedImage playerImage = null;

        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/" + player.getHero().getName().toLowerCase() + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Draw background
        g2d.setColor(new Color(200, 230, 255));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw shop area
        g2d.setColor(new Color(150, 100, 50));
        g2d.fill(shopArea);
        g2d.setColor(Color.BLACK);
        g2d.draw(shopArea);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Shop", shopArea.x + 35, shopArea.y + 55);

        // Draw dungeon area
        g2d.setColor(new Color(100, 100, 100));
        g2d.fill(dungeonArea);
        g2d.setColor(Color.BLACK);
        g2d.draw(dungeonArea);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Dungeon", dungeonArea.x + 25, dungeonArea.y + 55);

        drawPlayer(g2d);
    }


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
     * Check if character is in shop area and open shop if needed
     */
    private void checkShopCollision() {
        Rectangle characterBounds = new Rectangle(
            characterX - characterSize/2, 
            characterY - characterSize/2, 
            characterSize, 
            characterSize
        );

        boolean wasInShopArea = inShopArea;
        inShopArea = characterBounds.intersects(shopArea);

        // If character just entered shop area, open shop
        if (inShopArea && !wasInShopArea && !shopOpen) {
            openShop();
        }
    }

    /**
     * Open the shop dialog
     */
    private void openShop() {
        shopOpen = true;
        JOptionPane.showMessageDialog(this, 
            "Welcome to the shop!\nItems will be available in future updates.", 
            "Shop", 
            JOptionPane.INFORMATION_MESSAGE);
        shopOpen = false;
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
        }

        // Check for collision with shop
        checkShopCollision();

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
}
