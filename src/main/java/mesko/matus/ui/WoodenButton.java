package mesko.matus.ui;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Custom button with wooden appearance for fantasy theme
 */
public class WoodenButton extends JButton {

    /**
     * Creates a styled wooden button with image background
     */
    public WoodenButton(String text) {
        super(text);
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Load button background image
        try {
            ImageIcon buttonIcon = new ImageIcon(WoodenButton.class.getResource("/button.png"));
            this.setIcon(buttonIcon);
            this.setHorizontalTextPosition(JButton.CENTER);
            this.setVerticalTextPosition(JButton.CENTER);
            // Make the button size match the image size
            this.setPreferredSize(new Dimension(buttonIcon.getIconWidth(), buttonIcon.getIconHeight()));
            // Make the content area transparent so the image shows through
            this.setContentAreaFilled(false);
        } catch (Exception e) {
            // Fallback to colored background if image can't be loaded
            System.err.println("Could not load button image: " + e.getMessage());
            this.setBackground(new Color(139, 69, 19)); // Brown color
            this.setContentAreaFilled(true);
        }

        // Add hover effect (optional - can be removed if not needed with image)
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                WoodenButton.this.setForeground(new Color(255, 215, 0)); // Gold color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                WoodenButton.this.setForeground(Color.WHITE); // Back to original text color
            }
        });
    }


    public WoodenButton(String text, int width, int height, int fontSize) {
        super(text);
        this.setFont(new Font("Arial", Font.BOLD, fontSize));
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setPreferredSize(new Dimension(width, height));

        // Load button background image
        try {
            ImageIcon buttonIcon = new ImageIcon(WoodenButton.class.getResource("/button.png"));
            this.setIcon(buttonIcon);
            this.setHorizontalTextPosition(JButton.CENTER);
            this.setVerticalTextPosition(JButton.CENTER);
            // Make the content area transparent so the image shows through
            this.setContentAreaFilled(false);
        } catch (Exception e) {
            // Fallback to colored background if image can't be loaded
            System.err.println("Could not load button image: " + e.getMessage());
            this.setBackground(new Color(139, 69, 19)); // Brown color
            this.setContentAreaFilled(true);
        }

        // Add hover effect (optional - can be removed if not needed with image)
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                WoodenButton.this.setForeground(new Color(255, 215, 0)); // Gold color on hover
            }

            public void mouseExited(MouseEvent evt) {
                WoodenButton.this.setForeground(Color.WHITE); // Back to original text color
            }
        });
    }
}
