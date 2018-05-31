package me.domaszewicz.lyricys.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Helper class for handling updating and retrieving preferences
 */
public class PreferenceHelper {
    private static SharedPreferences _sharedPreferences;

    /**
     * Initializes the PreferenceHelper
     *
     * @param context application context
     */
    public PreferenceHelper(Context context) {
        _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Retrieves a boolean value for the given key, or returns the default value
     *
     * @param key          name of desired preference
     * @param defaultValue value to return if key has no stored value
     * @return the value stored in key, or the defaultValue
     */
    public static boolean GetBoolValue(String key, boolean defaultValue) {
        return _sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * Retrieves an integer value for the given key, or returns the default value
     *
     * @param key          name of desired preference
     * @param defaultValue value to return if key has no stored value
     * @return the value stored in key, or the defaultValue
     */
    public static int GetIntValue(String key, int defaultValue) {
        return _sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Stores an integer value for the given key
     *
     * @param key   name of desired preference to store value in
     * @param value value to store
     */
    public static void SetIntValue(String key, int value) {
        _sharedPreferences.edit().putInt(key, value).apply();
    }
}
