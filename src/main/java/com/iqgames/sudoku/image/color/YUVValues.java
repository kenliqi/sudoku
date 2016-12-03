package com.iqgames.sudoku.image.color;

/**
 * Created by qili on 03/12/2016.
 */
public class YUVValues {
    private final double y;
    private final double u;
    private final double v;

    private final int red;
    private final int green;
    private final int blue;

    public YUVValues(RGBAValues rgb) {
        red = rgb.getRed();
        green = rgb.getGreen();
        blue = rgb.getBlue();
        y=0.299*red+0.587*green+0.114*blue;
        u=-0.14713*red-0.28886*green+0.436*blue;
        v=0.615*red-0.51499*green-0.10001*blue;
    }

    public double getY() {
        return y;
    }

    public double getU() {
        return u;
    }

    public double getV() {
        return v;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
