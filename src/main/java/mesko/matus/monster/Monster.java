package mesko.matus.monster;

import mesko.matus.player.Player;

public interface Monster {
    String getName();
    int getHealth();
    void damagePlayer(Player player);
}
