package de.dorianignee.aoc.challenges;

import java.util.*;

import de.dorianignee.aoc.challenges.helpers.Point;

public class Day9 extends Aoc {

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

    private static class RopePart {
        Point position = new Point(0, 0);

        Set<Point> visitedPositions = new HashSet<>(List.of(new Point(0, 0)));
        RopePart follower = null;

        private static enum Direction {
            RIGHT(new Point(1, 0)),
            LEFT(new Point(-1,0)),
            UP(new Point (0,1)),
            DOWN(new Point (0,-1));

            private Point vector;

            Direction(Point vector) {
                this.vector = vector;
            }

            public Point next(Point from) {
                return from.translate(vector);
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

        private void follow(Point head) {
            int dx = Integer.signum(head.x() - position.x());
            int dy = Integer.signum(head.y() - position.y());
            Point vector = new Point(dx, dy);

            // if distance is more than one square, follow head
            if (position.verticalDistance(head) > 1 || position.horizontalDistance(head) > 1) {
                position = position.translate(vector);

                if (follower == null) {
                    visitedPositions.add(position); // we only need to track the positions of the last node
                } else {
                    follower.follow(position);
                }
            }
        }
    }
}
