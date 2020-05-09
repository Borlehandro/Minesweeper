package exceptions;

public class UnknownCommandException extends ConsoleException {

    private String command;

    UnknownCommandException(String command) {
        this.command = command;
        this.message = "Unknown command : " + command;
    }

    public String getCommand() {
        return command;
    }
}
