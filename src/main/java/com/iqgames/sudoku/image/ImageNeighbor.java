package com.iqgames.sudoku.image;

import com.iqgames.sudoku.data.Position;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by qili on 03/12/2016.
 */
public class ImageNeighbor implements Neighbor {
    private final BufferedImage image;

    public ImageNeighbor(BufferedImage image) {
        this.image = image;
    }

    private List<Pair<Integer, Integer>> allCoordinates(int x, int y) {
        return Arrays.asList(Pair.of(x - 1, y - 1), Pair.of(x - 1, y), Pair.of(x - 1, y + 1),
                Pair.of(x, y - 1), Pair.of(x, y + 1), Pair.of(x + 1, y - 1), Pair.of(x + 1, y), Pair.of(x + 1, y + 1));
    }

    @Override
    public List<Position> get(Position pos) {
        List<Position> results = new LinkedList<>();
        for(Pair<Integer, Integer> p : allCoordinates(pos.getX(), pos.getY())) {
            if(p.getLeft()>=0 && p.getLeft() <image.getWidth() && p.getRight() >=0 && p.getRight() < image.getHeight()) results.add(new Position(p.getLeft(), p.getRight()));
        }
        return results;
    }
}
