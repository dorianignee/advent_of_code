package de.dorianignee.aoc.challenges;

import java.util.stream.Stream;

public class Day10 extends Aoc {

    /**
     * Todays first challenge is to find the sum of all "signal strengths" of every 40th cycle of a cpu,
     * beginning with cycle 20
     */
    @Override
    public int challenge1() {
        int cycle = 1;
        int value = 1;

        int relevantCycle = 20;
        int result = 0;

        for (String line: iterableStream(lines())) {
            int prevValue = value;

            if(line.equals("noop")) {
                ++cycle;
            } else {
                cycle += 2;
                value += Integer.parseInt(line.substring(5));
            }

            if(cycle == relevantCycle) {
                result += relevantCycle * value;
                relevantCycle += 40;
            } else if (cycle -1 == relevantCycle) {
                result += relevantCycle * prevValue;
                relevantCycle += 40;
            }
        }

        return result;
    }

    /**
     * The second task of the day is to render the contents of a screen
     */
    @Override
    public String strChallenge2() {
        StringBuilder builder = new StringBuilder();
        int cycle = 0;
        int spritePosition = 1; 

        for (String line: iterableStream(lines())) {
            if (line.equals("noop")) {
                drawPixel(builder, cycle++, spritePosition);
            } else {
                drawPixel(builder, cycle++, spritePosition);
                drawPixel(builder, cycle++, spritePosition);
                spritePosition += Integer.parseInt(line.substring(5));
            }
        }
        return builder.toString();
    }

    /**
     * Appends a "." or a "#" to the {@code builder} depending on the spritePosition
     * Automatically adds a newline every 40 cycles
     * @param builder the StringBuilder to append the character
     * @param cycle the cycle in which the cpu is in
     * @param spritePosition the current sprite X-position
     */
    private void drawPixel(StringBuilder builder, int cycle, int spritePosition) {
        int rayPosition = cycle % 40;

        if (Math.abs(rayPosition - spritePosition) <= 1) {
            builder.append('#');
        } else {
            builder.append('.');
        }

        if (rayPosition == 39) 
            builder.append('\n');
    }

    /**
     * Wrapper for making a stream iterable without converting it to an Array or Collection
     * @param <T> The type that is used by the stream
     * @param stream the stream that should be iterated through
     * @return an iterable of the stream
     */
    private <T> Iterable<T> iterableStream(Stream<T> stream) {
        return stream::iterator;
    }
}
