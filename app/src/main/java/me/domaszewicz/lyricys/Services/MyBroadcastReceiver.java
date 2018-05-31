package me.domaszewicz.lyricys.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import me.domaszewicz.lyricys.Helpers.NotificationHelper;
import me.domaszewicz.lyricys.DAL.ArtistInfo;

public class MyBroadcastReceiver extends BroadcastReceiver {

    /*static final class BroadcastTypes {
        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";
    }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        // This is sent with all broadcasts, regardless of type. The value is taken from
        // System.currentTimeMillis(), which you can compare to in order to determine how
        // old the event is.
        //long timeSentInMs = intent.getLongExtra("timeSent", 0L);

        //String action = intent.getAction();

        //if (action.equals(BroadcastTypes.METADATA_CHANGED)) {
            //String trackId = intent.getStringExtra("id");
        String artistName = intent.getStringExtra("artist");
            //String albumName = intent.getStringExtra("album");
        String trackName = intent.getStringExtra("track");
            //int trackLengthInSec = intent.getIntExtra("length", 0);
        EventBus.getDefault().postSticky(new ArtistInfo(artistName, trackName));

        NotificationHelper.updateNotificationText(artistName, trackName);
        NotificationHelper.DisplayNotification();

            // Do something with extracted information...
        /*} else if (action.equals(BroadcastTypes.PLAYBACK_STATE_CHANGED)) {
            boolean playing = intent.getBooleanExtra("playing", false);
            int positionInMs = intent.getIntExtra("playbackPosition", 0);
            // Do something with extracted information
        } else if (action.equals(BroadcastTypes.QUEUE_CHANGED)) {
            // Sent only as a notification, your app may want to respond accordingly.
        }*/
    }
}