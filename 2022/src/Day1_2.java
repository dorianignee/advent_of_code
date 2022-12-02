import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * The second task of the day is to find sum of the top 3 elves calories
 */
public class Day1_2 {
    public static void main(String[] args) {
        System.out.println(Aoc
            .blocks("01.txt")
            .map(block->Aoc.ints(block).sum())
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .mapToInt(Integer::intValue)
            .sum()
        );
    }
}
