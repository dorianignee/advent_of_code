package de.dorianignee.aoc.challenges;

import java.util.*;
import java.util.stream.*;

/**
 * Some helpful functions to reduce boilerplate in the actual Challenges
 * @author Dorian Ignee
 */
public class Aoc {
    /**
     * The day of the current challenge. 
     * Set this, so the correct input file can be selected
     */
    private int day = 0;

    /**
     * Set this true, to use the files in the res/test folder
     */
    private boolean useTestInput = false;

    /**
     * Set this to custom values for debugging
     */
    private String customInput = null;

    /**
     * outputs the results of the daily challenges
     */
    public final void solve(int day) {
        this.day = day;
        if (useTestInput || customInput != null) {
            System.out.println("#### USING DEBUG INPUT ####");
        }
        System.out.println("Day" + day + " Result of challenge one:");
        System.out.println(strChallenge1());
        System.out.println();
        System.out.println("Day" + day + " Result of challenge two:");
        System.out.println(strChallenge2());
    }

    /**
     * Result of the first daily challenge
     * Override this in the subclass for string results
     * @return string result of first daily challenge if overridden. Else result of {@link #challenge1()}
     */
    public String strChallenge1() {
        return Integer.toString(challenge1());
    }

    /**
     * Result of the second daily challenge
     * Override this in the subclass for string results
     * @return string result of second daily challenge if overridden. Else result of {@link #challenge2()}
     */
    public String strChallenge2() {
        return Integer.toString(challenge2());
    }

    /**
     * Integer result of the first daily challenge
     * Override this in the subclass for integer results
     * @return integer result of first daily challenge if overridden
     * @throws NotImplementedException if neither {@link #strChallenge1()} nor {@link #challenge1()} are overridden
     */
    public int challenge1() {
        throw new NotImplementedException("Day " + day + " challenge 1 not implemented, yet.");
    }

    /**
     * Integer result of the second daily challenge
     * Override this in the subclass for integer results
     * @return integer result of second daily challenge if overridden
     * @throws NotImplementedException if neither {@link #strChallenge2()} nor {@link #challenge2()} are overridden
     */
    public int challenge2() {
        throw new NotImplementedException("Day " + day + " challenge 2 not implemented, yet.");
    }

    /**
     * Prepare this class for running a unit test
     * @param day the day that will be tested
     */
    public Aoc prepareTest(int day) {
        this.day = day;
        this.useTestInput = true;
        return this;
    }

    /**
     * Returns the input for the given day depending on {@link #customInput} and {@link #useTestInput}
     * @return the input for the given day depending on {@link #customInput} and {@link #useTestInput}
     */
    public final String getInput() {
        if (customInput != null) {
            return customInput;
        }
        if (useTestInput) {
            return readFile(String.format("/test/%02d.txt", day));
        }
        return readFile(String.format("/%02d.txt", day));
    }

    /**
     * Returns the contents of the file with the name {@code fileName}
     * @param fileName the name of the file
     * @return the contents of the file with the name {@code fileName}
     */
    private static final String readFile(String fileName) {
        var resource = Aoc.class.getResourceAsStream(fileName);
        if (resource == null) throw new RuntimeException("File " + fileName + " not found.");
        try (Scanner scanner = new Scanner(resource)) {
            scanner.useDelimiter("\\Z");
            return scanner.next();
        }
    }

    /**
     * Cuts a file or challenge input string into pieces, delimited by {@code delimiterPattern}
     * @param input the String that should be tokenized
     * @param delimiterPattern The pattern string that delimits the elements
     * @return A stream of Strings
     */
    private final Stream<String> tokens(String input, String delimiterPattern) {
        try (Scanner data = new Scanner(input)) {
            data.useDelimiter(delimiterPattern);
            // we have to take a detour, because the tokens Stream get's closed automatically when the Scanner is closed
            List<String> result = data.tokens().toList();
            return result.stream();
        }
    }

    /**
     * Simply get a Stream containing all lines of the given {@code file}
     * @return A stream of lines
     */
    public final Stream<String> lines() {
        return lines(getInput());
    }
    
    /**
     * Simply get a Stream containing all lines of the given {@code file}
     * @param input the String that should be tokenized
     * @return A stream of lines
     */
    public final Stream<String> lines(String input) {
        return tokens(input, "\\R");
    }

    /**
     * Simply get a Stream containing all lines of the given {@code file}
     * @return A stream of all integers in the file
     */
    public final IntStream ints() {
        return ints(getInput());
    }
    
    /**
     * Simply get a Stream containing all lines of the given {@code file}
     * @param input the String that should be tokenized
     * @return A stream of all integers in the file
     */
    public final IntStream ints(String input) {
        return tokens(input, "\\D+").mapToInt(Integer::parseInt);
    }

    /**
     * Simply get a Stream of blocks (Strings that are separated by a blank line)
     * @return A stream of all blocks in the file
     */
    public final Stream<String> blocks() {
        return blocks(getInput());
    }

    /**
     * Simply get a Stream of blocks (Strings that are separated by a blank line)
     * @param input the String that should be tokenized
     * @return A stream of all blocks in the file
     */
    public final Stream<String> blocks(String input) {
        return tokens(input, "\\R{2,}");
    }
}

/**
 * To show that the solution for a challenge is not implemented yet
 */
class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
        super();
    }

    public NotImplementedException(String message) {
        super(message);
    }
}