package com.iqgames.sudoku.input;

import com.iqgames.sudoku.data.SudokuBoard;

import java.util.Optional;

/**
 * Created by qili on 20/11/2016.
 */
public interface SudokuParser {

    default Integer char2Int(Character ch) {
        return (null!=ch&&ch>='0'&&ch<='9')? ch-'0': SudokuBoard.NONE;
    }

    default Optional<SudokuBoard> parseLine(String line) {
        Optional<SudokuBoard> board = Optional.empty();
        if(null!=line&&line.length()==SudokuBoard.SIZE) {
            int[][] data = new int[SudokuBoard.LENGTH][SudokuBoard.LENGTH];
            for(int i=0;i<SudokuBoard.LENGTH;i++)
                for(int j=0;j<SudokuBoard.LENGTH;j++)
                    data[i][j] = char2Int(line.charAt(i*SudokuBoard.LENGTH+j));
            board = Optional.of(new SudokuBoard(data));
        }
        return board;
    }
}
