package mesko.matus.monster;

import mesko.matus.player.Player;

public interface Monster {
    String getName();
    int getHealth();
    void setHealth(int health);
    void damagePlayer(Player player);
    boolean isDefeated();
    int getRewardCoins();
}
