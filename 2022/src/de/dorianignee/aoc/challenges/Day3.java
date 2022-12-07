package de.dorianignee.aoc.challenges;

import java.util.*;

/**
 * Today, we are comparing the two compartments of a rucksack and find the common item
 */
public class Day3 extends Aoc {
    /**
     * Standard main-method for running the days directly
     * @param args are ignored
     */
    public static void main(String[] args) {
        new Day3().solve(3);
    }
    
    @Override
    public int challenge1() {
        return lines()
            .map(Rucksack::new)
            .mapToInt(rs -> getPriority(rs.getDuplicateItems().get(0)))
            .sum();
    }

    @Override
    public int challenge2() {
        List<Rucksack> rucksacks = lines().map(Rucksack::new).toList();
        
        // Find the common item of each group
        List<Character> groups = new ArrayList<>(rucksacks.size()/3);
        for (int i = 0; i < rucksacks.size(); i+=3) {
            List<Character> commonItems = new ArrayList<>(rucksacks.get(i).getContents());
            commonItems.retainAll(rucksacks.get(i+1).getContents());
            commonItems.retainAll(rucksacks.get(i+2).getContents());
            groups.add(commonItems.get(0));
        }

        // return the sum
        return groups
            .stream()
            .mapToInt(c -> getPriority(c))
            .sum();
    }

    /**
     * The priority, in this challenge is 1..26 for lowercase letters and 27..52 for uppercase letters
     * @param the letter of the item
     * @return the priority of the item
     */
    private static int getPriority(char item) {
        return Character.isUpperCase(item) ? (item - 'A' + 27) : (item - 'a' + 1);
    }

    /**
     * Defines methods for usage with rucksacks
     */
    private static class Rucksack {
        List<Character> compartment1;
        List<Character> compartment2;
        
        /**
         * Rucksack constructor
         * @param contents the contents of the rucksack as a String
         */
        public Rucksack(String contents) {
            compartment1 = contents
                .chars()
                .limit(contents.length()/2)
                .mapToObj(c->Character.valueOf((char)c))
                .toList();

            compartment2 = contents
                .chars()
                .skip(contents.length()/2)
                .mapToObj(c->Character.valueOf((char)c))
                .toList();
        }

        public List<Character> getCompartment1() {
            return compartment1;
        }

        public List<Character> getCompartment2() {
            return compartment2;
        }

        /**
         * Getter for the combined contents of both compartments
         * @return the combined contents of both compartments
         */
        public List<Character> getContents() {
            List<Character> contents = new ArrayList<>(getCompartment1());
            contents.addAll(getCompartment2());
            return contents;
        }

        /**
         * Returns the items that are present in both compartments.
         * For this challenge, there should be only one item in the result
         * @return the items that are present in both compartments
         */
        public List<Character> getDuplicateItems() {
            List<Character> result = new ArrayList<>(compartment1);
            result.retainAll(compartment2);
            return result;
        }
    }
}
