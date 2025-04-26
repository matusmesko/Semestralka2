package mesko.matus.items.wearable.impl;

import mesko.matus.items.wearable.WearableItem;

public class MagicCloak implements WearableItem {

    @Override
    public String getName() {
        return "Magic Cloak";
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
        return 5;
    }

    @Override
    public int addHealth() {
        return 10;
    }
}
