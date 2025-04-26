package mesko.matus.items.consumable;

import mesko.matus.items.Item;
import mesko.matus.player.Player;

public interface ConsumableItem extends Item {

    @Override
    String getName();

    void useItem(Player player);
}
