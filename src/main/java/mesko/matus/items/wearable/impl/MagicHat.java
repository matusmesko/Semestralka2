package mesko.matus.items.wearable.impl;

import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;

public class MagicHat implements WearableItem {

    private String name;
    private int intelligence;
    private int luck;
    private int health;
    private int power;
    private WearableItemType type;
    private String imagePath;
    private int prize;

    public MagicHat() {
        this.name = "Magic Hat";
        this.intelligence = 25;
        this.luck = 15;
        this.health = 5;
        this.power = 0;
        this.type = WearableItemType.HEAD;
        this.imagePath = "/items/magichat.png";
        this.prize = 100;   
    }

    public MagicHat(int intelligence, int luck, int health, int power) {
        this();
        this.intelligence = intelligence;
        this.luck = luck;
        this.health = health;
        this.power = power;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int addPower() {
        return this.power;
    }

    @Override
    public int addIntelligence() {
        return this.intelligence;
    }

    @Override
    public int addLuck() {
        return this.luck;
    }

    @Override
    public int addHealth() {
        return this.health;
    }

    @Override
    public WearableItemType getWearableItemType() {
        return this.type;
    }

    @Override
    public String getImagePath() {
        return this.imagePath;
    }

    @Override
    public int getPrize() {
        return this.prize;
    }
}
