package library.utils.images;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import library.utils.R;
import library.utils.Screen;

@SuppressWarnings("unused")
public class Images {
    /**
     * Byte array to bitmap.
     *
     * @param res       the res
     * @param byteArray the byte array
     * @return the bitmap
     */
    public Bitmap byteArrayToBitmap(Resources res, byte[] byteArray) {

        if (byteArray == null) return null;
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, getBitMapOption(res));
    }

    /**
     * Bitmap to base 64.
     *
     * @param bitmap the bitmap
     * @return the string
     */
    public String bitmapToBase64(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Base 64 to bitmap.
     *
     * @param context the context
     * @param input   the input
     * @return the bitmap
     */
    public Bitmap base64ToBitmap(Context context, String input) {
        return base64ToBitmap(context, input, true);
    }

    /**
     * Base 64 to bitmap.
     *
     * @param context    the context
     * @param input      the input
     * @param defaultImg the default img
     * @return the bitmap
     */
    public Bitmap base64ToBitmap(Context context, String input, boolean defaultImg) {
        Bitmap b = null;

        if (defaultImg) {
            b = BitmapFactory.decodeResource(context.getResources(), R.drawable.unknown);
        } else if (input == null || "".equals(input)) {
            return null;
        }

        try {
            byte[] decodeByte = Base64.decode(input, Base64.DEFAULT);
            b = BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length, getBitMapOption(context.getResources()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * Resource ID to bitmap with sepcific size
     *
     * @param res      resources
     * @param size     the size we want the drawable to be
     * @param drawable the drawable to convert
     * @return the drawable converted
     */
    public Drawable bitmapToDrawable(Resources res, int size, int drawable) {
        Bitmap bitmap = ((BitmapDrawable) res.getDrawable(drawable)).getBitmap();
        int dp = Screen.dpToPx(res, size);
        return new BitmapDrawable(res, Bitmap.createScaledBitmap(bitmap, dp, dp, true));
    }

    private BitmapFactory.Options getBitMapOption(Resources res) {

        BitmapFactory.Options option = new BitmapFactory.Options();
        if (Screen.isLowOrMediumDensity(res)) {
            option.inSampleSize = 2;
        } else {
            option.inSampleSize = 1;
        }
        option.inPurgeable = true;

        return option;
    }

    public void changeBitmapColor(Bitmap sourceBitmap, ImageView image, int color) {

        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);
        image.setImageBitmap(resultBitmap);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
    }

    public String saveToInternalSorage(Context applicationContext, Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(applicationContext);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public String saveToExternalStorage(String appName, String imageName, Bitmap image) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + appName.replaceAll(" ", ""));
        myDir.mkdirs();
        File file = new File(myDir, imageName + ".jpg");
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap processPhoto(String path, int maxSize) {
        File photo = new File(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photo.getAbsolutePath(), options);
        do {
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / 1.1), (int) (bitmap.getHeight() / 1.1), false);
        } while (bitmap.getByteCount() > maxSize);
        return bitmap;
    }

    public Bitmap processPhoto(Bitmap image, int maxSize) {
        Bitmap bitmap = image;
        do {
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / 1.1), (int) (bitmap.getHeight() / 1.1), false);
        } while (bitmap.getByteCount() > maxSize);
        return bitmap;
    }
}
