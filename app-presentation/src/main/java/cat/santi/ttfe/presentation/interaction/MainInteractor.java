package cat.santi.ttfe.presentation.interaction;

import cat.santi.ttfe.Engine;
import cat.santi.ttfe.core.interaction.UseCase;

/**
 * @author Santiago Gonzalez
 */
public interface MainInteractor {

    UseCase getResetGameUC();

    UseCase getPlayUC(Engine.Direction direction);
}
