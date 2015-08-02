package cat.santi.ttfe.presentation;

import cat.santi.mod.fabric.CrashReportServiceImpl;
import cat.santi.mod.ga.AppTrackingServiceImpl;
import cat.santi.ttfe.core.Service;

/**
 *
 */
public class Dependencies {

    public static final int SERVICE_CRASH_REPORT = 0;
    public static final int SERVICE_APP_TRACKING = 1;

    private static Service sCrashReportService;
    private static Service sAppTrackingService;

    private Dependencies() {
        // Private constructor to thwart instantiation
    }

    public static Service getService(int service) {

        switch (service) {
            case SERVICE_CRASH_REPORT:
                return getCrashReportService();
            case SERVICE_APP_TRACKING:
                return getAppTrackingService();
            default:
                throw new IllegalArgumentException("unknown service");
        }
    }

    private static Service getCrashReportService() {

        if(sCrashReportService == null)
            sCrashReportService = new CrashReportServiceImpl();
        return sCrashReportService;
    }

    private static Service getAppTrackingService() {

        if(sAppTrackingService == null)
            sAppTrackingService = new AppTrackingServiceImpl();
        return sAppTrackingService;
    }
}
