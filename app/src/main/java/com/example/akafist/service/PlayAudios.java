package com.example.akafist.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.akafist.R;

public class PlayAudios {

    private MediaPlayer mediaPlayer;
    private final SeekBar seekBar;
    private final ImageButton playStopButton;
    private final TextView seekBarHint;
    private final TextView seekBarMax;
    private final Handler handler = new Handler();
    private final View view;
    public static Runnable runnable;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @SuppressLint("ClickableViewAccessibility")
    public PlayAudios(String name, Context context, View view){
        this.view = view;

        this.mediaPlayer = MediaPlayer.create(context, Uri.parse(name));
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.setLooping(false);

        seekBarHint = view.findViewById(R.id.seekBarDurTime);
        seekBarMax = view.findViewById(R.id.seekBarMaxTime);
        seekBarMax.setVisibility(View.VISIBLE);
        seekBarHint.setVisibility(View.VISIBLE);
        playStopButton = view.findViewById(R.id.imageButtonPlay);

        seekBar = view.findViewById(R.id.durationBarMolitvy);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBarMax.setText(formatDur(seekBar.getMax()));
        seekBar.setProgress(0);
        seekBarHint.setText(formatDur(mediaPlayer.getCurrentPosition()));

        if (mediaPlayer != null) {
            runnable = () -> {
                if(!Thread.currentThread().isInterrupted()) {
                    seekBarHint.setText(formatDur(mediaPlayer.getCurrentPosition()));
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(runnable, 1000);
                }else {
                    runnable = null;
                }
            };
            handler.postDelayed(runnable, 0);
        }

        seekBar.setOnTouchListener((v, event) -> {
            if(mediaPlayer.isPlaying()){
                SeekBar sb = (SeekBar)v;
                mediaPlayer.seekTo(sb.getProgress());
            }
            return false;
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i > 0 && !mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public PlayAudios(int resId, Context context, View view){
        this.view = view;

        this.mediaPlayer = MediaPlayer.create(context, resId);

        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.setLooping(false);

        seekBarHint = view.findViewById(R.id.seekBarDurTime);
        seekBarMax = view.findViewById(R.id.seekBarMaxTime);
        seekBarMax.setVisibility(View.VISIBLE);
        seekBarHint.setVisibility(View.VISIBLE);
        playStopButton = view.findViewById(R.id.imageButtonPlay);

        seekBar = view.findViewById(R.id.durationBarMolitvy);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBarMax.setText(formatDur(seekBar.getMax()));
        seekBar.setProgress(0);
        seekBarHint.setText(formatDur(mediaPlayer.getCurrentPosition()));


        if (mediaPlayer != null) {
            runnable = () -> {
                if(!Thread.currentThread().isInterrupted()){
                    seekBarHint.setText(formatDur(mediaPlayer.getCurrentPosition()));
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(runnable, 1000);
                }else {
                    runnable = null;
                }
            };
            handler.postDelayed(runnable, 0);
        }

        seekBar.setOnTouchListener((v, event) -> {
            if(mediaPlayer.isPlaying()){
                SeekBar sb = (SeekBar)v;
                mediaPlayer.seekTo(sb.getProgress());
            }
            return false;
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i > 0 && !mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });

    }

    public String formatDur(int i){
        int x = (int) Math.ceil(i / 1000f);
        String fin;

        if (x < 10)
            fin = "0:0" + x;
        else if(x > 10 && x < 60)
            fin = "0:" + x;
        else {
            int min = x / 60, sec = x % 60;
            if (min < 10)
                if(sec < 10)
                    fin = "0"+ min + ":0" + sec;
                else
                    fin = "0"+ min + ":" + sec;
            else
                if(sec < 10)
                    fin = min + ":0" + sec;
                else
                    fin = min + ":" + sec;

        }
        return fin;
    }

    public void playAndStop(){
        if (!mediaPlayer.isPlaying()) {
            playStopButton.setImageResource(android.R.drawable.ic_media_pause);
            mediaPlayer.start();
        }else {
            playStopButton.setImageResource(android.R.drawable.ic_media_play);
            mediaPlayer.pause();
        }
    }

    public void destroyPlayAudios() {
        if(mediaPlayer != null) {
            Thread.currentThread().interrupt();
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}
