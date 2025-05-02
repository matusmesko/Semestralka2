package mesko.matus.items.consumable.impl;

import mesko.matus.items.consumable.ConsumableItem;
import mesko.matus.player.Player;

public class PowerPotion implements ConsumableItem {
    @Override
    public String getName() {
        return "Power Potion";
    }

    @Override
    public void useItem(Player player) {
        player.setPower(player.getPower() + 10);
    }

    @Override
    public String getImagePath() {
        return "/items/powerpotion.png";
    }
}
