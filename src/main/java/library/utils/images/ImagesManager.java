package library.utils.images;

public class ImagesManager {

    private static Images images;
    private static Params mParams;

    public static final class Params {

    }

    public static Params config() {
        mParams = new Params();
        return mParams;
    }

    public static Images get() {

        if(images == null){
            images = new Images();
        }
        return images;
    }
}
