import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            GameController game = new GameController();
            game.run();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}