package cat.santi.ttfe.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class Preferences {

	private static final String TAG = Preferences.class.getSimpleName();
	
	private static final String KEY_PREFIX = "cat.santi.android.ttfe.preferences";
	
	private Preferences() {}
	
	public static class Sounds extends AbsTTTFPreferences<Boolean> {
		
		public Sounds() {
			super(Boolean.class, ".sounds");
		}
		
		public static Sounds getInstance() {
			return new Sounds();
		}
	}
	
	private static abstract class AbsTTTFPreferences<T> {
		
		private Class<T> clazz = null;
		private String key = null;
		
		public AbsTTTFPreferences(Class<T> clazz, String key) {
		
			if(clazz == null)
				throw new RuntimeException("Parameter [clazz] must not be null");
			if(key == null)
				throw new RuntimeException("Parameter [key] must not be null");
				
			this.clazz = clazz;
			this.key = KEY_PREFIX + key;
		}
		
		public T load(Context context) {
			
			return load(context, null);
		}
		
		public T load(Context context, T defaultValue) {
			
			if(clazz == null) {
				
				Log.e(TAG, "Called load with member [clazz] null");
				return null;
			}
			
			if(key == null) {
				
				Log.e(TAG, "Called load with member [key] null");
				return null;
			}
			
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			
			if(!prefs.contains(this.key))
				return defaultValue;
				
			if(clazz.getName().equals(Boolean.class.getName()))
				return clazz.cast(prefs.getBoolean(this.key, (Boolean)defaultValue));
			else if(clazz.getName().equals(Float.class.getName()))
				return clazz.cast(prefs.getFloat(this.key, (Float)defaultValue));
			else if(clazz.getName().equals(Integer.class.getName()))
				return clazz.cast(prefs.getInt(this.key, (Integer)defaultValue));
			else if(clazz.getName().equals(Long.class.getName()))
				return clazz.cast(prefs.getLong(this.key, (Long)defaultValue));
			else if(clazz.getName().equals(String.class.getName()))
				return clazz.cast(prefs.getString(this.key, (String)defaultValue));
			return null;
		}
		
		@SuppressWarnings("ConstantConditions")
		public void save(Context context, T value) {
			
			if(clazz == null) {
				
				Log.e(TAG, "Called load with member [clazz] null");
				return;
			}
			
			if(key == null) {
				
				Log.e(TAG, "Called load with member [key] null");
				return;
			}
			
			Editor editor = PreferenceManager.getDefaultSharedPreferences(context)
                    .edit();
			if(value == null) {
				
				editor = editor.remove(this.key);
			} else {
				
				if(Boolean.class.getName().equals(value.getClass().getName()))
					editor = editor.putBoolean(this.key, (Boolean)value);
				if(Float.class.getName().equals(value.getClass().getName()))
					editor = editor.putFloat(this.key, (Float)value);
				if(Integer.class.getName().equals(value.getClass().getName()))
					editor = editor.putInt(this.key, (Integer)value);
				if(Long.class.getName().equals(value.getClass().getName()))
					editor = editor.putLong(this.key, (Long)value);
				if(String.class.getName().equals(value.getClass().getName()))
					editor = editor.putString(this.key, (String)value);
			}
			editor.apply();
		}
	}
}
