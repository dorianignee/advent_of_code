package de.dorianignee.aoc.challenges;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day5 extends Aoc {
    private List<? extends Deque<Character>> stacks;

    @Override
    public String strChallenge1() {
        return prepareResults(Command::executeWithSingleContainers);
    }

    @Override
    public String strChallenge2() {
        return prepareResults(Command::executeWithBlocks);
    }

    /**
     * Executes the shared commands of todays challenges
     * @param commandExecutor a void method that executes the commands of the current challenge
     */
    private String prepareResults(Consumer<Command> commandExecutor) {
        List<String> blocks = blocks().toList();

        // build stacks
        List<String> rawStacks = blocks.get(0).lines().toList();
        int stackCount = (rawStacks.get(0).length()+1)/4;
        stacks = Stream.generate(LinkedList<Character>::new)
                       .limit(stackCount)
                       .toList();
        for (String line : rawStacks) {
            for (int stackId = 0; stackId < stackCount; ++stackId) {
                // cut out single containers out of the line String
                String rawContainer = line.substring(4*stackId, 4*stackId + 3);
                if (rawContainer.startsWith("[")) {
                    // and add them to the stack
                    stacks.get(stackId).add(rawContainer.charAt(1));
                }
            }
        }

        // execute commands
        blocks.get(1)
              .lines()
              .map(Command::new)
              .forEach(commandExecutor);

        // return uppermost containers
        return stacks.stream()
                .map(stack -> stack.pop().toString())
                .collect(Collectors.joining());
    }

    private class Command {
        int count;
        Deque<Character> from;
        Deque<Character> to;

        public Command(String commandLine)  {
            Matcher matcher = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)").matcher(commandLine);
            if (!matcher.matches()) {
                throw new IllegalArgumentException();
            }

            this.count = Integer.parseInt(matcher.group(1));
            this.from = stacks.get(Integer.parseInt(matcher.group(2))-1);
            this.to = stacks.get(Integer.parseInt(matcher.group(3))-1);
        }

        public void executeWithSingleContainers() {
            for (int i = 0; i < count; ++i) {
                to.push(from.pop());
            }
        }

        public void executeWithBlocks() {
            Deque<Character> temp = new LinkedList<Character>();
            for (int i = 0; i < count; ++i) {
                temp.push(from.pop());
            }
            while (!temp.isEmpty()) {
                to.push(temp.pop());
            }
        }        
    }
}
