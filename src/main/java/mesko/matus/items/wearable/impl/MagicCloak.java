package mesko.matus.items.wearable.impl;

import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;

public class MagicCloak implements WearableItem {

    private String name;
    private int intelligence;
    private int luck;
    private int health;
    private int power;
    private WearableItemType type;
    private String imagePath;
    private int prize;

    /**
     * Constructs a new MagicCloak with default stat bonuses and properties.
     */
    public MagicCloak() {
        this.name = "Magic Cloak";
        this.intelligence = 25;
        this.luck = 5;
        this.health = 10;
        this.power = 0;
        this.type = WearableItemType.BODY;
        this.imagePath = "/items/magiccloak.png";
        this.prize = 200;   
    }

    /**
     * Constructs a new MagicCloak with custom stat bonuses.
     *
     * @param intelligence The bonus intelligence value.
     * @param luck The bonus luck value.
     * @param health The bonus health value.
     * @param power The bonus power value.
     */
    public MagicCloak(int intelligence, int luck, int health, int power) {
        this();
        this.intelligence = intelligence;
        this.luck = luck;
        this.health = health;
        this.power = power;
    }


    /**
     * Retrieves the name of the wearable item.
     * @return The name of the item, which is "Magic Cloak".
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
