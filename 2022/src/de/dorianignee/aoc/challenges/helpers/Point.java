package de.dorianignee.aoc.challenges.helpers;

/**
 * Coordinates of a point on a 2D map
 */
public record Point(int x, int y) {

    /**
     * Calculates the horizontal distance (i.e. the sum of deviation in the x axis) between two {@link Point}s
     * @param other the {@link Point} to calculate the distance to
     * @return the horizontal distance between this and the other {@link Point}
     */
    public int horizontalDistance(Point other) {
        return Math.abs(this.x - other.x);
    }

    /**
     * Calculates the vertical distance (i.e. the sum of deviation in the y axis) between two {@link Point}s
     * @param other the {@link Point} to calculate the distance to
     * @return the vertical distance between this and the other {@link Point}
     */
    public int verticalDistance(Point other) {
        return Math.abs(this.y - other.y);
    }

    /**
     * Calculates the manhatten distance (i.e. the sum of deviation in the x and the y axis) between two {@link Point}s
     * @param other the {@link Point} to calculate the distance to
     * @return the manhattan distance between this and the other {@link Point}
     */
    public int manhattanDistance(Point other) {
        return horizontalDistance(other) + verticalDistance(other);
    }

    /**
     * Calculates the minimum x and y values of two {@link Point}s
     * These are the coordinates of the upper left corner of a rectangle between those {@link Point}s
     * @param other the {@link Point} to compare
     * @return a Point with the minimum x and y values of two {@link Point}s
     */
    public Point minBounds(Point other) {
        int x = Math.min(this.x, other.x);
        int y = Math.min(this.y, other.y);

        return new Point(x, y);
    }

    /**
     * Calculates the maximum x and y values of two {@link Point}s
     * These are the coordinates of the lower right corner of a rectangle between those {@link Point}s
     * @param other the {@link Point} to compare
     * @return a Point with the maximum x and y values of two {@link Point}s
     */
    public Point maxBounds(Point other) {
        int x = Math.max(this.x, other.x);
        int y = Math.max(this.y, other.y);

        return new Point(x, y);
    }

    /**
     * Returns a new {@link Point} that is moved by the distance defined by {@code vector}
     * @param vector the distance this {@link Point} should be moved
     * @return a new {@link Point} that is moved by the distance defined by {@code vector}
     */
    public Point translate(Point vector) {
        return new Point(this.x + vector.x, this.y + vector.y);
    }
}