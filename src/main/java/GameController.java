import api.ServerCommand;
import console.CommandParser;
import console.ConsoleController;
import gui.MainMenu;
import server_api.ServerController;

import java.io.*;


/**
 * Optimized for working with server
 * Todo Add local and server modes choice
 */
public class GameController {

    private final BufferedReader consoleReader;
    private ServerController serverController;

    public GameController() throws IOException {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        serverController = new ServerController();
    }

    public void run() throws IOException, ClassNotFoundException {

        System.out.println("Welcome to Minesweeper!\n" +
                "Choose console or ui game mode or type \"help\" to get more information.");

        String mode;
        MainMenu test = null;

        loop:
        while ((mode = consoleReader.readLine()) != null) {

            // Todo fix ui exit
            if(test!=null && !test.isVisible())
                System.err.println("TURNED OFF!");

            switch (CommandParser.parse(mode.toLowerCase())) {
                case CONSOLE -> {
                    ConsoleController controller = new ConsoleController(consoleReader, serverController);
                    controller.start();
                }
                case UI -> {
                    System.out.println("Try to launch Minesweeper in UI mode...");
                    test = new MainMenu(serverController);
                }
                case HELP -> System.out.println(serverController.send(ServerCommand.MENU_HELP));
                case ABOUT -> System.out.println(serverController.send(ServerCommand.ABOUT));
                case EXIT -> {break loop;}
                default -> System.out.println("Unknown command");
            }
        }
    }
}