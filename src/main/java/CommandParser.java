public class CommandParser {

    public static final String CONSOLE_COMMANDS_INFO = "Available commands: \n"
            + "* New game <field_size> <count_mines> - launch new game with field_size * field_size field and count_mines bombs\n"
            + "* About - get information about the program\n"
            + "* Exit - exit the Minesweeper\n"
            + "* High Scores - get score table\n";

    public static final String MAIN_MENU_COMMANDS_INFO = "Available commands:\n"
            + "* Console - use Minesweeper in console mode\n"
            + "* UI - use Minesweeper with user interface\n"
            + "* About - get information about the program\n"
            + "* Exit - exit the Minesweeper\n";

    public static final String GAME_INFO = "Available commands:\n"
            + "* Check <list of x y pairs> - check (x;y) cell\n"
            + "* Flag <list of x y pairs> - set or remove flag on (x;y) cell\n"
            + "* Exit - exit the game without saving\n";

    public static final String ABOUT = "Simple minesweeper game.\nAlex Borzikov © 2020.\n";

    public static Command parse(String s) {
        s = s.toLowerCase();
        if (s.matches("check( \\d+ \\d+)+"))
            return Command.CHECK;
        else if (s.matches("flag( \\d+ \\d+)+"))
            return Command.FLAG;
        else  if(s.matches("new game \\d+ \\d+"))
            return Command.NEW_GAME;
        else return switch (s) {
                case "help" -> Command.HELP;
                case "exit" -> Command.EXIT;
                case "about" -> Command.ABOUT;
                case "high scores" -> Command.HIGH_SCORES;
                case "console" -> Command.CONSOLE;
                case "ui" -> Command.UI;
                default -> Command.UNKNOWN;
            };
    }
}