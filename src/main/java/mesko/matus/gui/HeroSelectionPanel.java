package mesko.matus.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import mesko.matus.hero.Hero;
import mesko.matus.hero.impl.Warrior;
import mesko.matus.hero.impl.Wizard;
import mesko.matus.player.Player;
import mesko.matus.ui.ArrowButton;
import mesko.matus.ui.WoodenButton;
import mesko.matus.utils.Utils;

/**
 * Panel for selecting a hero character
 */
public class HeroSelectionPanel extends JPanel {
    private List<Hero> heroes;
    private int currentHeroIndex = 0;
    private JLabel heroImageLabel;
    private JPanel statsPanel;
    private JLabel nameLabel;
    private JLabel healthLabel;
    private JLabel strengthLabel;
    private JLabel intelligenceLabel;
    private JLabel luckLabel;
    private Hero selectedHero;

    /**
     * Panel for choosing hero for player
     */
    private JPanel parentPanel;

    /**
     * Panel for choosing hero for player
     */
    public HeroSelectionPanel() {
        this(null);
    }

    /**
     * Panel for choosing hero for player
     * @param parentPanel The parent panel to return to after selection
     */
    public HeroSelectionPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
        this.heroes = new ArrayList<>();
        this.heroes.add(new Warrior());
        this.heroes.add(new Wizard());


        this.setLayout(new BorderLayout());
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);

        this.heroImageLabel = new JLabel(new ImageIcon(getClass().getResource("/warrior.png")));
        this.heroImageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(this.heroImageLabel, BorderLayout.CENTER);
        this.add(imagePanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.setOpaque(false);

        this.statsPanel = new JPanel();
        this.statsPanel.setLayout(new GridLayout(5, 1, 5, 5));
        this.statsPanel.setBorder(BorderFactory.createTitledBorder("Hero Stats"));
        this.statsPanel.setOpaque(false);

        this.nameLabel = new JLabel();
        this.healthLabel = new JLabel();
        this.strengthLabel = new JLabel();
        this.intelligenceLabel = new JLabel();
        this.luckLabel = new JLabel();

        this.statsPanel.add(this.nameLabel);
        this.statsPanel.add(this.healthLabel);
        this.statsPanel.add(this.strengthLabel);
        this.statsPanel.add(this.intelligenceLabel);
        this.statsPanel.add(this.luckLabel);

        southPanel.add(this.statsPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        WoodenButton chooseButton = new WoodenButton("Choose Hero");
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeroSelectionPanel.this.selectedHero = HeroSelectionPanel.this.heroes.get(HeroSelectionPanel.this.currentHeroIndex);

                // Create a Player instance with the selected hero
                Player player = new Player(HeroSelectionPanel.this.selectedHero);

                // Get the parent container
                Container parent = HeroSelectionPanel.this.getParent();

                // Create the game panel with the player
                GamePanel gamePanel = new GamePanel(player);

                // Replace this panel with the game panel
                if (parent != null) {
                    parent.remove(HeroSelectionPanel.this);
                    parent.add(gamePanel, BorderLayout.CENTER);
                    parent.revalidate();
                    parent.repaint();

                    // Request focus for the game panel to receive key events
                    gamePanel.requestFocusInWindow();
                }
            }
        });

        ArrowButton leftArrow = new ArrowButton("left");

        leftArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeroSelectionPanel.this.navigateToPreviousHero();
            }
        });

        ArrowButton rightArrow = new ArrowButton("right");

        rightArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeroSelectionPanel.this.navigateToNextHero();
            }
        });

        buttonPanel.add(leftArrow);
        buttonPanel.add(rightArrow);
        buttonPanel.add(chooseButton);
        southPanel.add(buttonPanel);
        this.add(southPanel, BorderLayout.SOUTH);
        this.updateHeroDisplay();
    }




    /**
     * Updates hero image and statistics
     */
    private void updateHeroDisplay() {
        Hero currentHero = this.heroes.get(this.currentHeroIndex);
        this.nameLabel.setText("Name: " + currentHero.getName());
        this.healthLabel.setText("Health: " + currentHero.getHealth());
        this.strengthLabel.setText("Strength: " + currentHero.getPower());
        this.intelligenceLabel.setText("Intelligence: " + currentHero.getIntelligence());
        this.luckLabel.setText("Luck: " + currentHero.getLuck());


        String imageName = currentHero.getName().toLowerCase() + ".png";
        ImageIcon heroIcon = new ImageIcon(getClass().getResource("/" + imageName));
        Image img = heroIcon.getImage();
        this.heroImageLabel.setIcon(new ImageIcon(Utils.resizeImage(heroIcon, img, 200, 200)));
        this.heroImageLabel.setText("");
    }


    /**
     * Navigate to the next hero in the list
     */
    private void navigateToNextHero() {
        this.currentHeroIndex = (this.currentHeroIndex + 1) % this.heroes.size();
        this.updateHeroDisplay();
    }

    /**
     * Navigate to the previous hero in the list
     */
    private void navigateToPreviousHero() {
        this.currentHeroIndex = (this.currentHeroIndex - 1 + this.heroes.size()) % this.heroes.size();
        this.updateHeroDisplay();
    }

    /**
     * Get the currently selected hero
     * @return Hero the selected hero
     */
    public Hero getSelectedHero() {
        return this.selectedHero;
    }
}
