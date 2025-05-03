package mesko.matus.areas;

import javax.swing.*;
import java.awt.*;
import mesko.matus.gui.GamePanel;

/**
 * Represents a shop area on the game screen
 * Handles shop-specific interactions like opening the shop dialog
 */
public class ShopArea extends Area {
    private boolean isPlayerInside = false;
    private boolean isShopOpen = false;
    private Component parent;

    /**
     * Creates a new shop area with the specified properties
     * 
     * @param x X coordinate of the area
     * @param y Y coordinate of the area
     * @param width Width of the area
     * @param height Height of the area
     * @param parent The parent component for dialog boxes
     */
    public ShopArea(int x, int y, int width, int height, Component parent) {
        super(x, y, width, height, "Shop", "/shopicon.png");
        this.parent = parent;
    }

    /**
     * Checks if the player has entered or exited the shop area
     * and triggers appropriate actions
     * 
     * @param characterBounds The bounds of the player character
     * @return true if the player is inside the area, false otherwise
     */
    public boolean checkPlayerCollision(Rectangle characterBounds) {
        boolean wasInside = isPlayerInside;
        isPlayerInside = intersects(characterBounds);

        // If player just entered the area and shop isn't open, open it
        if (isPlayerInside && !wasInside && !isShopOpen) {
            onPlayerEnter();
        }

        return isPlayerInside;
    }

    /**
     * Called when the player enters the shop area
     * Opens the shop dialog
     */
    private void onPlayerEnter() {
        openShop();
    }

    /**
     * Opens the shop panel
     */
    private void openShop() {
        isShopOpen = true;

        // Check if parent is a GamePanel and show the shop panel
        if (parent instanceof GamePanel) {
            ((GamePanel) parent).showShopPanel();
        } else {
            // Fallback to message dialog if parent is not a GamePanel
            JOptionPane.showMessageDialog(parent, 
                "Welcome to the shop!\nItems will be available in future updates.",
                "Shop", 
                JOptionPane.INFORMATION_MESSAGE);
            isShopOpen = false;
        }
    }

    /**
     * Checks if the player is currently inside the shop area
     * 
     * @return true if the player is inside, false otherwise
     */
    public boolean isPlayerInside() {
        return isPlayerInside;
    }

    /**
     * Resets the shop open state so it can be reopened
     */
    public void resetShopOpenState() {
        isShopOpen = false;
    }
}
