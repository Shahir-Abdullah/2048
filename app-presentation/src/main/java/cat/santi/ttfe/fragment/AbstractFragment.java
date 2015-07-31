package cat.santi.ttfe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractFragment extends Fragment {

    protected View mFragmentView;

    public View onCreateView(int layoutResID, LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null)
            return null;

        configureActionBar();
        parseArguments(getArguments());
        if (savedInstanceState != null)
            restoreState(savedInstanceState);
        mFragmentView = inflater.inflate(layoutResID, null);
        getViews(mFragmentView);
        if (savedInstanceState == null)
            firstInit();
        init();
        return mFragmentView;
    }

    ;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(outState);
    }

    protected abstract void configureActionBar();

    protected abstract void parseArguments(Bundle args);

    protected abstract void getViews(View fragmentView);

    protected abstract void firstInit();

    protected abstract void init();

    protected abstract void saveState(Bundle outState);

    protected abstract void restoreState(Bundle savedInstanceState);
}
