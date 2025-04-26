package mesko.matus.hero;

public abstract class Hero {

    private String name;
    private int intelligence;
    private int luck;
    private int health;
    private int strength;

    public Hero() {
        this.name = "";
        this.intelligence = 0;
        this.luck = 0;
        this.health = 0;
        this.strength = 0;
    }

    public int getLuck() {
        return luck;
    }

    public String getName() {
        return name;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public abstract void useAbility();
}
