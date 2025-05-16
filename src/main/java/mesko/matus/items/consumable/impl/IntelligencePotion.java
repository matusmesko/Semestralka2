package mesko.matus.items.consumable.impl;

import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.player.Player;

public class IntelligencePotion implements ConsumableItem {

    private String name;
    private String imagePath;
    private int prize;
    private int intelligenceBonus;

    public IntelligencePotion() {
        this.name = "Intelligence Potion";
        this.imagePath = "/items/intelligencepotion.png";
        this.prize = 75;
        this.intelligenceBonus = 10;
    }

    public IntelligencePotion(int intelligenceBonus) {
        this();
        this.intelligenceBonus = intelligenceBonus;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void useItem(Player player) {
        player.setIntelligence(player.getIntelligence() + this.intelligenceBonus);
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
