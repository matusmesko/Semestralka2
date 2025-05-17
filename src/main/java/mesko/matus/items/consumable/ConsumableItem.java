package mesko.matus.items.consumable;

import mesko.matus.items.Item;
import mesko.matus.player.Player;

public interface ConsumableItem extends Item {

    /**
     * Uses the consumable item on the specified player, applying its effects.
     * @param player The player who is using the item.
     */
    void useItem(Player player);
}
