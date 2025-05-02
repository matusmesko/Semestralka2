package mesko.matus.player;

import mesko.matus.hero.Hero;
import mesko.matus.items.consumable.impl.HealthPotion;

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
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getLuck() {
        return luck;
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
}
