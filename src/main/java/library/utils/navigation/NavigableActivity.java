package library.utils.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import library.utils.R;

public class NavigableActivity extends FragmentActivity implements NavigationController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);
    }

    @Override public void navigate(Class<? extends Activity> activity, int flags, Bundle extras) {
        navigateToActivity(activity, extras, flags, null);
    }

    @Override public void navigateForResult(Class<? extends Activity> activity, int flags, Bundle extras, int code) {
        navigateToActivity(activity, extras, flags, code);
    }

    @Override public void navigateToSection(Fragment fragment, boolean addToBackStack) {
        navigateToSectionFragment(fragment, getFrameContainerID(), addToBackStack);
    }

    @Override public void navigateDown(Fragment fragment, boolean addToBackStack) {
        navigateToFragment(getSupportFragmentManager(), fragment, getFrameContainerID(), true, addToBackStack);
    }

    @Override public void navigateNestedDown(FragmentManager fm, Fragment fragment, int containerId, boolean addToBackStack) {
        navigateToFragment(fm, fragment, containerId, false, addToBackStack);
    }

    protected int getFrameContainerID() {
        return -1;
        //		throw new IllegalAccessError("Must provide a valid view container ID");
    }

    private void navigateToActivity(Class<? extends Activity> activity, Bundle extras, int flags, Integer code) {
        Intent i = new Intent(this, activity);
        i.setFlags(flags);
        if (extras != null) {
            i.putExtras(extras);
        }
        if (code == null) {
            startActivity(i);
        } else {
            startActivityForResult(i, code);
        }
    }

    private void navigateToSectionFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        if (containerId == -1) {
            throw new IllegalAccessError("Must provide a valid view container ID");
        }

        FragmentManager fm = getSupportFragmentManager();

        if (fm != null) {
            String tag = fragment.getClass().toString();

            Fragment foundFragment = fm.findFragmentByTag(tag);

            if (foundFragment != null) {
                if (!fm.popBackStackImmediate(tag, 0)) {
                    clearBackStack();
                }
                return;
            }

            clearBackStack();

            FragmentTransaction transaction = fm.beginTransaction();
            //			transaction.setCustomAnimations(R.anim.alpha_in, R.anim.alpha_out, R.anim.alpha_in, R.anim.alpha_out);
            transaction.replace(containerId, fragment, tag);

            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
        }
    }

    private void navigateToFragment(FragmentManager fm, Fragment fragment, int containerId, boolean animated, boolean addToBackStack) {
        if (containerId == -1) {
            throw new IllegalAccessError("Must provide a valid view container ID");
        }

        if (fm != null) {
            FragmentTransaction transaction = fm.beginTransaction();

            String tag = fragment.getClass().toString();
            if (animated) {
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            }

            transaction.replace(containerId, fragment, tag);

            //			fm.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }

            transaction.commit();
        }
    }

    private void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    @Override public void onBackPressed() {
        if (getFrameContainerID() != -1) {
            Fragment current = getCurrentFragment(getFrameContainerID());
            if (current == null || !current.getChildFragmentManager().popBackStackImmediate()) {
                onBackFromNavigation();
            }
        } else {
            onBackFromNavigation();
        }
    }

    @Override public void navigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override public void navigateNestedUp(FragmentManager fm) {
        fm.popBackStack();
    }

    protected Fragment getCurrentFragment(int containerId) {
        return getSupportFragmentManager().findFragmentById(containerId);
    }

    private FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override public void onBackStackChanged() {
            NavigableActivity.this.onBackStackChanged();
        }
    };

    protected void onBackFromNavigation() {
        navigateUp();
    }

    protected void onBackStackChanged() {}
}
