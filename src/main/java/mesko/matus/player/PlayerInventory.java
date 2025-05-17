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
    private WearableItem[] wearableItems;

    /**
     * Constructs a new PlayerInventory with default settings.
     * Initializes the inventory to hold up to 6 items and 3 wearable slots.
     */
    public PlayerInventory() {
        this.items = new Item[MAX_INVENTORY_SIZE];
        this.itemCount = 0;
        this.wearableItems = new WearableItem[3];
    }

    /**
     * Adds an item to the inventory if there is space available.
     * @param item The item to be added to the inventory.
     */
    public void addItem(Item item) {
        if (this.itemCount < MAX_INVENTORY_SIZE) {
            this.items[this.itemCount] = item;
            this.itemCount++;
        }
    }

    /**
     * Removes the specified item from the inventory.
     * @param item The item to be removed.
     */
    public void removeItem(Item item) {
        for (int i = 0; i < this.itemCount; i++) {
            if (this.items[i] == item) {
                for (int j = i; j < this.itemCount - 1; j++) {
                    this.items[j] = this.items[j + 1];
                }
                this.items[this.itemCount - 1] = null;
                this.itemCount--;
                break;
            }
        }
    }

    /**
     * Sells an item from the inventory, removing it without adding it back.
     * @param item The item to be sold.
     */
    public void sellItem(Item item) {
        for (int i = 0; i < this.itemCount; i++) {
            if (this.items[i].getName().equals(item.getName())) {
                for (int j = i; j < this.itemCount - 1; j++) {
                    this.items[j] = this.items[j + 1];
                }
                this.items[this.itemCount - 1] = null; // Clear the last position
                this.itemCount--;
                break;
            }
        }
    }

    /**
     * Retrieves an item from the inventory at the specified index.
     * @param index The index of the item to retrieve.
     * @return The item at the specified index, or null if the index is out of bounds.
     */
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
        WearableItemType type = item.getWearableItemType();
        int slotIndex = type.ordinal();
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
        if (this.wearableItems[slotIndex] != null) {
            this.unequipItem(type);
        }
        this.removeItem(item);
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
            this.wearableItems[slotIndex] = null;
            if (this.itemCount < MAX_INVENTORY_SIZE) {
                this.addItem(item);
            } else {
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


    /**
     * Checks if the inventory contains the specified item.
     * @param item The item to check for.
     * @return true if the item is present in the inventory, false otherwise.
     */
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
