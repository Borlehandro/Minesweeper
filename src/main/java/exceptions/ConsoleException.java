package exceptions;

public class ConsoleException extends Exception {

    protected String message;

    @Override
    public String getMessage() {
        return message;
    }
}
