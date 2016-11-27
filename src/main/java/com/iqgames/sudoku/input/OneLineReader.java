package com.iqgames.sudoku.input;

import com.iqgames.sudoku.common.Loggable;
import com.iqgames.sudoku.data.SudokuBoard;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by qili on 20/11/2016.
 */
public class OneLineReader implements SudokuReader, SudokuParser, Loggable{

    private List<SudokuBoard> str2Sudoku(Stream<String> lines) {
        return lines.map(line -> parseLine(line))
                .filter(o->o.isPresent()).map(o->o.get()).collect(Collectors.toList());
    }
    @Override
    public List<SudokuBoard> read(String fileName) {
        List<SudokuBoard> result = new ArrayList<>();
        try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
            result = str2Sudoku(stream);

        } catch (IOException ex) {
            getLog().error("Failed to open file - ", ex);

        }
        return result;
    }

    @Override
    public List<SudokuBoard> read(InputStream input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        return str2Sudoku(reader.lines());
    }
}
