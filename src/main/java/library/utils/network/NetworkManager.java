package library.utils.network;

public class NetworkManager {

    private static Network network;
    private static Params mParams;

    public static final class Params {

    }

    public static Params config() {
        mParams = new Params();
        return mParams;
    }

    public static Network get() {

        if (network == null) {
            network = new Network();
        }
        return network;
    }
}
