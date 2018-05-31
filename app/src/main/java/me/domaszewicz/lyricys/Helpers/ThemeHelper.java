package me.domaszewicz.lyricys.Helpers;

import android.content.Context;

import me.domaszewicz.lyricys.R;

/**
 * Helper class for handling the state of the application theme
 */
public class ThemeHelper {
    /**
     * Checks whether the current theme is light or dark, and sets it appropriately
     *
     * @param context application context
     */
    public static void CheckAndSetTheme(Context context) {
        if (IsDarkTheme()) {
            context.setTheme(R.style.AppTheme_Dark);
        }
    }

    /**
     * Checks if the current theme is dark
     *
     * @return true if current theme setting is dark
     */
    public static boolean IsDarkTheme() {
        return PreferenceHelper.GetBoolValue("dark_theme_switch", false);
    }
}
