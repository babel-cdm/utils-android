package library.utils.rss;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RssModule.class})
public interface RssComponent {

    Rss provideRss();
}
