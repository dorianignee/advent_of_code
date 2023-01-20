package de.dorianignee.aoc.challenges.helpers;

import java.util.*;

public record Range(int start, int end) {
    public Range(int start, int end) {
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    public boolean contains(int number) {
        return number >= start && number <= end;
    }

    public boolean intersects(Range other) {
        return this.contains(other.start) 
        || this.contains(other.end)
        || other.fullyContains(this);
    }

    public boolean fullyContains(Range other) {
        return this.contains(other.start) && this.contains(other.end);
    }

    public boolean mergeableWith(Range other) {
        return this.intersects(other)
        || this.end + 1 == other.start
        || other.end + 1 == this.start;
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
        return end - start + 1;
    }
}
