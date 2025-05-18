package mesko.matus.utils;


import javax.swing.ImageIcon;
import java.awt.Image;

public class Utils {

    /**
     * Resizes an image to the specified dimensions while maintaining its aspect ratio.
     *
     * The method calculates the aspect ratio of the original image and adjusts the provided width or height
     * to preserve this ratio. The image is then scaled smoothly to fit the new dimensions.
     *
     * @param imageIcon The ImageIcon containing the original image.
     * @param image The Image object to be resized.
     * @param width The desired width of the resized image.
     * @param height The desired height of the resized image.
     * @return The resized Image object.
     */
    public static Image resizeImage(ImageIcon imageIcon, Image image, int width, int height) {
        double imgRatio = (double)imageIcon.getIconWidth() / imageIcon.getIconHeight();
        if (imgRatio > 1) {
            height = (int)(width / imgRatio);
        } else {
            width = (int)(height * imgRatio);
        }
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
