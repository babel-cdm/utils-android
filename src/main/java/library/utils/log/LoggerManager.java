package library.utils.log;

public class LoggerManager {

    private static Params mParams;

    public static final class Params {
        Type type;

        public Type getType() {
            return type;
        }

        public Params setShowLevel(Type t) {
            this.type = t;
            return this;
        }
    }

    public static Params config() {
        mParams = new Params();
        return mParams;
    }

    public static Logger get() {

        LoggerComponent component = DaggerLoggerComponent.builder()
                .loggerModule(new LoggerModule()).build();

        Logger logger = component.provideLogger();
        logger.setShowLevel(mParams.getType());
        return logger;
    }
}
