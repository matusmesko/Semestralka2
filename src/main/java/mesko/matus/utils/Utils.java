package mesko.matus.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {

    public static Image resizeImage(ImageIcon imageIcon, Image image, int width, int height) {
        double imgRatio = (double) imageIcon.getIconWidth() / imageIcon.getIconHeight();
        if (imgRatio > 1) {
            height = (int) (width / imgRatio);
        } else {
            width = (int) (height * imgRatio);
        }
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
