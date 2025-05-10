package mesko.matus.monster.impl;

import mesko.matus.monster.Monster;
import mesko.matus.player.Player;

/**
 * A Skeleton monster with medium difficulty
 */
public class Skeleton implements Monster {
    private int health = 150;
    private final String name = "Skeleton";

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
    public void damagePlayer(Player player) {
        player.setHealth(player.getHealth() - 15);
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
