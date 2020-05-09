package csv_parser;

import exceptions.NoResourceInitException;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class Writer extends InitializableFilesHandler{

    public static void write(Set<ScoreItem> table) throws NoResourceInitException {

        if(scoreTable==null)
            throw new NoResourceInitException("Score table has not initialized");

        try(OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(scoreTable))) {

            table.forEach(item -> {
                try {
                    output.write(item.getName() + ","
                            + ScoreItem.noteDateFormatter.format(item.getNoteTime()) + ","
                            + ScoreItem.timeFormatter.format(item.getTime()) + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Can't write in " + scoreTable);
        }
    }
}