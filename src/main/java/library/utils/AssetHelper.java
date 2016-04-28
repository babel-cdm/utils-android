package library.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by BABEL Sistemas de Información.
 */
public class AssetHelper {

    private static AssetHelper mInstance;

    private AssetHelper(){

    }

    public static AssetHelper getInstance(){
        if (mInstance == null) {
            mInstance = new AssetHelper();;
        }

        return mInstance;
    }

    /**
     * Copy the asset at the specified path to this app's data directory. If the
     * asset is a directory, its contents are also copied.
     *
     * @param path Path to asset, relative to app's assets directory.
     * @param context Necessary for retrieving directory path
     */
    public void copyAsset(String path, Context context) {
        AssetManager manager = context.getAssets();

        // If we have a directory, we make it and recurse. If a file, we copy its
        // contents.
        try {
            String[] contents = manager.list(path);

            // The documentation suggests that list throws an IOException, but doesn't
            // say under what conditions. It'd be nice if it did so when the path was
            // to a file. That doesn't appear to be the case. If the returned array is
            // null or has 0 length, we assume the path is to a file. This means empty
            // directories will get turned into files.
            if (contents == null || contents.length == 0)
                throw new IOException();

            // Make the directory.
            File dir = new File(context.getExternalFilesDir(null), path);
            dir.mkdirs();

            // Recurse on the contents.
            for (String entry : contents) {
                copyAsset(path + "/" + entry, context);
            }
        } catch (IOException e) {
            copyFileAsset(path, context);
        }
    }

    /**
     * Copy the asset file specified by path to app's data directory. Assumes
     * parent directories have already been created.
     *
     * @param path Path to asset, relative to app's assets directory.
     * @param context Necessary for retrieving directory path
     */
    public void copyFileAsset(String path, Context context) {
        File file = new File(context.getExternalFilesDir(null), path);
        try {
            InputStream in = context.getAssets().open(path);
            OutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read = in.read(buffer);
            while (read != -1) {
                out.write(buffer, 0, read);
                read = in.read(buffer);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }

}
