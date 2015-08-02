package cat.santi.mod.interaction;

import java.lang.ref.WeakReference;

import cat.santi.ttfe.core.interaction.Interactor;
import cat.santi.ttfe.core.interaction.UseCase;

/**
 *
 */
public abstract class AbstractUseCase implements UseCase {

    private final WeakReference<Interactor> mInteractorRef;

    public AbstractUseCase(Interactor interactor) {

        if(interactor == null)
            throw new IllegalArgumentException("interactor == null");

        mInteractorRef = new WeakReference<>(interactor);
    }

    @Override
    public Interactor getInteractor() {
        return mInteractorRef.get();
    }
}
