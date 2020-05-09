package csv_parser;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ScoreItem implements Comparable<ScoreItem> {

    public static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
    public static final DateTimeFormatter noteDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss");

    // Unique
    private final LocalDateTime noteTime;
    private final Date time;
    private final String name;

    public ScoreItem(String name, LocalDateTime noteTime, Date time) {
        this.noteTime = noteTime;
        this.time = time;
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getNoteTime() {
        return noteTime;
    }

    @Override
    public int compareTo(ScoreItem o) {
        return this.time.compareTo(o.time);
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Note date: " + noteDateFormatter.format(noteTime) +
                ", Game time: " + timeFormatter.format(time);
    }
}