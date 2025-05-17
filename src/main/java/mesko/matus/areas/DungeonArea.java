package mesko.matus.areas;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.Rectangle;
import mesko.matus.gui.GamePanel;

/**
 * Represents a dungeon entrance area on the game screen
 * Handles dungeon-specific interactions like entering the dungeon
 */
public class DungeonArea extends Area {
    private boolean isPlayerInside = false;
    private boolean isDungeonOpen = false;
    private Component parent;

    /**
     * Creates a new dungeon area with the specified properties
     * 
     * @param x X coordinate of the area
     * @param y Y coordinate of the area
     * @param width Width of the area
     * @param height Height of the area
     * @param parent The parent component for dialog boxes
     */
    public DungeonArea(int x, int y, int width, int height, Component parent) {
        super(x, y, width, height, "Dungeon", "/dungeonicon.png");
        this.parent = parent;
    }

    /**
     * Checks if the player has entered or exited the dungeon area
     * and triggers appropriate actions
     * 
     * @param characterBounds The bounds of the player character
     * @return true if the player is inside the area, false otherwise
     */
    public boolean checkPlayerCollision(Rectangle characterBounds) {
        boolean wasInside = this.isPlayerInside;
        this.isPlayerInside = intersects(characterBounds);
        if (this.isPlayerInside && !wasInside && !this.isDungeonOpen) {
            this.onPlayerEnter();
        }

        return this.isPlayerInside;
    }

    /**
     * Called when the player enters the dungeon area
     * Opens the dungeon entrance dialog
     */
    private void onPlayerEnter() {
        this.openDungeon();
    }

    /**
     * Opens the dungeon panel
     */
    private void openDungeon() {
        this.isDungeonOpen = true;

        if (this.parent instanceof GamePanel) {
            ((GamePanel)this.parent).showDungeonPanel();
        } else {
            this.isDungeonOpen = false;
        }
    }

    /**
     * Resets the dungeon open state so it can be reopened
     */
    public void resetDungeonOpenState() {
        this.isDungeonOpen = false;
    }
}
