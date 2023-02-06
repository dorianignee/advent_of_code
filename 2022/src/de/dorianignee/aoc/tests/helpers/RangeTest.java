package de.dorianignee.aoc.tests.helpers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import de.dorianignee.aoc.challenges.helpers.Range;

public class RangeTest {
    @Test
    public void ranges_equal() {
        assertEquals(new Range(12, 27), new Range(12, 27));
    }

    @Test
    public void ranges_not_equal() {
        assertNotEquals(new Range(12, 27), new Range(11, 27));
    }

    @Test
    public void ranges_equal_with_swapped_start_end() {
        assertEquals(new Range(12, 27), new Range(27, 12));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 5, 10})
    public void range_contains_number(int value) {
        assertTrue(new Range(5, 11).contains(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 4, 11})
    public void range_doesnt_contain_number(int value) {
        assertFalse(new Range(5, 11).contains(value));
    }

    @Test
    public void range_intersects_large() {
        assertTrue(new Range(-3, 12).intersects(new Range(0, 15)));
    }

    @Test
    public void range_intersects_start() {
        assertTrue(new Range(-3, 12).intersects(new Range(-10, -2)));
    }

    @Test
    public void range_intersects_end() {
        assertTrue(new Range(-3, 12).intersects(new Range(11, 15)));
    }

    @Test
    public void range_intersects_containing() {
        assertTrue(new Range(-3, 12).intersects(new Range(3, 5)));
    }

    @Test
    public void range_intersects_contained_by() {
        assertTrue(new Range(3, 5).intersects(new Range(-3, 12)));
    }

    @Test
    public void range_not_intersects_far_away() {
        assertFalse(new Range(-3, 12).intersects(new Range(33, 40)));
    }

    @Test
    public void range_not_intersects_start() {
        assertFalse(new Range(-3, 12).intersects(new Range(-10, -3)));
    }

    @Test
    public void range_not_intersects_end() {
        assertFalse(new Range(-3, 12).intersects(new Range(12, 15)));
    }

    @Test
    public void range_contains_all() {
        assertTrue(new Range(-3, 12).fullyContains(new Range(-3, 12)));
    }

    @Test
    public void range_contains_start() {
        assertTrue(new Range(-3, 12).fullyContains(new Range(-3, 2)));
    }

    @Test
    public void range_contains_end() {
        assertTrue(new Range(-3, 12).fullyContains(new Range(7, 12)));
    }

    @Test
    public void range_not_contains_intersecting_start() {
        assertFalse(new Range(-3, 12).fullyContains(new Range(-4, 5)));
    }

    @Test
    public void range_not_contains_intersecting_end() {
        assertFalse(new Range(-3, 12).fullyContains(new Range(7, 13)));
    }

    @Test
    public void range_not_contains_far_away() {
        assertFalse(new Range(-3, 12).fullyContains(new Range(33, 40)));
    }

    @Test
    public void range_mergeable_intersecting() {
        assertTrue(new Range(-3, 12).mergeableWith(new Range(0, 15)));
    }

    @Test
    public void range_mergeable_start() {
        assertTrue(new Range(-3, 12).mergeableWith(new Range(-10, -2)));
    }

    @Test
    public void range_mergeable_end() {
        assertTrue(new Range(-3, 12).mergeableWith(new Range(11, 15)));
    }

    @Test
    public void range_mergeable_containing() {
        assertTrue(new Range(-3, 12).mergeableWith(new Range(3, 5)));
    }

    @Test
    public void range_mergeable_contained_by() {
        assertTrue(new Range(3, 5).mergeableWith(new Range(-3, 12)));
    }

    @Test
    public void range_mergeable_before_start() {
        assertTrue(new Range(-3, 12).mergeableWith(new Range(-10, -3)));
    }

    @Test
    public void range_mergeable_after_end() {
        assertTrue(new Range(-3, 12).mergeableWith(new Range(12, 15)));
    }

    @Test
    public void range_not_mergeable_far_away() {
        assertFalse(new Range(-3, 12).mergeableWith(new Range(33, 40)));
    }

    @Test
    public void range_not_mergeable_start() {
        assertFalse(new Range(-3, 12).mergeableWith(new Range(-10, -4)));
    }

    @Test
    public void range_not_mergeable_end() {
        assertFalse(new Range(-3, 12).mergeableWith(new Range(13, 15)));
    }

    // Method source for mergeWith and tryMergeWith tests
    // can be merged with Range(-3, 12)
    public static Stream<Arguments> mergeableRanges() {
        return Stream.of(
            Arguments.of(new Range(-3, 15), new Range(0, 15)),
            Arguments.of(new Range(-10, 12), new Range(-10, -2)),
            Arguments.of(new Range(-3, 15), new Range(11, 15)),
            Arguments.of(new Range(-3, 12), new Range(3, 5)),
            Arguments.of(new Range(-3, 12), new Range(-3, 12)),
            Arguments.of(new Range(-10, 20), new Range(-10, 20)),
            Arguments.of(new Range(-10, 12), new Range(-10, -3)),
            Arguments.of(new Range(-3, 15), new Range(12, 15))
        );
    }

    // Method source for mergeWith and tryMergeWith tests
    // can not be merged with Range(-3, 12)
    public static Stream<Arguments> nonMergeableRanges() {
        return Stream.of(
            Arguments.of(new Range(33, 40)),
            Arguments.of(new Range(-10, -4)),
            Arguments.of(new Range(13, 15))
        );
    }

    @ParameterizedTest
    @MethodSource("mergeableRanges")
    public void merge_ranges(Range expected, Range toMerge) {
        assertEquals(expected, new Range(-3, 12).mergeWith(toMerge));
    }

    @ParameterizedTest
    @MethodSource("nonMergeableRanges")
    public void fail_merge_ranges(Range toMerge) {
        assertThrows(IllegalArgumentException.class, () -> new Range(-3, 12).mergeWith(toMerge));
    }

    @ParameterizedTest
    @MethodSource("mergeableRanges")
    public void try_merge_ranges(Range expected, Range toMerge) {
        assertEquals(Optional.of(expected), new Range(-3, 12).tryMergeWith(toMerge));
    }

    @ParameterizedTest
    @MethodSource("nonMergeableRanges")
    public void try_merge_non_mergeable_ranges(Range toMerge) {
        assertEquals(Optional.empty(), new Range(-3, 12).tryMergeWith(toMerge));
    }

    // Method source for length Tests
    public static Stream<Arguments> lengths() {
        return Stream.of(
            Arguments.of(15, new Range(0, 15)),
            Arguments.of(7, new Range(-10, -3)),
            Arguments.of(3, new Range(12, 15)),
            Arguments.of(2, new Range(3, 5)),
            Arguments.of(15, new Range(-3, 12)),
            Arguments.of(6, new Range(-10, -4)),
            Arguments.of(2, new Range(13, 15)),
            Arguments.of(7, new Range(33, 40)),
            Arguments.of(5, new Range(-10, -5)),
            Arguments.of(1, new Range(14, 15))
        );
    }

    @ParameterizedTest
    @MethodSource("lengths")
    public void calculate_lengths(int expected, Range range) {
        assertEquals(expected, range.length());
    }
}
