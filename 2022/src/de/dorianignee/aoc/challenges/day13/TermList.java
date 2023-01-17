package de.dorianignee.aoc.challenges.day13;

import java.util.*;
import java.text.*;

public class TermList extends TermItem {
    private List<TermItem> items = new LinkedList<>();

    /**
     * Parse the term
     * @param definition
     */
    public TermList(String definition) {
        this(new StringCharacterIterator(definition));
    }

    public TermList(StringCharacterIterator iterator) {
        iterator.next();
        while (iterator.current() != ']') {
            switch (iterator.current()) {
                case '[' -> {
                    items.add(new TermList(iterator));
                    iterator.next();
                }
                case ' ', ',' -> iterator.next();
                default -> { // can only be a digit
                    StringBuilder number = new StringBuilder();
                    number.append(iterator.current());
                    while(Character.isDigit(iterator.next())) {
                        number.append(iterator.current());
                    }
                    items.add(new TermInt(Integer.parseInt(number.toString())));
                }
            }
        }
    }

    public TermList(TermInt value) {
        items.add(value);
    }

    @Override
    public int compareTo(TermItem termItem) {
        if (termItem instanceof TermInt termInt)
            return compareTo(new TermList(termInt));

        TermList other = (TermList) termItem;
        var leftItems = this.items.listIterator();
        var rightItems = other.items.listIterator();

        // both have items: compare items
        while (leftItems.hasNext() && rightItems.hasNext()) {
            int comparison = leftItems.next().compareTo(rightItems.next());
            if (comparison != 0) 
                return comparison;
        }

        // this ran out of items: equal if other also ran out, else this is smaller
        if (!leftItems.hasNext())
            return rightItems.hasNext()?-1:0;

        // other ran out of items: this is greater
        return 1;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermItem other)
            return this.compareTo(other) == 0;
        return false;
    }
}