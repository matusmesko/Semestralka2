package mesko.matus.hero.impl;

import mesko.matus.hero.Hero;

public class Wizard extends Hero {

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
    public void useAbility() {

    }
}
