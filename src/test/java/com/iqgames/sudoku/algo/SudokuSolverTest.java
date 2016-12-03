package com.iqgames.sudoku.algo;

import com.iqgames.sudoku.common.Loggable;
import com.iqgames.sudoku.data.PositionWithOptions;
import com.iqgames.sudoku.data.SudokuBoard;
import com.iqgames.sudoku.input.SudokuProvider;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by qili on 18/11/2016.
 */
public class SudokuSolverTest implements Loggable {
    private SudokuProvider reader = new SudokuProvider();
    private SudokuSolver solver = new DefaultSudokuSolver();
    private SudokuBoard getOneSudoku() {
        Optional<SudokuBoard> opt = reader.parseLine("......41.9..2.....3...5.....48..7..........62.1.......6..5....3.7....8......9....");
        return opt.get();
    }


    @Test
    public void testBoard() {
        SudokuBoard board = getOneSudoku();
        getLog().info("The board is " + board);
        Assert.assertTrue(board.helper.hasEmpty());
        Assert.assertFalse(board.helper.isDone());

        board.set(0,0,9);
        Assert.assertEquals(9, board.get(0,0));
        board.clear(0,0);
        Assert.assertEquals(SudokuBoard.NONE, (Integer) board.get(0,0));

        for(int i=0;i<SudokuBoard.LENGTH;i++)
            for(int j=0;j<SudokuBoard.LENGTH;j++)
                board.set(i,j,9);

        getLog().info(board);
        Assert.assertFalse(board.helper.hasEmpty());
        Assert.assertFalse(board.helper.isDone());

    }


    @Test
    public void testHints() {
        SudokuBoard board = getOneSudoku();
        getLog().info("The board is " + board);
        List<Integer> opt = board.helper.getOptions(0,0);
        Assert.assertEquals(4, opt.size());
        Assert.assertEquals(Arrays.asList(2,5,7,8), opt);
        List<PositionWithOptions> options = board.helper.hints();
        for(PositionWithOptions p : options) {
            getLog().info(p);
        }
        PositionWithOptions firstHint = options.get(0);
        Assert.assertEquals(3, firstHint.getX());
        Assert.assertEquals(0, firstHint.getY());
        Assert.assertEquals(Arrays.asList(2,5), firstHint.getOptions());

        List<Integer> opt2 = board.helper.getOptions(6,7);
        Assert.assertEquals(Arrays.asList(2,4,7,9), opt2);

    }

    @Test
    public void testSolveOne() {
        SudokuBoard board = getOneSudoku();
        getLog().info("The sudoku board is " + board.toString());

        List<SudokuBoard> solutions = solver.solve(board);
        Assert.assertFalse(solutions.isEmpty());
        for(SudokuBoard s : solutions) {
            getLog().info("Answer : " + s.toString());
        }

        SudokuBoard answer = reader.parseLine("827639415954218637361754928248167359793485162516923784689571243175342896432896571").get();
        Assert.assertTrue(SudokuBoard.equals(answer, solutions.get(0)));
    }


    private void solve(String fileName) throws IOException {
        List<SudokuBoard> boards = reader.read(this.getClass().getClassLoader().getResourceAsStream(fileName));
        List<String> results = new ArrayList<>();
        for(SudokuBoard b : boards) {
            List<SudokuBoard> solutions = solver.solve(b);
            getLog().info("Question is " + b);
            getLog().info("Answer is " + solutions);
            Assert.assertFalse(solutions.isEmpty());
            results.add(solutions.get(0).toOneLine());
        }

        Files.write(Paths.get(".", fileName+".solution"), results);
    }

    @Test
    public void testSolveAll() throws IOException {
        List<String> testFiles = Arrays.asList("top87.sdk", "top95.sdk", "top234.sdk", "top1465.sdk");
        for(String f: testFiles) {
            solve(f);
        }

    }
}
