public enum Instrument {
    piano("ti-ta-ti"), trumpet("pouet"), flute("trulu"), violin("gzi-gzi"), drum("boum-boum");
    private final String sound;

    Instrument(String s) {
        sound = s;
    }

    public String sound() {
        return sound;
    }

    static public Instrument getInstrumentWithSound(String sound) {
        for (var instrument : values()) {
            if (sound.equals(instrument.sound())) {
                return instrument;
            }
        }
        return null;
    }
}

