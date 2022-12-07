package de.dorianignee.aoc.tests;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.dorianignee.aoc.challenges.*;

public class AocTest {
    /**
     * Test all days with int results
     * @param day an instance of the day to test
     * @param result1 expected result for the first challenge
     * @param result2 expected result for the second challenge
     */
    @ParameterizedTest
    @MethodSource("intDays")
    public void testIntDays(Aoc day, int result1, int result2) {
        assertEquals(day.challenge1(), result1);
        assertEquals(day.challenge2(), result2);
    }

    /**
     * MethodSource for testIntDays method
     * @return a stream of Day objects and their expected results
     */
    private static Stream<Arguments> intDays() {
        return Stream.of(
            Arguments.of(new Day1().prepareTest(1), 24000, 45000),
            Arguments.of(new Day2().prepareTest(2), 15, 12),
            Arguments.of(new Day3().prepareTest(3), 157, 70),
            Arguments.of(new Day4().prepareTest(4), 2, 4)
        );
    }
}
