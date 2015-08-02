package cat.santi.ttfe.presentation.interaction.usecase;

import cat.santi.mod.interaction.AbstractUseCase;
import cat.santi.mod.interaction.UseCaseResultImpl;
import cat.santi.ttfe.Engine;
import cat.santi.ttfe.core.interaction.Interactor;
import cat.santi.ttfe.core.interaction.UseCaseResult;

/**
 *
 */
public class PlayUseCaseImpl extends AbstractUseCase {

    private final Engine.Direction mDirection;

    public PlayUseCaseImpl(Interactor interactor, Engine.Direction direction) {
        super(interactor);

        mDirection = direction;
    }

    @Override
    public UseCaseResult execute() {

        final boolean result = Engine.getInstance().play(mDirection, false);
        return new UseCaseResultImpl.Builder()
                .setSuccess(result)
                .build();
    }
}
