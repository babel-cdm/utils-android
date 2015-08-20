package library.utils.rss;

public class RssManager {

    private static Params mParams;

    public static final class Params {
    }

    public static Params config() {
        mParams = new Params();
        return mParams;
    }

    public static Rss get() {

        RssComponent component = DaggerRssComponent.builder()
                .rssModule(new RssModule()).build();

        Rss rss = component.provideRss();
        return rss;
    }
}
