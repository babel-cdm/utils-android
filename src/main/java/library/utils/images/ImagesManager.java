package library.utils.images;

public class ImagesManager {

    private static Params mParams;

    public static final class Params {

    }

    public static Params config() {
        mParams = new Params();
        return mParams;
    }

    public static Images get() {

        ImagesComponent component = DaggerImagesComponent.builder()
                .imagesModule(new ImagesModule()).build();

        Images images = component.provideImages();
        return images;
    }
}
