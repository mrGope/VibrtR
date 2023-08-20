package com.example.vibrtr;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer music[] = new MediaPlayer[4];
    Uri uri;
    VideoView videoview;
    int[] songs = { R.raw.udd_gaye, R.raw.sathi, R.raw.liggi,R.raw.weeknd};
    int [] videos={R.raw.topgun,R.raw.calm_down_vid,R.raw.iamgood,R.raw.weekndvid};
    int[] images = {R.drawable.song_image, R.drawable.sathi_image, R.drawable.liggi_image,R.drawable.weeknd};
    String[] song_names = {"I Ain't Worried", "Calm Down", "I'm Good","Sajna"};

    TextView txt;
    ImageView img;
    ImageButton imgBtn;
    Handler mSeekbarUpdateHandler;
    Runnable mUpdateSeekbar;
    Boolean playPause = false;
    Timer timer;
    SeekBar mSeekBar;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = 0;
        uri = Uri.parse("android.resource://"+getPackageName()+"/"+videos[i]);
        videoview = findViewById(R.id.backvid);
        videoview.setVideoURI(uri);
        videoview.start();
        for (int j = 0; j < songs.length; j++) {
            music[j] = MediaPlayer.create(this, songs[j]);
        }
        img = (ImageView) findViewById(R.id.imageView2);
        txt = (TextView) findViewById(R.id.textView2);
        imgBtn = (ImageButton) findViewById(R.id.imageButton4);
        mSeekBar=findViewById(R.id.playprogress);
        txt.setText(song_names[i]);
        mSeekBar.setMax(music[i].getDuration());
          mSeekbarUpdateHandler = new Handler();
        mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                mSeekBar.setProgress(music[i].getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 50);
            }
        };

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    music[i].seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void playSong(View view) {
        if(!playPause) {
            music[i].start();
            imgBtn.setImageResource(R.drawable.pausef);
            playPause = true;

            mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
        }
        else {
            music[i].pause();
            imgBtn.setImageResource(R.drawable.play);
            playPause = false;

            mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
        }
    }
    public void stopSong(View view) {
        music[i].stop();
        music[i] = MediaPlayer.create(this, songs[i]);
        imgBtn.setImageResource(R.drawable.play);
        playPause = false;

        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
    }
    public void previousSong(View view) {
        music[i].stop();
        music[i] = MediaPlayer.create(this, songs[i]);

        if(i==0)
            i = songs.length-1;
        else
            i -= 1;
        img.setImageResource(images[i]);
        txt.setText(song_names[i]);
        imgBtn.setImageResource(R.drawable.pausef);
        playPause = true;
        music[i].start();
        uri = Uri.parse("android.resource://"+getPackageName()+"/"+videos[i]);
        videoview.setVideoURI(uri);

        videoview.start();
    }
    public void nextSong(View view) {
        music[i].stop();
        music[i] = MediaPlayer.create(this, songs[i]);
        if(i == songs.length-1)
            i = 0;
        else
            i += 1;
        img.setImageResource(images[i]);
        txt.setText(song_names[i]);
        imgBtn.setImageResource(R.drawable.pausef);
        playPause = true;
        music[i].start();
        uri = Uri.parse("android.resource://"+getPackageName()+"/"+videos[i]);
        videoview.setVideoURI(uri);

        videoview.start();
    }
}