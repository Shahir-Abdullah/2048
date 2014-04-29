package cat.santi.android.tttf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import cat.santi.android.tttf.R;
import cat.santi.android.tttf.commons.TTTFConstants;

public abstract class AbsTTTFActivity extends ActionBarActivity {

	protected static final String EXTRA    = TTTFConstants.PACKAGE + ".extra";
	protected static final String INSTANCE = TTTFConstants.PACKAGE + ".instance";
	
	protected void onCreate(int layoutResID, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		parseExtras(getIntent());
		restoreState(savedInstanceState);
		setContentView(layoutResID);
		getViews();
		if(savedInstanceState == null)
			firstInit();
		init();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		
		parseExtras(getIntent());
		getViews();
		init();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState(outState);
	}
	
	protected void configureActionBar(String title) {
		
		if(title == null)
			title = getString(R.string.ffft__app_name);
		
		getSupportActionBar().setTitle(title);
	}
	
	protected abstract void parseExtras(Intent intent);
	
	protected abstract void getViews();
	
	protected abstract void firstInit();
	
	protected abstract void init();
	
	protected abstract void saveState(Bundle outState);
	
	protected abstract void restoreState(Bundle savedInstanceState);
}
