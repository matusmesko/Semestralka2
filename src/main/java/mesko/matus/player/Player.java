package mesko.matus.player;

import mesko.matus.hero.Hero;
import mesko.matus.items.consumable.impl.HealthPotion;
import mesko.matus.items.wearable.WearableItem;
import mesko.matus.items.wearable.WearableItemType;
import mesko.matus.monster.Monster;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private PlayerInventory inventory;
    private int health;
    private int power;
    private int intelligence;
    private int luck;
    private int coins;
    private Hero hero;
    private Set<String> defeatedMonsters;

    public Player(Hero hero) {
        this.inventory = new PlayerInventory();
        this.hero = hero;
        this.health = hero.getHealth() + 100;
        this.power = hero.getPower();
        this.intelligence = hero.getIntelligence();
        this.luck = hero.getLuck() + 20;
        this.coins = 200;
        this.inventory.addItem(new HealthPotion(100));
        this.defeatedMonsters = new HashSet<>();
    }

    /**
     * Checks if the player has defeated a specific monster
     * @param monsterName The name of the monster to check
     * @return true if the monster has been defeated, false otherwise
     */
    public boolean hasDefeatedMonster(String monsterName) {
        return this.defeatedMonsters.contains(monsterName);
    }

    /**
     * Marks a monster as defeated
     * @param monster The monster to mark as defeated
     */
    public void defeatMonster(Monster monster) {
        this.defeatedMonsters.add(monster.getName());
    }

    public PlayerInventory getInventory() {
        return this.inventory;
    }


    public int getHealth() {
        int totalHealth = this.health;

        // Add bonuses from equipped items
        for (WearableItem item : this.inventory.getEquippedItems()) {
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
        int totalPower = this.power;

        // Add bonuses from equipped items
        for (WearableItem item : this.inventory.getEquippedItems()) {
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
        int totalIntelligence = this.intelligence;

        // Add bonuses from equipped items
        for (WearableItem item : this.inventory.getEquippedItems()) {
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
        int totalLuck = this.luck;

        // Add bonuses from equipped items
        for (WearableItem item : this.inventory.getEquippedItems()) {
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
        return this.coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Hero getHero() {
        return this.hero;
    }

    /**
     * Equips a wearable item
     * @param item The wearable item to equip
     * @return true if the item was equipped, false otherwise
     */
    public boolean equipItem(WearableItem item) {
        return this.inventory.equipItem(item);
    }

    /**
     * Unequips a wearable item of the specified type
     * @param type The type of wearable item to unequip
     * @return The unequipped item, or null if no item was equipped
     */
    public WearableItem unequipItem(WearableItemType type) {
        return this.inventory.unequipItem(type);
    }

    /**
     * Gets the wearable item equipped in the specified slot
     * @param type The type of wearable item to get
     * @return The equipped item, or null if no item is equipped
     */
    public WearableItem getEquippedItem(WearableItemType type) {
        return this.inventory.getEquippedItem(type);
    }

}
