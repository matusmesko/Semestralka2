package mesko.matus.items.wearable;

import mesko.matus.items.Item;

public interface WearableItem extends Item {

    @Override
    String getName();
    int addPower();
    int addIntelligence();
    int addLuck();
    int addHealth();
}
