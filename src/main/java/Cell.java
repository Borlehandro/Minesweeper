public class Cell {

    enum Status {
        OPENED,
        CLOSED,
        MARKED
    }

    private Status status;
    private int value;
    private boolean marked;

    public Cell(int value) {
        this.status = Status.CLOSED;
        this.marked = false;
        this.value = value;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isMarked() {
        return marked;
    }

    public void mark() {
        marked = true;
    }

    public void unmark() {
        marked = false;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value==-1 ? "." : String.valueOf(value);
    }
}