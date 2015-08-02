package cat.santi.ttfe.presentation.interaction.interactor;

import cat.santi.mod.interaction.AbstractInteractor;
import cat.santi.ttfe.Engine;
import cat.santi.ttfe.core.App;
import cat.santi.ttfe.core.interaction.UseCase;
import cat.santi.ttfe.presentation.interaction.MainInteractor;
import cat.santi.ttfe.presentation.interaction.usecase.PlayUseCaseImpl;
import cat.santi.ttfe.presentation.interaction.usecase.ResetGameUseCaseImpl;

/**
 * @author Santiago Gonzalez
 */
public class MainInteractorImpl extends AbstractInteractor implements MainInteractor {

    public MainInteractorImpl(App app) {
        super(app);
    }

    @Override
    public UseCase getResetGameUC() {

        return new ResetGameUseCaseImpl(this);
    }

    @Override
    public UseCase getPlayUC(Engine.Direction direction) {

        return new PlayUseCaseImpl(this, direction);
    }
}
