import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Gets the current date and time
public class DateTimeManager {
    public String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now);
    }
    public String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now);
    }
}
