package mesko.matus.player;

import mesko.matus.items.Item;

import java.util.ArrayList;

public class PlayerInventory {

    private ArrayList<Item> items;

    public PlayerInventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item getItem(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public ArrayList<Item> getItems() {
        return items.iterator().hasNext() ? new ArrayList<>(items) : new ArrayList<>();
    }
}
