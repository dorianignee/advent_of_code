package de.dorianignee.aoc.tests.day13;

import static org.junit.Assert.*;
import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import de.dorianignee.aoc.challenges.day13.TermList;

public class TermListTest {
    @Test
    public void parse_empty_list() {
        assertEquals("[]", new TermList("[]").toString());
    }

    @Test
    public void parse_single_number() {
        assertEquals("[5]", new TermList("[5]").toString());
    }

    @Test
    public void parse_multiple_numbers() {
        assertEquals("[5, 3, 7]", new TermList("[5,3,7]").toString());
    }

    @Test
    public void parse_multi_digit_numbers() {
        assertEquals("[12, 5, 234]", new TermList("[12,5,234]").toString());
    }

    @Test
    public void parse_inner_empty_lists() {
        assertEquals("[[], []]", new TermList("[[],[]]").toString());
    }

    @Test
    public void parse_mixed_lists() {
        assertEquals("[[[1, 15], 454, [33]], 6, [4, [3767, 2]]]", new TermList("[[[1,15],454,[33]],6,[4,[3767,2]]]").toString());
    }

    @Test
    public void compare_single_values() {
        assertTrue(new TermList("[5]")
        .compareTo(new TermList("[3]")) > 0);
    }

    @Test
    public void compare_multiple_values() {
        assertTrue(new TermList("[1,1,3,1,1]")
        .compareTo(new TermList("[1,1,5,1,1]")) < 0);
    }

    @Test
    public void compare_run_out_of_values() {
        assertTrue(new TermList("[7,7,7,7]")
        .compareTo(new TermList("[7,7,7]")) > 0);
    }

    @Test
    public void compare_empty_list_to_integer() {
        assertTrue(new TermList("[]")
        .compareTo(new TermList("[3]")) < 0);
    }

    @Test
    public void compare_run_out_of_nested_lists() {
        assertTrue(new TermList("[[[]]]")
        .compareTo(new TermList("[[]]")) > 0);
    }

    @Test
    public void compare_list_to_integer() {
        assertTrue(new TermList("[[[2]]])")
        .compareTo(new TermList("[2]")) == 0);
    }

    @Test
    public void compare_list_to_nested_list() {
        assertTrue(new TermList("[1,2,3]")
        .compareTo(new TermList("[1,[2,[3]]]")) < 0);
    }

    @Test
    public void sort_simple() {
        TermList[] lists = {
            new TermList("[1,1,3,1,1]"),
            new TermList("[7,7,7,7]"),
            new TermList("[3]"),
            new TermList("[1,1,5,1,1]"),
            new TermList("[7,7,7]")
        };
        Arrays.sort(lists);

        assertArrayEquals(new TermList[] {
            new TermList("[1,1,3,1,1]"),
            new TermList("[1,1,5,1,1]"),
            new TermList("[3]"),
            new TermList("[7,7,7]"),
            new TermList("[7,7,7,7]")
        }, lists);
    }

    public static String[] complexLists = {
        "[[1],[2,3,4]]", "[[1],4]",
        "[[8,7,6]]", "[9]",
        "[[4,4],4,4]", "[[4,4],4,4,4]",
        "[1,[2,[3,[4,[5,6,0]]]],8,9]", "[1,[2,[3,[4,[5,6,7]]]],8,9]"
    };

    @Test
    public void sort_complex() {
        TermList[] lists = Arrays.stream(complexLists)
                                 .map(TermList::new)
                                 .sorted()
                                 .toArray(TermList[]::new);

        assertArrayEquals(new TermList[] {
            new TermList("[[1],[2,3,4]]"),
            new TermList("[1,[2,[3,[4,[5,6,0]]]],8,9]"),
            new TermList("[1,[2,[3,[4,[5,6,7]]]],8,9]"),
            new TermList("[[1],4]"),
            new TermList("[[4,4],4,4]"),
            new TermList("[[4,4],4,4,4]"),
            new TermList("[[8,7,6]]"),
            new TermList("[9]")
        }, lists);
    }

    // Compare with left side always smaller
    @ParameterizedTest
    @MethodSource("complexListsSource")
    public void compare_complex_lists(String def1, String def2) {
        assertTrue(new TermList(def1).compareTo(new TermList(def2)) < 0);
    }

    public static Stream<Arguments> complexListsSource() {
        Iterator<String> lists = Arrays.stream(complexLists).iterator();
        return Stream.generate(()->Arguments.of(lists.next(),lists.next())).limit(complexLists.length/2);
    }
}
