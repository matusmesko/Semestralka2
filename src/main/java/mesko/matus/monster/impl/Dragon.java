package mesko.matus.monster.impl;

import mesko.matus.monster.Monster;

/**
 * A Dragon monster with high difficulty
 */
public class Dragon implements Monster {

    private int health;
    private String name;
    private int power;

    /**
     * Creates a new Dragon with default stats
     */
    public Dragon() {
        this.name = "Dragon";
        this.health = 300;
        this.power = 200;
    }

    /**
     * Gets the name of the dragon
     * @return The dragon's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the power of the dragon (used for damage calculation)
     * @return The dragon's power value
     */
    @Override
    public int getPower() {
        return this.power;
    }

    /**
     * Gets the current health of the dragon
     * @return The dragon's health points
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Sets the health of the dragon
     * @param health The new health value
     */
    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets the number of coins rewarded for defeating this dragon
     * @return The coin reward amount
     */
    @Override
    public int getRewardCoins() {
        return 200;
    }
}
