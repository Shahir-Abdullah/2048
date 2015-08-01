package cat.santi.ttfe;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.gameanalytics.sdk.GAPlatform;
import com.gameanalytics.sdk.GameAnalytics;
import com.gameanalytics.sdk.StringVector;

import io.fabric.sdk.android.Fabric;

public class Presentation extends Application {

    private static final String GAME_ANALYTICS_SYSTEM_LIB = "GameAnalytics";

    static {

        System.loadLibrary(GAME_ANALYTICS_SYSTEM_LIB);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initCrashlytics();
        initGameAnalyticsLib();
    }

    private void initCrashlytics() {

        Fabric.with(this, new Crashlytics());
    }

    private void initGameAnalyticsLib() {

        // Before everything else
        GAPlatform.initializeWithContext(this);

        // Configuration
        StringVector currencies = new StringVector();
        currencies.add("gems");
        currencies.add("gold");
        GameAnalytics.configureAvailableResourceCurrencies(currencies);

        // Initialization
        GameAnalytics.initializeWithGameKey(
                getString(R.string.ttfe__key_game_analytics),
                getString(R.string.ttfe__secret_game_analytics));
    }
}
