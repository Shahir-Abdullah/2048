package cat.santi.ttfe.core.crashReport;

import android.content.Context;

import cat.santi.ttfe.core.Service;

/**
 * @author Santiago Gonzalez
 */
public interface CrashReportService extends Service {

    void initialize(Context appContext);
}
