package cat.santi.ttfe.core.appTracking;

import android.content.Context;

import cat.santi.ttfe.core.Service;

/**
 * @author Santiago Gonzalez
 */
public interface AppTrackingService extends Service {

    void initialize(Context appContext);
}
