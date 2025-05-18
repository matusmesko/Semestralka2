package mesko.matus.items.consumable.impl;

import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.player.Player;

/**
 * Item PowerPotion
 */
public class PowerPotion implements ConsumableItem {

    private String name;
    private String imagePath;
    private int prize;
    private int powerBonus;

    /**
     * Creates a default PowerPotion with preset name, image, price, and intelligence bonus.
     */
    public PowerPotion() {
        this.name = "Power Potion";
        this.imagePath = "/items/powerpotion.png";
        this.prize = 75;
        this.powerBonus = 10;
    }

    /**
     * Creates a Intelligence Potion with a custom power bonus.
     * @param powerBonus the amount of power the potion restores
     */
    public PowerPotion(int powerBonus) {
        this();
        this.powerBonus = powerBonus;
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
     * This will increase the player's power by the potion's power bonus.
     *
     * @param player the player who uses the item
     */
    @Override
    public void useItem(Player player) {
        player.setPower(player.getPower() + this.powerBonus);
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
