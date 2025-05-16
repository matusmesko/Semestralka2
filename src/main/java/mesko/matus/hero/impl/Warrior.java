package mesko.matus.hero.impl;

import mesko.matus.hero.Hero;
import mesko.matus.player.Player;

import java.util.Random;

public class Warrior extends Hero {

    private boolean abilityUsed;

    public Warrior() {
        this.abilityUsed = false;
    }

    @Override
    public String getName() {
        return "Warrior";
    }

    @Override
    public int getHealth() {
        return 200;
    }

    @Override
    public int getPower() {
        return 50;
    }

    @Override
    public void useAbility(Player player) {
        if (player.getHealth() < 75 && !this.abilityUsed) {
            Random random = new Random();
            int randPlusPower = random.nextInt(10 - 1 + 1) + 1;
            this.abilityUsed = true;
            player.setPower( player.getPower() + 10 + randPlusPower);
        }
    }
}
