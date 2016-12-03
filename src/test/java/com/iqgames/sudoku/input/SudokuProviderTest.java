package com.iqgames.sudoku.input;

import com.iqgames.sudoku.common.Loggable;
import com.iqgames.sudoku.data.SudokuBoard;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Created by qili on 20/11/2016.
 */
public class SudokuProviderTest implements Loggable {

    SudokuProvider reader = new SudokuProvider();
    @Test
    public void testSingleChar() {
        Assert.assertEquals(SudokuBoard.NONE, reader.char2Int('.'));
    }

    @Test
    public void testSingleLine() {
        Optional<SudokuBoard> board = reader.parseLine("4...3.......6..8..........1....5..9..8....6...7.2........1.27..5.3....4.9........");
        Assert.assertTrue(board.isPresent());
        Assert.assertEquals(SudokuBoard.NONE, (Integer) board.get().get(0,1));
        getLog().info(board.get());
    }
    @Test
    public void testRead() {

        InputStream input = this.getClass().getClassLoader().getResourceAsStream("top87.sdk");
        List<SudokuBoard> boards = reader.read(input);
        Assert.assertEquals(87, boards.size());

        getLog().info(boards.get(86));

    }

    @Test
    public void testReadLarge() {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("top1465.sdk");
        List<SudokuBoard> boards = reader.read(input);
        Assert.assertEquals(1465, boards.size());
    }
}
