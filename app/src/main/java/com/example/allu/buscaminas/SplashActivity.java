package com.example.allu.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Allu on 15/04/2015.
 */
public class SplashActivity extends Activity {

    private final int DURACION_SPLASH = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sound_explosion);
        mediaPlayer.start();

        new Handler().postDelayed(new Runnable(){
            public void run(){
                mediaPlayer.stop();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
    }
}