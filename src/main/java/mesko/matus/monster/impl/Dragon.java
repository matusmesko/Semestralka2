package mesko.matus.monster.impl;

import mesko.matus.monster.Monster;
import mesko.matus.player.Player;

/**
 * A Dragon monster with high difficulty
 */
public class Dragon implements Monster {

    private int health;
    private String name;
    private int power;

    public Dragon() {
        this.name = "Dragon";
        this.health = 300;
        this.power = 200;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPower() {
        return power;
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
    public boolean isDefeated() {
        return health <= 0;
    }

    @Override
    public int getRewardCoins() {
        return 200; // Hardest monster gives most coins
    }
}
