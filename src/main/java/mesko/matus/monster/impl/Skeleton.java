package mesko.matus.monster.impl;

import mesko.matus.monster.Monster;
import mesko.matus.player.Player;

/**
 * A Skeleton monster with medium difficulty
 */
public class Skeleton implements Monster {
    private int health;
    private String name;
    private int power;

    public Skeleton() {
        this.name = "Skeleton";
        this.health = 150;
        this.power = 50;
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
        return 100; // Medium difficulty monster gives medium coins
    }
}
