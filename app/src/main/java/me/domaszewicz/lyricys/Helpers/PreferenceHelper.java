package me.domaszewicz.lyricys.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {
    private static SharedPreferences _sharedPreferences;

    private static Context _context;

    public PreferenceHelper(Context context) {
        _context = context;
        _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
    }

    public static boolean GetBoolValue(String key, boolean defaultValue)
    {
        return _sharedPreferences.getBoolean(key, defaultValue);
    }
}
