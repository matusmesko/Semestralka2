package mesko.matus.areas;

import java.awt.Component;
import java.awt.Rectangle;
import mesko.matus.gui.GamePanel;

/**
 * Represents a pub area on the game screen
 * Handles pub-specific interactions like opening the cup game
 */
public class PubArea extends Area {
    private boolean isPlayerInside = false;
    private boolean isPubOpen = false;
    private final Component parent;

    /**
     * Creates a new pub area with the specified properties
     * 
     * @param x X coordinate of the area
     * @param y Y coordinate of the area
     * @param width Width of the area
     * @param height Height of the area
     * @param parent The parent component for dialog boxes
     */
    public PubArea(int x, int y, int width, int height, Component parent) {
        super(x, y, width, height, "Pub", "/pub.png");
        this.parent = parent;
    }

    /**
     * Checks if the player has entered or exited the pub area
     * and triggers appropriate actions
     * 
     * @param characterBounds The bounds of the player character
     * @return true if the player is inside the area, false otherwise
     */
    public boolean checkPlayerCollision(Rectangle characterBounds) {
        boolean wasInside = this.isPlayerInside;
        this.isPlayerInside = this.intersects(characterBounds);
        if (this.isPlayerInside && !wasInside && !this.isPubOpen) {
            this.onPlayerEnter();
        }

        return this.isPlayerInside;
    }

    /**
     * Called when the player enters the pub area
     * Opens the pub dialog
     */
    private void onPlayerEnter() {
        this.isPubOpen = true;
        if (this.parent instanceof GamePanel) {
            ((GamePanel)this.parent).showPubPanel();
        } else {
            this.isPubOpen = false;
        }
    }


    /**
     * Resets the pub open state so it can be reopened
     */
    public void resetPubOpenState() {
        this.isPubOpen = false;
    }
}