package me.domaszewicz.lyricys;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
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


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lyricys.MainActivity";

    private NotificationManager nMN;

    public TextView resultTextView;
    private TextView titleTextView;

    private String _artist;
    private String _track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        nMN = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        new NotificationHelper(nMN, this);
        new PreferenceHelper(this);

        ThemeHelper.CheckAndSetTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView) findViewById(R.id.result);
        titleTextView = (TextView) findViewById(R.id.title);

        resultTextView.setText("This might take a few moments.");
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

    private void BuildAboutPage()
    {
        LibsBuilder aboutLibs = new LibsBuilder();
        if(ThemeHelper.CheckTheme())
        {
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

        titleTextView.setText("Looking for lyrics...");

        new GetLyrics(resultTextView, titleTextView).execute(_artist, _track);
    }
}
