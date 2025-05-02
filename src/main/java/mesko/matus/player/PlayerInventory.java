package mesko.matus.player;

import mesko.matus.items.Item;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerInventory {

    private static final int MAX_INVENTORY_SIZE = 6;
    private Item[] items;
    private int itemCount;

    public PlayerInventory() {
        items = new Item[MAX_INVENTORY_SIZE];
        itemCount = 0;
    }

    public void addItem(Item item) {
        if (itemCount < MAX_INVENTORY_SIZE) {
            items[itemCount] = item;
            itemCount++;
        }
    }

    public void removeItem(Item item) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i] == item) {
                // Shift all items after this one to fill the gap
                for (int j = i; j < itemCount - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[itemCount - 1] = null; // Clear the last position
                itemCount--;
                break;
            }
        }
    }

    public Item getItem(int index) {
        if (index < 0 || index >= itemCount) {
            return null;
        }
        return items[index];
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> itemList = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            itemList.add(items[i]);
        }
        return itemList;
    }
}
