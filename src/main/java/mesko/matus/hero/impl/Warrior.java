package mesko.matus.hero.impl;

import mesko.matus.hero.Hero;
import mesko.matus.player.Player;

import java.util.Random;

public class Warrior extends Hero {

    private boolean abilityUsed;
    private String name;
    private int health;
    private int power;


    /**
     * Constructs a Warrior.
     */
    public Warrior(int health, int power) {
        this.name = "Warrior";
        this.health = health;
        this.power = power;
        this.abilityUsed = false;
    }

    /**
     * Returns the name of the hero.
     * @return the hero's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the base health of the Warrior.
     * @return the hero's health
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Returns the base power of the Warrior.
     * @return the hero's power
     */
    @Override
    public int getPower() {
        return this.power;
    }

    /**
     * Activates the Warrior's special ability.
     * If the player's health is below 75 and the ability hasn't been used yet,
     * it increases the player's power by a base of 10 plus a random value (1–10).
     *
     * @param player the player using the Warrior hero
     */
    @Override
    public void useAbility(Player player) {
        if (player.getHealth() < 75 && !this.abilityUsed) {
            Random random = new Random();
            int randPlusPower = random.nextInt(10 - 1 + 1) + 1;
            this.abilityUsed = true;
            player.setPower( player.getPower() + 10 + randPlusPower);
        }
    }
}
