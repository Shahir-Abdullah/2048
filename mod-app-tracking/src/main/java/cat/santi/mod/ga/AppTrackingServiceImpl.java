package cat.santi.mod.ga;

import android.content.Context;
import android.content.res.Resources;

import com.gameanalytics.sdk.GAPlatform;
import com.gameanalytics.sdk.GameAnalytics;
import com.gameanalytics.sdk.StringVector;

import cat.santi.ttfe.core.appTracking.AppTrackingService;

/**
 * @author Santiago Gonzalez
 */
public class AppTrackingServiceImpl implements AppTrackingService {

    private static final String GAME_ANALYTICS_SYSTEM_LIB = "GameAnalytics";

    static {

        System.loadLibrary(GAME_ANALYTICS_SYSTEM_LIB);
    }

    @Override
    public void initialize(Context appContext) {

        // Before everything else
        GAPlatform.initializeWithContext(appContext);

        // Configuration
        StringVector currencies = new StringVector();
        currencies.add("gems");
        currencies.add("gold");
        GameAnalytics.configureAvailableResourceCurrencies(currencies);

        // Initialization
        Resources resources = appContext.getResources();
        GameAnalytics.initializeWithGameKey(
                resources.getString(R.string.key_game_analytics),
                resources.getString(R.string.secret_game_analytics));
    }
}
