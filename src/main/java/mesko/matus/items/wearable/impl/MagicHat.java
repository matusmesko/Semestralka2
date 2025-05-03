package mesko.matus.items.wearable.impl;

import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;

public class MagicHat implements WearableItem {
    @Override
    public String getName() {
        return "Magic Hat";
    }

    @Override
    public int addPower() {
        return 0;
    }

    @Override
    public int addIntelligence() {
        return 25;
    }

    @Override
    public int addLuck() {
        return 15;
    }

    @Override
    public int addHealth() {
        return 5;
    }

    @Override
    public WearableItemType getWearableItemType() {
        return WearableItemType.HEAD;
    }

    @Override
    public String getImagePath() {
        return "/items/magichat.png";
    }

    @Override
    public int getPrize() {
        return 100;
    }
}
