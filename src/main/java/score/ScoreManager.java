package score;

import exceptions.NoResourceInitException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Properties;
import java.util.TreeSet;

public class ScoreManager {

    private final String scoreTablePath;

    public ScoreManager() throws IOException {
        Properties properties = new Properties();
        // Todo change path for *.jar
        properties.load(new FileInputStream("src\\main\\resources\\config.properties"));
        scoreTablePath = properties.getProperty("score_location");
        createFile(scoreTablePath);
    }

    public ScoreManager(String path) throws IOException {
        scoreTablePath = path;
        createFile(path);
    }

    private static void createFile(String name) throws IOException {
        File file = new File(name);
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    public void add(String name, LocalTime time) throws NoResourceInitException {

        TreeSet<ScoreItem> scoreTable = Parser.parse(scoreTablePath);

        scoreTable.add(new ScoreItem(name, LocalDateTime.now(), time));

        Writer.write(scoreTablePath, scoreTable);
    }

    public ScoreItem getBest() throws NoResourceInitException {

        TreeSet<ScoreItem> scoreTable = Parser.parse(scoreTablePath);
        return scoreTable.first();

    }

    public TreeSet<ScoreItem> getScoreTable() throws NoResourceInitException {
        return Parser.parse(scoreTablePath);
    }

}