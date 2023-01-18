package de.dorianignee.aoc.challenges;

import java.util.Comparator;

public class Day1 extends Aoc {

    /**
     * Today we have to find the elve with the highest sum of calories and output that sum
     */
    @Override
    public int challenge1() {
        return blocks()
            .mapToInt(block->ints(block).sum())
            .max()
            .getAsInt();
    }

    /**
     * The second task of the day is to find sum of the top 3 elves calories
     */
    @Override
    public int challenge2() {
        return blocks()
            .map(block->ints(block).sum())
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .mapToInt(Integer::intValue)
            .sum();
    }
}
