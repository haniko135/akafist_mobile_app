package com.example.akafist.fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.FragmentKt;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akafist.R;
import com.example.akafist.databinding.ActivityMainBinding;
import com.example.akafist.databinding.FragmentOnlineTempleBinding;

import java.net.URI;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnlineTempleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlineTempleFragment extends Fragment {

    public FragmentOnlineTempleBinding onlineTempleBinding;
    public MediaPlayer mediaPlayer;
    public AudioManager audioManager;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Трансляция общины Арх. Михаила");

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
            String urlSound = getArguments().getString("urlToSound");
            Log.d("FUCKING_BULLSHIT", urlSound);
            onlineTempleBinding.urlToSounds.setText(urlSound);
            Uri uriSound = Uri.parse(urlSound);
            onlineTempleBinding.playButton.setOnClickListener(view12 -> play(view12));

            onlineTempleBinding.pauseButton.setOnClickListener(view1 -> pause(view1));

            mediaPlayer = MediaPlayer.create(getView().getContext(), uriSound);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlay();
                }
            });

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
            onlineTempleBinding.pauseButton.setEnabled(false);
        }
    }

    private void stopPlay(){
        mediaPlayer.stop();
        onlineTempleBinding.pauseButton.setEnabled(false);
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
            onlineTempleBinding.playButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void play(View view){
        mediaPlayer.start();
        onlineTempleBinding.playButton.setEnabled(false);
        onlineTempleBinding.pauseButton.setEnabled(true);
    }
    public void pause(View view){
        mediaPlayer.pause();
        onlineTempleBinding.playButton.setEnabled(true);
        onlineTempleBinding.pauseButton.setEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            stopPlay();
        }
    }
}