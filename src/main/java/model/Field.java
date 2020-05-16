package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {

    private final InternalCell[][] internalCells;
    private final ExternalCell[][] externalCells;
    private int marks;
    private final int marksLimit;
    private int openedCells;
    private boolean completed;

    public Field(int size, int numberMines) {
        this.internalCells = new InternalCell[size][size];
        this.externalCells = new ExternalCell[size][size];
        this.marksLimit = numberMines;
        this.marks = 0;
        this.openedCells = 0;
        this.completed = false;

        generate(size, numberMines);
    }

    public InternalCell[][] getInternalCells() {
        return internalCells;
    }

    private void generate(int size, int numberMines) {
        Random random = new Random(System.currentTimeMillis());

        // TODO FIX FOLLOW CODE.
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                internalCells[i][j] = new InternalCell(0);
                externalCells[i][j] = ExternalCell.UNKNOWN;
            }
        }

        for (int i = 0; i < numberMines; ++i) {
            int x = random.nextInt(size - 1);
            int y = random.nextInt(size - 1);
            if (internalCells[x][y] != null && internalCells[x][y].getValue() == -1) {
                i--;
            } else {
                internalCells[x][y].setValue(-1);
                for (Pair<Integer> c : getNeighbours(x, y)) {
                    internalCells[c.x][c.y].setValue(internalCells[c.x][c.y].getValue() == -1 ? -1 : 1 + internalCells[c.x][c.y].getValue());
                }
            }

        }
    }

    public ExternalCell[][] getExternalCells() {
        return externalCells;
    }

    public boolean check(int x, int y) {

        System.err.println("Check " + x + " " + y);

        if (internalCells[x][y].getValue() != 0)
            return open(x, y);
        else {
            cascadeOpen(x, y);
            return true;
        }
    }

    public boolean setFlag(int x, int y) {

        System.err.println("Flag " + x + " " + y);

        if (internalCells[x][y].isMarked()) {
            internalCells[x][y].unmark();
            externalCells[x][y] = ExternalCell.UNKNOWN;
            marks--;
            return true;
        } else if (marks < marksLimit && !internalCells[x][y].isOpened()) {
            internalCells[x][y].mark();
            externalCells[x][y] = ExternalCell.MARK;
            marks++;
            return true;
        } else return internalCells[x][y].isOpened();

    }

    private void cascadeOpen(int x, int y) {
        System.err.println("Cascade : " + x + " " + y);

        open(x, y);

        for (Pair<Integer> c : getNeighbours(x, y)) {

            if (internalCells[c.x][c.y].getValue() != -1) {
                if (internalCells[c.x][c.y].getValue() != 0) {
                    open(c.x, c.y);
                } else if (!internalCells[c.x][c.y].isOpened())
                    cascadeOpen(c.x, c.y);
            }
        }
    }

    private boolean open(int x, int y) {

        if (internalCells[x][y].getValue() != -1) {
            if (!internalCells[x][y].isOpened()) {
                if (internalCells[x][y].isMarked()) {
                    internalCells[x][y].unmark();
                    marks--;
                }
                internalCells[x][y].open();
                externalCells[x][y] = ExternalCell.fromNumber(internalCells[x][y].getValue());
                openedCells++;

                System.err.println(openedCells);

                if (openedCells == (internalCells.length * internalCells.length) - marksLimit)
                    completed = true;
            }

            return true;
        } else
            for(int i = 0; i < internalCells.length; ++i )
                for(int j = 0; j < internalCells.length; ++j)
                    if(!(internalCells[i][j].isMarked() && internalCells[i][j].getValue()==-1)) {
                        if (internalCells[i][j].getValue() == -1)
                            externalCells[i][j] = ExternalCell.MINE;
                        else if(internalCells[i][j].isMarked())
                            externalCells[i][j] = ExternalCell.WRONG_MARK;
                        else
                            externalCells[i][j] = ExternalCell.fromNumber(internalCells[i][j].getValue());
                    }
            return false;
    }

    private List<Pair<Integer>> getNeighbours(int x, int y) {

        List<Pair<Integer>> neighbours = new ArrayList<>();

        if (x - 1 >= 0) {
            neighbours.add(new Pair<>(x - 1, y));
            if (y - 1 >= 0)
                neighbours.add(new Pair<>(x - 1, y - 1));
            if (y + 1 < internalCells.length)
                neighbours.add(new Pair<>(x - 1, y + 1));
        }
        if (y - 1 >= 0)
            neighbours.add(new Pair<>(x, y - 1));
        if (y + 1 < internalCells.length)
            neighbours.add(new Pair<>(x, y + 1));
        if (x + 1 < internalCells.length) {
            neighbours.add(new Pair<>(x + 1, y));
            if (y - 1 >= 0)
                neighbours.add(new Pair<>(x + 1, y - 1));
            if (y + 1 < internalCells.length)
                neighbours.add(new Pair<>(x + 1, y + 1));
        }

        return neighbours;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getMarks() {
        return marks;
    }

    public int getMarksLimit() {
        return marksLimit;
    }

    public int getSize() {
        return internalCells.length;
    }

}