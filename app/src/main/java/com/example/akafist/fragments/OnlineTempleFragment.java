package com.example.akafist.fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentOnlineTempleBinding;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnlineTempleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlineTempleFragment extends Fragment {

    public FragmentOnlineTempleBinding onlineTempleBinding;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private String urlSound;
    private ImageButton playStopButton;

    public OnlineTempleFragment() {
        // Required empty public constructor
    }

    public static OnlineTempleFragment newInstance() {
        OnlineTempleFragment fragment = new OnlineTempleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Трансляция общины");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_onlineTempleFragment_to_home2);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onlineTempleBinding = FragmentOnlineTempleBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        return onlineTempleBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            urlSound = getArguments().getString("urlToSound");
            String soundTitle = getArguments().getString("soundTitle");
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(soundTitle);
            //onlineTempleBinding.urlToSounds.setText(urlSound);
            onlineTempleBinding.stopPlayButton.setOnClickListener(view1 -> play());
        }
    }

    public void play(){
        audioManager = (AudioManager) getActivity().getSystemService(getView().getContext().AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeBar = getView().findViewById(R.id.volumeBar);
        volumeBar.setMax(maxVolume);
        volumeBar.setProgress(curValue);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(urlSound);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    getContext();
                }
            });
            mediaPlayer.prepareAsync();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        playAndStop();
    }

    public void playAndStop(){
        if (!mediaPlayer.isPlaying()) {
            playStopButton = getView().findViewById(R.id.stopPlayButton);
            playStopButton.setImageResource(android.R.drawable.ic_media_pause);
            mediaPlayer.start();
        }else {
            playStopButton = getView().findViewById(R.id.stopPlayButton);
            playStopButton.setImageResource(android.R.drawable.ic_media_play);
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}