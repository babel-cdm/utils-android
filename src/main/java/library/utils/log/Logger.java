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
    private Type mLevelLog = Type.ALL;

    private static String mPath;

    public Logger() {}

    public void setShowLevel(Type type) {
        mLevelLog = type;
    }

    public void print(String appTag, String classTag, String message) {
        if (mLevelLog.getLevel() >= Type.INFO.getLevel()) {
            android.util.Log.i(appTag, classTag + " - " + message);
            writeToFile(appTag, Type.INFO, classTag + " - " + message);
        }
    }

    public void print(Type type, String appTag, String classTag, String message) {
        switch (type) {
            case INFO:
                if (mLevelLog.getLevel() >= Type.INFO.getLevel()) {
                    android.util.Log.i(appTag, classTag + " - " + message);
                }
                break;
            case ERROR:
                if (mLevelLog.getLevel() >= Type.ERROR.getLevel()) {
                    android.util.Log.e(appTag, classTag + " - " + message);
                }
                break;
            case WARN:
                if (mLevelLog.getLevel() >= Type.WARN.getLevel()) {
                    android.util.Log.w(appTag, classTag + " - " + message);
                }
                break;
            case DEBUG:
                if (mLevelLog.getLevel() >= Type.DEBUG.getLevel()) {
                    android.util.Log.d(appTag, classTag + " - " + message);
                }
                break;
            case VERBOSE:
                if (mLevelLog.getLevel() >= Type.VERBOSE.getLevel()) {
                    android.util.Log.v(appTag, classTag + " - " + message);
                }
                break;
        }

        writeToFile(appTag, type, classTag + " - " + message);
    }

    public void initRecordInFile(String appTag) {
        initPath(appTag);
    }

    private void initPath(String appTag) {
        mPath = Environment.getExternalStorageDirectory() + "/" + appTag;
        File dir = new File(mPath);
        if (!dir.exists()) dir.mkdirs();
    }

    private void writeToFile(String appTag, Type type, final String data) {
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
