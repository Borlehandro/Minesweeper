package console;

import api.ServerCommand;
import model.ExternalCell;
import model.Pair;
import score.ScoreItem;
import serialization.Serializer;
import server_api.ServerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.TreeSet;

public class ConsoleController {

    private final BufferedReader consoleReader;

    private final ServerController serverController;

    public ConsoleController(BufferedReader consoleReader) throws IOException {
        this.consoleReader = consoleReader;
        this.serverController = new ServerController();
    }

    public void start() throws IOException {

        System.out.println("Welcome to console mode! Type \"help\" to get more information.");

        String s;
        do {

            System.out.print("Console: ");

            s = consoleReader.readLine();

            if(s == null)
                break;

            Command command = CommandParser.parse(s);
            switch (command) {
                case HELP -> System.out.println(serverController.send(ServerCommand.CONSOLE_HELP));
                case ABOUT -> System.out.println(serverController.send(ServerCommand.ABOUT));
                case NEW_GAME -> {
                    String[] args = s.split(" ");
                    int size = Integer.parseInt(args[2]);
                    int numberMines = Integer.parseInt(args[3]);
                    if (numberMines <= size * size - 1) {
                        try {
                            run(size, numberMines);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Can't run the game!");
                        }
                    } else System.out.println("Wrong field parameters.");
                }
                case EXIT -> {
                    serverController.close();
                    return;
                }
                case HIGH_SCORES -> {

                    TreeSet<ScoreItem> scoreTable = Serializer.jsonToScoreTable(serverController.send(ServerCommand.HIGH_SCORE));

                    if (!scoreTable.isEmpty()) {
                        System.out.println("Score table.\n");
                        scoreTable.forEach(System.out::println);
                        System.out.println();
                    } else System.out.println("Score table not found. You can be the first player!");

                }
                default -> System.out.println("Unknown command");
            }
        } while (true);
    }

    private void run(int size, int numberMines) throws IOException {

        ServerCommand serverCommand = ServerCommand.NEW_GAME;
        serverCommand.setArgs(String.valueOf(size), String.valueOf(numberMines));
        ExternalCell[][] cells = Serializer.jsonToExternal(serverController.send(serverCommand));

        long start = System.currentTimeMillis();

        boolean failed = false;

        String line = null;

        run:
        do {

            if (line != null) {

                Command command = CommandParser.parse(line);

                // System.err.println(command);

                if (command == Command.CHECK || command == Command.FLAG) {
                    String[] args = line.split(" ");
                    switch (command) {
                        case CHECK -> {
                            for (int i = 1; i < (args.length - 1); i += 2) {
                                ServerCommand checkCommand = ServerCommand.CHECK;
                                checkCommand.setArgs(args[i], args[i + 1]);
                                cells = Serializer.jsonToExternal(serverController.send(checkCommand));

                                if (cells.length > 0) {
                                    System.out.println("Checked.");
                                } else {
                                    failed = true;
                                    showField(Serializer.jsonToExternal(serverController.send(ServerCommand.SHOW_FIELD)));
                                    break run;
                                }
                            }
                        }
                        case FLAG -> {
                            for (int i = 1; i < (args.length - 1); i += 2) {
                                ServerCommand flagCommand = ServerCommand.FLAG;
                                flagCommand.setArgs(args[i], args[i+1]);
                                cells = Serializer.jsonToExternal(serverController.send(flagCommand));
                            }

                        }
                    }
                } else switch (command) {
                    case EXIT -> {
                        return;
                    }
                    case HELP -> System.out.println(CommandParser.GAME_INFO);
                    default -> System.out.println("Unknown command.");
                }
            }

            showField(cells);

            if (Boolean.parseBoolean(serverController.send(ServerCommand.IS_COMPLETED)))
                break;

            System.out.print("Game: ");

        } while ((line = consoleReader.readLine()) != null);

        if (failed)
            System.out.println("Game failed!");
        else {

            LocalTime duration = LocalTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - start), ZoneOffset.UTC);

            System.out.println("You win!");
            System.out.println("Your time is " + ScoreItem.timeFormatter.format(duration));

            ScoreItem best = Serializer.jsonToScoreItem(serverController.send(ServerCommand.GET_TOP_USER)); // scoreManager.getBest();

            if (best!=null)
                System.out.println(best.getTime().compareTo(duration) <= 0 ?
                        "The best time is " + ScoreItem.timeFormatter.format(best.getTime())
                        : "It's the best time!");

            System.out.print("Enter your name: ");
            String name = consoleReader.readLine();

            ServerCommand scoreCommand = ServerCommand.SAVE_SCORE;
            scoreCommand.setArgs(name, ScoreItem.timeFormatter.format(duration));
            serverController.send(scoreCommand);
        }
    }

    private void showField(ExternalCell[][] externalCells) throws IOException {

        Pair<Integer> marks = Serializer.jsonToPair(serverController.send(ServerCommand.GET_MARKS));

        System.out.println("Marks : " + marks.x + "/" + marks.y);

        Character[][] cells = Arrays.stream(externalCells).map(item -> Arrays.stream(item).map(ExternalCell::getSymbol).toArray(Character[]::new)).toArray(Character[][]::new);

        System.out.print("  ");
        if(externalCells.length > 10)
            System.out.print(" ");

        for (int j = 0; j < cells.length; ++j) {
            System.out.print("|" + j);
            if(cells.length > 10 && j < 10)
                System.out.print("|");
        }
        System.out.println("|");

        for (int i = 0; i < cells.length; ++i) {
            System.out.print("|" + i + "|");
            if(cells.length > 10 && i < 10)
                System.out.print(" ");
            for (int j = 0; j < cells.length; ++j) {
                System.out.print(cells[i][j] + " ");
                if(cells.length > 10)
                    System.out.print(" ");
            }
            System.out.println("\b|");
        }
        System.out.println();
    }
}