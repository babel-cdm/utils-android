package library.utils.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import library.utils.R;
import library.utils.Screen;

@SuppressWarnings("unused")
public class Utils {
    /**
     * Byte array to bitmap.
     *
     * @param res       the res
     * @param byteArray the byte array
     * @return the bitmap
     */
    public static Bitmap byteArrayToBitmap(Resources res, byte[] byteArray) {

        if (byteArray == null) return null;
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, getBitMapOption(res));
    }

    /**
     * Bitmap to base 64.
     *
     * @param bitmap the bitmap
     * @return the string
     */
    public static String bitmapToBase64(Bitmap bitmap) {

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
    public static Bitmap base64ToBitmap(Context context, String input) {
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
    public static Bitmap base64ToBitmap(Context context, String input, boolean defaultImg) {
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


    private static BitmapFactory.Options getBitMapOption(Resources res) {

        BitmapFactory.Options option = new BitmapFactory.Options();
        if (Screen.isLowOrMediumDensity(res)) {
            option.inSampleSize = 2;
        } else {
            option.inSampleSize = 1;
        }
        option.inPurgeable = true;

        return option;
    }
}
