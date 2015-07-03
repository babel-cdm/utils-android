package library.utils.security.encryptation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EncryptationModule {

    @Provides
    @Singleton
    Encrypt provideEncrypt() {
        return new Encrypt();
    }
}
