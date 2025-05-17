package mesko.matus.areas;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.Rectangle;
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
        boolean wasInside = this.isPlayerInside;
        this.isPlayerInside = intersects(characterBounds);

        if (this.isPlayerInside && !wasInside && !this.isShopOpen) {
            this.onPlayerEnter();
        }

        return this.isPlayerInside;
    }

    /**
     * Called when the player enters the shop area
     * Opens the shop dialog
     */
    private void onPlayerEnter() {
        this.openShop();
    }

    /**
     * Opens the shop panel
     */
    private void openShop() {
        this.isShopOpen = true;

        if (this.parent instanceof GamePanel) {
            ((GamePanel)this.parent).showShopPanel();
        } else {
            JOptionPane.showMessageDialog(this.parent,
                "Welcome to the shop!\nItems will be available in future updates.",
                "Shop", 
                JOptionPane.INFORMATION_MESSAGE);
            this.isShopOpen = false;
        }
    }


    /**
     * Resets the shop open state so it can be reopened
     */
    public void resetShopOpenState() {
        this.isShopOpen = false;
    }
}
