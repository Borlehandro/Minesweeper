package exceptions;

public class NoResourceInitException extends Exception {

    public NoResourceInitException(String message) {
        super(message);
        this.message = message;
    }

    protected String message;

    @Override
    public String getMessage() {
        return message;
    }

}