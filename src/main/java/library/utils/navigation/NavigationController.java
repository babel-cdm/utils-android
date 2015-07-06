package library.utils.navigation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public interface NavigationController {

    void navigate(Class<? extends Activity> activity, int flags, Bundle extras);

    void navigateForResult(Class<? extends Activity> activity, int flags, Bundle extras, int code);

    void navigateToSection(Fragment fragment, boolean addToBackStack);

    void navigateDown(Fragment fragment, boolean addToBackStack);

    void navigateNestedDown(FragmentManager fm, Fragment fragment, int containerId, boolean addToBackStack);

    void navigateUp();

    void navigateNestedUp(FragmentManager fm);
}
