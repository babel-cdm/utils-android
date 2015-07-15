package library.utils.navigation.interfaces;

import library.utils.navigation.NavigationManager;

/**
 * Interface that adds some utility methods to the Fragment Class to be used with
 * {@link NavigationManager FraggleManager}
 */
@SuppressWarnings("unused")
public interface NavigationFragment {
    /**
     * Flags the {@link NavigationManager FraggleManager} if there is to be expected
     * a certain behavior on pressing the back button or performing a back operation. This can
     * be perform through {@link #onBackPressed() onBackPressed()} method.
     *
     * @return TRUE if the Fragment needs to delegate some custom back operation to the Fragment, FALSE
     * otherwise
     */
    boolean customizedOnBackPressed();

    /**
     * Returns the Fragment tag.
     * Printter} methods and through some of the {@link NavigationManager FraggleManager}
     * operations.
     * <p/>
     * This tag is intended to be unique across all the application's fragments.
     *
     * @return A String with an ID of the Fragment.
     */
    String getFragmentTag();

    /**
     * Checks if it is an Entry Framgent.
     * <p/>
     * An entry fragment is considered any Fragment which upon a back button press will exit the application
     * instead of popping a Fragment from the back stack.
     * <p/>
     * The default behavior for this method is to declare that no fragment is an entry fragment. The
     * developer can override this method to declare under which conditions a fragment is considered
     * "entry fragment".
     *
     * @return TRUE if is is one of those, FALSE otherwise
     */
    boolean isEntryFragment();

    /**
     * Discerns if we can popBackStack a Fragment.
     * <p/>
     * The default behavior is to always add the Fragment. However the developer might find useful
     * to implement an "up" navigation and avoid infinite loop navigation through their application.
     * <p/>
     * If the Fragment is not found to need to be added, the NavigationManager will look for the
     * first known instance of it on the backstack and pop back all the fragments until it.
     *
     * @return TRUE if the Fragment needs to be Instantiated, FALSE if the fragment was popped
     */
    boolean isSingleInstance();

    /**
     * An addition to the Fragment lifecycle to ensure that the developer has a chance to perform
     * some activation after the fragment is reactivated into the fragment stack. It will be executed
     * after the {@link android.app.Fragment#onResume() onResume()} method.
     */
    void onFragmentVisible();

    /**
     * An addition to the Fragment lifecycle to ensure that the developer has a chance to perform
     * some activation before the fragment is saved into the fragment backstack. It will be executed
     * after the {@link android.app.Fragment#onDestroyView() onDestroyView()} method.
     */
    void onFragmentNotVisible();

    /**
     * Utility method to execute custom back button press actions besides returning to the previous
     * fragment
     */
    void onBackPressed();

    /**
     * Indicates if the current Fragment should return back to other different Fragment than the
     * previous one. This will allow the {@link NavigationManager#popBackStack(String, int) popBackstack(String, int)}
     * to do a jump several Fragments away.
     *
     * @return Valid NavigationFragment tag to provide the jump.
     */
    String onBackPressedTarget();

    /**
     * Utility method to execute when the current frgament try be load, so does not load, but onReload is called.
     */
    void onReload();
}
