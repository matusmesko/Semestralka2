package mesko.matus.player;

import mesko.matus.hero.Hero;
import mesko.matus.items.consumable.impl.HealthPotion;
import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;

public class Player {

    private PlayerInventory inventory;
    private int health;
    private int power;
    private int intelligence;
    private int luck;
    private int coins;
    private Hero hero;

    public Player(Hero hero) {
        inventory = new PlayerInventory();
        this.hero = hero;
        health = hero.getHealth() + 100;
        power = hero.getPower();
        intelligence = hero.getIntelligence();
        luck = hero.getLuck() + 20;
        coins = 500;
        inventory.addItem(new HealthPotion());
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setInventory(PlayerInventory inventory) {
        this.inventory = inventory;
    }

    public int getHealth() {
        int totalHealth = health;

        // Add bonuses from equipped items
        for (WearableItem item : inventory.getEquippedItems()) {
            if (item != null) {
                totalHealth += item.addHealth();
            }
        }

        return totalHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        int totalPower = power;

        // Add bonuses from equipped items
        for (WearableItem item : inventory.getEquippedItems()) {
            if (item != null) {
                totalPower += item.addPower();
            }
        }

        return totalPower;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getIntelligence() {
        int totalIntelligence = intelligence;

        // Add bonuses from equipped items
        for (WearableItem item : inventory.getEquippedItems()) {
            if (item != null) {
                totalIntelligence += item.addIntelligence();
            }
        }

        return totalIntelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getLuck() {
        int totalLuck = luck;

        // Add bonuses from equipped items
        for (WearableItem item : inventory.getEquippedItems()) {
            if (item != null) {
                totalLuck += item.addLuck();
            }
        }

        return totalLuck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Hero getHero() {
        return hero;
    }

    /**
     * Equips a wearable item
     * @param item The wearable item to equip
     * @return true if the item was equipped, false otherwise
     */
    public boolean equipItem(WearableItem item) {
        return inventory.equipItem(item);
    }

    /**
     * Unequips a wearable item of the specified type
     * @param type The type of wearable item to unequip
     * @return The unequipped item, or null if no item was equipped
     */
    public WearableItem unequipItem(WearableItemType type) {
        return inventory.unequipItem(type);
    }

    /**
     * Gets the wearable item equipped in the specified slot
     * @param type The type of wearable item to get
     * @return The equipped item, or null if no item is equipped
     */
    public WearableItem getEquippedItem(WearableItemType type) {
        return inventory.getEquippedItem(type);
    }

    /**
     * Gets all equipped wearable items
     * @return Array of equipped wearable items (may contain null values)
     */
    public WearableItem[] getEquippedItems() {
        return inventory.getEquippedItems();
    }
}
