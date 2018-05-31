package me.domaszewicz.lyricys.Helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;

import me.domaszewicz.lyricys.R;

public class ThemeHelper {
    private static Context _context;

    public ThemeHelper(Context context)
    {
        _context = context;
    }

    public static void CheckAndSetTheme(Context context)
    {
        if(CheckTheme()) {
            context.setTheme(R.style.AppTheme_Dark);
        }
    }

    public static boolean CheckTheme()
    {
        return PreferenceHelper.GetBoolValue("dark_theme_switch", false);
    }
}
