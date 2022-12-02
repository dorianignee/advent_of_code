/**
 * Today we have to find the elve with the highest sum of calories and output that sum
 */
public class Day1_1 {
    public static void main(String[] args) {
        System.out.println(
            Aoc
            .blocks("01.txt")
            .mapToInt(block->Aoc.ints(block).sum())
            .max()
            .getAsInt()
        );
    }
}
