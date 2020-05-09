package csv_parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

abstract class InitializableFilesHandler {

    public static void init() throws IOException {
        Properties properties = new Properties();
        // Todo change path for *.jar
        properties.load(new FileInputStream("src\\main\\resources\\config.properties"));
        scoreTable = properties.getProperty("score_location");
        createFile(scoreTable);
    }

    public static void init(String path) throws IOException {
        scoreTable = path;
        createFile(scoreTable);
    }

    protected static void createFile(String name) throws IOException {
        File file = new File(name);
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

    protected static String scoreTable;
}