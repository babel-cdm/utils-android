package library.utils.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Network provideNetwork() {
        return new Network();
    }
}
