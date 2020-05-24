import api.ServerCommand;
import console.CommandParser;
import console.ConsoleController;
import gui.MainMenu;
import server_api.ServerController;

import java.io.*;


/**
 * Optimized for working with server
 */
public class GameController {

    private final BufferedReader consoleReader;
    private ServerController serverController;

    public GameController() throws IOException {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        serverController = new ServerController();
    }

    public void run() throws IOException {

        System.out.println("Welcome to Minesweeper!\n" +
                "Choose console or ui game mode or type \"help\" to get more information.");

        String mode;

        loop:
        do {

            System.out.print("Start menu: ");

            mode = consoleReader.readLine();

            if (mode == null) {
                consoleReader.close();
                break;
            }

            switch (CommandParser.parse(mode.toLowerCase())) {
                case CONSOLE -> {
                    ConsoleController controller = new ConsoleController(consoleReader);
                    controller.start();
                }
                case UI -> {
                    System.out.println("Try to launch Minesweeper in UI mode...");
                    new MainMenu();
                }
                case HELP -> System.out.println(serverController.send(ServerCommand.MENU_HELP));
                case ABOUT -> System.out.println(serverController.send(ServerCommand.ABOUT));
                case EXIT -> {
                    serverController.send(ServerCommand.CLOSE);
                    break loop;
                }
                default -> System.out.println("Unknown command");
            }
        } while (true);

        serverController.close();
        consoleReader.close();

        System.exit(0);
    }
}