package cat.santi.android.tttf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import cat.santi.android.tttf.R;
import cat.santi.android.tttf.fragments.MainFragment;
import cat.santi.tttf.TTTFEngine;

public class MainActivity extends AbsTTTFActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(R.layout.activity_main, savedInstanceState);
	}
	
	@Override
	protected void configureActionBar() {
		
	}

	@Override
	protected void parseExtras(Intent intent) {
		
		if(intent == null || intent.getExtras() == null)
			return;
	}

	@Override
	protected void getViews() {
		
	}

	@Override
	protected void firstInit() {
		
		TTTFEngine.getInstance().reset();
		
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.tttf__activity_main__container, MainFragment.newInstance())
		.commit();
	}

	@Override
	protected void init() {
		
	}

	@Override
	protected void saveState(Bundle outState) {
		
	}

	@Override
	protected void restoreState(Bundle savedInstanceState) {
		
		if(savedInstanceState == null)
			return;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}
}
