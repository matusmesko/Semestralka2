package mesko.matus.areas;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents an interactive area on the game screen
 * Used for shop, dungeon, and other special areas
 */
public class Area {
    private Rectangle bounds;
    private String name;
    private Color fillColor;
    private Color borderColor;
    private Color textColor;
    private BufferedImage icon;
    private String iconPath;

    /**
     * Creates a new area with the specified properties
     * 
     * @param x X coordinate of the area
     * @param y Y coordinate of the area
     * @param width Width of the area
     * @param height Height of the area
     * @param name Name of the area (displayed as text)
     * @param fillColor Color to fill the area
     * @param borderColor Color for the area border
     * @param textColor Color for the area text
     */
    public Area(int x, int y, int width, int height, String name, Color fillColor, Color borderColor, Color textColor) {
        this.bounds = new Rectangle(x, y, width, height);
        this.name = name;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        this.textColor = textColor;
    }

    /**
     * Creates a new area with default colors
     * 
     * @param x X coordinate of the area
     * @param y Y coordinate of the area
     * @param width Width of the area
     * @param height Height of the area
     * @param name Name of the area (displayed as text)
     */
    public Area(int x, int y, int width, int height, String name) {
        this(x, y, width, height, name, new Color(150, 100, 50), Color.BLACK, Color.WHITE);
    }

    /**
     * Creates a new area with an icon
     * 
     * @param x X coordinate of the area
     * @param y Y coordinate of the area
     * @param width Width of the area
     * @param height Height of the area
     * @param name Name of the area (displayed as text)
     * @param iconPath Path to the icon image
     */
    public Area(int x, int y, int width, int height, String name, String iconPath) {
        this(x, y, width, height, name);
        this.iconPath = iconPath;
        try {
            this.icon = ImageIO.read(getClass().getResourceAsStream(iconPath));
        } catch (IOException e) {
            System.err.println("Failed to load icon for area: " + name);
        }
    }

    /**
     * Draws the area on the specified graphics context
     * 
     * @param g2d Graphics context to draw on
     */
    public void draw(Graphics2D g2d) {
        if (this.icon != null) {
            g2d.drawImage(this.icon, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height, null);
        } else {
            g2d.setColor(this.fillColor);
            g2d.fill(this.bounds);
            g2d.setColor(this.borderColor);
            g2d.draw(this.bounds);
            g2d.setColor(this.textColor);

            FontMetrics metrics = g2d.getFontMetrics();
            int textX = this.bounds.x + (this.bounds.width - metrics.stringWidth(this.name)) / 2;
            int textY = this.bounds.y + (this.bounds.height + metrics.getHeight()) / 2;

            g2d.drawString(this.name, textX, textY);
        }
    }

    /**
     * Checks if the specified rectangle intersects with this area
     * 
     * @param rect Rectangle to check for intersection
     * @return true if the rectangles intersect, false otherwise
     */
    public boolean intersects(Rectangle rect) {
        return this.bounds.intersects(rect);
    }


    /**
     * Gets the name of this area
     * 
     * @return Name of the area
     */
    public String getName() {
        return this.name;
    }
}
