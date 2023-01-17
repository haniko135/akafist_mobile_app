package com.example.akafist.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.akafist.R;

import java.util.Objects;

public class PlayAudios {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private ImageButton playStopButton;
    private TextView checking, seekBarHint;
    private Runnable runnable;
    private Handler handler = new Handler();
    private View view;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @SuppressLint("ClickableViewAccessibility")
    public PlayAudios(String name, Context context, View view){
        this.view = view;

        this.mediaPlayer = MediaPlayer.create(context, Uri.parse(name));
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.setLooping(false);

        seekBarHint = view.findViewById(R.id.seekBarHint);
        playStopButton = view.findViewById(R.id.imageButtonPlay);

        seekBar = view.findViewById(R.id.durationBarMolitvy);
        seekBar.setMax(mediaPlayer.getDuration()/1000);
        seekBar.setProgress(0);
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

        /*runnable = () -> {
            seekBar.setProgress(mediaPlayer.getCurrentPosition()/1000);
            handler.postDelayed(runnable, 1000);
        };
        handler.postDelayed(runnable, 1000);*/

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //seekBarHint.setVisibility(View.VISIBLE);

                runnable = () -> {
                    seekBar.setProgress(i/1000);
                    handler.postDelayed(runnable, 1000);
                };
                //handler.postDelayed(runnable, 1000);

                int x = (int) Math.ceil(i / 1000f);

                if (x < 10)
                    seekBarHint.setText("0:0" + x);
                else if(x > 10 && x < 60)
                    seekBarHint.setText("0:" + x);
                else {
                    int min = x / 60, sec = x % 60;
                    if (min < 10)
                        if(sec < 10)
                            seekBarHint.setText("0"+ min + ":0" + sec);
                        else
                            seekBarHint.setText("0"+ min + ":" + sec);
                    else
                    if(sec < 10)
                        seekBarHint.setText(min + ":0" + sec);
                    else
                        seekBarHint.setText(min + ":" + sec);

                }

                double percent = i / (double) seekBar.getMax();
                int offset = seekBar.getThumbOffset();
                int seekWidth = seekBar.getWidth();
                int val = (int) Math.round(percent * (seekWidth - 2 * offset));
                int labelWidth = seekBarHint.getWidth();
                seekBarHint.setX(offset + seekBar.getX() + val
                        - Math.round(percent * offset)
                        - Math.round(percent * labelWidth / 2));

                if(i > 0 && !mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarHint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarHint.setVisibility(View.INVISIBLE);
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

        seekBarHint = view.findViewById(R.id.seekBarHint);
        playStopButton = view.findViewById(R.id.imageButtonPlay);

        seekBar = view.findViewById(R.id.durationBarMolitvy);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(0);
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

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarHint.setVisibility(View.VISIBLE);
                int x = (int) Math.ceil(i / 1000f);

                if (x < 10)
                    seekBarHint.setText("0:0" + x);
                else if(x > 10 && x < 60)
                    seekBarHint.setText("0:" + x);
                else {
                    int min = x / 60, sec = x % 60;
                    if (min < 10)
                        if(sec < 10)
                            seekBarHint.setText("0"+ min + ":0" + sec);
                        else
                            seekBarHint.setText("0"+ min + ":" + sec);
                    else
                    if(sec < 10)
                        seekBarHint.setText(min + ":0" + sec);
                    else
                        seekBarHint.setText(min + ":" + sec);

                }

                double percent = i / (double) seekBar.getMax();
                int offset = seekBar.getThumbOffset();
                int seekWidth = seekBar.getWidth();
                int val = (int) Math.round(percent * (seekWidth - 2 * offset));
                int labelWidth = seekBarHint.getWidth();
                seekBarHint.setX(offset + seekBar.getX() + val
                        - Math.round(percent * offset)
                        - Math.round(percent * labelWidth / 2));

                if(i > 0 && !mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarHint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarHint.setVisibility(View.INVISIBLE);
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });

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

    public void destroyPlayAudios(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        seekBar = null;
    }


}
