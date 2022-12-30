package com.example.akafist.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.example.akafist.R;

public class PlayAudios {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private ImageButton playStopButton;
    private View view;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @SuppressLint("ClickableViewAccessibility")
    public PlayAudios(String name, Context context, View view){
        this.view = view;

        mediaPlayer = MediaPlayer.create(context, Uri.parse(name));

        seekBar = view.findViewById(R.id.durationBarMolitvy);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mediaPlayer.isPlaying()){
                    SeekBar sb = (SeekBar)v;
                    mediaPlayer.seekTo(sb.getProgress());
                }
                return false;
            }
        });
    }

    public void playAndStop(){
        if (!mediaPlayer.isPlaying()) {
            playStopButton = this.view.findViewById(R.id.imageButtonPlay);
            playStopButton.setImageResource(android.R.drawable.ic_media_pause);
            mediaPlayer.start();
        }else {
            playStopButton = this.view.findViewById(R.id.imageButtonPlay);
            playStopButton.setImageResource(android.R.drawable.ic_media_play);
            mediaPlayer.pause();
        }
    }

    public void destroyPlayAudios(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
