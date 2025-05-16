package mesko.matus.monster.impl;

import mesko.matus.monster.Monster;
import mesko.matus.player.Player;

public class Zombie implements Monster {
    private int health;
    private String name;
    private int power;

    public Zombie() {
        this.name = "Zombie";
        this.health = 100;
        this.power = 25;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public boolean isDefeated() {
        return health <= 0;
    }

    @Override
    public int getRewardCoins() {
        return 50; // Easiest monster gives least coins
    }
}
