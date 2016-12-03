package com.iqgames.sudoku.image.color;

/**
 * Created by qili on 03/12/2016.
 */
public class ColorDistance {
    public static double distance(YUVValues c1, YUVValues c2) {
        return Math.sqrt(Math.pow(c1.getU() - c2.getU(), 2) + Math.pow(c1.getV() - c2.getV(), 2));
    }

}
