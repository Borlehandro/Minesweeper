package score;

import exceptions.NoResourceInitException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Parser {

    /**
     * Parse .csv file with score table.
     * @return Sorted set of ScoreItems.
     */
    static TreeSet<ScoreItem> parse(String scoreTable) throws NoResourceInitException {

        if (scoreTable==null)
            throw new NoResourceInitException("Score table has not been initialized");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(scoreTable)))) {

            return reader.lines().map(s -> {
                String[] columns = s.split(",");

                return new ScoreItem(columns[0], LocalDateTime.parse(columns[1], ScoreItem.noteDateFormatter),
                        LocalTime.parse(columns[2], ScoreItem.timeFormatter));

            }).collect(Collectors.toCollection(TreeSet::new));

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Can't parse file " + scoreTable);
            return null;
        }
    }

}