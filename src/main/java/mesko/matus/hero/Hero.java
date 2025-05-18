package mesko.matus.hero;

import mesko.matus.player.Player;

/**
 * Abstract class hero on the start players need to choose hero
 */
public abstract class Hero {

    private String name;
    private int intelligence;
    private int luck;
    private int health;
    private int power;

    /**
     * Constructs a default hero with zeroed attributes and empty name.
     */
    public Hero() {
        this.name = "";
        this.intelligence = 0;
        this.luck = 0;
        this.health = 0;
        this.power = 0;
    }

    /**
     * Returns the hero's luck value.
     * @return the luck attribute
     */
    public int getLuck() {
        return this.luck;
    }

    /**
     * Returns the name of the hero.
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the intelligence of the hero.
     * @return the intelligence attribute
     */
    public int getIntelligence() {
        return this.intelligence;
    }

    /**
     * Returns the health of the hero.
     * @return the health attribute
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Returns the power of the hero.
     * @return the power attribute
     */
    public int getPower() {
        return this.power;
    }

    /**
     * Abstract method to activate the hero's special ability.
     * This method must be implemented by all hero subclasses.
     *
     * @param player the player using this hero
     */
    public abstract void useAbility(Player player);
}
