package de.dorianignee.aoc.challenges;

import java.util.*;
import java.util.stream.Stream;

public class Day14 extends Aoc {
    /**
     * Todays first challenge is to count the number of sand grains that a pre-defined structure can hold
     * before the sand starts to fall into the void
     */
    @Override
    public int challenge1() {   
        // read structures     
        List<Structure> structures = lines().map(Structure::new).toList();

        // return grains
        return new Slice(structures).pourSand();
    }

    /**
     * The second challenge is to add a floor and count the grains until the source is blocked
     */
    @Override
    public int challenge2() {
        // read structures
        List<Structure> structures = new LinkedList<>(lines().map(Structure::new).toList());
        int height = structures.parallelStream()
                               .mapToInt(structure -> structure.bounds().y())
                               .max()
                               .getAsInt();

        // add floor
        height += 2;
        structures.add(new Structure(String.format("%d,%d -> %d,%d", 500-height, height, 500+height, height)));

        // return grains
        return new Slice(structures).pourSand();
    }

    private static class Structure {
        Point[] points;

        public Structure(String definition) {
            Scanner scanner = new Scanner(definition);
            scanner.useDelimiter(" -> ");

            points = scanner.tokens().map(point -> {
                String[] coordinates = point.split(",");
                return new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            }).toArray(Point[]::new);
            scanner.close();
        }

        public Point bounds() {
            int x = Arrays.stream(points)
                          .mapToInt(Point::x)
                          .max()
                          .getAsInt();

            int y = Arrays.stream(points)
                          .mapToInt(Point::y)
                          .max()
                          .getAsInt();

            return new Point(x, y);
        }

        public void drawInSlice(Slice slice) {
            Point previous = points[0];

            for (Point point: points) {
                // lines are always horizontal or vertical
                if (previous.x() == point.x()) {
                    // draw vertical
                    int minY = Math.min(previous.y(), point.y());
                    int maxY = Math.max(previous.y(), point.y());

                    for (int y = minY; y <= maxY; ++y) {
                        slice.set(point.x(), y);
                    }
                } else {
                    // draw horizontal
                    int minX = Math.min(previous.x(), point.x());
                    int maxX = Math.max(previous.x(), point.x());

                    slice.set(minX, maxX, point.y());
                }
                previous = point;
            }
        }
    }

    private static class Slice {
        BitSet[] slice;

        public Slice(Collection<Structure> structures) {
            // get max height of structures
            // width is automatically handled by the BitSet
            int height = structures.stream()
                                   .mapToInt(structure -> structure.bounds().y())
                                   .max()
                                   .getAsInt();

            slice = Stream.generate(BitSet::new)
                          .limit(height+1)
                          .toArray(BitSet[]::new);

            // draw in structures
            for (Structure structure: structures) {
                structure.drawInSlice(this);
            }
        }

        private boolean isFree(int x, int y) {
            return !slice[y].get(x);
        }

        public void set(int x, int y) {
            slice[y].set(x);
        }

        public void set(int fromX, int toX, int y) {
            slice[y].set(fromX, toX+1);
        }

        /**
         * count sand grains until they fall into the void
         * @return the number of sand grains before they fall into the void
         */
        public int pourSand() {
            int grains = 0;

            grainLoop:
            while (true) { // spawn grains until return
                int x = 500;
                int y = 0;
                ++grains;

                while (y < slice.length-1) {
                    // try to fall down
                    if (isFree(x, y+1)) {
                        ++y;
                        continue;
                    }

                    // try to fall down-left
                    if (isFree(x-1, y+1)) {
                        ++y;
                        --x;
                        continue;
                    }

                    // try to fall down-right
                    if (isFree(x+1, y+1)) {
                        ++y;
                        ++x;
                        continue;
                    }

                    // grain can't fall anymore, so place it on slice and pour next one
                    set(x, y);

                    // end if the source is blocked
                    if (x == 500 && y == 0)
                        return grains;
                    
                    // else pour next grain
                    continue grainLoop;
                }
                return grains-1;
            }
        }
    }

    public static void printBitSets(BitSet[] bitSets) {
        for (BitSet set: bitSets) {
            set.set(1000); // so the byte array will be long enough
            byte[] bytes = Arrays.copyOfRange(set.toByteArray(), 60, 65);
            for (byte b: bytes) {
                byte mask = 1;
                for (int i = 0; i<8; ++i) {
                    System.out.print((b & mask) != 0 ? "#": ".");
                    mask <<= 1;
                }
            }
            System.out.println();
        }
    }
}
