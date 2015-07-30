package library.utils.log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoggerModule {

    @Provides
    @Singleton
    Logger provideLogger() {
        return new Logger();
    }
}
