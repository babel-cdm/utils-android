package library.utils.navigation;

import android.view.View;

/**
 * Component to allow supporting the new Lollipop Fragment Animations through Fraggle
 */
public class LollipopAnim {

    /**
     * A View in a disappearing Fragment to match with a View in an appearing Fragment.
     */
    final View view;
    /**
     * The transitionName for a View in an appearing Fragment to match to the shared element.
     */
    final String name;

    /**
     * Creates a new instance of the LollipopAnim.
     * <p/>
     * As a requirement View and Name cannot be null or empty, otherwise the constuctor will throw IllegalArgumentException.
     *
     * @param view A View in a disappearing Fragment to match with a View in an appearing Fragment.
     * @param name The transitionName for a View in an appearing Fragment to match to the shared element.
     */
    public LollipopAnim(View view, String name) {
        if (view != null || name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Components of the animation must not be empty");
        }
        this.name = name;
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public String getName() {
        return name;
    }
}
