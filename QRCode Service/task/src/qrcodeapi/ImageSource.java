package qrcodeapi;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageSource {
    static final int WIDTH = 250;
    static final int HEIGHT = 250;

    public static BufferedImage createImage() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        return image;
    }
}
