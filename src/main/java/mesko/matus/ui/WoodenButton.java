package mesko.matus.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button with wooden appearance for fantasy theme
 */
public class WoodenButton extends JButton {

    /**
     * Creates a styled wooden button with image background
     */
    public WoodenButton(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 18));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Load button background image
        try {
            ImageIcon buttonIcon = new ImageIcon(WoodenButton.class.getResource("/button.png"));
            setIcon(buttonIcon);
            setHorizontalTextPosition(JButton.CENTER);
            setVerticalTextPosition(JButton.CENTER);
            // Make the button size match the image size
            setPreferredSize(new Dimension(buttonIcon.getIconWidth(), buttonIcon.getIconHeight()));
            // Make the content area transparent so the image shows through
            setContentAreaFilled(false);
        } catch (Exception e) {
            // Fallback to colored background if image can't be loaded
            System.err.println("Could not load button image: " + e.getMessage());
            setBackground(new Color(139, 69, 19)); // Brown color
            setContentAreaFilled(true);
        }

        // Add hover effect (optional - can be removed if not needed with image)
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
