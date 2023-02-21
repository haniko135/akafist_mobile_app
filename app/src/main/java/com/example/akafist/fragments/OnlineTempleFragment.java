package com.example.akafist.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.databinding.FragmentOnlineTempleBinding;
import com.example.akafist.viewmodel.OnlineTempleViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnlineTempleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlineTempleFragment extends Fragment {

    public FragmentOnlineTempleBinding onlineTempleBinding;
    private String urlSound;
    private MediaPlayer mediaPlayer;
    private OnlineTempleViewModel onlineTempleViewModel;


    public OnlineTempleFragment() {
        // Required empty public constructor
    }

    public static OnlineTempleFragment newInstance() {
        return new OnlineTempleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(this);
        onlineTempleViewModel = provider.get(OnlineTempleViewModel.class);
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
            onlineTempleBinding.stopPlayButton.setOnClickListener(view1 -> onlineTempleViewModel.play(getLayoutInflater(), getView(), urlSound));
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        onlineTempleViewModel.checkPlaying();
    }

    @Override
    public void onPause() {
        super.onPause();
        onlineTempleViewModel.checkPlaying();
    }
}