package com.iqgames.sudoku.algo;

import com.iqgames.sudoku.data.PositionWithOptions;
import com.iqgames.sudoku.data.SudokuBoard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qili on 16/11/2016.
 */
public class DefaultSudokuHelper implements SudokuHelper {
    private final int ONE2NINE = 0b1111111110;
    private final List<Boolean> MARK = new ArrayList<>();
    private final SudokuBoard board;

    public DefaultSudokuHelper(SudokuBoard board) {
        this.board = board;
        for(int i=0;i<SudokuBoard.LENGTH;i++) MARK.add(0, false);
    }

    @Override
    public boolean isValid(int i, int j, int val, boolean ignoreEmpty) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public boolean isValid() {
        for(int i=0;i<SudokuBoard.LENGTH;i++) {
            List<Boolean> tag = new ArrayList<>(MARK);
            for(int j=0;j<SudokuBoard.LENGTH;j++) {
                if(SudokuBoard.NONE!=board.get(i,j)) {
                    int val = board.get(i,j);
                    if(tag.get(val-1)) return false;
                    tag.set(val-1, true);
                }
            }
        }
        for(int j=0;j<SudokuBoard.LENGTH;j++) {
            List<Boolean> tag = new ArrayList<>(MARK);
            for(int i=0;i<SudokuBoard.LENGTH;i++) {
                if(SudokuBoard.NONE!=board.get(i,j)) {
                    int val = board.get(i,j);
                    if(tag.get(val-1)) return false;
                    tag.set(val-1, true);
                }
            }
        }
        for(int i=0;i<SudokuBoard.LENGTH;i+=3)
            for(int j=0;j<SudokuBoard.LENGTH;j+=3) {
                List<Boolean> tag = new ArrayList<>(MARK);
                for(int row=i;row<i+3;row++)
                    for(int col=j;col<j+3;col++)
                        if(SudokuBoard.NONE!=board.get(row, col)) {
                            int val = board.get(row, col);
                            if(tag.get(val-1)) return false;
                            tag.set(val-1, true);
                        }
            }
        return true;
    }

    @Override
    public List<Integer> getOptions(int i, int j) {
        int val = board.get(i, j);
        List<Integer> options = new ArrayList<>();
        if (val == SudokuBoard.NONE) {
            List<Boolean> tag = new ArrayList<>(MARK);
            for (int row = 0; row < 9; row++)
                if (SudokuBoard.NONE != board.get(row, j)) {
                    tag.set(board.get(row, j)-1, true);
                }
            for (int col = 0; col < 9; col++)
                if (SudokuBoard.NONE != board.get(i, col)) {
                    tag.set(board.get(i, col)-1, true);
                }
            for (int row = i / 3 * 3; row < i / 3 * 3 + 3; row++)
                for (int col = j / 3 * 3; col < j / 3 * 3+ 3; col++)
                    if (SudokuBoard.NONE != board.get(row, col)) {
                        tag.set(board.get(row, col)-1, true);
                    }
            for(int idx=0;idx<SudokuBoard.LENGTH;idx++) {
                if(!tag.get(idx)) options.add(idx+1);
            }
        }
        return options;
    }

    @Override
    public boolean isDone() {
        return !hasEmpty() && isValid();
    }

    @Override
    public boolean hasEmpty() {
        for (int i = 0; i < SudokuBoard.LENGTH; i++)
            for (int j = 0; j < SudokuBoard.LENGTH; j++)
                if (board.get(i, j) == SudokuBoard.NONE) return true;
        return false;
    }

    @Override
    public boolean isSquareValid(int i, int j, boolean ignoreEmpty) {
        int tag = ONE2NINE;
        for (int row = i / 3 * 3; row < i / 3 * 3 + 3; row++)
            for (int col = j / 3 * 3; col < j / 3 * 3+ 3; col++) {
                if (SudokuBoard.NONE == board.get(row, col)) {
                    if (ignoreEmpty) continue;
                    else return false;
                }
                int val = (1 << board.get(row, col));
                if ((tag & val) != 0) {
                    tag ^= val;
                } else {
                    return false;
                }
            }

        return true;
    }

    @Override
    public List<PositionWithOptions> hints() {
        int X = board.getRowLen();
        int Y = board.getColLen();
        List<PositionWithOptions> results = new ArrayList<>();
        for (int i = 0; i < X; i++)
            for (int j = 0; j < Y; j++)
                results.add(new PositionWithOptions(i, j, getOptions(i, j)));
        //short cut the hints
        if(results.stream().filter(p -> board.get(p)==SudokuBoard.NONE && p.getOptions().isEmpty()).findAny().isPresent()) return results;
        Comparator<PositionWithOptions> optionCmp = (p1, p2) -> ((Integer)p1.getOptions().size()).compareTo(p2.getOptions().size());
        List<PositionWithOptions> filtered = results.stream().filter(p -> !p.getOptions().isEmpty())
                .sorted(optionCmp).collect(Collectors.toList());
        return filtered;
    }
}
