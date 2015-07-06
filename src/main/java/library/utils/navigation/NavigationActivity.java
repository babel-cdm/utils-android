package library.utils.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import library.utils.navigation.interfaces.Exceptions;
import library.utils.navigation.interfaces.NavigationController;

public class NavigationActivity extends FragmentActivity implements NavigationController, Exceptions {

    protected Integer mContainer = null;
    protected NavigationManager mNavigationManager;

    protected FragmentAnimation animation = new FragmentAnimation(android.R.animator.fade_in,
            android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigationManager = new NavigationManager();
        mNavigationManager.initialize(getSupportFragmentManager());
    }

    protected void initContainer(int id) {
        mContainer = id;
    }

    @Override
    public void navigateToSection(Fragment fragment, boolean addToBackStack) throws Exception {

        int flags = (addToBackStack ? NavigationManager.ADD_TO_BACKSTACK : NavigationManager.DO_NOT_ADD_TO_BACKSTACK) &
                NavigationManager.CLEAR_BACKSTACK;

        setFragment(fragment, flags);
    }

    @Override
    public void navigateDown(Fragment fragment, boolean addToBackStack) throws Exception {
        int flags = (addToBackStack ? NavigationManager.ADD_TO_BACKSTACK : NavigationManager.DO_NOT_ADD_TO_BACKSTACK);

        setFragment(fragment, flags);
    }

    @Override
    public void navigateUp() throws Exception {
        if (mContainer == null) {
            throw new Exception(CONTAINER_EXCEPTION);
        }

        mNavigationManager.popBackStack(mContainer);
    }

    @Override
    public void navigateUp(int levels) throws Exception {
        if (mContainer == null) {
            throw new Exception(CONTAINER_EXCEPTION);
        }

        if (levels > mNavigationManager.getBackStackEntryCount()) {
            throw new Exception(SIZE_STACK_EXCEPTION);
        }

        for (int i = 0; i < levels; i++) {
            mNavigationManager.popBackStack(mContainer);
        }
    }

    private void setFragment(Fragment fragment, int flags) throws Exception {
        if (mContainer == null) {
            throw new Exception(CONTAINER_EXCEPTION);
        }

        mNavigationManager.addFragment(fragment, fragment.getTag(), animation, flags, mContainer);
    }

    public void onBackFromNavigation() throws Exception {
        navigateUp();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationManager.canActivityFinish()) {
            finish();
        } else {
            mNavigationManager.popBackStack(mContainer);
        }
    }
}
