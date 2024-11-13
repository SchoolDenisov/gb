package gb.oop;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private LocalDateTime dateTime;
    private String description;

    public Note(LocalDateTime dateTime, String description) {
        this.dateTime = dateTime;
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ": " + description;
    }
}