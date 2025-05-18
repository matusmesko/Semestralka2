package mesko.matus.items.wearable;

import mesko.matus.items.Item;

/**
 * interface WearableItem when player is wearing them he get bonus stats
 */
public interface WearableItem extends Item {

    /**
     * Gets the power bonus provided by this item
     * @return Items power bonus
     */
    int addPower();

    /**
     * Gets the intelligence bonus provided by this item
     * @return Items intelligence bonus
     */
    int addIntelligence();

    /**
     * Gets the luck bonus provided by this item
     * @return Items luck bonus
     */
    int addLuck();

    /**
     * Gets the health bonus provided by this item
     * @return Items health bonus
     */
    int addHealth();

    /**
     * Identifies the equipment slot this item occupies
     * @return The type defining where this item can be equipped
     */
    WearableItemType getWearableItemType();
}
