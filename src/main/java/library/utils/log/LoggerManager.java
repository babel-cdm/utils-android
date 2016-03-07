package library.utils.log;

public class LoggerManager {

    private static Params mParams;
    private static Logger logger;

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

        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }
}
