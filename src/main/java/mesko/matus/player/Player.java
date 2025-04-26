package mesko.matus.player;

import mesko.matus.hero.Hero;

public class Player {

    private PlayerInventory inventory;
    private int health;
    private int power;
    private int intelligence;
    private int luck;
    private int coins;
    private Hero hero;

    public Player() {
        inventory = new PlayerInventory();
        health = 100;
        power = 0;
        intelligence = 0;
        luck = 0;
        coins = 0;
        hero = null;
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
