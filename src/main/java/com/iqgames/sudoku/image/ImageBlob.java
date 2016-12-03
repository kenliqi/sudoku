package com.iqgames.sudoku.image;

import com.iqgames.sudoku.common.Loggable;
import com.iqgames.sudoku.data.Position;
import com.iqgames.sudoku.data.Square;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by qili on 03/12/2016.
 */
public class ImageBlob implements Loggable {
    private BufferedImage image;
    private boolean[][] visited;
    private List<Pair<Blob, Double>> blobs = new LinkedList<>();
    private Neighbor imageNeighbor;

    public ImageBlob(BufferedImage image) {
        this.image = image;
        this.imageNeighbor = new ImageNeighbor(image);
        visited = new boolean[image.getWidth()][image.getHeight()];
        initBlobs();
    }

    private void initBlobs() {
        for (int x = 0; x < image.getWidth(); x++)
            for (int y = 0; y < image.getHeight(); y++) {
                Position cur = new Position(x, y);
                if (visited[x][y] || !isBlack(cur)) continue;
                Blob blob = bfs(cur);
                Pair p = Pair.of(blob, blob.getBoundary().getSize() * 1.0 / getTotal());
                getLog().trace("Found one blob - " + p);
                blobs.add(p);
            }
    }

    private void visit(Position p) {
        this.visited[p.getX()][p.getY()] = true;
    }

    private Blob bfs(Position newPos) {
        List<Position> result = new LinkedList<>();
        result.add(newPos);
        visit(newPos);

        //Now bfs
        Queue<Position> level1 = new LinkedList<>();
        level1.addAll(unvisitedNeighbors(newPos));
        while (!level1.isEmpty()) {
            Set<Position> level2 = new HashSet<>();
            while (!level1.isEmpty()) {
                Position p = level1.poll();
                result.add(p);
                visit(p);
                for (Position newP : unvisitedNeighbors(p)) if (!level1.contains(newP)) level2.add(newP);
            }
            level1.addAll(level2);
        }
        return new Blob(result);
    }


    /**
     * return the unvisited neighbors
     *
     * @param cur
     * @return
     */
    private List<Position> unvisitedNeighbors(Position cur) {
        List<Position> res = new LinkedList<>();
        for (Position p : imageNeighbor.get(cur)) if (!visited[p.getX()][p.getY()] && isBlack(p)) res.add(p);
        return res;
    }

    private boolean isBlack(Position pos) {
        RGBAValues rgb = new RGBAValues(image.getRGB(pos.getX(), pos.getY()));
        return rgb.equals(RGBAValues.BLACK);
    }

    public Pair<Blob, Double> getMaxBlob() {
        return blobs.stream().sorted((p1, p2)->p2.getValue().compareTo(p1.getValue())).iterator().next();
    }

    public Long getTotal() {
        return (long) image.getWidth() * image.getHeight();
    }


    public class Blob {
        private List<Position> points;

        public Blob(List<Position> points) {
            this.points = points;
        }

        public Blob() {
            this.points = new LinkedList<>();
        }

        public void add(Position pos) {
            this.points.add(pos);
        }

        public Square getBoundary() {
            Square boundary = new Square();
            for (Position p : points) {
                boundary.update(p);
            }
            return boundary;
        }

    }

}
