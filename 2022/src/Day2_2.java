import java.util.Arrays;
import java.util.function.BiPredicate;

public class Day2_2 {
    public static void main(String[] args) {
        System.out.println(Aoc
            .lines("02.txt")
            .mapToInt(line -> new Round(line).getPoints())
            .sum()
        );
    }

    private static class Round {
        Shape opponentShape;
        RoundResult roundResult;
        
        public Round(String line) {
            String[] moves = line.split(" ");
            String opponentMove = moves[0];
            String myMove = moves[1];
    
            opponentShape = switch(opponentMove) {
                case "A" -> Shape.ROCK;
                case "B" -> Shape.PAPER;
                case "C" -> Shape.SCISSORS;
                default -> null;
            };
    
            roundResult = switch(myMove) {
                case "X" -> RoundResult.LOSE;
                case "Y" -> RoundResult.DRAW;
                case "Z" -> RoundResult.WIN;
                default -> null;
            };
        }
    
        public int getPoints() {
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

class Round {
    Shape opponentShape;
    RoundResult roundResult;
    
    public Round(String line) {
        String[] moves = line.split(" ");
        String opponentMove = moves[0];
        String myMove = moves[1];

        opponentShape = switch(opponentMove) {
            case "A" -> Shape.ROCK;
            case "B" -> Shape.PAPER;
            case "C" -> Shape.SCISSORS;
            default -> null;
        };

        roundResult = switch(myMove) {
            case "X" -> RoundResult.LOSE;
            case "Y" -> RoundResult.DRAW;
            case "Z" -> RoundResult.WIN;
            default -> null;
        };
    }

    public int getPoints() {
        return roundResult.getResponse(opponentShape).getTotalPoints(opponentShape);
    }
}

enum Shape {
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

enum RoundResult {
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