package mesko.matus.items.consumable.impl;

import mesko.matus.items.consumable.ConsumableItem;

public class IntelligencePotion implements ConsumableItem {
    @Override
    public String getName() {
        return "Intelligence Potion";
    }

    @Override
    public void useItem(mesko.matus.player.Player player) {
        player.setIntelligence(player.getIntelligence() + 10);
    }
}
