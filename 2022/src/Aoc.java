import java.util.*;
import java.util.stream.*;

/**
 * Some helpful functions to reduce boilerplate in the actual Challenges
 * @author Dorian Ignee
 */
public class Aoc {
    /**
     * Checks, if the paramater is a file in res Folder. 
     * If it is, returns the contents of the file, else the parameter
     * @param file A filename or challenge input string
     * @return contents of the file, if {@code file} is a filename, else {@code file}
     */
    private static String checkFile(String file) {
        if (file.endsWith(".txt")) {
            var resource = Aoc.class.getResourceAsStream(file);
            if (resource == null) throw new RuntimeException("File " + file + " not found.");
            Scanner scanner = new Scanner(Aoc.class.getResourceAsStream(file));
            scanner.useDelimiter("\\Z");
            String data = scanner.next();
            scanner.close();
            return data;
        }
        return file;
    }

    /**
     * Cuts a file or challenge input string into pieces, delimited by {@code delimiterPattern}
     * @param file A filename or challenge input string
     * @param delimiterPattern The pattern string that delimits the elements
     * @return A stream of Strings
     */
    private static Stream<String> tokens(String file, String delimiterPattern) {
        String input = checkFile(file);
        Scanner data = new Scanner(input); // Leave Scanner open because Stream will be closed when Scanner is closed
        data.useDelimiter(delimiterPattern);
        return data.tokens();
    }

    /**
     * Simply get a Stream containing all lines of the given {@code file}
     * @param file A filename or challenge input string
     * @return A stream of lines
     */
    public static Stream<String> lines(String file) {
        return tokens(file, "\\R");
    }

    /**
     * Simply get a Stream containing all lines of the given {@code file}
     * @param file A filename or challenge input string
     * @return A stream of all integers in the file
     */
    public static IntStream ints(String file) {
        return tokens(file, "\\D+").mapToInt(Integer::parseInt);
    }

    /**
     * Simply get a Stream of blocks (Strings that are separated by a blank line)
     * @param file A filename or challenge input string
     * @return A stream of all blocks in the file
     */
    public static Stream<String> blocks(String file) {
        return tokens(file, "\\R{2,}");
    }
}
