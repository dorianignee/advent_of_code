package de.dorianignee.aoc.challenges;

import java.util.*;

import de.dorianignee.aoc.challenges.day13.*;

public class Day13 extends Aoc {
     /**
     * Standard main-method for running the days directly
     * @param args are ignored
     */
    public static void main(String[] args) {
        new Day13().solve(13);
    }

    /**
     * Todays first challenge is to compare two nested lists
     */
    @Override
    public int challenge1() {
        Equation[] equations = blocks().map(Equation::new).toArray(Equation[]::new);
        int resultSum = 0;

        for (int i = 0; i < equations.length; i++) {
            if (equations[i].isInOrder())
                resultSum += i+1;
        }

        return resultSum;
    }

    /**
     * Todays second challenge is to order the packets and return the indices of two special divider packets
     */
    @Override
    public int challenge2() {
        // Create List of Terms
        List<TermItem> items = new ArrayList<>(
            lines().filter(line -> !line.isEmpty())
                   .map(TermList::new)
                   .toList()
        );

        // no need to sort. Just count the number of smaller items for each divider
        TermItem divider2 = new TermList("[[2]]");
        TermItem divider6 = new TermList("[[6]]");

        int divider2index = (int) items.stream().filter(item -> divider2.compareTo(item) > 0).count() + 1; // +1 because solution is 1-indexed
        int divider6index = (int) items.stream().filter(item -> divider6.compareTo(item) > 0).count() + 2; // +1 because solution is 1-indexed and divider2 is not in list

        // Multiply indices
        return divider2index * divider6index;
    }

    private static class Equation {
        TermItem term1;
        TermItem term2;

        public Equation(String block) {
            var iterator = block.lines().iterator();
            term1 = new TermList(iterator.next());
            term2 = new TermList(iterator.next());
        }

        /**
         * Check if the first term is equal or smaller than the second term
         * I don't create nested lists, but only compare the characters of both terms recursively
         * @return true if the first term is equal or smaller than the second term, otherwise false
         */
        private boolean isInOrder() {
            return term1.compareTo(term2) <= 0;
        }
    }
}
