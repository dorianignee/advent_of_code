package de.dorianignee.aoc.tests;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.dorianignee.aoc.challenges.*;

public class AocTest {
    /**
     * MethodSource for testIntDays method
     * @return a stream of Day objects and their expected results
     */
    private static Stream<Arguments> intDays() {
        return Stream.of(
            Arguments.of(new Day1().prepareTest(1), 24000, 45000),
            Arguments.of(new Day2().prepareTest(2), 15, 12),
            Arguments.of(new Day3().prepareTest(3), 157, 70),
            Arguments.of(new Day4().prepareTest(4), 2, 4),
            Arguments.of(new Day6().prepareTest(6), 11, 26),
            Arguments.of(new Day7().prepareTest(7), 95437, 24933642),
            Arguments.of(new Day8().prepareTest(8), 21, 8),
            Arguments.of(new Day9().prepareTest(9), 88, 36)
        );
    }

    /**
     * MethodSource for testStringDays method
     * @return a stream of Day objects and their expected results
     */
    private static Stream<Arguments> stringDays() {
        return Stream.of(
            Arguments.of(new Day5().prepareTest(5), "CMZ", "MCD"),
            Arguments.of(new Day10().prepareTest(10), "13140", """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
                """),
            Arguments.of(new Day11().prepareTest(11), "10605", "2713310158")
        );
    }

    /**
     * Test all days with int results
     * @param day an instance of the day to test
     * @param result1 expected result for the first challenge
     * @param result2 expected result for the second challenge
     */
    @ParameterizedTest
    @MethodSource("intDays")
    public void testIntDays(Aoc day, int result1, int result2) {
        assertEquals(result1, day.challenge1());
        assertEquals(result2, day.challenge2());
    }

    /**
     * Test all days with String results
     * @param day an instance of the day to test
     * @param result1 expected result for the first challenge
     * @param result2 expected result for the second challenge
     */
    @ParameterizedTest
    @MethodSource("stringDays")
    public void testStringDays(Aoc day, String result1, String result2) {
        assertEquals(result1, day.strChallenge1());
        assertEquals(result2, day.strChallenge2());
    }
}
