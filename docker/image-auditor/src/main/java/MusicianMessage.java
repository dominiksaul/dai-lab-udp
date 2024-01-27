import java.time.LocalDateTime;

// Solution where musician doesn't send Date/Time of his last activity
record MusicianMessage(String uuid, String sound) { }

// Alternative solution where musician sends Date/Time of his last activity
// record MusicianMessage(String uuid, String sound, LocalDateTime lastActivity) { }