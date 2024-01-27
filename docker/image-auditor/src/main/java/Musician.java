import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Musician {
    private final String uuid;
    private final Instrument instrument;
    private LocalDateTime lastActivity;

    // Solution where musician doesn't send Date/Time of his last activity
    public Musician(String uuid, String sound) {
        this.uuid = uuid;
        this.instrument = Instrument.getInstrumentWithSound(sound);
        this.lastActivity = LocalDateTime.now();
    }

    // Alternative solution where musician sends Date/Time of his last activity
    public Musician(String uuid, String sound, LocalDateTime lastActivity) {
        this.uuid = uuid;
        this.instrument = Instrument.getInstrumentWithSound(sound);
        this.lastActivity = lastActivity;
    }

    public boolean isActive() {
        return lastActivity.plus(Duration.ofSeconds(5)).isAfter(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Musician musician = (Musician) o;
        return Objects.equals(uuid, musician.uuid) && instrument == musician.instrument;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, instrument);
    }

    // Solution where musician doesn't send Date/Time of his last activity
    public void setLastActivity() {
        this.lastActivity = LocalDateTime.now();
    }

    // Alternative solution where musician sends Date/Time of his last activity
    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }
}
