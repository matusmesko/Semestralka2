package mesko.matus.items.consumable.impl;

import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.player.Player;

public class HealthPotion implements ConsumableItem {

    private String name;
    private String imagePath;
    private int prize;
    private int healthBonus;

    public HealthPotion() {
        this.name = "Health Potion";
        this.imagePath = "/items/healthpotion.png";
        this.prize = 50;
        this.healthBonus = 10;
    }

    public HealthPotion(int healthBonus) {
        this();
        this.healthBonus = healthBonus;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void useItem(Player player) {
        player.setHealth(player.getHealth() + this.healthBonus);
    }

    @Override
    public String getImagePath() {
        return this.imagePath;
    }

    @Override
    public int getPrize() {
        return this.prize;
    }
}
