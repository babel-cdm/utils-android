package library.utils.log;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class Logger {

    public static final Object[] DATA_LOCK = new Object[0];

    private static boolean WRITE_IN_FILE = false;
    private static Type LEVEL_LOG = Type.ALL;

    private static String mPath;

    public enum Type {
        INFO(0, "I"),
        ERROR(1, "E"),
        WARN(2, "W"),
        DEBUG(3, "D"),
        VERBOSE(4, "V"),
        ALL(99, "A"),
        OFF(-1, "");

        int level;
        String value;

        Type(int l, String v) {
            level = l;
            value = v;
        }

        public int getLevel() {
            return level;
        }

        public String getValue() {
            return value;
        }

        static public Type fromId(int i) {
            for (Type at : Type.values()) {
                if (at.getLevel() == i) {
                    return at;
                }
            }
            return OFF;
        }
    }

    public static void print(String appTag, String classTag, String message) {
        if (LEVEL_LOG.getLevel() >= Type.INFO.getLevel()) {
            android.util.Log.i(appTag, classTag + " - " + message);
            writeToFile(appTag, Type.INFO, classTag + " - " + message);
        }
    }

    public static void print(Type type, String appTag, String classTag, String message) {
        switch (type) {
            case INFO:
                if (LEVEL_LOG.getLevel() >= Type.INFO.getLevel()) {
                    android.util.Log.i(appTag, classTag + " - " + message);
                }
                break;
            case ERROR:
                if (LEVEL_LOG.getLevel() >= Type.ERROR.getLevel()) {
                    android.util.Log.e(appTag, classTag + " - " + message);
                }
                break;
            case WARN:
                if (LEVEL_LOG.getLevel() >= Type.WARN.getLevel()) {
                    android.util.Log.w(appTag, classTag + " - " + message);
                }
                break;
            case DEBUG:
                if (LEVEL_LOG.getLevel() >= Type.DEBUG.getLevel()) {
                    android.util.Log.d(appTag, classTag + " - " + message);
                }
                break;
            case VERBOSE:
                if (LEVEL_LOG.getLevel() >= Type.VERBOSE.getLevel()) {
                    android.util.Log.v(appTag, classTag + " - " + message);
                }
                break;
        }

        writeToFile(appTag, type, classTag + " - " + message);
    }

    public static void initRecordInFile(String appTag) {
        initPath(appTag);
    }

    private static void initPath(String appTag) {
        mPath = Environment.getExternalStorageDirectory() + "/" + appTag;
        File dir = new File(mPath);
        if (!dir.exists()) dir.mkdirs();
    }

    private static void writeToFile(String appTag, Type type, final String data) {
        if (WRITE_IN_FILE) {
            File file = new File(mPath + "/" + appTag + ".log");
            SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss:SSS");

            try {
                synchronized (DATA_LOCK) {
                    if (file != null) {
                        file.createNewFile();
                        Writer out = new BufferedWriter(new FileWriter(file, true), 1024);
                        out.write(sdf.format(new Date()) + " " + type.getValue() + "/" + data + "\n");
                        out.close();
                    }
                }
            } catch (IOException e) {
            }
        }
    }
}
