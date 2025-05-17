package mesko.matus.hero.impl;

import mesko.matus.hero.Hero;
import mesko.matus.player.Player;

import java.util.Random;

public class Wizard extends Hero {

    private boolean abilityUsed;

    /**
     * Constructs a Wizard with the ability unused.
     */
    public Wizard() {
        this.abilityUsed = false;
    }

    /**
     * Returns the name of the hero.
     * @return the hero's name
     */
    @Override
    public String getName() {
        return "Wizard";
    }

    /**
     * Returns the base health of the Wizard.
     * @return the hero's health
     */
    @Override
    public int getHealth() {
        return 100;
    }

    /**
     * Returns the base intelligence of the Wizard.
     * @return the hero's intelligence
     */
    @Override
    public int getIntelligence() {
        return 20;
    }

    /**
     * Returns the base luck of the Wizard.
     * @return the hero's luck
     */
    @Override
    public int getLuck() {
        return 2;
    }

    /**
     * Activates the Wizard's special ability.
     * If the player's health is below 50 and the ability hasn't been used yet,
     * it restores the player's health by a base of 10 plus a random value (1–10).
     *
     * @param player the player using the Wizard hero
     */
    @Override
    public void useAbility(Player player) {
        if (player.getHealth() < 50 && !this.abilityUsed) {
            Random random = new Random();
            int randPlusHealth = random.nextInt(10 - 1 + 1) + 1; // Random between 1 and 10
            this.abilityUsed = true;
            player.setHealth(player.getHealth() + 10 + randPlusHealth);
        }
    }
}
