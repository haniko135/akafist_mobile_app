package com.example.akafist.fragments;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentMolitvyOfflineBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MolitvyOfflineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MolitvyOfflineFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private ImageButton playStopButton;
    FragmentMolitvyOfflineBinding molitvyOfflineBinding;

    public MolitvyOfflineFragment() {
        // Required empty public constructor
    }

    public static MolitvyOfflineFragment newInstance() {
        MolitvyOfflineFragment fragment = new MolitvyOfflineFragment();
        return fragment;
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

    public void playMolitva(int num){
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
        playAndStop();
    }

    public void playAndStop(){
        if (!mediaPlayer.isPlaying()) {
            playStopButton = getView().findViewById(R.id.imageButtonPlay);
            playStopButton.setImageResource(android.R.drawable.ic_media_pause);
            mediaPlayer.start();
        }else {
            playStopButton = getView().findViewById(R.id.imageButtonPlay);
            playStopButton.setImageResource(android.R.drawable.ic_media_play);
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}