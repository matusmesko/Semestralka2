package mesko.matus.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mesko.matus.hero.Hero;
import mesko.matus.hero.impl.Warrior;
import mesko.matus.hero.impl.Wizard;
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
     * Creates a new hero selection panel
     */
    public HeroSelectionPanel() {
        // Initialize heroes list
        heroes = new ArrayList<>();
        heroes.add(new Warrior());
        heroes.add(new Wizard());

        // Set panel properties
        setLayout(new BorderLayout());

        // Create hero image panel (center)
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);

        heroImageLabel = new JLabel(new ImageIcon(getClass().getResource("/warrior.png")));
        heroImageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(heroImageLabel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.CENTER);

        // Create stats panel (south)
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.setOpaque(false);

        // Stats display
        statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(5, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Hero Stats"));
        statsPanel.setOpaque(false);

        nameLabel = new JLabel();
        healthLabel = new JLabel();
        strengthLabel = new JLabel();
        intelligenceLabel = new JLabel();
        luckLabel = new JLabel();

        statsPanel.add(nameLabel);
        statsPanel.add(healthLabel);
        statsPanel.add(strengthLabel);
        statsPanel.add(intelligenceLabel);
        statsPanel.add(luckLabel);

        southPanel.add(statsPanel);

        // Choose button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        WoodenButton chooseButton = new WoodenButton("Choose Hero");
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedHero = heroes.get(currentHeroIndex);
                JOptionPane.showMessageDialog(HeroSelectionPanel.this, 
                    "You selected: " + selectedHero.getName(), 
                    "Hero Selected", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        ArrowButton leftArrow = new ArrowButton("left");

        leftArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToPreviousHero();
            }
        });

        ArrowButton rightArrow = new ArrowButton("right");

        rightArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToNextHero();
            }
        });

        buttonPanel.add(leftArrow);
        buttonPanel.add(rightArrow);
        buttonPanel.add(chooseButton);
        southPanel.add(buttonPanel);

        add(southPanel, BorderLayout.SOUTH);

        updateHeroDisplay();
    }




    /**
     * Updates the display with the current hero's information and image
     */
    private void updateHeroDisplay() {
        Hero currentHero = heroes.get(currentHeroIndex);
        nameLabel.setText("Name: " + currentHero.getName());
        healthLabel.setText("Health: " + currentHero.getHealth());
        strengthLabel.setText("Strength: " + currentHero.getStrength());
        intelligenceLabel.setText("Intelligence: " + currentHero.getIntelligence());
        luckLabel.setText("Luck: " + currentHero.getLuck());


        String imageName = currentHero.getName().toLowerCase() + ".png";
        ImageIcon heroIcon = new ImageIcon(getClass().getResource("/" + imageName));
        Image img = heroIcon.getImage();
        heroImageLabel.setIcon(new ImageIcon(Utils.resizeImage(heroIcon, img, 200, 200)));
        heroImageLabel.setText("");
    }


    /**
     * Navigate to the next hero in the list
     */
    private void navigateToNextHero() {
        currentHeroIndex = (currentHeroIndex + 1) % heroes.size();
        updateHeroDisplay();
    }

    /**
     * Navigate to the previous hero in the list
     */
    private void navigateToPreviousHero() {
        currentHeroIndex = (currentHeroIndex - 1 + heroes.size()) % heroes.size();
        updateHeroDisplay();
    }

    /**
     * Get the currently selected hero
     * @return the selected hero
     */
    public Hero getSelectedHero() {
        return selectedHero;
    }
}
