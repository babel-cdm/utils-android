package library.utils.network;

public class NetworkManager {

    private static Params mParams;

    public static final class Params {

    }

    public static Params config() {
        mParams = new Params();
        return mParams;
    }

    public static Network get() {

        NetworkComponent component = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule()).build();

        Network network = component.provideNetwork();
        return network;
    }
}
