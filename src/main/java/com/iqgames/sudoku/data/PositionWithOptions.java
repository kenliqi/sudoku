package com.iqgames.sudoku.data;

import java.util.List;

/**
 * Created by qili on 16/11/2016.
 */
public class PositionWithOptions extends Position {
    private List<Integer> options;

    public PositionWithOptions(int x, int y, List<Integer> options) {
        super(x, y);
        this.options = options;
    }

    public List<Integer> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "PositionWithOptions{(" +
                getX() + ", " + getY() + ") " +
                "options=" + options +
                '}';
    }
}
