package com.iqgames.sudoku.data;

import com.iqgames.sudoku.image.RGBAValues;

import java.awt.image.BufferedImage;

/**
 * Created by qili on 03/12/2016.
 */

public class Square {
    private int x0;
    private int x1;
    private int y0;
    private int y1;

    public Square() {
        this.x0 = this.y0 = Integer.MAX_VALUE;
        this.x1 = this.y1 = Integer.MIN_VALUE;
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void update(Position point) {
        if(point.getX()<x0) x0 = point.getX();
        if(point.getX()>x1) x1 = point.getX();
        if(point.getY()<y0) y0 = point.getY();
        if(point.getY()>y1) y1 = point.getY();
    }

    public Long getSize() {
        long width = (getX1()>=getX0() ? getX1()-getX0()+1 : 0);
        long length = (getY1()>=getY0() ? getY1()-getY0()+1: 0);
        return width * length;
    }

    public void draw(BufferedImage image, RGBAValues color) {
        for(int x=x0;x<x1;x++) { image.setRGB(x, y0, color.getRGB()); image.setRGB(x, y1, color.getRGB()); }
        for(int y=y0;y<y1;y++) { image.setRGB(x0, y, color.getRGB()); image.setRGB(x1, y, color.getRGB()); }
    }
}
