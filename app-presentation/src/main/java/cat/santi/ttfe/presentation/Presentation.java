package cat.santi.ttfe.presentation;

import android.app.Application;

import cat.santi.ttfe.core.AppTrackingService;
import cat.santi.ttfe.core.fabric.CrashReportService;

/**
 * Main presentation module controller. Also extends the {@link Application} class.
 *
 * @author Santiago Gonzalez
 */
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

    public CrashReportService getFabricService() {

        return (CrashReportService) Dependencies.getService(Dependencies.SERVICE_CRASH_REPORT);
    }

    public AppTrackingService getGameAnalyticsService() {

        return (AppTrackingService) Dependencies.getService(Dependencies.SERVICE_APP_TRACKING);
    }

    private void initCrashlytics() {

        getFabricService().initialize(this);
    }

    private void initGameAnalyticsLib() {

        getGameAnalyticsService().initialize(this);
    }
}
