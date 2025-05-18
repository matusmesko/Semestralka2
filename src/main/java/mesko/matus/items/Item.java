package mesko.matus.items;

/**
 * Basic interface Item
 */
public interface Item {

    /**
     * Gets item name
     * @return name of the item
     */
    String getName();

    /**
     * Gets the path to the image for this item
     * @return Path to the item's image resource
     */
    default String getImagePath() {
        return null;
    }

    /**
     * Gets items prize in store
     * @return items prize
     */
    int getPrize();
}
