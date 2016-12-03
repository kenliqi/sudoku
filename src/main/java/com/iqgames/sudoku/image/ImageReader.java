package com.iqgames.sudoku.image;

import com.iqgames.sudoku.common.Loggable;
import com.iqgames.sudoku.image.color.RGBAValues;
import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by qili on 29/11/2016.
 */
public class ImageReader implements Loggable {

    private String file;
    private BufferedImage image;
    public static final Double BLACK_THRESHOLD = 0.9;

    public ImageReader(String file) {
        this.file = file;
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(file));
        } catch (IOException ex) {
            getLog().error("Failed to load the image file", ex);
        }
    }

    public RGBAValues getAValue() {
        long r = 0, g = 0, b = 0;
        for (int x = 0; x < image.getWidth(); x++)
            for (int y = 0; y < image.getHeight(); y++) {
                RGBAValues rgb = new RGBAValues(image.getRGB(x, y));
                r += rgb.getRed();
                g += rgb.getGreen();
                b += rgb.getBlue();
            }

        long sum = image.getHeight() * image.getWidth();
        return new RGBAValues((int) (r / sum), (int) (g / sum), (int) (b / sum));
    }

    private int getAvg(int x, int y) {
        long red = 0, green = 0, blue = 0;
        int count = 0;
        List<Pair<Integer, Integer>> coordinates = Arrays.asList(Pair.of(x - 1, y - 1), Pair.of(x - 1, y), Pair.of(x - 1, y + 1),
                Pair.of(x, y - 1), Pair.of(x, y + 1), Pair.of(x + 1, y - 1), Pair.of(x + 1, y), Pair.of(x + 1, y + 1));
        for (Pair<Integer, Integer> c : coordinates) {
            if (c.getLeft() >= 0 && c.getLeft() < image.getWidth() && c.getRight() >= 0 && c.getRight() < image.getHeight()) {
                count++;
                RGBAValues rgb = new RGBAValues(image.getRGB(c.getLeft(), c.getRight()));
                red += rgb.getRed();
                green += rgb.getGreen();
                blue += rgb.getBlue();
            }
        }
        return new RGBAValues((int) red / count, (int) green / count, (int) blue / count).getRGB();
    }

    public BufferedImage removeColor() {
        getLog().info("Starts to remove color");
        BufferedImage blackWhite = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
//        for (int x = 0; x < image.getWidth(); x++)
//            for (int y = 0; y < image.getHeight(); y++) {
//                int avg = getAvg(x, y);
//                int cur = image.getRGB(x, y) & 0x00ffffff;
//                if (cur < BLACK_THRESHOLD * avg || cur==0) blackWhite.setRGB(x,y, RGBAValues.BLACK.getRGB());
//                else blackWhite.setRGB(x,y, RGBAValues.WHITE.getRGB());
//            }
//        for (int x = 0; x < image.getWidth(); x++)
//            for (int y = 0; y < image.getHeight(); y++) {
//                if(blacks.contains(Pair.of(x,y))) image.setRGB(x,y, RGBAValues.BLACK.getRGB());
//                else image.setRGB(x,y, RGBAValues.WHITE.getRGB());
//            }


//        Graphics2D graphics2D = blackWhite.createGraphics();
//        graphics2D.drawImage(image, 0,0,null);
//        graphics2D.dispose();
        blackWhite = AdaptiveImageBin.binarize(image);
        getLog().info("Completes removing the color");
        return blackWhite;
    }
}
