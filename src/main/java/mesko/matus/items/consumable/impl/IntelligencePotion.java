package mesko.matus.items.consumable.impl;

import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.player.Player;

/**
 * Item IntelligencePotion
 */
public class IntelligencePotion implements ConsumableItem {

    private String name;
    private String imagePath;
    private int prize;
    private int intelligenceBonus;

    /**
     * Creates a default Intelligence Potion with preset name, image, price, and intelligence bonus.
     */
    public IntelligencePotion() {
        this.name = "Intelligence Potion";
        this.imagePath = "/items/intelligencepotion.png";
        this.prize = 75;
        this.intelligenceBonus = 10;
    }

    /**
     * Creates a Intelligence Potion with a custom intelligence bonus.
     * @param intelligenceBonus the amount of intelligence the potion restores
     */
    public IntelligencePotion(int intelligenceBonus) {
        this();
        this.intelligenceBonus = intelligenceBonus;
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
     * This will increase the player's intelligence by the potion's intelligence bonus.
     *
     * @param player the player who uses the item
     */
    @Override
    public void useItem(Player player) {
        player.setIntelligence(player.getIntelligence() + this.intelligenceBonus);
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
