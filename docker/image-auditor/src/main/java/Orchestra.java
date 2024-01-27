import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Orchestra {
    private final Gson gson;
    private final Set<Musician> musicians = new HashSet<>();

    public Orchestra() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, srcType, context) -> new JsonPrimitive(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").format(localDateTime)));
        // Only used for alternative solution where musician sends Date/Time of his last activity
        // gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")));
        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public void registerMusician(String message) {
        MusicianMessage musicianMessage = gson.fromJson(message, MusicianMessage.class);

        // Solution where musician doesn't send Date/Time of his last activity
        Musician musician = new Musician(musicianMessage.uuid(), musicianMessage.sound());

        // Alternative solution where musician sends Date/Time of his last activity
        // Musician musician = new Musician(musicianMessage.uuid(), musicianMessage.sound(), musicianMessage.lastActivity());

        if (!musicians.contains(musician)) {
            musicians.add(musician);
        } else {
            for (var m : musicians) {
                if (m.equals(musician)){
                    // Solution where musician doesn't send Date/Time of his last activity
                    m.setLastActivity();

                    // Alternative solution where musician sends Date/Time of his last activity
                    // m.setLastActivity(musicianMessage.lastActivity());
                }
            }
        }
    }

    public String getActiveMusicians() {
        List<Musician> activeMusician = new LinkedList<>();
        for (var m : musicians) {
            if (m.isActive()) {
                activeMusician.add(m);
            }
        }
        return gson.toJson(activeMusician);
    }
}
