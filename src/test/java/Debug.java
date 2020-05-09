import score.Parser;
import score.ScoreItem;
import score.Writer;
import exceptions.NoResourceInitException;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class Debug {

    @Test
    public void test() {
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(10000), ZoneOffset.UTC);
        System.err.println(date);
    }

    @Test
    public void scoreTest() throws NoResourceInitException {

        TreeSet<ScoreItem> scoreTable = Parser.parse();
        scoreTable.forEach((item) -> System.out.println(item.getTime()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("PST"));
        calendar.setTimeInMillis(10);
        System.err.println(calendar.getTimeZone());

        // scoreTable.add(new ScoreItem("Test", LocalDateTime.now(), calendar.getTime()));
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