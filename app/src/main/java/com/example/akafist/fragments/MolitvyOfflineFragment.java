package com.example.akafist.fragments;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;;
import android.os.Bundle;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentMolitvyOfflineBinding;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MolitvyOfflineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MolitvyOfflineFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private ImageButton playStopButton;
    private TextView checking, seekBarHint;
    FragmentMolitvyOfflineBinding molitvyOfflineBinding;

    public MolitvyOfflineFragment() {
        // Required empty public constructor
    }

    public static MolitvyOfflineFragment newInstance() {
        return new MolitvyOfflineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_molitvy_offline, container, false);

        view.findViewById(R.id.molitva_po_soglasheniyu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null)
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
                playMolitva(1);
            }
        });
        view.findViewById(R.id.molitva_ytrenya_polynosh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null)
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
                playMolitva(2);
            }
        });
        view.findViewById(R.id.molitva_ytrenya_pomyannik).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null)
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
                playMolitva(3);
            }
        });
        view.findViewById(R.id.imageButtonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAndStop();
            }
        });
        return view;
    }

    public void playMolitva(int num) {
        switch (num){
            case 1:
                play(R.raw.molitva_po_soglasheniyu);
                break;
            case 2:
                play(R.raw.molitva_ytrenyaa_polunosh);
                break;
            case 3:
                play(R.raw.molitva_utrenyaa_pomynnik);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void play(int uriRaw){
        mediaPlayer = MediaPlayer.create(getContext(), uriRaw);
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.setLooping(false);

        seekBarHint = getView().findViewById(R.id.seekBarHint);
        playStopButton = getView().findViewById(R.id.imageButtonPlay);
        checking = getView().findViewById(R.id.checking);

        seekBar = getView().findViewById(R.id.durationBarMolitvy);
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

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                checking.setText(String.valueOf(seekBar.getProgress()));
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

                /*if (i > 0 && mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    clearMediaPlayer();
                    playStopButton.setImageResource(android.R.drawable.ic_media_play);
                    seekBar.setProgress(0);
                }*/
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
        playAndStop();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mediaPlayer != null) {
            clearMediaPlayer();
        }
    }

    private void clearMediaPlayer(){
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}