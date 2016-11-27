package com.iqgames.sudoku.algo;

import com.iqgames.sudoku.data.Position;
import com.iqgames.sudoku.data.PositionWithOptions;

import java.util.List;

/**
 * Created by qili on 16/11/2016.
 */
public interface SudokuHelper {
    boolean isValid(int i, int j, int val, boolean ignoreEmpty);

    List<Integer> getOptions(int i, int j);

    default List<Integer> getOptions(Position p) {
        return getOptions(p.getX(), p.getY());
    }

    boolean isDone();

    boolean isValid();

    boolean hasEmpty();

    boolean isSquareValid(int i, int j, boolean ignoreEmpty);

    List<PositionWithOptions> hints();

}
