package com.melos.Melos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Modifier;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView SongName;
    ImageView AlbumArt;
    ConstraintLayout circleFrame;
    LinearLayoutCompat cl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar()!=null){
            this.getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);


        SongName = (TextView) findViewById(R.id.songname);
        AlbumArt = (ImageView) findViewById(R.id.albumart);
        circleFrame = (ConstraintLayout) findViewById(R.id.constraintcircle);
        circleFrame.setTag("circleframe");

        cl = findViewById(R.id.mainLayout);



        IntentFilter filter = new IntentFilter("com.spotify.music.metadatachanged");
        SpotiSong s = new SpotiSong();
        registerReceiver(s, filter);



        circleFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl.setBackgroundResource(R.drawable.gradientlist);
                AnimationDrawable animationDrawable = (AnimationDrawable) cl.getBackground();
                animationDrawable.setEnterFadeDuration(1000);
                animationDrawable.setExitFadeDuration(1000);
                animationDrawable.start();
                ObjectAnimator animator = ObjectAnimator.ofFloat(circleFrame, "translationY", 650f);
                animator.setDuration(2000);
                animator.start();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void connected() {
    }



    public void updateSong(String song, String artist, String album, String songid){
        SongName.setText(song);

//        Random rand = new Random();
//        int rndInt = rand.nextInt(5) + 1; // n = the number of images, that start at idx 1
//        String imgName = "sampleart" + rndInt;
//        int id = getResources().getIdentifier(imgName, "drawable", getPackageName());
//        AlbumArt.setImageResource(id);

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