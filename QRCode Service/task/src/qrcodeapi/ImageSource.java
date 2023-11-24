package qrcodeapi;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageSource {

    public static BufferedImage createImage(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size, size);

        return image;
    }
}
