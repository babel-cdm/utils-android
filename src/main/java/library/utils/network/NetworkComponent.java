package library.utils.network;

import javax.inject.Singleton;

import dagger.Component;
import library.utils.images.Images;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {

    Network provideNetwork();
}
