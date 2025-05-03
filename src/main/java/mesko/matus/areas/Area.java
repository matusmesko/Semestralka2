package mesko.matus.areas;

import javax.imageio.ImageIO;
import java.awt.*;
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
    public Area(int x, int y, int width, int height, String name, 
                Color fillColor, Color borderColor, Color textColor) {
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
        this(x, y, width, height, name, 
             new Color(150, 100, 50), // Default fill color
             Color.BLACK,             // Default border color
             Color.WHITE);            // Default text color
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
            e.printStackTrace();
        }
    }

    /**
     * Draws the area on the specified graphics context
     * 
     * @param g2d Graphics context to draw on
     */
    public void draw(Graphics2D g2d) {
        if (icon != null) {
            // Draw the icon
            g2d.drawImage(icon, bounds.x, bounds.y, bounds.width, bounds.height, null);
        } else {
            // Fall back to drawing a rectangle
            g2d.setColor(fillColor);
            g2d.fill(bounds);
            g2d.setColor(borderColor);
            g2d.draw(bounds);
            g2d.setColor(textColor);

            // Center the text in the area
            FontMetrics metrics = g2d.getFontMetrics();
            int textX = bounds.x + (bounds.width - metrics.stringWidth(name)) / 2;
            int textY = bounds.y + (bounds.height + metrics.getHeight()) / 2;

            g2d.drawString(name, textX, textY);
        }
    }

    /**
     * Checks if the specified rectangle intersects with this area
     * 
     * @param rect Rectangle to check for intersection
     * @return true if the rectangles intersect, false otherwise
     */
    public boolean intersects(Rectangle rect) {
        return bounds.intersects(rect);
    }

    /**
     * Gets the bounds of this area
     * 
     * @return Rectangle representing the bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Gets the name of this area
     * 
     * @return Name of the area
     */
    public String getName() {
        return name;
    }
}
