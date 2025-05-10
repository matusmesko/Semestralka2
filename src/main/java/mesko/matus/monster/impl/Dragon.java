package mesko.matus.monster.impl;

import mesko.matus.monster.Monster;
import mesko.matus.player.Player;

/**
 * A Dragon monster with high difficulty
 */
public class Dragon implements Monster {
    private int health = 300;
    private final String name = "Dragon";

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
        player.setHealth(player.getHealth() - 25);
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
