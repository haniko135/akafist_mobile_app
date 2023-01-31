package com.example.akafist.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentMolitvyOfflineBinding;
import com.example.akafist.service.PlayAudios;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MolitvyOfflineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MolitvyOfflineFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    PlayAudios plAu;
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Загруженные молитвы");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        molitvyOfflineBinding = FragmentMolitvyOfflineBinding.inflate(getLayoutInflater());

        molitvyOfflineBinding.molitvaPoSoglasheniyu.setOnClickListener(view -> {
            checkPlaying();
            playMolitva(1);
        });
        molitvyOfflineBinding.molitvaYtrenyaPolynosh.setOnClickListener(view -> {
            checkPlaying();
            playMolitva(2);
        });
        molitvyOfflineBinding.molitvaYtrenyaPomyannik.setOnClickListener(view -> {
            checkPlaying();
            playMolitva(3);
        });
        molitvyOfflineBinding.imageButtonPlay.setOnClickListener(view -> {
            if (plAu != null){
                plAu.playAndStop();
            }
        });

        return molitvyOfflineBinding.getRoot();
    }

    public void checkPlaying(){
        if (mediaPlayer != null)
            if(mediaPlayer.isPlaying()) {
                PlayAudios.runnable = null;
                mediaPlayer.stop();
                mediaPlayer = null;
            }
    }

    public void playMolitva(int num) {
        switch (num){
            case 1:
                plAu = new PlayAudios(R.raw.molitva_po_soglasheniyu, getContext(), getView(), molitvyOfflineBinding.molitvaPoSoglasheniyuTitle.getText());
                mediaPlayer = plAu.getMediaPlayer();
                plAu.playAndStop();
                break;
            case 2:
                plAu = new PlayAudios(R.raw.molitva_ytrenyaa_polunosh, getContext(), getView(), molitvyOfflineBinding.molitvaYtrenyaPolynoshTitle.getText());
                mediaPlayer = plAu.getMediaPlayer();
                plAu.playAndStop();
                break;
            case 3:
                plAu = new PlayAudios(R.raw.molitva_utrenyaa_pomynnik, getContext(), getView(), molitvyOfflineBinding.molitvaYtrenyaPomyannikTitle.getText());
                mediaPlayer = plAu.getMediaPlayer();
                plAu.playAndStop();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            plAu.destroyPlayAudios();
        }
    }
}