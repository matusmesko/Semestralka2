package mesko.matus;

import javax.swing.*;
import java.awt.*;

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
        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel(GAME_TITLE);
        titleLabel.setFont(new Font("Medieval", Font.BOLD, 48));
        titleLabel.setForeground(new Color(218, 165, 32));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 250, 100, 250));

        JButton newGameButton = new WoodenButton("New Game");
        JButton optionsButton = new WoodenButton("Options");
        JButton exitButton = new WoodenButton("Exit");

        newGameButton.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.add(new HeroSelectionPanel(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        exitButton.addActionListener(e -> System.exit(0));


        buttonPanel.add(newGameButton);
        buttonPanel.add(optionsButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }


    /**
     * Panel for linear gradient background in main menu
     */
    private static class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(25, 25, 112),
                0, getHeight(), new Color(70, 130, 180)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());


            g2d.setColor(new Color(255, 255, 255, 30));


            for (int i = 0; i < 100; i++) {
                int x = (int) (Math.random() * getWidth());
                int y = (int) (Math.random() * getHeight());
                int size = (int) (Math.random() * 4) + 1;
                g2d.fillOval(x, y, size, size);
            }

            g2d.dispose();
        }
    }
}
