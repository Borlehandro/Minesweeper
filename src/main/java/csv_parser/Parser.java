package csv_parser;

import exceptions.NoResourceInitException;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Parser extends InitializableFilesHandler{

    /**
     * Parse .csv file with score table.
     * @return Sorted set of ScoreItems.
     */
    public static TreeSet<ScoreItem> parse() throws NoResourceInitException {

        if (scoreTable==null)
            throw new NoResourceInitException("Score table has not been initialized");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(scoreTable)))) {

            return reader.lines().map(s -> {
                try {
                    String[] columns = s.split(",");

                    return new ScoreItem(columns[0], LocalDateTime.parse(columns[1], ScoreItem.noteDateFormatter),
                            ScoreItem.timeFormatter.parse(columns[2]));

                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toCollection(TreeSet::new));

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Can't parse file " + scoreTable);
            return null;
        }
    }

}