package com.iqgames.sudoku.algo;

import com.iqgames.sudoku.data.PositionWithOptions;
import com.iqgames.sudoku.data.SudokuBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qili on 16/11/2016.
 */
public class DefaultSudokuSolver implements SudokuSolver {
    @Override
    public List<SudokuBoard> solve(SudokuBoard board) {
        List<SudokuBoard> results = new ArrayList<>();
        solve0(board, results);
        return results;
    }

    private void solve0(SudokuBoard board, List<SudokuBoard> results) {
        List<PositionWithOptions> emptyPos = board.helper.hints();
        if(!board.helper.isValid()) return;
        if(emptyPos.isEmpty()) {
            if(board.helper.isDone()) results.add(SudokuBoard.clone(board));
        } else {
            List<PositionWithOptions> singleOptions = emptyPos.stream().filter(p->p.getOptions().size()==1).collect(Collectors.toList());
            if(!singleOptions.isEmpty()) {
                for(PositionWithOptions p: singleOptions) board.set(p, p.getOptions().get(0)); //set all single choice
                solve0(board, results);
                for(PositionWithOptions p : singleOptions) board.clear(p);
            } else {
                PositionWithOptions p = emptyPos.get(0);
                for (int i : p.getOptions()) {
                    board.set(p, i);
                    solve0(board, results);
                    board.clear(p);
                }
            }

        }
        return;
    }
}
