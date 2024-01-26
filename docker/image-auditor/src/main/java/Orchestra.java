import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Orchestra {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }).create();
    private final Set<Musician> musicians = new HashSet<>();

    public void registerMusician(String message) {
        //musicians.add(gson.fromJson(message, Musician.class));
        //Extremely dirty way gson doesn't call the Musician constructor properly, needs a custom deserializer for gson
        String uuid = message.substring(message.indexOf("uuid") + 8, message.indexOf(",")-1);
        String sound = message.substring(message.indexOf("sound") + 9, message.lastIndexOf("\""));
        boolean alreadyRegistered = false;
        for (Musician m : musicians){
            if (Objects.equals(m.getUuid(), uuid)) {
                m.setLastActivity(LocalDateTime.now());
                alreadyRegistered = true;
            }
        }
        if (!alreadyRegistered) {
            musicians.add(new Musician(uuid, sound));
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
