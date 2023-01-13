package de.dorianignee.aoc.challenges;

import java.util.*;

public class Day12 extends Aoc {
    private Square startSquare;
    private Square endSquare;

    /**
     * Standard main-method for running the days directly
     * @param args are ignored
     */
    public static void main(String[] args) {
        new Day12().solve(12);
    }

    /**
     * Todays challenge is to find the shortest path up a hill
     */
    @Override
    public int challenge1() {
        // build array of Squares
        Square[][] map = lines().map(line -> {
            Square[] result = new Square[line.length()];
            for (int i = 0; i < result.length; ++i) {
                result[i] = new Square(line.charAt(i));
            }
            return result;
        }).toArray(Square[][]::new);

        // find neighbors
        neighbors(map, false);

        // walk all
        startSquare.walk(new Square((char) ('a'-1)));
        
        return endSquare.distance;
    }

    @Override
    public int challenge2() {
        // build array of Squares
        Square[][] map = lines().map(line -> {
            Square[] result = new Square[line.length()];
            for (int i = 0; i < result.length; ++i) {
                result[i] = new Square(line.charAt(i));
            }
            return result;
        }).toArray(Square[][]::new);

        // find neighbors
        neighbors(map, false);

        // walk from all "a" Squares
        final Square starter = new Square((char) ('a'-1));
        Arrays.stream(map)
              .flatMap(Arrays::stream)
              .filter(square -> square.height == 0)
              .forEach(square -> square.walk(starter));

        return endSquare.distance;
    }

    private class Square implements Neighbor<Square> {
        int height;
        int distance = Integer.MAX_VALUE;
        List<Square> neighbors = new ArrayList<>(4);

        public Square(char heightIdentifier) {
            if (heightIdentifier == 'S') {
                startSquare = this;
                heightIdentifier = 'a';
            } else if (heightIdentifier == 'E') {
                endSquare = this;
                heightIdentifier = 'z';
            }
            height = heightIdentifier - 'a';
        }

        /**
         * find the distance to the start for every square
         * @param previous
         */
        public void walk(Square previous) {
            if (previous.height == -1) { // special case for first square
                previous.distance = -1;
            }
            if (previous.height >= this.height - 1) {
                // stop if this has already been visited from a nearer square
                if (this.distance <= previous.distance + 1) 
                    return;

                this.distance = previous.distance + 1;
                neighbors.forEach(neighbor -> neighbor.walk(this));
            }
        }

        @Override
        public void addNeighbor(Square neighbor) {
            this.neighbors.add(neighbor);
        }

        @Override
        public Square identity() {
            return this;
        }
    }
}
