package com.iqgames.sudoku.data;

import java.util.Iterator;

/**
 * Created by qili on 16/11/2016.
 */
public class Board implements Iterable<Integer> {
    protected int[][] board;
    protected final int RowLen;
    protected final int ColLen;
    public Board(int[][] board) {
        this.RowLen = board.length;
        this.ColLen = RowLen<=0? 0 : board[0].length;
        this.board = new int[RowLen][ColLen];
        for(int i=0;i<RowLen;i++)
            for(int j=0;j<ColLen;j++)
                this.board[i][j] = board[i][j];
    }

    public Board(int X, int Y, int val) {
        this.board = new int[X][Y];
        this.RowLen = X;
        this.ColLen = Y;
        for(int i=0;i<X;i++)
            for(int j=0;j<Y;j++)
                board[i][j] = val;
    }

    public Board(int X, int Y) {
        this(X, Y, 0);
    }

    public Iterator<Integer> iterator() {
        return null;
    }

    public void set(int i, int j, int val) {
        assertRange(i, j);
        board[i][j] = val;
    }

    public void set(Position p, int val) {
        set(p.getX(), p.getY(), val);
    }

    public void clear(int i, int j) {
        assertRange(i, j);
        board[i][j] = SudokuBoard.NONE;
    }
    public void clear(Position p) {
        clear(p.getX(), p.getY());
    }

    public int get(int i, int j) {
        assertRange(i, j);
        return board[i][j];
    }

    public int get(Position p) {
        return get(p.getX(), p.getY());
    }

    private void assertRange(int i, int j) {
        if(i>=RowLen || j>=ColLen) throw new IllegalArgumentException("The index is out of range! ");
    }

    public int getRowLen() {
        return RowLen;
    }

    public int getColLen() {
        return ColLen;
    }
}

