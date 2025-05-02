package mesko.matus.items.consumable.impl;

import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.player.Player;

public class HealthPotion implements ConsumableItem {

    @Override
    public String getName() {
        return "Health Potion";
    }

    @Override
    public void useItem(Player player) {
        player.setHealth(player.getHealth() + 10);
    }

    @Override
    public String getImagePath() {
        return "/items/healthpotion.png";
    }
}
