package de.dorianignee.aoc.challenges;

import java.util.*;
import java.util.regex.*;
import java.util.stream.IntStream;

import de.dorianignee.aoc.challenges.helpers.*;

public class Day15 extends Aoc {
    List<Sensor> sensors;

    /**
     * Todays first challenge is to find out where no beacon can be in order to find out
     * where the beacon is, that sends a distress signal
     */
    @Override
    public int challenge1() {
        sensors = lines().map(Sensor::new).toList();
        int line = useTestInput? 10 : 2000000;

        // Calculate, which range overlaps in the specified line for each sensor
        List<Range> ranges = sensors.stream()
            .map(sensor -> sensor.overlapInLine(line))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .sorted(Comparator.comparing(Range::start))
            .toList();
        
        // try to merge the ranges
        List<Range> mergedRanges = new LinkedList<>();
        Range previousRange = ranges.get(0);

        for (Range range: ranges) {
            if (previousRange.mergeableWith(range)) {
                previousRange = previousRange.mergeWith(range);
            } else {
                mergedRanges.add(previousRange);
                previousRange = range;
            }
        }
        mergedRanges.add(previousRange);

        // get sum of range lengths
        int sumOfRanges = mergedRanges.stream().mapToInt(Range::length).sum();

        // subtract known beacons in line and range
        int subtractBeacons = (int) sensors.stream()
            .map(sensor -> sensor.closestBeacon)
            .filter(beacon -> beacon.y() == line)
            .distinct()
            .filter(beacon -> mergedRanges.stream().anyMatch(range -> range.contains(beacon.x())))
            .count();

        // return sum of range lengths
        return sumOfRanges - subtractBeacons;
    }

    /**
     * The second challenge of the day is to find the only position in range 0, 4000000
     * for x and y that is not covered by any sensor
     */
    @Override
    public String strChallenge2() {
        sensors = lines().map(Sensor::new).toList();
        int bound = useTestInput? 20 : 4000000;

        // parallel search lines for a gap
        Point gap = IntStream.rangeClosed(0, bound)
            .parallel()
            .mapToObj(this::findBeaconInLine)
            .filter(Optional::isPresent)
            .findAny()
            .get().get();

        // return tuning frequency
        return Long.toString(gap.x() * 4000000L + gap.y());
    }

    private Optional<Point> findBeaconInLine(int line) {
        // Calculate, which range overlaps in the specified line for each sensor
        List<Range> ranges = sensors.stream()
            .map(sensor -> sensor.overlapInLine(line))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .sorted(Comparator.comparing(Range::start))
            .toList();
        
        // try to merge the ranges
        Range previousRange = ranges.get(0);

        for (Range range: ranges) {
            if (previousRange.mergeableWith(range)) {
                previousRange = previousRange.mergeWith(range);
            } else if (previousRange.end() < 0 && range.start() <= 0) {
                previousRange = range;
            } else {
                return Optional.of(new Point(range.start()-1,line));
            }
        }
        return Optional.empty();
    }

    private static class Sensor {
        public final Point position;
        public final Point closestBeacon;

        private static final Pattern definitionPattern = 
            Pattern.compile("Sensor at x=(?<SensorX>-?\\d+), y=(?<SensorY>-?\\d+): closest beacon is at x=(?<BeaconX>-?\\d+), y=(?<BeaconY>-?\\d+)");

        public Sensor(String definition) {
            Matcher matcher = definitionPattern.matcher(definition);
            matcher.find();

            position = new Point(
                Integer.parseInt(matcher.group("SensorX")), 
                Integer.parseInt(matcher.group("SensorY"))
            );

            closestBeacon = new Point(
                Integer.parseInt(matcher.group("BeaconX")), 
                Integer.parseInt(matcher.group("BeaconY"))
            );
        }

        public int distanceToBeacon() {
            return position.manhattanDistance(closestBeacon);
        }

        public Optional<Range> overlapInLine(int line) {
            int overlap = distanceToBeacon() - position.verticalDistance(new Point(0, line)) ;
            return overlap < 0? Optional.empty() : Optional.of(Range.closed(position.x() - overlap, position.x() + overlap));
        }
    }
}
