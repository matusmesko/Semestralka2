package mesko.matus.monster.impl;

import mesko.matus.monster.Monster;

/**
 * Zombie monster implementation
 * The easiest monster in the dungeon with moderate health and low power
 */
public class Zombie implements Monster {
    private int health;
    private String name;
    private int power;

    /**
     * Creates a new zombie with default stats
     */
    public Zombie() {
        this.name = "Zombie";
        this.health = 100;
        this.power = 25;
    }

    /**
     * Gets the name of the zombie
     * @return The zombie's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the current health of the zombie
     * @return The zombie's health points
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Sets the health of the zombie
     * @param health The new health value
     */
    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets the power of the zombie (used for damage calculation)
     * @return The zombie's power value
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
        return 50;
    }
}
