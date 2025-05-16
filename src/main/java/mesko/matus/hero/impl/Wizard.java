package mesko.matus.hero.impl;

import mesko.matus.hero.Hero;
import mesko.matus.player.Player;

import java.util.Random;

public class Wizard extends Hero {

    private boolean abilityUsed;

    public Wizard() {
        this.abilityUsed = false;
    }

    @Override
    public String getName() {
        return "Wizard";
    }

    @Override
    public int getHealth() {
        return 100;
    }

    @Override
    public int getIntelligence() {
        return 20;
    }

    @Override
    public int getLuck() {
        return 2;
    }

    @Override
    public void useAbility(Player player) {
        if (player.getHealth() < 50 && !this.abilityUsed) {
            Random random = new Random();
            int randPlusHealth = random.nextInt(10 - 1 + 1) + 1;
            this.abilityUsed = true;
            player.setHealth(player.getHealth() + 10 + randPlusHealth);
        }
    }
}
