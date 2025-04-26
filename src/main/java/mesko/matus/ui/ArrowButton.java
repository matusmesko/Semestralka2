package mesko.matus.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button with arrow appearance for navigation
 */
public class ArrowButton extends JButton {

    /**
     * Creates a styled arrow button with image background
     * @param direction "left" or "right" to specify which arrow image to load
     */
    public ArrowButton(String direction) {
        super();
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setContentAreaFilled(false);

        // Load arrow image
        try {
            String imagePath = "/" + direction + ".png";
            ImageIcon arrowIcon = new ImageIcon(ArrowButton.class.getResource(imagePath));
            setIcon(arrowIcon);
            // Make the button size match the image size
            setPreferredSize(new Dimension(arrowIcon.getIconWidth(), arrowIcon.getIconHeight()));
        } catch (Exception e) {
            // Fallback to text if image can't be loaded
            System.err.println("Could not load " + direction + " arrow image: " + e.getMessage());
            setText(direction.equals("left") ? "<" : ">");
            setFont(new Font("Arial", Font.BOLD, 24));
            setForeground(Color.WHITE);
            setBackground(new Color(139, 69, 19)); // Brown color
            setContentAreaFilled(true);
        }

        // Add hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setForeground(new Color(255, 215, 0)); // Gold color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setForeground(Color.WHITE); // Back to original text color
            }
        });
    }
}