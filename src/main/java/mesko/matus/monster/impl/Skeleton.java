package mesko.matus.monster.impl;

import mesko.matus.monster.Monster;

/**
 * A Skeleton monster with medium difficulty
 */
public class Skeleton implements Monster {
    private int health;
    private String name;
    private int power;

    /**
     * Creates a new skeleton with default stats
     */
    public Skeleton() {
        this.name = "Skeleton";
        this.health = 150;
        this.power = 50;
    }

    /**
     * Gets the name of the skeleton
     * @return The skeleton's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the current health of the skeleton
     * @return The skeleton's health points
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Sets the health of the skeleton
     * @param health The new health value
     */
    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets the power of the skeleton (used for damage calculation)
     * @return The skeleton's power value
     */
    @Override
    public int getPower() {
        return this.power;
    }

    /**
     * Gets the number of coins rewarded for defeating this zombie
     * @return The coin reward amount
     */
    @Override
    public int getRewardCoins() {
        return 100;
    }
}
