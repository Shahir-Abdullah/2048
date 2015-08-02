package cat.santi.mod.fabric;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import cat.santi.ttfe.core.crashReport.CrashReportService;
import io.fabric.sdk.android.Fabric;

/**
 * @author Santiago Gonzalez
 */
public class CrashReportServiceImpl implements CrashReportService {

    @Override
    public void initialize(Context appContext) {

        Fabric.with(appContext, new Crashlytics());
    }
}
