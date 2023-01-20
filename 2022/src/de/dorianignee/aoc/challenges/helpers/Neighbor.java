package de.dorianignee.aoc.challenges.helpers;

/**
 * This can be used, so elements of a two-dimensional grid can access their neighbor elements
 */
public interface Neighbor<T> {
    public void addNeighbor(T neighbor);
}