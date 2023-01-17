package de.dorianignee.aoc.challenges.day13;

public class TermInt extends TermItem {
    int value;

    public TermInt(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(TermItem termItem) {
        if (termItem instanceof TermInt termInt)
            return Integer.compare(this.value, termInt.value);

        return new TermList(this).compareTo(termItem);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermInt otherInt)
            return this.value == otherInt.value;

        else if (obj instanceof TermItem other)
            return this.compareTo(other) == 0;

        return false;
    }
}