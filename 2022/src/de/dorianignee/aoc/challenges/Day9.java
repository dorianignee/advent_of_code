package de.dorianignee.aoc.challenges;

import java.util.*;

public class Day9 extends Aoc {
    /**
     * Standard main-method for running the days directly
     * @param args are ignored
     */
    public static void main(String[] args) {
        new Day9().solve(9);
    }

    /**
     * todays challenge is to count the positions, a tail of a rope visited
     * (for better understanding, look at the challenge on https://adventofcode.com/2022/day/9)
     */
    @Override
    public int challenge1() {
        RopePart head = new RopePart();
        RopePart tail = new RopePart();
        
        head.setFollower(tail);

        lines().forEach(head::move);

        return tail.getVisitedPositionsCount();
    }

    /**
     * the second challenge is to do the same thing with a rope of length 10
     */
    @Override
    public int challenge2() {
        // create 10 rope parts and let each follow the previous one
        RopePart[] ropeParts = new RopePart[10];
        for (int i = 0; i < ropeParts.length; i++) {
            ropeParts[i] = new RopePart();
            if (i > 0) 
                ropeParts[i-1].setFollower(ropeParts[i]);
        }

        RopePart head = ropeParts[0];

        lines().forEach(head::move);

        return ropeParts[9].getVisitedPositionsCount();
    }

    private static record Position(int x, int y) {}

    private static class RopePart {
        Position position = new Position(0, 0);

        Set<Position> visitedPositions = new HashSet<>(List.of(new Position(0, 0)));
        RopePart follower = null;

        private static enum Direction {
            RIGHT(1, 0),
            LEFT(-1,0),
            UP(0,1),
            DOWN(0,-1);

            private int dx;
            private int dy;

            Direction(int dx, int dy) {
                this.dx = dx;
                this.dy = dy;
            }

            public Position next(Position from) {
                return new Position(from.x + dx, from.y + dy);
            }
        }

        public void move(String command) {
            String[] commandTokens = command.split(" ");
            String directionString = commandTokens[0];
            int steps = Integer.parseInt(commandTokens[1]);

            Direction direction = switch (directionString) {
                case "R" -> Direction.RIGHT;
                case "L" -> Direction.LEFT;
                case "U" -> Direction.UP;
                case "D" -> Direction.DOWN;
                default -> throw new IllegalArgumentException("Direction " + directionString + " is unknown.");
            };

            for (int i = 0; i < steps; ++i) {
                var nextPosition = direction.next(this.position);
                this.position = nextPosition;
                follower.follow(nextPosition);
            }
        }

        public void setFollower(RopePart follower) {
            this.follower = follower;
        }

        public int getVisitedPositionsCount() {
            return visitedPositions.size();
        }

        private void follow(Position head) {
            int dx = Integer.signum(head.x - position.x);
            int dy = Integer.signum(head.y - position.y);

            // if distance is more than one square, follow head
            if (Math.abs(head.x - position.x) > 1 || Math.abs(head.y - position.y) > 1) {
                position = new Position(position.x + dx, position.y + dy);

                if (follower == null) {
                    visitedPositions.add(position); // we only need to track the positions of the last node
                } else {
                    follower.follow(position);
                }
            }
        }
    }
}
