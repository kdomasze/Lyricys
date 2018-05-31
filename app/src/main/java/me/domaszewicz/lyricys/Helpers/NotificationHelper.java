package me.domaszewicz.lyricys.Helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.atomic.AtomicInteger;

import me.domaszewicz.lyricys.MainActivity;
import me.domaszewicz.lyricys.R;

/**
 * Helper class for handling notifications throughout the application
 */
public class NotificationHelper {
    private static final int NOTIFICATION_ID = NotificationID.getID();

    private static NotificationManager _notificationManager;

    private static String _artist;
    private static String _track;

    /**
     * Initializes the NotificationHelper
     *
     * @param notificationManager instance of the notificationManager
     */
    public NotificationHelper(NotificationManager notificationManager, Context context) {
        _notificationManager = notificationManager;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(String.valueOf(NOTIFICATION_ID), "Lyricys", importance);
            _notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * Updates the artist and track info stored in the helper for the notification
     *
     * @param artist artist's name
     * @param track  track's name
     */
    public static void updateNotificationText(String artist, String track) {
        _artist = artist;
        _track = track;
    }

    /**
     * Initializes and displays a persistent notification. If the notification is already active,
     * calling this function again will update the notification text
     */
    public static void DisplayNotification(Context context) {

        // kill the notification if notifications are disabled
        if (!PreferenceHelper.GetBoolValue("notification_switch", true)) {
            KillNotification();
            return;
        }

        // setup intent to open MainActivity if notification is tapped
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification n;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // setup notification
            n = new Notification.Builder(context, String.valueOf(NOTIFICATION_ID))
                    .setContentTitle(_track)
                    .setContentText(_artist)
                    .setSmallIcon(R.drawable.lyricys_notification_icon)
                    .setOngoing(true)
                    .setContentIntent(pendingIntent)
                    .build();
            _notificationManager.notify(NOTIFICATION_ID, n);
        } else {
            // setup notification
            n = new Notification.Builder(context)
                    .setContentTitle(_track)
                    .setContentText(_artist)
                    .setSmallIcon(R.drawable.lyricys_notification_icon)
                    .setOngoing(true)
                    .setPriority(Notification.PRIORITY_MIN)
                    .setContentIntent(pendingIntent)
                    .build();

            // shows notification
            _notificationManager.notify(NOTIFICATION_ID, n);
        }
    }

    /**
     * Removes a displayed notification
     */
    public static void KillNotification() {
        _notificationManager.cancel(NOTIFICATION_ID);
    }

    /**
     * Helper class to generate a notificationID
     */
    private static class NotificationID {
        private final static AtomicInteger c = new AtomicInteger(PreferenceHelper.GetIntValue("Notification_ID", 890123));

        static int getID() {
            int i = c.incrementAndGet();
            PreferenceHelper.SetIntValue("Notification_ID", i);
            return i;
        }
    }
}
