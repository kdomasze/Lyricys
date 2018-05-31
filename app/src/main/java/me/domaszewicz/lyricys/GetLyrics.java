package me.domaszewicz.lyricys;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Async class to retrieve lyrics and update the MainActivity's display
 */
public class GetLyrics extends AsyncTask<String, Void, String> {
    private static final String TAG = "lyricys.GetLyrics";

    private final WeakReference<TextView> _resultView;
    private final WeakReference<TextView> _titleView;

    private String _artist;
    private String _song;

    GetLyrics(TextView resultView, TextView titleView) {
        _resultView = new WeakReference(resultView);
        _titleView = new WeakReference(titleView);
    }

    @Override
    protected String doInBackground(String... params) {
        _artist = params[0];
        _song = params[1];

        String url = "http://lyrics.wikia.com/wiki/" + _artist + ":" + _song;
        String output;

        try {
            output = ParseLyrics(url);
        } catch (IOException e) {
            output = "No lyrics found.";
            Log.e(TAG, e.getMessage());
        }

        return output;
    }

    /**
     * Downloads and parses the html for the lyrics
     *
     * @param url url for page with lyrics
     * @return a string of lyrics
     * @throws IOException if method cannot retrieve html for lyrics
     */
    private String ParseLyrics(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Element lyrics = doc.selectFirst("div.lyricbox");

        String output = lyrics.html().replaceAll("\\\\n", "\n");
        output = output.replace("<i>", "");
        output = output.replace("</i>", "");
        output = output.replace("<br>", "");
        output = output.replace("</br>", "");
        output = output.replace("<div class=\"lyricsbreak\"></div>", "");

        return output;
    }

    @Override
    protected void onPostExecute(String lyrics) {
        _titleView.get().setText(_artist + ": " + _song);
        _resultView.get().setText(lyrics);
    }
}