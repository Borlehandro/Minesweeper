package console;

import model.Field;
import score.Parser;
import score.ScoreItem;
import score.Writer;
import exceptions.NoResourceInitException;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.TreeSet;

public class ConsoleController {

    private final BufferedReader reader;

    public ConsoleController(BufferedReader reader) {
        this.reader = reader;
    }

    public void start() throws IOException {

        System.out.println("Welcome to console mode! Type \"help\" to get more information.");

        String s;
        while ((s = reader.readLine()) != null) {
            Command command = CommandParser.parse(s);
            switch (command) {
                case HELP:
                    System.out.println(CommandParser.CONSOLE_COMMANDS_INFO);
                    break;
                case ABOUT:
                    System.out.println(CommandParser.ABOUT);
                    break;
                case NEW_GAME: {
                    String[] args = s.split(" ");
                    int size = Integer.parseInt(args[2]);
                    int numberMines = Integer.parseInt(args[3]);
                    if (numberMines <= size * size - 1) {
                        try {
                            run(size, numberMines);
                        } catch (IOException | NoResourceInitException e) {
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

                    try {

                        TreeSet<ScoreItem> scoreTable = Parser.parse();

                        if (scoreTable != null && !scoreTable.isEmpty()) {
                            System.out.println("Score table.\n");
                            scoreTable.forEach(System.out::println);
                            System.out.println();
                        } else System.out.println("Score table not found. You can be the first player!");

                    } catch (NoResourceInitException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;

                default:
                    System.out.println("Unknown command");
            }
        }
    }

    private void run(int size, int numberMines) throws IOException, NoResourceInitException {

        long start = System.currentTimeMillis();

        boolean failed = false;

        Field field = new Field(size, numberMines);

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
                                boolean success = field.check(Integer.parseInt(args[i]), Integer.parseInt(args[i + 1]));
                                if (success) {
                                    System.out.println("Checked.");
                                } else {
                                    failed = true;
                                    showField(field);
                                    break run;
                                }
                            }
                        }
                        case FLAG -> {
                            for (int i = 1; i < (args.length - 1); i += 2) {
                                boolean success = field.setFlag(Integer.parseInt(args[i]), Integer.parseInt(args[i + 1]));
                                if (success) {
                                    System.out.println("Fagged.");
                                } else {
                                    System.out.println("No flags available!");
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

            showField(field);

            if (field.isCompleted())
                break;

        } while ((line = reader.readLine()) != null);

        if (failed)
            System.out.println("FAIL!");
        else {

            // TODO Read from properties

            LocalTime duration = LocalTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - start), ZoneOffset.UTC);

            TreeSet<ScoreItem> scoreTable = Parser.parse();

            System.out.println("You win!");
            System.out.println("Your time is " + ScoreItem.timeFormatter.format(duration));

            if (scoreTable != null && !scoreTable.isEmpty())
                System.out.println(scoreTable.first().getTime().compareTo(duration) <= 0 ?
                        "The best time is " + ScoreItem.timeFormatter.format(scoreTable.first().getTime())
                        : "It's the best time!");

            System.out.print("Enter your name: ");
            String name = reader.readLine();

            scoreTable.add(new ScoreItem(name, LocalDateTime.now(), duration));

            Writer.write(scoreTable);
        }
    }

    private void showField(Field field) {
        System.out.println("Marks : " + field.getMarks() + "/" + field.getMarksLimit());

        char[][] cells = field.getField();

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