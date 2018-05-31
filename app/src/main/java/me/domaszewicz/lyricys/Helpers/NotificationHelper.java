package me.domaszewicz.lyricys.Helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import me.domaszewicz.lyricys.MainActivity;
import me.domaszewicz.lyricys.R;

public class NotificationHelper {
    private static final int NOTIFICATION_ID = 76532875;

    private static NotificationManager _notificationManager;
    private static Context _context;

    private static String _artist;
    private static String _track;

    public NotificationHelper(NotificationManager notificationManager, Context context) {
        _notificationManager = notificationManager;
        _context = context;
    }

    public static void updateNotificationText(String artist, String track) {
        _artist = artist;
        _track = track;
    }

    public static void DisplayNotification() {
        Intent intent = new Intent(_context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(_context, 0, intent, 0);

        Notification n  = new Notification.Builder(_context)
                .setContentTitle(_track)
                .setContentText(_artist)
                .setSmallIcon(R.drawable.lyricys_notification_icon)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_MIN)
                .setContentIntent(pendingIntent)
                .build();

        if(PreferenceHelper.GetBoolValue("notification_switch", true)) {
            _notificationManager.notify(NOTIFICATION_ID, n);
        } else {
            _notificationManager.cancel(NOTIFICATION_ID);
        }
    }

    public static void KillNotification() {
        _notificationManager.cancel(NOTIFICATION_ID);
    }
}
