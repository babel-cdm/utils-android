package library.utils.rss;

public class RssManager {

    private static Rss rss;
    private static Params mParams;

    public static final class Params {
    }

    public static Params config() {
        mParams = new Params();
        return mParams;
    }

    public static Rss get() {

        if (rss == null) {
            rss = new Rss();
        }
        return rss;
    }
}
