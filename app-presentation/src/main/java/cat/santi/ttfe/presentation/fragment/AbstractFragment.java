package cat.santi.ttfe.presentation.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.santi.ttfe.core.App;

/**
 * @author Santiago Gonzalez
 */
public abstract class AbstractFragment<Interact> extends Fragment {

    private Interact mInteractor;

    public View onCreateView(int layoutResID, LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        configureActionBar();
        parseArguments(getArguments());
        if (savedInstanceState != null)
            restoreState(savedInstanceState);
        View mFragmentView = inflater.inflate(layoutResID, container, false);
        getViews(mFragmentView);
        if (savedInstanceState == null)
            firstInit();
        init();
        return mFragmentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mInteractor = onCreateInteractor();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mInteractor = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(outState);
    }

    protected Interact getInteractor() {

        return mInteractor;
    }

    protected App getApp() {

        if(getActivity() == null)
            throw new IllegalStateException("Fragment is already detached from the Activity");

        return (App) getActivity().getApplication();
    }

    protected abstract Interact onCreateInteractor();

    protected abstract void configureActionBar();

    protected abstract void parseArguments(Bundle args);

    protected abstract void getViews(View fragmentView);

    protected abstract void firstInit();

    protected abstract void init();

    protected abstract void saveState(Bundle outState);

    protected abstract void restoreState(Bundle savedInstanceState);
}
