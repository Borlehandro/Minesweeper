package model;

import java.awt.*;

public enum ExternalCell {

    //TODO Change colors to images
    UNKNOWN('?', Color.darkGray),
    ZERO('0', Color.GREEN),
    ONE('1', Color.yellow),
    TWO('2', Color.ORANGE),
    THREE('3', Color.cyan),
    FOUR('4', Color.blue),
    FIVE('5', Color.MAGENTA),
    SIX('6', Color.RED),
    SEVEN('7', Color.red),
    EIGHT('8', Color.red),
    MARK('!', Color.pink),
    MINE('*', Color.black),
    WRONG_MARK('w', Color.red);

    char symbol;
    Color image;

    ExternalCell(char s, Color i) {
        symbol = s;
        image = i;
    }

    public static ExternalCell fromNumber(int number) {
        return switch (number) {
            case 0 -> ZERO;
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            case 5 -> FIVE;
            case 6 -> SIX;
            case 7 -> SEVEN;
            case 8 -> EIGHT;
            default -> throw new IllegalStateException("Unexpected value: " + number);
        };
    }

    public char getSymbol(){
        return symbol;
    }

    public Color getImage() {
        return image;
    }
}
