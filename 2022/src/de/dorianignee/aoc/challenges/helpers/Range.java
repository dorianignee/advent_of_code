package de.dorianignee.aoc.challenges.helpers;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * Provides some functions for doing calculations on numeric ranges
 */
public record Range(int start, int end) {
    /**
     * Constructor {@code start} and {@code end} can be swapped
     * @param start starting index of {@link Range} (inclusive)
     * @param end ending index of {@link Range} (exclusive)
     */
    public Range(int start, int end) {
        if (start == end) throw new InvalidParameterException("Null length Range is not allowed");
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    public static Range closed(int start, int end) {
        int _start = Math.min(start, end);
        int _end = Math.max(start, end);
        return new Range(_start, _end + 1);
    }

    /**
     * check if a number is inside the given {@link Range}
     * @param number the number to check
     * @return true if the number is inside the given {@link Range} else false
     */
    public boolean contains(int number) {
        return number >= start && number < end;
    }

    /**
     * 
     */
    public boolean intersects(Range other) {
        return this.contains(other.start) 
        || this.contains(other.end - 1)
        || other.fullyContains(this);
    }

    public boolean fullyContains(Range other) {
        return this.contains(other.start) && this.contains(other.end-1);
    }

    public boolean mergeableWith(Range other) {
        return this.intersects(other)
        || this.end == other.start
        || other.end == this.start;
    }

    public Range mergeWith(Range other) {
        return tryMergeWith(other).orElseThrow(() -> new IllegalArgumentException(this + " is not mergeable with " + other));
    }

    public Optional<Range> tryMergeWith(Range other) {
        if (!this.mergeableWith(other)) 
            return Optional.empty();

        int start = Math.min(this.start, other.start);
        int end = Math.max(this.end, other.end);

        return Optional.of(new Range(start, end));
    }

    public int length() {
        return end - start;
    }
}
