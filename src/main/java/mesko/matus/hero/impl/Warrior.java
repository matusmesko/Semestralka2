package mesko.matus.hero.impl;

import mesko.matus.hero.Hero;

public class Warrior extends Hero {

    @Override
    public String getName() {
        return "Warrior";
    }

    @Override
    public int getHealth() {
        return 200;
    }

    @Override
    public int getStrength() {
        return 50;
    }

    @Override
    public void useAbility() {

    }
}
