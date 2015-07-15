package library.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;

@SuppressWarnings("unused")
public class Screen {

    public enum ScreenSize {LARGE, NORMAL, SMALL, OTHER}
    public enum ScreenDensity {LOW, MEDIUM, HIGH, XHIGH, OTHER}

    public static int dpToPx(Resources resources, float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

    public static int convertToPixels(Resources resources, int nDP) {
        final float conversionScale = resources.getDisplayMetrics().density;
        return (int) ((nDP * conversionScale) + 0.5f) ;
    }

    public static ScreenSize getScreenSize(Resources resources) {
        //Determine screen size
        if ((resources.getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return ScreenSize.LARGE;
        } else if ((resources.getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            return ScreenSize.NORMAL;
        } else if ((resources.getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            return ScreenSize.SMALL;
        } else {
            return ScreenSize.OTHER;
        }
    }

    public static ScreenDensity getScreenDesnsity(Resources resources) {
        switch (resources.getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return ScreenDensity.LOW;
            case DisplayMetrics.DENSITY_MEDIUM:
                return ScreenDensity.MEDIUM;
            case DisplayMetrics.DENSITY_HIGH:
                return ScreenDensity.HIGH;
            case DisplayMetrics.DENSITY_XHIGH:
                return ScreenDensity.XHIGH;
            case DisplayMetrics.DENSITY_XXHIGH:
                return ScreenDensity.XHIGH;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return ScreenDensity.XHIGH;
            default:
                return ScreenDensity.OTHER;
        }
    }

    public static boolean isLowOrMediumOrHighDensity(Resources resources) {
        return (getScreenDesnsity(resources) == ScreenDensity.LOW ||
                getScreenDesnsity(resources) == ScreenDensity.MEDIUM ||
                getScreenDesnsity(resources) == ScreenDensity.HIGH ||
                getScreenDesnsity(resources) == ScreenDensity.OTHER ||
                getScreenDesnsity(resources) == ScreenDensity.XHIGH);
    }

    public static boolean isLowOrMediumDensity(Resources resources) {
        return (getScreenDesnsity(resources) == ScreenDensity.LOW ||
                getScreenDesnsity(resources) == ScreenDensity.MEDIUM ||
                getScreenDesnsity(resources) == ScreenDensity.OTHER);
    }

    public static int getWidth(Activity a) {
        Display display = a.getWindowManager().getDefaultDisplay();

        if (AndroidVersion.isHoneycombMR2OrUp()) {
            Point size = new Point();
            display.getSize(size);
            return size.x;
        } else {
            return display.getWidth();
        }
    }

    public static int getHeight(Activity a) {
        Display display = a.getWindowManager().getDefaultDisplay();

        if (AndroidVersion.isHoneycombMR2OrUp()) {
            Point size = new Point();
            display.getSize(size);
            return size.y;
        } else {
            return display.getHeight();
        }
    }
}

