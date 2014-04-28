package cat.santi.android.tttf;

import android.app.Application;
import android.content.Context;

public class TTTFApplication extends Application {

	private static Context app;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		app = getApplicationContext();
	}
	
	public static Context getApp() {
		
		return app;
	}
}
