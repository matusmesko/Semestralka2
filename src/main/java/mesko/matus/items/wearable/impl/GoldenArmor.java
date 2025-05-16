package mesko.matus.items.wearable.impl;

import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;

public class GoldenArmor implements WearableItem {

    private String name;
    private int intelligence;
    private int luck;
    private int health;
    private int power;
    private WearableItemType type;
    private String imagePath;
    private int prize;

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

    public GoldenArmor(int intelligence, int luck, int health, int power) {
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
