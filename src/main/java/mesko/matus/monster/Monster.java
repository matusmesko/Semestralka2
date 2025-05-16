package mesko.matus.monster;


/**
 * Interface for monsters that can be fought in the dungeon
 */
public interface Monster {
    /**
     * Gets the name of the monster
     * @return The monster's name
     */
    String getName();

    /**
     * Gets the current health of the monster
     * @return The monster's health points
     */
    int getHealth();

    /**
     * Sets the health of the monster
     * @param health The new health value
     */
    void setHealth(int health);

    /**
     * Gets the power of the monster (used for damage calculation)
     * @return The monster's power value
     */
    int getPower();

    /**
     * Gets the number of coins rewarded for defeating this monster
     * @return The coin reward amount
     */
    int getRewardCoins();
}
