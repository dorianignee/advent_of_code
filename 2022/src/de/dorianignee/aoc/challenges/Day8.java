package de.dorianignee.aoc.challenges;

import java.util.*;

public class Day8 extends Aoc {
    /**
     * Standard main-method for running the days directly
     * @param args are ignored
     */
    public static void main(String[] args) {
        new Day8().solve(8);
    }

    /**
     * Todays first task is to count how many trees are visible from the outside of the grid
     */
    @Override
    public int challenge1() {
        // create array of trees
        var forest = getForest();

        // mark visible trees from west
        for (int y = 0; y < forest.length; ++y) {
            int currentHeight = -1;
            for (int x = 0; x < forest[y].length; ++x) {
                Tree tree = forest[y][x];
                if (tree.getHeight() > currentHeight) {
                    currentHeight = tree.getHeight();
                    tree.setVisible();
                }
            }
        }

        // mark visible trees from east
        for (int y = 0; y < forest.length; ++y) {
            int currentHeight = -1;
            for (int x = forest[y].length - 1; x >= 0 ; --x) {
                Tree tree = forest[y][x];
                if (tree.getHeight() > currentHeight) {
                    currentHeight = tree.getHeight();
                    tree.setVisible();
                }
            }
        }

        // mark visible trees from north
        for (int x = 0; x < forest[0].length; ++x) {
            int currentHeight = -1;
            for (int y = 0; y < forest.length; ++y) {
                Tree tree = forest[y][x];
                if (tree.getHeight() > currentHeight) {
                    currentHeight = tree.getHeight();
                    tree.setVisible();
                }
            }
        }

        // mark visible trees from south
        for (int x = 0; x < forest[0].length; ++x) {
            int currentHeight = -1;
            for (int y = forest.length-1; y >= 0 ; --y) {
                Tree tree = forest[y][x];
                if (tree.getHeight() > currentHeight) {
                    currentHeight = tree.getHeight();
                    tree.setVisible();
                }
            }
        }

        // count visible trees
        return (int) Arrays.stream(forest)
                           .flatMap(Arrays::stream)
                           .filter(Tree::isVisible)
                           .count();
    }

    /**
     * The second challenge of today is to find the tree with the best view
     */
    @Override
    public int challenge2() {
        var forest = getForest();

        // calculate view distances to the west
        for (int y = 0; y < forest.length; ++y) {
            int[] heightDistances = new int[10];
            for (int x = 0; x < forest[y].length; ++x) {
                visitTree(heightDistances, forest[y][x]);
            }
        }

        // calculate view distances to the east
        for (int y = 0; y < forest.length; ++y) {
            int[] heightDistances = new int[10];
            for (int x = forest[y].length - 1; x >= 0; --x) {
                visitTree(heightDistances, forest[y][x]);
            }
        }

        // calculate view distances to the north
        for (int x = 0; x < forest[0].length; ++x) {
            int[] heightDistances = new int[10];
            for (int y = 0; y < forest.length; ++y) {
                visitTree(heightDistances, forest[y][x]);
            }
        }

        // calculate view distances to the south
        for (int x = 0; x < forest[0].length; ++x) {
            int[] heightDistances = new int[10];
            for (int y = forest.length - 1; y >= 0; --y) {
                visitTree(heightDistances, forest[y][x]);
            }
        }

        // return highest scenic score
        return Arrays.stream(forest)
                     .flatMap(Arrays::stream)
                     .mapToInt(Tree::getScenicScore)
                     .max()
                     .getAsInt();
    }

    /**
     * function getForest builds up a two-dimensional array of all the trees in the forest
     * @return a two-dimensional array of all the trees in the forest
     */
    private Tree[][] getForest() {
        return lines().map(line -> {
            Tree[] treeLine = new Tree[line.length()];
            for (int i = 0; i < treeLine.length; ++i)
                treeLine[i] = new Tree(Character.getNumericValue(line.charAt(i)));

            return treeLine;
        }).toArray(Tree[][]::new);
    }

    /**
     * method visitTree calculates the scenic factor for the specified direction at the visited tree
     * the idea is to use an array with indices 0 through 9, in which the distance to the nearest
     * tree with equal or higher height than the indicated height is stored
     * @param heightDistances the array that should be incremented (by reference)
     * @param treeHeight reset all indices that are below this
     */
    private void visitTree(int[] heightDistances, Tree tree) {
        // add factor to tree
        tree.addScenicFactor(heightDistances[tree.getHeight()]);

        // increment array
        for (int i = 0; i < heightDistances.length; ++i)
            ++heightDistances[i];
        
        // reset indices below tree.height
        for (int i = 0; i <= tree.getHeight(); ++i)
            heightDistances[i] = 1;
    }

    /**
     * class Tree provides properties to check if it is visible from the outside
     * and how many other trees can be seen from it
     */
    private static class Tree {
        private int height;
        private boolean visible = false;
        private int scenicScore = 1;

        public Tree(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public boolean isVisible() {
            return visible;
        }

        public int getScenicScore() {
            return scenicScore;
        }

        public void setVisible() {
            visible = true;
        }

        public void addScenicFactor(int factor) {
            scenicScore *= factor;
        }
    }
}
