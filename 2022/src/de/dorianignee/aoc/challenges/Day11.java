package de.dorianignee.aoc.challenges;

import java.util.*;
import java.util.function.*;

public class Day11 extends Aoc {
    /**
     * Standard main-method for running the days directly
     * @param args are ignored
     */
    public static void main(String[] args) {
        new Day11().solve(11);
    }

    /**
     * Todays first challenge is to determine the two monkeys that tease you the most
     */
    @Override
    public int challenge1() {
        // build list of monkeys
        List<Monkey> monkeys = blocks().map(Monkey::new).toList();
        monkeys.forEach(monkey -> monkey.setMonkeyObjects(monkeys));

        // let monkeys play 20 rounds
        for (int i = 0; i < 20; ++i) {
            monkeys.forEach(monkey -> monkey.takeTurn(true, Integer.MAX_VALUE));
        }

        // return the two most active monkeys
        return monkeys.stream()
                      .sorted(Comparator.comparing(Monkey::getInspectionCount).reversed())
                      .limit(2)
                      .mapToInt(Monkey::getInspectionCount)
                      .reduce((a, b) -> a * b)
                      .getAsInt();
    }

    /**
     * The second challenge is the same thing as the first, only with 10000 rounds
     * So we need to calculate the worry level a little different
     */
    @Override
    public String strChallenge2() {
        // build list of monkeys
        List<Monkey> monkeys = blocks().map(Monkey::new).toList();
        monkeys.forEach(monkey -> monkey.setMonkeyObjects(monkeys));

        // get main mod (a number that is divisible by all monkey's test numbers)
        long mainMod = monkeys.stream().mapToLong(monkey -> monkey.testMod).reduce((a, b) -> a * b).getAsLong();

        // let monkeys play 10000 rounds
        for (int i = 0; i < 10000; ++i) {
            monkeys.forEach(monkey -> monkey.takeTurn(false, mainMod));
        }

        // return the two most active monkeys
        return Long.toString(monkeys.stream()
                      .sorted(Comparator.comparing(Monkey::getInspectionCount).reversed())
                      .limit(2)
                      .mapToLong(Monkey::getInspectionCount)
                      .reduce((a, b) -> a * b)
                      .getAsLong()
        );
    }

    private static class Monkey {
        List<Long> items;
        LongUnaryOperator operation;
        int testMod; // test is always "divisible by x", so we only need to extract x
        Monkey trueMonkey; // Throw item to this monkey if test succeeds
        Monkey falseMonkey; // Throw item to this monkey if test fails
        int trueMonkeyId; // Id of trueMonkey (monkey can only be referenced, when it's instantiated)
        int falseMonkeyId; // Id of falseMonkey

        int inspectionCount = 0;

        public Monkey(String definition) {
            String[] lines = definition.lines().toArray(String[]::new);

            // Get starting items
            String[] strItems = lines[1].trim().substring(16).split(", ");
            items = new ArrayList<>(Arrays.stream(strItems).map(Long::valueOf).toList());

            // Get operation
            String[] strOperation = lines[2].trim().substring(21).split(" "); // beginning at operator (for example {"*", "7"})
            if (strOperation[0].charAt(0) == '+') {
                if (strOperation[1].equals("old")) {
                    operation = old -> old + old;
                } else {
                    operation = old -> old + Integer.parseInt(strOperation[1]);
                }
            } else {
                if (strOperation[1].equals("old")) {
                    operation = old -> old * old;
                } else {
                    operation = old -> old * Integer.parseInt(strOperation[1]);
                }
            }

            // Get test
            testMod = Integer.parseInt(lines[3].trim().substring(19));

            // Get monkeyIds (Monkey objects are assigned, when all of them are instanciated)
            trueMonkeyId = Integer.parseInt(lines[4].trim().substring(25));
            falseMonkeyId = Integer.parseInt(lines[5].trim().substring(26));
        }

        /**
         * The list of monkeys doesn't exist yet, when the monkeys are created,
         * so we need to assign them afterwards
         * @param monkeys the list of all monkeys
         */
        public void setMonkeyObjects(List<Monkey> monkeys) {
            trueMonkey = monkeys.get(trueMonkeyId);
            falseMonkey = monkeys.get(falseMonkeyId);
        }

        /**
         * tell this monkey to inspect all objects and throw them around
         * @param withDivision apply division by 3 (for first challenge)
         */
        public void takeTurn(boolean withDivision, long mod) {
            for (long worryLevel: items) {
                worryLevel = operation.applyAsLong(worryLevel);
                if (withDivision)
                    worryLevel /= 3;

                // calculations will still be valid, if we apply the mod to the worry level
                // so, let's do it
                worryLevel %= mod;
                if (worryLevel % testMod == 0) {
                    trueMonkey.catchItem(worryLevel);
                } else {
                    falseMonkey.catchItem(worryLevel);
                }
            }

            inspectionCount += items.size();
            items.clear();
        }

        /**
         * catch an item from another monkey
         * @param item the item to catch
         */
        public void catchItem(long item) {
            items.add(item);
        }

        public int getInspectionCount() {
            return inspectionCount;
        }
    }
}
