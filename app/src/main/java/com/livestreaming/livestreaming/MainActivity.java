package com.livestreaming.livestreaming;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView videoViewOne;
    private VideoView videoView;
    private ImageView imageViewPlay;
    private ProgressBar progressBar;
    private ProgressBar pb_timer;
    private TextView timerPlayed;
    private TextView timerDuration;

    private Uri uriVideoOne;
    private Uri uriVideo;
    boolean isPlaying;

    private int current = 0;
    private int duration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isPlaying = false;
        videoView = findViewById(R.id.vv_play);
        videoViewOne = findViewById(R.id.vv_play_one);
        imageViewPlay = findViewById(R.id.iv_play);
        progressBar = findViewById(R.id.progressBarMain);
        timerPlayed = findViewById(R.id.tv_played);
        timerDuration = findViewById(R.id.tv_duration);
        pb_timer = findViewById(R.id.pb_timer);
        pb_timer.setMax(100);


        uriVideoOne = Uri.parse("https://firebasestorage.googleapis.com/v0/b/appslelotsf.appspot.com/o/Bohut%20Zabardast%20Kalaam%20Shan%20e%20Hazrat%20Umer%20Farooq%20RA.mp4?alt=media&token=7d017d77-bc6f-4683-904b-a49d7326ea11");
        videoViewOne.setVideoURI(uriVideoOne);
        videoViewOne.requestFocus();
        imageViewPlay.setImageResource(R.drawable.ic_pause);
        videoViewOne.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {

                if (i == mediaPlayer.MEDIA_INFO_BUFFERING_START){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else if (i == mediaPlayer.MEDIA_INFO_BUFFERING_END){
                    progressBar.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });

        videoViewOne.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                duration = mediaPlayer.getDuration()/1000;
                String durationTimer = String.format("%02d:%02d", duration/60, duration%60);
                timerDuration.setText(durationTimer);
            }
        });



        videoViewOne.start();
        isPlaying = true;
        new videoProgress().execute();




        videoView.setVisibility(View.GONE);

//        uriVideo = Uri.parse("https://firebasestorage.googleapis.com/v0/b/appslelotsf.appspot.com/o/Maula%20Ye%20Kia%20Kar%20Betha%20Hun%2C%20Lyrical%20Video%2C%20Hafiz%20Fahad%20Shah%20New%20Kalaam%20Released%2C%20Islamic%20Releases.mp4?alt=media&token=d90ecc68-8c3c-405e-899c-879a2fae824c");
//        videoView.setVideoURI(uriVideo);
//        videoView.requestFocus();
//        videoView.start();

        imageViewPlay.setOnClickListener(this);

        }

    @Override
    public void onClick(View view) {

       switch (view.getId()){

           case R.id.iv_play:
               if (isPlaying) {
                   videoViewOne.pause();
                   imageViewPlay.setImageResource(R.drawable.ic_play);
                   isPlaying = false;
               }
               else {

                   videoViewOne.start();
                   imageViewPlay.setImageResource(R.drawable.ic_pause);
                   isPlaying = true;


               }

               break;

        }
    }

    public class videoProgress extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            do {

                current = videoViewOne.getCurrentPosition()/1000;

                    publishProgress(current);




            }while (pb_timer.getProgress() <=100);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            try
            {
                int currentPercent = values[0] * 100/duration;
                pb_timer.setProgress(currentPercent);
                String playedTimer = String.format("%02d:%02d", values[0] / 60, values[0] % 60);
                timerPlayed.setText(playedTimer);

            }catch (Exception e){

            }


        }
    }
}