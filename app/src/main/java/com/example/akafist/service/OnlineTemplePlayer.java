package com.example.akafist.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.akafist.viewmodel.OnlineTempleViewModel;

import java.io.IOException;

public class OnlineTemplePlayer extends Worker {

    public OnlineTemplePlayer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    protected void onPostExecute() {
        if (OnlineTempleViewModel.getProgressDialog().isShowing()){
            OnlineTempleViewModel.getProgressDialog().cancel();
        }
        OnlineTempleViewModel.getMediaPlayer().start();
        OnlineTempleViewModel.initStage = false;
    }

    @NonNull
    @Override
    public Result doWork() {
        ListenableWorker.Result result;
        try {
            OnlineTempleViewModel.getMediaPlayer().setDataSource(getInputData().getString("URL_SOUND"));
            OnlineTempleViewModel.getMediaPlayer().setOnCompletionListener(mediaPlayer -> {
                OnlineTempleViewModel.initStage = true;
                OnlineTempleViewModel.playPause = false;
                //playStopButton.setImageResource(android.R.drawable.ic_media_play);
                OnlineTempleViewModel.getMediaPlayer().stop();
                OnlineTempleViewModel.getMediaPlayer().reset();
            });
            OnlineTempleViewModel.getMediaPlayer().prepare();
            result = ListenableWorker.Result.success();
        } catch (IOException e) {
            Log.e("ONLINE_TEMPLE_ERROR",e.getLocalizedMessage());
            result = ListenableWorker.Result.failure();
        }
        onPostExecute();
        return result;
    }
}
