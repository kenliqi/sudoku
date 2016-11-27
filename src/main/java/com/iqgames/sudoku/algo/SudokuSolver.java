package com.iqgames.sudoku.algo;

import com.iqgames.sudoku.data.SudokuBoard;

import java.util.List;

/**
 * Created by qili on 16/11/2016.
 */
public interface SudokuSolver {
    List<SudokuBoard> solve(SudokuBoard board);
}
