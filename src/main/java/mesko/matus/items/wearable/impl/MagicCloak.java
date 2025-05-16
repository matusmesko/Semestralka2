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

    public MagicCloak(int intelligence, int luck, int health, int power) {
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
    public String getImagePath() {
        return this.imagePath;
    }

    @Override
    public WearableItemType getWearableItemType() {
        return this.type;
    }

    @Override
    public int getPrize() {
        return this.prize;
    }
}
