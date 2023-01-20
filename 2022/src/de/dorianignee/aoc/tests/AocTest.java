package de.dorianignee.aoc.tests;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import de.dorianignee.aoc.challenges.*;

public class AocTest {
    /**
     * MethodSource for testIntDays method
     * @return a stream of Day objects and their expected results
     */
    private static Stream<Arguments> intDays() {
        return Stream.of(
            day(1, 24000, 45000),
            day(2, 15, 12),
            day(3, 157, 70),
            day(4, 2, 4),
            day(6, 11, 26),
            day(7, 95437, 24933642),
            day(8, 21, 8),
            day(9, 88, 36),
            day(12, 31, 29),
            day(13, 13, 140),
            day(14, 24, 93)
        );
    }

    /**
     * MethodSource for testStringDays method
     * @return a stream of Day objects and their expected results
     */
    private static Stream<Arguments> stringDays() {
        return Stream.of(
            day(5, "CMZ", "MCD"),
            day(10, "13140", """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
                """),
            day(11, "10605", "2713310158"),
            day(15, "26", "56000011")
        );
    }

    /**
     * Test all days with int results
     * @param numDay the day to test
     * @param result1 expected result for the first challenge
     * @param result2 expected result for the second challenge
     */
    @ParameterizedTest
    @MethodSource("intDays")
    public void testIntDays(int numDay, int result1, int result2) throws ReflectiveOperationException {
        Aoc day = (Aoc) Class.forName("de.dorianignee.aoc.challenges.Day" + numDay).getDeclaredConstructor().newInstance();
        day.prepareTest();

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
    public void testStringDays(int numDay, String result1, String result2) throws ReflectiveOperationException {
        Aoc day = (Aoc) Class.forName("de.dorianignee.aoc.challenges.Day" + numDay).getDeclaredConstructor().newInstance();
        day.prepareTest();

        assertEquals(result1, day.strChallenge1());
        assertEquals(result2, day.strChallenge2());
    }

    /**
     * Wrapper for int tests (so the IDE shows parameter hints)
     * @param day the day to test
     * @param result1 expected result for the first challenge
     * @param result2 expected result for the second challenge
     * @return
     */
    private static Arguments day(int day, int result1, int result2) {
        return Arguments.of(day, result1, result2);
    }

    /**
     * Wrapper for String tests (so the IDE shows parameter hints)
     * @param day the day to test
     * @param result1 expected result for the first challenge
     * @param result2 expected result for the second challenge
     * @return
     */
    private static Arguments day(int day, String result1, String result2) {
        return Arguments.of(day, result1, result2);
    }
}
