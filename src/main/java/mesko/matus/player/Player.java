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

    /**
     * Return instance of players inventory
     * @return player inventory
     */
    public PlayerInventory getInventory() {
        return this.inventory;
    }

    /**
     * Returns players health with all items bonuses
     * @return players health
     */
    public int getHealth() {
        int totalHealth = this.health;
        for (WearableItem item : this.inventory.getEquippedItems()) {
            if (item != null) {
                totalHealth += item.addHealth();
            }
        }
        return totalHealth;
    }

    /**
     * Set player health
     * @param health player health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Returns players power with all items bonuses
     * @return players power
     */
    public int getPower() {
        int totalPower = this.power;
        for (WearableItem item : this.inventory.getEquippedItems()) {
            if (item != null) {
                totalPower += item.addPower();
            }
        }
        return totalPower;
    }

    /**
     * Set player power
     * @param power players power
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * Returns players intelligence with all items bonuses
     * @return players intelligence
     */
    public int getIntelligence() {
        int totalIntelligence = this.intelligence;
        for (WearableItem item : this.inventory.getEquippedItems()) {
            if (item != null) {
                totalIntelligence += item.addIntelligence();
            }
        }

        return totalIntelligence;
    }

    /**
     * Set player intelligence
     * @param intelligence player intelligence
     */
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    /**
     * Returns players luck with all items bonuses
     * @return players luck
     */
    public int getLuck() {
        int totalLuck = this.luck;
        for (WearableItem item : this.inventory.getEquippedItems()) {
            if (item != null) {
                totalLuck += item.addLuck();
            }
        }
        return totalLuck;
    }

    /**
     * Set player luck
     * @param luck players luck
     */
    public void setLuck(int luck) {
        this.luck = luck;
    }

    /**
     * Get players coins
     * @return players coins
     */
    public int getCoins() {
        return this.coins;
    }

    /**
     * Set players coins
     * @param coins players coins
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * Get players selected hero
     * @return players hero
     */
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
