public class Day2_1 {
    public static void main(String[] args) {
        System.out.println(Aoc
            .lines("02.txt")
            .mapToInt(line -> new Round(line).getPoints())
            .sum()
        );
    }

    private static class Round {
        Shape opponentShape;
        Shape myShape;
        
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
    
            myShape = switch(myMove) {
                case "X" -> Shape.ROCK;
                case "Y" -> Shape.PAPER;
                case "Z" -> Shape.SCISSORS;
                default -> null;
            };
        }
    
        public int getPoints() {
            return myShape.getTotalPoints(opponentShape);
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
    }
}

