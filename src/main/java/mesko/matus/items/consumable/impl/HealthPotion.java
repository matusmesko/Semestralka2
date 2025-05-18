package mesko.matus.items.consumable.impl;

import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.player.Player;

/**
 * Item HealthPotion
 */
public class HealthPotion implements ConsumableItem {

    private String name;
    private String imagePath;
    private int prize;
    private int healthBonus;

    /**
     * Creates a default HealthPotion with preset name, image, price, and health bonus.
     */
    public HealthPotion() {
        this.name = "Health Potion";
        this.imagePath = "/items/healthpotion.png";
        this.prize = 50;
        this.healthBonus = 10;
    }

    /**
     * Creates a HealthPotion with a custom health bonus.
     * @param healthBonus the amount of health the potion restores
     */
    public HealthPotion(int healthBonus) {
        this();
        this.healthBonus = healthBonus;
    }

    /**
     * Returns the name of the item.
     * @return the item name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Applies the effect of the item to the given player.
     * This will increase the player's health by the potion's health bonus.
     *
     * @param player the player who uses the item
     */
    @Override
    public void useItem(Player player) {
        player.setHealth(player.getHealth() + this.healthBonus);
    }

    /**
     * Returns the path to the image representing this item.
     * @return the image path
     */
    @Override
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * Returns the price of the item.
     * @return the item price
     */
    @Override
    public int getPrize() {
        return this.prize;
    }
}
