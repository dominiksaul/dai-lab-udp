import com.google.gson.Gson;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Orchestra {
    private static final Gson gson = new Gson();
    private final Set<Musician> musicians = new HashSet<>();

    public void registerMusician(String message) {
        musicians.add(gson.fromJson(message, Musician.class));
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
