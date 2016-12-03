package com.iqgames.sudoku.image;

/**
 * Created by qili on 29/11/2016.
 */
public class RGBAValues {
    private int red;
    private int green;
    private int blue;

    public RGBAValues(int rgb) {
        this.red = (rgb>>16)&0xFF;
        this.green = (rgb>>8)&0xFF;
        this.blue = (rgb)&0xFF;
    }

    public RGBAValues(int r, int g, int b) {
        this.red =r;
        this.green =g;
        this.blue =b;
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

    public int getRGB() {
        return (red<<16) + (green<<8) + blue;
    }

    public static final RGBAValues WHITE = new RGBAValues(0xff, 0xff, 0xff);
    public static final RGBAValues BLACK = new RGBAValues(0,0,0);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RGBAValues that = (RGBAValues) o;

        if (red != that.red) return false;
        if (green != that.green) return false;
        return blue == that.blue;
    }

    @Override
    public int hashCode() {
        int result = red;
        result = 31 * result + green;
        result = 31 * result + blue;
        return result;
    }

    @Override
    public String toString() {
        return "RGBAValues{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
