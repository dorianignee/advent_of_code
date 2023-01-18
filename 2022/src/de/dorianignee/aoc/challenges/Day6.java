package de.dorianignee.aoc.challenges;

import java.util.*;

public class Day6 extends Aoc {

    /**
     * Today, we have to find the first occurence of four different characters in a String
     */
    @Override
    public int challenge1() {
        return getMarker(4);
    }

    /**
     * And the same, but with 14 characters
     */
    @Override
    public int challenge2() {
        return getMarker(14);
    }

    private int getMarker(int consecutiveCount) {
        String input = getInput();
        
        outer:
        for (int i = 0; i <= input.length()-consecutiveCount; ++i) {
            Set<Character> characters = new HashSet<>();
            for (int j = 0; j < consecutiveCount; ++j) {
                if (!characters.add(input.charAt(i+j))) {
                    continue outer;
                }
            }
            return i + consecutiveCount;
        }
        return -1;
    }
}
