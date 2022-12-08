package de.dorianignee.aoc.challenges;

import java.util.HashSet;
import java.util.Set;

public class Day6 extends Aoc {
    /**
     * Standard main-method for running the days directly
     * @param args are ignored
     */
    public static void main(String[] args) {
        new Day6().solve(6);
    }

    /**
     * Today, we have to find the first occurence of four different characters in a String
     */
    @Override
    public int challenge1() {
        String input = getInput();
        
        outer:
        for (int i = 0; i <= input.length()-4; ++i) {
            Set<Character> characters = new HashSet<>();
            for (int j = 0; j < 4; ++j) {
                if (!characters.add(input.charAt(i+j))) {
                    continue outer;
                }
            }
            return i + 4;
        }
        return -1;
    }

    @Override
    public int challenge2() {
        String input = getInput();
        
        outer:
        for (int i = 0; i <= input.length()-14; ++i) {
            Set<Character> characters = new HashSet<>();
            for (int j = 0; j < 14; ++j) {
                if (!characters.add(input.charAt(i+j))) {
                    continue outer;
                }
            }
            return i + 14;
        }
        return -1;
    }
}
