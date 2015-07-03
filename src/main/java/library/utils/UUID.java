package library.utils;

public class UUID {

    public static String generate() {
        return java.util.UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
}
