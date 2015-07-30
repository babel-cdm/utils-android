package library.utils.log;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LoggerModule.class})
public interface LoggerComponent {

    Logger provideLogger();
}
