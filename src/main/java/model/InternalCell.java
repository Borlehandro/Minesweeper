package model;

public class InternalCell {

    private int value;
    private boolean opened;
    private boolean marked;

    public InternalCell(int value) {
        this.opened = false;
        this.marked = false;
        this.value = value;
    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isOpened() {
        return opened;
    }

    public void mark() {
        marked = true;
    }

    public void unmark() {
        marked = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void open(){opened = true;}

    // Debug
    @Override
    public String toString() {
        return value==-1 ? "." : String.valueOf(value);
    }
}