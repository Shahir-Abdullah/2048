package cat.santi.ttfe.core.interaction;

/**
 * @author Santiago Gonzalez
 */
public interface UseCase {

    Interactor getInteractor();

    void execute();
}
