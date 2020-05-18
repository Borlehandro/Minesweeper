package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public enum ExternalCell {

    //TODO Change colors
    UNKNOWN('?', Color.darkGray),
    ZERO('0', new Color(0xFFE1F8A8, true)),
    ONE('1', new Color(0xFFF6F6A4, true)),
    TWO('2', new Color(0xFFF6DDA8, true)),
    THREE('3', new Color(0xFFF6CBA8, true)),
    FOUR('4', new Color(0xFFF6BEAC, true)),
    FIVE('5', new Color(0xFFF5A9A5, true)),
    SIX('6', new Color(0xFFF3A0BC, true)),
    SEVEN('7', new Color(0xFFF5A5F0, true)),
    EIGHT('8', new Color(0xFFC5A8F5, true)),
    MARK('!', Color.white),
    MINE('*', Color.white),
    WRONG_MARK('w', Color.white);

    char symbol;
    Color color;
    Image image;

    ExternalCell(char s, Color c) {
        symbol = s;
        color = c;
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
