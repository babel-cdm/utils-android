package library.utils.images;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImagesModule {

    @Provides
    @Singleton
    Images provideImages() {
        return new Images();
    }
}
