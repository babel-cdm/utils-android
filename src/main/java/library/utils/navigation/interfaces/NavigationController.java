package library.utils.navigation.interfaces;

import android.support.v4.app.Fragment;

public interface NavigationController {

    void navigateToSection(Fragment fragment, boolean addToBackStack) throws Exception;

    void navigateDown(Fragment fragment, boolean addToBackStack) throws Exception;

    void navigateUp() throws Exception;

    void navigateUp(int levels) throws Exception;
}
