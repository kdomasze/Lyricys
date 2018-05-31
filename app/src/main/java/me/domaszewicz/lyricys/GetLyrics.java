package me.domaszewicz.lyricys;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.Text;

import java.io.IOException;

public class GetLyrics extends AsyncTask<String, Void, String> {
    private static final String TAG = "lyricys.GetLyrics";

    private final TextView _resultView;
    private final TextView _titleView;

    private String _artist;
    private String _song;

    public GetLyrics(TextView resultView, TextView titleView) {
        _resultView = resultView;
        _titleView = titleView;
    }

    @Override
    protected String doInBackground(String... params) {
        _artist = params[0];
        _song = params[1];

        String url = "http://lyrics.wikia.com/wiki/" + _artist + ":" + _song;
        String output = "";

        try {
            output = ParseLyrics(url);
        } catch (IOException e) {
            output = "No lyrics found.";
            Log.e(TAG, e.getMessage());
        }

        return output;
    }

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
        _titleView.setText(_artist + ": " + _song);
        _resultView.setText(lyrics);
    }
}