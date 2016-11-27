package com.iqgames.sudoku.input;

import com.iqgames.sudoku.data.SudokuBoard;

import java.io.InputStream;
import java.util.List;

/**
 * Created by qili on 20/11/2016.
 */
public interface SudokuReader {
    List<SudokuBoard> read(String fileName);
    List<SudokuBoard> read(InputStream input);
}
