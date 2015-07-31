package cat.santi.ttfe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import cat.santi.ttfe.R;

public abstract class AbstractActivity extends AppCompatActivity {

    protected void onCreate(int layoutResID, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parseExtras(getIntent());
        if (savedInstanceState != null)
            restoreState(savedInstanceState);
        setContentView(layoutResID);
        getViews();
        if (savedInstanceState == null)
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

        if (title == null)
            title = getString(R.string.ttfe__app_name);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected abstract void parseExtras(Intent intent);

    protected abstract void getViews();

    protected abstract void firstInit();

    protected abstract void init();

    protected abstract void saveState(Bundle outState);

    protected abstract void restoreState(Bundle savedInstanceState);
}
