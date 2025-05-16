package mesko.matus.monster;

import mesko.matus.player.Player;

public interface Monster {
    String getName();
    int getHealth();
    void setHealth(int health);
    int getPower();
    boolean isDefeated();
    int getRewardCoins();
}
