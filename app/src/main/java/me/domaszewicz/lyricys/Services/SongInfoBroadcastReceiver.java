package me.domaszewicz.lyricys.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import me.domaszewicz.lyricys.DAL.ArtistInfo;
import me.domaszewicz.lyricys.Helpers.NotificationHelper;

/**
 * Service for listening for song information broadcasted by other music players
 */
public class SongInfoBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String artistName = intent.getStringExtra("artist");
        String trackName = intent.getStringExtra("track");
        // sends artist info to MainActivity
        EventBus.getDefault().postSticky(new ArtistInfo(artistName, trackName));

        // updates notification and displays it
        NotificationHelper.updateNotificationText(artistName, trackName);
        NotificationHelper.DisplayNotification(context);
    }
}