package cat.santi.ttfe.core;

import android.content.Context;

/**
 * @author Santiago Gonzalez
 */
public interface AppTrackingService extends Service {

    void initialize(Context appContext);
}
