package library.utils.rss;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RssModule {

    @Provides
    @Singleton
    Rss provideRss() {
        return new Rss();
    }
}
