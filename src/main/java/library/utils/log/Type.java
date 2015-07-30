package library.utils.log;

public enum Type {
    INFO(0, "I"),
    ERROR(1, "E"),
    WARN(2, "W"),
    DEBUG(3, "D"),
    VERBOSE(4, "V"),
    ALL(99, "A"),
    OFF(-1, "");

    int level;
    String value;

    Type(int l, String v) {
        level = l;
        value = v;
    }

    public int getLevel() {
        return level;
    }

    public String getValue() {
        return value;
    }

    static public Type fromId(int i) {
        for (Type at : Type.values()) {
            if (at.getLevel() == i) {
                return at;
            }
        }
        return OFF;
    }
}
