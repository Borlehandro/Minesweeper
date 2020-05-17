package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Properties;

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
    Color color;
    Image image;

    ExternalCell(char s, Color с) {
        symbol = s;
        color = с;
        try {
            Properties properties = new Properties();
            // Todo change path for *.jar
            properties.load(new FileInputStream("src\\main\\resources\\config.properties"));
            if(properties.getProperty(this.name())!=null)
                image = ImageIO.read(new File(properties.getProperty(this.name())));
            else image = null;
        } catch (IOException e) {
            image = null;
        }
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

    public char getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }

    public Image getImage() {
        return image;
    }
}
