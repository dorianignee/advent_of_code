import java.util.*;
import java.util.function.*;

public class Day2 extends Aoc {
    /**
     * Standard main-method for running the days directly
     * @param args are ignored
     */
    public static void main(String[] args) {
        new Day2().solve(2);    
    }

    /**
     * Today, we are playing a game of Rock, Paper, Scissors
     */
    @Override
    public int challenge1() {
        return lines()
            .mapToInt(line -> new Round(line, true).getPoints())
            .sum();
    }

    /**
     * In the second challenge of the day, the rules to determine your shape are a little bit different
     */
    @Override
    public int challenge2() {
        return lines()
            .mapToInt(line -> new Round(line, false).getPoints())
            .sum();
    }

    private static class Round {
        Shape opponentShape;
        Shape myShape = Shape.ROCK;
        RoundResult roundResult = RoundResult.DRAW;
        boolean firstRules; // Solve with challenge one rules or challenge two rules
        
        public Round(String line, boolean firstRules) {
            String[] moves = line.split(" ");
            String opponentMove = moves[0];
            String myMove = moves[1];
            this.firstRules = firstRules;
    
            opponentShape = switch(opponentMove) {
                case "A" -> Shape.ROCK;
                case "B" -> Shape.PAPER;
                case "C" -> Shape.SCISSORS;
                default -> null;
            };
    
            if (firstRules) {
                myShape = switch(myMove) {
                    case "X" -> Shape.ROCK;
                    case "Y" -> Shape.PAPER;
                    case "Z" -> Shape.SCISSORS;
                    default -> null;
                };
            } else {
                roundResult = switch(myMove) {
                    case "X" -> RoundResult.LOSE;
                    case "Y" -> RoundResult.DRAW;
                    case "Z" -> RoundResult.WIN;
                    default -> null;
                };
            }
        }
    
        public int getPoints() {
            if (firstRules)
                return myShape.getTotalPoints(opponentShape);
            return roundResult.getResponse(opponentShape).getTotalPoints(opponentShape);
        }
    }
    
   private static enum Shape {
        ROCK(1, null),
        PAPER(2, ROCK),
        SCISSORS(3, PAPER);
    
        // Can't reference to an Object before it is initialized
        // So we need to reference Rock beats Scissors manually
        static {
            ROCK.beats = SCISSORS;
        }
    
        private int extraPoints;
        private Shape beats;
    
        private Shape(int extraPoints, Shape beats) {
            this.extraPoints = extraPoints;
            this.beats = beats;
        }
    
        public int getTotalPoints(Shape against) {
            int points = 0;
            if (this.beats == against) {
                points = 6;
            } else if (this == against) {
                points = 3;
            }
    
            return points + extraPoints;
        }
    
        public Shape getBeats() {
            return beats;
        }
    }
    
    private static enum RoundResult {
        WIN((me, he) -> me.getBeats() == he),
        DRAW((me, he) -> me == he),
        LOSE((me, he) -> he.getBeats() == me);
    
        private BiPredicate<Shape,Shape> response;
    
        private RoundResult(BiPredicate<Shape,Shape> response) {
            this.response = response;
        }
    
        public Shape getResponse(Shape opponentShape) {
            return Arrays.stream(Shape.values()).filter(myShape -> response.test(myShape, opponentShape)).findAny().get();
        }
    }
}

