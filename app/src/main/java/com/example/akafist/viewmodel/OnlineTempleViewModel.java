package com.example.akafist.viewmodel;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.akafist.R;
import com.example.akafist.service.OnlineTemplePlayer;

public class OnlineTempleViewModel extends ViewModel {
    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private AudioManager audioManager;
    public static boolean playPause;
    public static boolean initStage = true;
    private static ProgressDialog progressDialog;
    private ImageButton playStopButton;
    private OneTimeWorkRequest workRequest;

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void play(LayoutInflater inflater, View view, String urlSound){
        progressDialog = new ProgressDialog(inflater.getContext());
        //audioManager = (AudioManager) requireActivity().getSystemService(getView().getContext().AUDIO_SERVICE);
        audioManager = (AudioManager) inflater.getContext().getSystemService(inflater.getContext().AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeBar = view.findViewById(R.id.volumeBar);
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

        playStopButton = view.findViewById(R.id.stopPlayButton);
        if(!playPause){
            playStopButton.setImageResource(android.R.drawable.ic_media_pause);
            if (initStage){
                progressDialog.setMessage("Загружается...");
                progressDialog.show();
                Data data = new Data.Builder().putString("URL_SOUND", urlSound).build();
                workRequest = new OneTimeWorkRequest.Builder(OnlineTemplePlayer.class)
                        .setInputData(data).build();
                WorkManager.getInstance(inflater.getContext()).enqueue(workRequest);
            }else {
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
            }
            playPause = true;
        } else{
            playStopButton.setImageResource(android.R.drawable.ic_media_play);
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
            playPause = false;
        }
    }

    public void checkPlaying(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            //mediaPlayer.release();
            //mediaPlayer = null;
        }
    }
}
