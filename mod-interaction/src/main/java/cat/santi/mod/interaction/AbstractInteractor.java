package cat.santi.mod.interaction;

import java.lang.ref.WeakReference;

import cat.santi.ttfe.core.App;
import cat.santi.ttfe.core.interaction.Interactor;

/**
 *
 */
public abstract class AbstractInteractor implements Interactor {

    private final WeakReference<App> mAppRef;

    public AbstractInteractor(App app) {

        if (app == null)
            throw new IllegalArgumentException("app == null");

        mAppRef = new WeakReference<>(app);
    }

    @Override
    public App getApp() {
        return mAppRef.get();
    }
}
