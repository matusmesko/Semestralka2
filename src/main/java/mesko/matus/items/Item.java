package mesko.matus.items;

public interface Item {

    String getName();

    /**
     * Gets the path to the image for this item
     * @return Path to the item's image resource
     */
    default String getImagePath() {
        return null;
    }

    int getPrize();
}
