package mesko.matus.items.wearable.impl;

import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;

/**
 * Item GoldenArmor
 */
public class GoldenArmor implements WearableItem {

    private String name;
    private int intelligence;
    private int luck;
    private int health;
    private int power;
    private WearableItemType type;
    private String imagePath;
    private int prize;

    /**
     * Constructs a new GoldenArmor with default stat bonuses and properties
     */
    public GoldenArmor() {
        this.name = "Golden Armor";
        this.intelligence = 0;
        this.luck = 0;
        this.health = 50;
        this.power = 20;
        this.type = WearableItemType.BODY;
        this.imagePath = "/items/goldenarmor.png";
        this.prize = 200;
    }

    /**
     * Constructs a new GoldenArmor with custom stat bonuses.
     *
     * @param intelligence The bonus intelligence value.
     * @param luck The bonus luck value.
     * @param health The bonus health value.
     * @param power The bonus power value.
     */
    public GoldenArmor(int intelligence, int luck, int health, int power) {
        this();
        this.intelligence = intelligence;
        this.luck = luck;
        this.health = health;
        this.power = power;
    }


    /**
     * Retrieves the name of the wearable item.
     * @return The name of the item, which is "Golden Armor".
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the power bonus provided by this item.
     * @return The bonus power value.
     */
    @Override
    public int addPower() {
        return this.power;
    }

    /**
     * Retrieves the intelligence bonus provided by this item.
     * @return The bonus intelligence value.
     */
    @Override
    public int addIntelligence() {
        return this.intelligence;
    }

    /**
     * Retrieves the luck bonus provided by this item.
     * @return The bonus luck value.
     */
    @Override
    public int addLuck() {
        return this.luck;
    }

    /**
     * Retrieves the health bonus provided by this item.
     * @return The bonus health value.
     */
    @Override
    public int addHealth() {
        return this.health;
    }

    /**
     * Retrieves the file path to the item's image for visual representation.
     * @return The path to the image file.
     */
    @Override
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * Retrieves the type of wearable item, indicating the equipment slot.
     * @return The type of wearable item
     */
    @Override
    public WearableItemType getWearableItemType() {
        return this.type;
    }

    /**
     * Retrieves the prize value of the item, used for trading or selling.
     * @return The prize value of the item.
     */
    @Override
    public int getPrize() {
        return this.prize;
    }
}
