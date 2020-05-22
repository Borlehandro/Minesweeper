package console;

import api.ServerCommand;
import exceptions.NoResourceInitException;
import model.ExternalCell;
import model.Field;
import model.Pair;
import score.ScoreItem;
import score.ScoreManager;
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
    private final ScoreManager scoreManager;

    private final ServerController serverController;

    public ConsoleController(BufferedReader consoleReader, ServerController serverController) throws IOException {
        this.consoleReader = consoleReader;
        this.serverController = serverController;
        scoreManager = new ScoreManager();
    }

    public void start() throws IOException, ClassNotFoundException {

        System.out.println("Welcome to console mode! Type \"help\" to get more information.");

        String s;
        while ((s = consoleReader.readLine()) != null) {
            Command command = CommandParser.parse(s);
            switch (command) {
                case HELP:
                    System.out.println(serverController.send(ServerCommand.CONSOLE_HELP));
                    break;
                case ABOUT:
                    System.out.println(serverController.send(ServerCommand.ABOUT));
                    break;
                case NEW_GAME: {
                    String[] args = s.split(" ");
                    int size = Integer.parseInt(args[2]);
                    int numberMines = Integer.parseInt(args[3]);
                    if (numberMines <= size * size - 1) {
                        try {
                            run(size, numberMines);
                        } catch (IOException | NoResourceInitException | ClassNotFoundException e) {
                            System.err.println(e.getMessage());
                            System.out.println("Can't run the game!");
                            continue;
                        }
                        System.out.println("Welcome to console mode! Type \"help\" to get more information.");
                    } else System.out.println("Wrong field parameters.");
                }
                break;
                case EXIT:
                    return;

                case HIGH_SCORES: {

                    // ?)
                    TreeSet<ScoreItem> scoreTable = (TreeSet<ScoreItem>) serverController.sendWithObjectResult(ServerCommand.HIGH_SCORE);

                    // scoreManager.getScoreTable();

                    if (scoreTable != null && !scoreTable.isEmpty()) {
                        System.out.println("Score table.\n");
                        scoreTable.forEach(System.out::println);
                        System.out.println();
                    } else System.out.println("Score table not found. You can be the first player!");

                }
                break;

                default:
                    System.out.println("Unknown command");
            }
        }
    }

    private void run(int size, int numberMines) throws IOException, NoResourceInitException, ClassNotFoundException {

        ServerCommand serverCommand = ServerCommand.NEW_GAME;
        serverCommand.setArgs(String.valueOf(size), String.valueOf(numberMines));
        // Field field = (Field) serverController.sendWithObjectResult(serverCommand);
        ExternalCell[][] cells = ExternalCell.jsonToTable(serverController.send(serverCommand));

        long start = System.currentTimeMillis();

        boolean failed = false;

        String line = null;

        run:
        do {

            if (line != null) {

                Command command = CommandParser.parse(line);

                System.err.println(command);

                if (command == Command.CHECK || command == Command.FLAG) {
                    String[] args = line.split(" ");
                    switch (command) {
                        case CHECK -> {
                            for (int i = 1; i < (args.length - 1); i += 2) {
                                // Todo test
                                ServerCommand checkCommand = ServerCommand.CHECK;
                                checkCommand.setArgs(args[i], args[i + 1]);
                                cells = ExternalCell.jsonToTable(serverController.send(checkCommand));

                                if (cells != null) {
                                    System.out.println("Checked.");
                                } else {
                                    failed = true;
                                    showField(ExternalCell.jsonToTable(serverController.send(ServerCommand.SHOW_FIELD)));
                                    break run;
                                }
                            }
                        }
                        case FLAG -> {
                            for (int i = 1; i < (args.length - 1); i += 2) {
                                ServerCommand flagCommand = ServerCommand.FLAG;
                                flagCommand.setArgs(args[i], args[i+1]);
                                cells = ExternalCell.jsonToTable(serverController.send(flagCommand));;
                                if (cells != null) {
                                    System.out.println("Fagged.");
                                } else {
                                    System.out.println("No flags available!");
                                    cells = ExternalCell.jsonToTable(serverController.send(ServerCommand.SHOW_FIELD));
                                }
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

        } while ((line = consoleReader.readLine()) != null);

        if (failed)
            System.out.println("FAIL!");
        else {

            // TODO Read from properties

            LocalTime duration = LocalTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - start), ZoneOffset.UTC);

            System.out.println("You win!");
            System.out.println("Your time is " + ScoreItem.timeFormatter.format(duration));

            ScoreItem best = scoreManager.getBest();

            if (best!=null)
                System.out.println(best.getTime().compareTo(duration) <= 0 ?
                        "The best time is " + ScoreItem.timeFormatter.format(best.getTime())
                        : "It's the best time!");

            System.out.print("Enter your name: ");
            String name = consoleReader.readLine();

            scoreManager.add(name, duration);
        }
    }

    private void showField(ExternalCell[][] externalCells) throws IOException, ClassNotFoundException {

        Pair<Integer> marks = (Pair<Integer>) serverController.sendWithObjectResult(ServerCommand.GET_MARKS);

        System.out.println("Marks : " + marks.x + "/" + marks.y);

        Character[][] cells = Arrays.stream(externalCells).map(item -> Arrays.stream(item).map(ExternalCell::getSymbol).toArray(Character[]::new)).toArray(Character[][]::new);

        System.out.print("  ");
        for (int j = 0; j < cells.length; ++j) {
            System.out.print("|" + j);
        }
        System.out.println("|");

        for (int i = 0; i < cells.length; ++i) {
            System.out.print("|" + i + "|");
            for (int j = 0; j < cells.length; ++j) {
                System.out.print(cells[i][j] + " ");
            }
            System.out.println("\b|");
        }

        System.out.println("--------------------");
    }
}