package com.melos.Melos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView SongName;
    TextView Artist;
    TextView Album;
    TextView SongID;
    Button FindSong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SongName = (TextView) findViewById(R.id.songname);
        Artist = (TextView) findViewById(R.id.artistname);
        Album = (TextView) findViewById(R.id.albumname);
        SongID = (TextView) findViewById(R.id.TrackID);
//        FindSong = (Button) findViewById(R.id.FindSong);
        IntentFilter filter = new IntentFilter("com.spotify.music.metadatachanged");
        SpotiSong s = new SpotiSong();
        registerReceiver(s, filter);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void connected() {
        return;
    }


    public void updateSong(String song, String artist, String album, String songid){
        SongName.setText(song);
        Artist.setText(artist);
        Album.setText(album);
        SongID.setText(songid);
    }



    public class SpotiSong extends BroadcastReceiver {

        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";


        @Override
        public void onReceive(Context context, Intent intent) {
            // This is sent with all broadcasts, regardless of type. The value is taken from
            // System.currentTimeMillis(), which you can compare to in order to determine how
            // old the event is.
            long timeSentInMs = intent.getLongExtra("timeSent", 0L);

            String action = intent.getAction();

            if (action.equals(METADATA_CHANGED)) {
                Log.i("recieverTag", "Song changed");
                String trackId = intent.getStringExtra("id");
                String artistName = intent.getStringExtra("artist");
                String albumName = intent.getStringExtra("album");
                String trackName = intent.getStringExtra("track");
                updateSong(trackName,artistName,albumName,trackId);
                // Do something with extracted information...
            } else if (action.equals(PLAYBACK_STATE_CHANGED)) {
                boolean playing = intent.getBooleanExtra("playing", false);
                int positionInMs = intent.getIntExtra("playbackPosition", 0);
                // Do something with extracted information
            }
        }
    }


}