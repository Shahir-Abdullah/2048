package cat.santi.ttfe.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Preferences {

    private static final String KEY_PREFIX = "cat.santi.ttfe.preferences";

    private Preferences() {
    }

    public static class Sounds extends AbstractPreferences<Boolean> {

        public Sounds() {
            super(Boolean.class, ".soundEnabled");
        }

        public static Sounds getInstance() {
            return new Sounds();
        }
    }

    private static abstract class AbstractPreferences<T> {

        private final Class<T> clazz;
        private final String key;

        public AbstractPreferences(Class<T> clazz, String key) {

            if (clazz == null)
                throw new IllegalArgumentException("clazz == null");
            if (key == null)
                throw new IllegalArgumentException("key == null");

            this.clazz = clazz;
            this.key = KEY_PREFIX + key;
        }

        public T load(Context context) {

            return load(context, null);
        }

        public T load(Context context, T defaultValue) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

            if (!prefs.contains(this.key))
                return defaultValue;

            if (clazz.getName().equals(Boolean.class.getName()))
                return clazz.cast(prefs.getBoolean(this.key, (Boolean) defaultValue));
            else if (clazz.getName().equals(Float.class.getName()))
                return clazz.cast(prefs.getFloat(this.key, (Float) defaultValue));
            else if (clazz.getName().equals(Integer.class.getName()))
                return clazz.cast(prefs.getInt(this.key, (Integer) defaultValue));
            else if (clazz.getName().equals(Long.class.getName()))
                return clazz.cast(prefs.getLong(this.key, (Long) defaultValue));
            else if (clazz.getName().equals(String.class.getName()))
                return clazz.cast(prefs.getString(this.key, (String) defaultValue));
            return null;
        }

        @SuppressWarnings("ConstantConditions")
        public void save(Context context, T value) {

            if (value == null) {

                remove(context);
                return;
            }

            Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            if (Boolean.class.getName().equals(value.getClass().getName()))
                editor = editor.putBoolean(this.key, (Boolean) value);
            if (Float.class.getName().equals(value.getClass().getName()))
                editor = editor.putFloat(this.key, (Float) value);
            if (Integer.class.getName().equals(value.getClass().getName()))
                editor = editor.putInt(this.key, (Integer) value);
            if (Long.class.getName().equals(value.getClass().getName()))
                editor = editor.putLong(this.key, (Long) value);
            if (String.class.getName().equals(value.getClass().getName()))
                editor = editor.putString(this.key, (String) value);
            editor.apply();
        }

        public void remove(Context context) {

            Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.remove(this.key);
            editor.apply();
        }
    }
}
