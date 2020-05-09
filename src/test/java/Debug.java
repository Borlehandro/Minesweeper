import csv_parser.Parser;
import csv_parser.ScoreItem;
import csv_parser.Writer;
import exceptions.NoResourceInitException;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.*;

public class Debug {

    @Test
    public void test() {

        for (int k = 0; k < 10; k++) {

            Field field = new Field(9, 10);

            char[][] fieldCells = field.getField();

            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    System.out.print(fieldCells[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("===================================");
        }
    }

    @Test
    public void scoreTest() throws NoResourceInitException {

        TreeSet<ScoreItem> scoreTable = Parser.parse();
        scoreTable.forEach((item) -> System.out.println(item.getTime()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("PST"));
        calendar.setTimeInMillis(10);
        System.err.println(calendar.getTimeZone());

        scoreTable.add(new ScoreItem("Test", LocalDateTime.now(), calendar.getTime()));
        Writer.write(scoreTable);

    }

    @Test
    public void consoleTest() {

        Scanner in = new Scanner(System.in);
        System.out.println("Test");
        String line = in.nextLine();
        System.out.println("\t\t" + line);

    }
}