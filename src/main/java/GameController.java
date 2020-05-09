import console.CommandParser;
import console.ConsoleController;
import score.Parser;
import score.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameController {

    private final BufferedReader reader;

    public GameController() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {

        // Initialization for work with score tables
        Parser.init();
        Writer.init();

        System.out.println("Welcome to Minesweeper!\n" +
                "Choose console or ui game mode or type \"help\" to get more information.");

        String mode;

        loop:
        while ((mode = reader.readLine()) != null) {

            switch (CommandParser.parse(mode.toLowerCase())) {
                case CONSOLE -> {
                    ConsoleController controller = new ConsoleController(reader);
                    controller.start();
                }
                case UI -> System.out.println("We do not support UI ((");
                case HELP -> System.out.println(CommandParser.MAIN_MENU_COMMANDS_INFO);
                case ABOUT -> System.out.println(CommandParser.ABOUT);
                case EXIT -> {break loop;}
                default -> System.out.println("Unknown command");
            }
        }
    }
}