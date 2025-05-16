package mesko.matus.player;

import mesko.matus.items.Item;
import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerInventory {

    private static final int MAX_INVENTORY_SIZE = 6;
    private Item[] items;
    private int itemCount;

    // Array to store equipped wearable items (HEAD, BODY, LEGS)
    private WearableItem[] wearableItems;

    public PlayerInventory() {
        this.items = new Item[MAX_INVENTORY_SIZE];
        this.itemCount = 0;

        // Initialize wearable items array with length 3 (for HEAD, BODY, LEGS)
        this.wearableItems = new WearableItem[3];
    }

    public void addItem(Item item) {
        if (this.itemCount < MAX_INVENTORY_SIZE) {
            this.items[this.itemCount] = item;
            this.itemCount++;
        }
    }

    public void removeItem(Item item) {
        for (int i = 0; i < this.itemCount; i++) {
            if (this.items[i] == item) {
                // Shift all items after this one to fill the gap
                for (int j = i; j < this.itemCount - 1; j++) {
                    this.items[j] = this.items[j + 1];
                }
                this.items[this.itemCount - 1] = null; // Clear the last position
                this.itemCount--;
                break;
            }
        }
    }

    public void sellItem(Item item) {
        for (int i = 0; i < this.itemCount; i++) {
            if (this.items[i].getName().equals(item.getName())) {
                // Shift all items after this one to fill the gap
                for (int j = i; j < this.itemCount - 1; j++) {
                    this.items[j] = this.items[j + 1];
                }
                this.items[this.itemCount - 1] = null; // Clear the last position
                this.itemCount--;
                break;
            }
        }
    }

    public Item getItem(int index) {
        if (index < 0 || index >= this.itemCount) {
            return null;
        }
        return this.items[index];
    }

    public ArrayList<Item> getItems() {
        return new ArrayList<>(Arrays.asList(this.items).subList(0, this.itemCount));
    }

    /**
     * Equips a wearable item from the inventory
     * @param item The wearable item to equip
     * @return true if the item was equipped, false otherwise
     */
    public boolean equipItem(WearableItem item) {
        // Get the type of the wearable item
        WearableItemType type = item.getWearableItemType();
        int slotIndex = type.ordinal();

        // Check if the item is in the inventory
        boolean foundInInventory = false;
        for (int i = 0; i < this.itemCount; i++) {
            if (this.items[i] == item) {
                foundInInventory = true;
                break;
            }
        }

        if (!foundInInventory) {
            return false;
        }

        // If there's already an item in that slot, unequip it first
        if (this.wearableItems[slotIndex] != null) {
            this.unequipItem(type);
        }

        // Remove the item from the inventory
        this.removeItem(item);

        // Equip the item
        this.wearableItems[slotIndex] = item;

        return true;
    }

    /**
     * Unequips a wearable item of the specified type
     * @param type The type of wearable item to unequip
     * @return The unequipped item, or null if no item was equipped
     */
    public WearableItem unequipItem(WearableItemType type) {
        int slotIndex = type.ordinal();
        WearableItem item = this.wearableItems[slotIndex];

        if (item != null) {
            // Remove the item from the equipped slot
            this.wearableItems[slotIndex] = null;

            // Add the item back to the inventory if there's space
            if (this.itemCount < MAX_INVENTORY_SIZE) {
                this.addItem(item);
            } else {
                // If inventory is full, just return the item without adding it back
                return item;
            }
        }

        return item;
    }

    /**
     * Gets the wearable item equipped in the specified slot
     * @param type The type of wearable item to get
     * @return The equipped item, or null if no item is equipped
     */
    public WearableItem getEquippedItem(WearableItemType type) {
        return this.wearableItems[type.ordinal()];
    }

    /**
     * Gets all equipped wearable items
     * @return Array of equipped wearable items (may contain null values)
     */
    public WearableItem[] getEquippedItems() {
        return this.wearableItems;
    }


    public boolean hasItem(Item item) {
        if (item == null) {
            return false;
        }
        for (int i = 0; i < this.itemCount; i++) {
            if (this.items[i].getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }
}
