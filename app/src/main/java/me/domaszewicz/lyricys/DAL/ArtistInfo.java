package me.domaszewicz.lyricys.DAL;

/**
 * Java Object for passing artist info from SongInfoBroadcastReceiver to MainActivity
 */
public class ArtistInfo {
    private String _artist;
    private String _track;

    public ArtistInfo(String artist, String track) {
        this._artist = artist;
        this._track = track;
    }

    public String getArtist() {
        return _artist;
    }

    public String getTrack() {
        return _track;
    }
}
