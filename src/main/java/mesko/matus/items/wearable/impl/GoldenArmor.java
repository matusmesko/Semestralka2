package mesko.matus.items.wearable.impl;

import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;

public class GoldenArmor implements WearableItem {

    @Override
    public String getName() {
        return "Golden Armor";
    }

    @Override
    public int addPower() {
        return 25;
    }

    @Override
    public int addIntelligence() {
        return 0;
    }

    @Override
    public int addLuck() {
        return 5;
    }

    @Override
    public int addHealth() {
        return 50;
    }

    @Override
    public String getImagePath() {
        return "/items/goldenarmor.png";
    }

    @Override
    public WearableItemType getWearableItemType() {
        return WearableItemType.BODY;
    }

    @Override
    public int getPrize() {
        return 200;
    }
}
