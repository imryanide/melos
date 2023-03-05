package com.melos.Melos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView SongName;
    TextView Artist;
    TextView Album;
    TextView SongID;
    ImageView AlbumArt;

    TextView Emotion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if action bar is hidden, if it is not, hide it.
//        if(getSupportActionBar()!=null){
//            this.getSupportActionBar().hide();
//        }

        SongName = (TextView) findViewById(R.id.songname);
        Artist = (TextView) findViewById(R.id.artistname);
        Album = (TextView) findViewById(R.id.albumname);
        SongID = (TextView) findViewById(R.id.TrackID);
        AlbumArt = (ImageView) findViewById(R.id.albumArt);
        Emotion =(TextView) findViewById(R.id.emotion);


        ConstraintLayout cl = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) cl.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();

        IntentFilter filter = new IntentFilter("com.spotify.music.metadatachanged");
        SpotiSong s = new SpotiSong();
        registerReceiver(s, filter);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void connected() {
    }



    public void updateSong(String song, String artist, String album, String songid){
        SongName.setText(song);
        Artist.setText(artist);
        Album.setText(album);
        SongID.setText(songid);
        Random rand = new Random();
        int rndInt = rand.nextInt(5) + 1; // n = the number of images, that start at idx 1
        String imgName = "sampleart" + rndInt;
        int id = getResources().getIdentifier(imgName, "drawable", getPackageName());
        AlbumArt.setImageResource(id);
        String[] emotions = {"Happy", "Melancholic", "Euphoric", "Groovy"};
        int r = rand.nextInt(emotions.length);
        Emotion.setText(emotions[r]);

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