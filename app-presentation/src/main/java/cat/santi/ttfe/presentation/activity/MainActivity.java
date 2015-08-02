package cat.santi.ttfe.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cat.santi.ttfe.Engine;
import cat.santi.ttfe.presentation.R;
import cat.santi.ttfe.presentation.common.Preferences;
import cat.santi.ttfe.presentation.common.SoundManager;
import cat.santi.ttfe.presentation.fragment.MainFragment;
import cat.santi.ttfe.presentation.fragment.MainFragment.MainFragmentCallbacks;

/**
 * @author Santiago Gonzalez
 */
public class MainActivity extends AbstractActivity implements
        MainFragmentCallbacks {

    private Boolean mSounds = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(R.layout.ttfe__activity_main, savedInstanceState);

        configureActionBar(getString(R.string.ttfe__main__title));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSounds = Preferences.Sounds.getInstance().load(this, true);
        SoundManager.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SoundManager.getInstance(this).onPause();
    }

    @Override
    protected void parseExtras(Intent intent) {
        // Do nothing
    }

    @Override
    protected void getViews() {
        // Do nothing
    }

    @Override
    protected void firstInit() {

        Engine.getInstance().reset();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ttfe__activity_main__container, MainFragment.newInstance())
                .commit();
    }

    @Override
    protected void init() {
        // Do nothing
    }

    @Override
    protected void saveState(Bundle outState) {
        // Do nothing
    }

    @Override
    protected void restoreState(Bundle savedInstanceState) {
        // Do nothing
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.ttfe__main, menu);

        for (int index = 0; index < menu.size(); index++)
            if (menu.getItem(index).getItemId() == R.id.ttfe__menu_action_sounds) {

                if (Preferences.Sounds.getInstance().load(this, true)) {

                    menu.getItem(index).setChecked(true);
                    menu.getItem(index).setTitle(R.string.ttfe__action_sounds_on);
                } else {

                    menu.getItem(index).setChecked(false);
                    menu.getItem(index).setTitle(R.string.ttfe__action_sounds_off);
                }
            }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.ttfe__menu_action_reset:

                Engine.getInstance().reset();
                return true;
            case R.id.ttfe__menu_action_sounds:

                item.setChecked(!item.isChecked());
                if (item.isChecked()) {

                    item.setTitle(R.string.ttfe__action_sounds_on);
                    mSounds = true;
                } else {

                    item.setTitle(R.string.ttfe__action_sounds_off);
                    mSounds = false;
                }
                Preferences.Sounds.getInstance().save(this, item.isChecked());
                return true;
            case R.id.ttfe__menu_action_settings:

                //- TODO: IMPLEMENT
                Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onUserPlay(Engine.Direction direction) {

        if (mSounds)
            SoundManager.getInstance(this).play(SoundManager.Sound.SLIDE);
    }
}
