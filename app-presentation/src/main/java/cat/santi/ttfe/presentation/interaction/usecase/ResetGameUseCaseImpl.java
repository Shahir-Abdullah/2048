package cat.santi.ttfe.presentation.interaction.usecase;

import cat.santi.mod.interaction.AbstractUseCase;
import cat.santi.ttfe.Engine;
import cat.santi.ttfe.core.interaction.Interactor;
import cat.santi.ttfe.presentation.interaction.ResetGameUseCase;

/**
 * @author Santiago Gonzalez
 */
public class ResetGameUseCaseImpl extends AbstractUseCase implements ResetGameUseCase {

    public ResetGameUseCaseImpl(Interactor interactor) {
        super(interactor);
    }

    @Override
    public void execute() {

        Engine.getInstance().reset();
    }
}
