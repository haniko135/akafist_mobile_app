package com.example.akafist.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import android.util.Log;
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
    private ProgressDialog progressDialog;
    private ImageButton playStopButton;
    private boolean playPause, initStage = true;
    private OneTimeWorkRequest workRequest;

    public OnlineTempleFragment() {
        // Required empty public constructor
    }

    public static OnlineTempleFragment newInstance() {
        return new OnlineTempleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Трансляция общины");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onlineTempleBinding = FragmentOnlineTempleBinding.inflate(getLayoutInflater());
        return onlineTempleBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            urlSound = getArguments().getString("urlToSound");
            Log.d("ONLINE_TEMPLE_ERROR",urlSound);
            String soundTitle = getArguments().getString("soundTitle");
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(soundTitle);
            mediaPlayer = new MediaPlayer();
            progressDialog = new ProgressDialog(getContext());
            onlineTempleBinding.stopPlayButton.setOnClickListener(view1 -> play());
        }
    }

    public void play(){
        audioManager = (AudioManager) requireActivity().getSystemService(getView().getContext().AUDIO_SERVICE);
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

        playStopButton = getView().findViewById(R.id.stopPlayButton);
        if(!playPause){
            playStopButton.setImageResource(android.R.drawable.ic_media_pause);
            if (initStage){
                progressDialog.setMessage("Загружается...");
                progressDialog.show();
                Data data = new Data.Builder().putString("URL_SOUND", urlSound).build();
                workRequest = new OneTimeWorkRequest.Builder(OnlineTemplePlayer.class)
                        .setInputData(data).build();
                WorkManager.getInstance(getContext()).enqueue(workRequest);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public class OnlineTemplePlayer extends Worker {

        public OnlineTemplePlayer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        protected void onPostExecute() {
            if (progressDialog.isShowing()){
                progressDialog.cancel();
            }
            mediaPlayer.start();
            initStage = false;
        }

        @NonNull
        @Override
        public Result doWork() {
            ListenableWorker.Result result;
            try {
                mediaPlayer.setDataSource(getInputData().getString("URL_SOUND"));
                mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                    initStage = true;
                    playPause = false;
                    playStopButton.setImageResource(android.R.drawable.ic_media_play);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                });
                mediaPlayer.prepare();
                result = ListenableWorker.Result.success();
            } catch (IOException e) {
                Log.e("ONLINE_TEMPLE_ERROR",e.getLocalizedMessage());
                result = ListenableWorker.Result.failure();
            }
            onPostExecute();
            return result;
        }
    }
}