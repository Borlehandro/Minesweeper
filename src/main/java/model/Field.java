package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {

    private final Cell[][] cells;
    private final char[][] field;
    private int marks;
    private final int marksLimit;
    private int openedCells;
    private boolean completed;

    public Field(int size, int numberMines) {
        this.cells = new Cell[size][size];
        this.field = new char[size][size];
        this.marksLimit = numberMines;
        this.marks = 0;
        this.openedCells = 0;
        this.completed = false;

        generate(size, numberMines);
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void generate(int size, int numberMines) {
        Random random = new Random(System.currentTimeMillis());

        // TODO FIX FOLLOW CODE.
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j] = new Cell(0);
                field[i][j] = '?';
            }
        }

        for (int i = 0; i < numberMines; ++i) {
            int x = random.nextInt(size - 1);
            int y = random.nextInt(size - 1);
            if (cells[x][y] != null && cells[x][y].getValue() == -1) {
                i--;
            } else {
                cells[x][y].setValue(-1);
                for (Pair<Integer> c : getNeighbours(x, y)) {
                    cells[c.x][c.y].setValue(cells[c.x][c.y].getValue() == -1 ? -1 : 1 + cells[c.x][c.y].getValue());
                }
            }

        }
    }

    public char[][] getField() {
        return field;
    }

    public boolean check(int x, int y) {

        System.err.println("Check " + x + " " + y);

        if (cells[x][y].getValue() != 0)
            return open(x, y);
        else {
            cascadeOpen(x, y);
            return true;
        }
    }

    public boolean setFlag(int x, int y) {

        System.err.println("Flag " + x + " " + y);

        if (cells[x][y].isMarked()) {
            cells[x][y].unmark();
            field[x][y] = '?';
            marks--;
            return true;
        } else if (marks < marksLimit && cells[x][y].getStatus() != Cell.Status.OPENED) {
            cells[x][y].mark();
            field[x][y] = '!';
            marks++;
            return true;
        } else return cells[x][y].getStatus() == Cell.Status.OPENED;

    }

    private void cascadeOpen(int x, int y) {
        System.err.println("Cascade : " + x + " " + y);

        open(x, y);

        for (Pair<Integer> c : getNeighbours(x, y)) {

            if (cells[c.x][c.y].getValue() != -1) {
                if (cells[c.x][c.y].getValue() != 0) {
                    open(c.x, c.y);
                } else if (cells[c.x][c.y].getStatus() != Cell.Status.OPENED)
                    cascadeOpen(c.x, c.y);
            }
        }
    }

    private boolean open(int x, int y) {

        if (cells[x][y].getValue() != -1) {
            if (cells[x][y].getStatus() != Cell.Status.OPENED) {
                if (cells[x][y].isMarked()) {
                    cells[x][y].unmark();
                    marks--;
                }
                cells[x][y].setStatus(Cell.Status.OPENED);
                field[x][y] = (char) (cells[x][y].getValue() + '0');
                openedCells++;

                System.err.println(openedCells);

                if (openedCells == (cells.length * cells.length) - marksLimit)
                    completed = true;
            }

            return true;
        } else
            for(int i = 0; i < cells.length; ++i )
                for(int j = 0; j < cells.length; ++j)
                    if(!(cells[i][j].isMarked() && cells[i][j].getValue()==-1)) {
                        if (cells[i][j].getValue() == -1)
                            field[i][j] = '*';
                        else if(cells[i][j].isMarked())
                            field[i][j] = 'w';
                        else
                            field[i][j] = (char) (cells[i][j].getValue() + '0');
                    }
            return false;
    }

    private List<Pair<Integer>> getNeighbours(int x, int y) {

        List<Pair<Integer>> neighbours = new ArrayList<>();

        if (x - 1 >= 0) {
            neighbours.add(new Pair<>(x - 1, y));
            if (y - 1 >= 0)
                neighbours.add(new Pair<>(x - 1, y - 1));
            if (y + 1 < cells.length)
                neighbours.add(new Pair<>(x - 1, y + 1));
        }
        if (y - 1 >= 0)
            neighbours.add(new Pair<>(x, y - 1));
        if (y + 1 < cells.length)
            neighbours.add(new Pair<>(x, y + 1));
        if (x + 1 < cells.length) {
            neighbours.add(new Pair<>(x + 1, y));
            if (y - 1 >= 0)
                neighbours.add(new Pair<>(x + 1, y - 1));
            if (y + 1 < cells.length)
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
        return cells.length;
    }

}