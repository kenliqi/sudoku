package com.iqgames.sudoku.data;

import com.iqgames.sudoku.algo.DefaultSudokuHelper;
import com.iqgames.sudoku.algo.SudokuHelper;

/**
 * Created by qili on 16/11/2016.
 */
public class SudokuBoard extends Board {
    public static final Integer NONE = -1;
    public static final Integer LENGTH = 9;
    public static final Integer SIZE = LENGTH * LENGTH;
    public final SudokuHelper helper = new DefaultSudokuHelper(this);

    public SudokuBoard(int[][] board) {
        super(board);
        if (board.length != LENGTH || board[0].length != LENGTH)
            throw new IllegalArgumentException("We only support 3*3 Sudoku board");
    }

    public SudokuBoard() {
        super(LENGTH, LENGTH, NONE);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        for (int i = 0; i < SudokuBoard.LENGTH; i++) {
            if (i % 3 == 0) sb.append("---------------------\n");
            for (int j = 0; j < SudokuBoard.LENGTH; j++) {
                if (j % 3 == 0) sb.append('|');
                int val = get(i, j);
                sb.append((char)(val == SudokuBoard.NONE ? '.' : val+'0'));
                sb.append(' ');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append('\n');
        }
        return sb.toString();

    }

    public static SudokuBoard clone(SudokuBoard that) {
        return new SudokuBoard(that.board);
    }

    public static boolean equals(SudokuBoard s1, SudokuBoard s2) {
        for(int i=0;i<SudokuBoard.LENGTH;i++)
            for(int j=0;j<SudokuBoard.LENGTH;j++)
                if(s1.get(i,j)!=s2.get(i,j)) return false;
        return true;
    }

    public String toOneLine() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<SudokuBoard.LENGTH;i++)
            for(int j=0;j<SudokuBoard.LENGTH;j++) {
                int val = get(i,j);
                sb.append((char)(val == SudokuBoard.NONE? '.': val+'0'));
            }
        sb.append('\n');
        return sb.toString();

    }

}
