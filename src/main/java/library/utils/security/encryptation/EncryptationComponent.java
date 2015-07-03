package library.utils.security.encryptation;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {EncryptationModule.class})
public interface EncryptationComponent {

    Encrypt provideEncrypt();
}
