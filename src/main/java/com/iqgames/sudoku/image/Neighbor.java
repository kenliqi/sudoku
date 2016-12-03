package com.iqgames.sudoku.image;

import com.iqgames.sudoku.data.Position;

import java.util.List;

/**
 * Created by qili on 03/12/2016.
 */
public interface Neighbor {
    List<Position> get(Position pos);
}
