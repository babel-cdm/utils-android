package library.utils.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import library.utils.R;
import library.utils.navigation.interfaces.Exceptions;
import library.utils.navigation.interfaces.NavigationController;
import library.utils.navigation.interfaces.NavigationFragment;
import library.utils.navigation.interfaces.OnActionNavigation;

@SuppressWarnings("unused")
public class NavigationActivity extends FragmentActivity implements NavigationController, Exceptions {

    protected Integer sContainer = null;
    protected NavigationManager sNavigationManager;
    protected OnActionNavigation sOnActionNavigation;

    protected FragmentAnimation sAnimation = new FragmentAnimation(
            R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sNavigationManager = new NavigationManager();
        this.sNavigationManager.initialize(getSupportFragmentManager());
        sNavigationManager.setAnimation(sAnimation);
    }

    public NavigationActivity setContainer(int id) {
        this.sContainer = id;
        return this;
    }

    public NavigationActivity config() {
        return this;
    }

    public NavigationActivity setAnimation(FragmentAnimation anim) {
        this.sAnimation = anim;
        sNavigationManager.setAnimation(sAnimation);
        return this;
    }

    public NavigationActivity setOnActionNavigation(OnActionNavigation listener) {
        this. sOnActionNavigation = listener;
        return this;
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
        if (sContainer == null) {
            throw new Exception(CONTAINER_EXCEPTION);
        }

        sNavigationManager.popBackStack(sContainer);
    }

    @Override
    public void navigateUp(int levels) throws Exception {
        if (sContainer == null) {
            throw new Exception(CONTAINER_EXCEPTION);
        }

        if (levels > sNavigationManager.getBackStackEntryCount()) {
            throw new Exception(SIZE_STACK_EXCEPTION);
        }

        for (int i = 0; i < levels; i++) {
            sNavigationManager.popBackStack(sContainer);
        }
    }

    private void setFragment(Fragment fragment, int flags) throws Exception {
        if (sContainer == null) {
            throw new Exception(CONTAINER_EXCEPTION);
        }

        sNavigationManager.addFragment(fragment, ((NavigationFragment) fragment).getFragmentTag(), sAnimation, flags, sContainer);
    }

    public boolean canActivityFinish(){
        return sNavigationManager.canActivityFinish();
    }

    @Override
    public void onBackPressed() {
        if (sOnActionNavigation != null) {
            sOnActionNavigation.onBackPressedNavigation();
        }
    }

    public int getCountBackNavigation() {
        return sNavigationManager.getBackStackEntryCount();
    }

    public NavigationFragment getCurrentFragment() {
        return sNavigationManager.getLastFragmentOfStack();
    }
}
