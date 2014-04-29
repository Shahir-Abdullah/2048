package cat.santi.android.tttf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import cat.santi.android.tttf.R;
import cat.santi.android.tttf.commons.TTTFPreferences;
import cat.santi.android.tttf.commons.TTTFSoundManager;
import cat.santi.android.tttf.fragments.MainFragment;
import cat.santi.android.tttf.fragments.MainFragment.MainFragmentCallbacks;
import cat.santi.tttf.TTTFEngine;

public class MainActivity extends AbsTTTFActivity
implements MainFragmentCallbacks {

	private Boolean mSounds = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(R.layout.activity_main, savedInstanceState);
		configureActionBar(getString(R.string.ffft__main__title));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		mSounds = TTTFPreferences.Sounds.getInstance().load(true);
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
		
		for(int index = 0 ; index < menu.size() ; index++)
			if(menu.getItem(index).getItemId() == R.id.ffft__menu_action_sounds) {
				
				if(TTTFPreferences.Sounds.getInstance().load(true)) {
					
					menu.getItem(index).setChecked(true);
					menu.getItem(index).setTitle(R.string.ffft__action_sounds_on);
				} else {
					
					menu.getItem(index).setChecked(false);
					menu.getItem(index).setTitle(R.string.ffft__action_sounds_off);
				}
			}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.ffft__menu_action_reset:
			
			TTTFEngine.getInstance().reset();
			return true;
		case R.id.ffft__menu_action_sounds:
			
				item.setChecked(!item.isChecked());
				if(item.isChecked()) {
					
					item.setTitle(R.string.ffft__action_sounds_on);
					mSounds = true;
				} else {
					
					item.setTitle(R.string.ffft__action_sounds_off);
					mSounds = false;
				}
				TTTFPreferences.Sounds.getInstance().save(item.isChecked());
			return true;
		case R.id.ffft__menu_action_settings:
			
			//- TODO: IMPLEMENT
			Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onUserPlay() {

		if(mSounds)
			TTTFSoundManager.getInstance(this).playSlide(this);
	}
}
