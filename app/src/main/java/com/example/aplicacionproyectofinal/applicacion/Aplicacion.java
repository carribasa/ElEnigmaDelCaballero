package com.example.aplicacionproyectofinal.applicacion;

import android.app.Application;
import android.media.MediaPlayer;

import com.example.aplicacionproyectofinal.R;

public class Aplicacion extends Application {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.beep);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
