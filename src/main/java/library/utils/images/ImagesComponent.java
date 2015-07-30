package library.utils.images;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ImagesModule.class})
public interface ImagesComponent {

    Images provideImages();
}
