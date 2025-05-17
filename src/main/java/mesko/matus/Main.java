package mesko.matus;

import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import mesko.matus.gui.HeroSelectionPanel;
import mesko.matus.ui.WoodenButton;


public class Main {

    private static final String GAME_TITLE = "Magic Adventure";
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 700;

    /**
     * Main method to start starting JFrame
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gameFrame();
        });
    }

    /**
     * Main frame and starting screen where user can choose to exit or
     * start new game. When click on "New Game" panel to choose hero
     * will appear
     */
    private static void gameFrame() {
        JFrame frame = new JFrame(GAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(177, 220, 115));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel(GAME_TITLE);
        titleLabel.setFont(new Font("Medieval", Font.BOLD, 48));
        titleLabel.setForeground(new Color(47, 81, 0));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(WINDOW_HEIGHT / 4, 50, 0, 50));
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 20));
        JButton newGameButton = new WoodenButton("New Game");
        JButton exitButton = new WoodenButton("Exit");

        newGameButton.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(new HeroSelectionPanel(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(newGameButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

}
