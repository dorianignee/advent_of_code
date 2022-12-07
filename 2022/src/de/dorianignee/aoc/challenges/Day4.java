package de.dorianignee.aoc.challenges;

public class Day4 extends Aoc {
    /**
     * Standard main-method for running the days directly
     * @param args are ignored
     */
    public static void main(String[] args) {
        new Day4().solve(4);
    }

    /**
     * The first challenge of today asks us to find completely overlapping sections of two elves
     */
    @Override
    public int challenge1() {
        return (int) lines()
            .filter(line -> {
                String[] pair = line.split(",");
                Section elve1 = new Section(pair[0]);
                Section elve2 = new Section(pair[1]);
                return elve1.fullyContains(elve2) || elve2.fullyContains(elve1);
            })
            .count();
    }

    /**
     * In the second challenge we will count sections that overlap at all
     */
    @Override
    public int challenge2() {
        return (int) lines()
            .filter(line -> {
                String[] pair = line.split(",");
                return new Section(pair[0]).overlaps(new Section(pair[1]));
            })
            .count();
    }

    class Section {
        int start;
        int end;
        
        public Section(String section) {
            String[] range = section.split("\\-");
            this.start = Integer.parseInt(range[0]);
            this.end = Integer.parseInt(range[1]);
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public boolean fullyContains(Section other) {
            return other.start >= this.start && other.end <= this.end;
        }

        public boolean overlaps(Section other) {
            return (other.start >= this.start && other.start <= this.end) ||
                (other.end >= this.start && other.end <= this.end) ||
                (other.start < this.start && other.end > this.end);
        }
    }
}
