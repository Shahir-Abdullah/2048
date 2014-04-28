package cat.santi.android.tttf.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cat.santi.android.tttf.commons.TTTFConstants;

public abstract class AbsTTTFFragment extends Fragment {

	protected static final String ARGUMENT = TTTFConstants.PACKAGE + ".arg";
	protected static final String INSTANCE = TTTFConstants.PACKAGE + ".instance";
	
	protected View mFragmentView;
	
	public View onCreateView(int layoutResID, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if(container == null)
			return null;
		
		configureActionBar();
		parseArguments(getArguments());
		restoreState(savedInstanceState);
		mFragmentView = inflater.inflate(layoutResID, null);
		getViews(mFragmentView);
		if(savedInstanceState == null)
			firstInit();
		init();
		return mFragmentView;
	};
	
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
