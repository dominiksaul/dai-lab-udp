import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Musician {
    private final String uuid;
    private final Instrument instrument;
    private LocalDateTime lastActivity;

    public Musician(String uuid, String sound) {
        this.uuid = uuid;
        this.instrument = Instrument.getInstrumentWithSound(sound);
        this.lastActivity = LocalDateTime.now();
    }

    public boolean isActive() {
        return lastActivity.plus(Duration.ofSeconds(5)).isAfter(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "{" +
                "\"uuid\"=\"" + uuid + "\", " +
                "\"instrument\"=\"" + instrument + "\", " +
                "\"lastActivity\"=" + lastActivity +
                "}";
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

    public String getUuid() {
        return uuid;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }
}
