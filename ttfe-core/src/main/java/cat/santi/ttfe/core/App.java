package cat.santi.ttfe.core;

import cat.santi.ttfe.core.appTracking.AppTrackingService;
import cat.santi.ttfe.core.crashReport.CrashReportService;

/**
 * @author Santiago Gonzalez
 */
public interface App {

    CrashReportService getFabricService();

    AppTrackingService getGameAnalyticsService();
}
