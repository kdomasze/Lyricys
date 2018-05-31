package me.domaszewicz.lyricys;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.domaszewicz.lyricys.DAL.ArtistInfo;
import me.domaszewicz.lyricys.Helpers.NotificationHelper;
import me.domaszewicz.lyricys.Helpers.PreferenceHelper;
import me.domaszewicz.lyricys.Helpers.ThemeHelper;
import me.domaszewicz.lyricys.Services.SongInfoBroadcastReceiver;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lyricys.MainActivity";

    private NotificationManager _notificationManager;

    private SongInfoBroadcastReceiver _broadcastReciever;

    public TextView resultTextView;
    private TextView titleTextView;

    private String _artist;
    private String _track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize helper classes
        _notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        new PreferenceHelper(this);
        new NotificationHelper(_notificationManager, this);
        ThemeHelper.CheckAndSetTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (_broadcastReciever == null) {

            _broadcastReciever = new SongInfoBroadcastReceiver();

            IntentFilter iF = new IntentFilter();
            iF.addAction("com.android.music.metachanged");
            iF.addAction("com.spotify.music.metadatachanged");
            iF.addAction("com.htc.music.metachanged");
            iF.addAction("fm.last.android.metachanged");
            iF.addAction("com.sec.android.app.music.metachanged");
            iF.addAction("com.nullsoft.winamp.metachanged");
            iF.addAction("com.amazon.mp3.metachanged");
            iF.addAction("com.miui.player.metachanged");
            iF.addAction("com.real.IMP.metachanged");
            iF.addAction("com.sonyericsson.music.metachanged");
            iF.addAction("com.rdio.android.metachanged");
            iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
            iF.addAction("com.andrew.apollo.metachanged");

            registerReceiver(_broadcastReciever, iF);
        }

        resultTextView = findViewById(R.id.result);
        titleTextView = findViewById(R.id.title);

        resultTextView.setText(R.string.take_few_moments);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        NotificationHelper.KillNotification();
    }

    @Override
    public void onDestroy() {
        if (_broadcastReciever != null) {
            unregisterReceiver(_broadcastReciever);
            _broadcastReciever = null;
        }

        super.onDestroy();
    }

    // Ensure the right menu is setup
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Start your settings activity when a menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.settings_action:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.about_action:
                BuildAboutPage();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Builds and displays the About Page for the application
     */
    private void BuildAboutPage() {
        LibsBuilder aboutLibs = new LibsBuilder();
        if (ThemeHelper.IsDarkTheme()) {
            aboutLibs.withActivityStyle(Libs.ActivityStyle.DARK);
        } else {
            aboutLibs.withActivityStyle(Libs.ActivityStyle.LIGHT);
        }

        aboutLibs.withAboutAppName("Lyricys");
        aboutLibs.withAboutDescription("Lyricys is a simple lyrics app that connects to your device\'s music player.<br />Created by <a href=\"https://github.com/kdomasze\">Kyle Domaszewicz.</a>");
        aboutLibs.withLicenseShown(true);
        aboutLibs.withLicenseDialog(true);
        aboutLibs.aboutAppSpecial1 = "Help";
        aboutLibs.aboutAppSpecial1Description = "If you are having trouble loading lyrics, try the following steps:<br /><br />" +
                "1. If using <b>Spotify</b>, ensure <b>Media Notifications</b> is enabled. You can check this by going to " +
                "<b>Spotify►Your Library►Cog Wheel </b><i>(upper right)</i><b>►Enable DeviceBroadcast Status</b>.<br /><br />" +
                "2. Due to the way Android works, the app can only pick up song information when the music player changes " +
                "tracks. Try <b>skipping</b> to the <b>next</b> or <b>previous</b> song with <b>Lyricys</b> and see if lyrics load.<br /><br />" +
                "3. Ensure you are <b>connected</b> to the internet as lyrics are pulled from the web.<br /><br />" +
                "If you are still having issues, contact me by <a href=\"mailto:kyle.domaszewicz@gmail.com\">tapping here</a>.";

        aboutLibs.aboutAppSpecial2 = "Additional Credits";
        aboutLibs.aboutAppSpecial2Description = "Lyrics supplied by <a href=\"http://lyrics.wikia.com/wiki/LyricWiki\">LyricWiki</a>.";


        aboutLibs.start(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMusicInfoReceived(ArtistInfo event){
        _artist = event.getArtist();
        _track = event.getTrack();

        titleTextView.setText(R.string.looking_for_lyrics);

        // starts a new thread to download song lyrics
        new GetLyrics(resultTextView, titleTextView).execute(_artist, _track);
    }
}
