package mesko.matus.monster.impl;

import mesko.matus.monster.Monster;
import mesko.matus.player.Player;

public class Zombie implements Monster {


    @Override
    public String getName() {
        return "Zombie";
    }
    @Override
    public int getHealth() {
        return 100;
    }
    @Override
    public void damagePlayer(Player player) {
        player.setHealth(player.getHealth() - 10);
    }
}
