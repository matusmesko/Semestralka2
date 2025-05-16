package mesko.matus.hero;

import mesko.matus.player.Player;

public abstract class Hero {

    private String name;
    private int intelligence;
    private int luck;
    private int health;
    private int power;

    public Hero() {
        this.name = "";
        this.intelligence = 0;
        this.luck = 0;
        this.health = 0;
        this.power = 0;
    }

    public int getLuck() {
        return this.luck;
    }

    public String getName() {
        return this.name;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public int getHealth() {
        return this.health;
    }

    public int getPower() {
        return this.power;
    }

    public abstract void useAbility(Player player);
}
