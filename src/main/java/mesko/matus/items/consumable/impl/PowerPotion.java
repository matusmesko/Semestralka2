package mesko.matus.items.consumable.impl;

import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.player.Player;

public class PowerPotion implements ConsumableItem {

    private String name;
    private String imagePath;
    private int prize;
    private int powerBonus;

    public PowerPotion() {
        this.name = "Power Potion";
        this.imagePath = "/items/powerpotion.png";
        this.prize = 75;
        this.powerBonus = 10;
    }

    public PowerPotion(int powerBonus) {
        this();
        this.powerBonus = powerBonus;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void useItem(Player player) {
        player.setPower(player.getPower() + this.powerBonus);
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
