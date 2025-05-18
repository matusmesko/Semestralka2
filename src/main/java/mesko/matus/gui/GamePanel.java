package mesko.matus.gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import mesko.matus.areas.ShopArea;
import mesko.matus.areas.DungeonArea;
import mesko.matus.areas.PubArea;
import mesko.matus.player.Player;


/**
 * Game panel displayed after hero selection
 * Contains shopkeeper and dungeon entrance
 * Allows character movement and interaction with shop
 */
public class GamePanel extends JPanel implements KeyListener {
    private Player player;

    private int characterX = 400;
    private int characterY = 300;
    private final int characterSize = 100;
    private final int moveSpeed = 5;

    private final ShopArea shopArea;
    private final DungeonArea dungeonArea;
    private final PubArea pubArea;

    private final int gameWidth = 800;
    private final int gameHeight = 500;


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

        this.shopArea = new ShopArea(150, 150, 100, 100, this);
        this.dungeonArea = new DungeonArea(550, 150, 100, 100, this);
        this.pubArea = new PubArea(350, 350, 100, 100, this);

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

        Timer gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel.this.repaint();
                GamePanel.this.checkAreaCollisions();
            }
        });
        gameTimer.start();
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

        this.shopArea.draw(g2d);
        this.dungeonArea.draw(g2d);
        this.pubArea.draw(g2d);

        this.drawPlayer(g2d);
    }

    /**
     * Draws the player's character image on the provided Graphics2D context.
     *
     * This method retrieves the player's hero image based on the hero's name, scales it to the specified
     * character size, and then draws it at the specified coordinates. The image is centered around the
     * character's position by adjusting the drawing coordinates.
     *
     * @param g2d The Graphics2D context used for drawing the player image.
     *
     * @throws RuntimeException if there is an error reading the image file.
     */
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
                this.characterX - this.characterSize / 2,
                this.characterY - this.characterSize / 2,
                this.characterSize,
                this.characterSize
        );

        this.shopArea.checkPlayerCollision(characterBounds);
        this.dungeonArea.checkPlayerCollision(characterBounds);
        this.pubArea.checkPlayerCollision(characterBounds);
    }

    /**
     * Handle key typed events (required by KeyListener)
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Handle key pressed events for character movement
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                this.characterY = Math.max(this.characterY - this.moveSpeed, this.characterSize / 2);
                break;
            case KeyEvent.VK_DOWN:
                this.characterY = Math.min(this.characterY + this.moveSpeed, this.gameHeight - this.characterSize / 2);
                break;
            case KeyEvent.VK_LEFT:
                this.characterX = Math.max(this.characterX - this.moveSpeed, this.characterSize / 2);
                break;
            case KeyEvent.VK_RIGHT:
                this.characterX = Math.min(this.characterX + this.moveSpeed, this.gameWidth - this.characterSize / 2);
                break;
            case KeyEvent.VK_I:
                this.showInventoryPanel();
                return;
        }

        this.checkAreaCollisions();
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
        Container parent = this.getParent();
        InventoryPanel inventoryPanel = new InventoryPanel(this.player, this);
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
        Container parent = this.getParent();
        ShopPanel shopPanel = new ShopPanel(this.player, this);
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
        Container parent = this.getParent();
        DungeonPanel dungeonPanel = new DungeonPanel(this.player, this);

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
        Container parent = this.getParent();
        PubPanel pubPanel = new PubPanel(this.player, this);
        if (parent != null) {
            parent.remove(this);
            parent.add(pubPanel, BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
}
