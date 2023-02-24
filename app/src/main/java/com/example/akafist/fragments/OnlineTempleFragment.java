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

import com.example.akafist.MainActivity;
import com.example.akafist.databinding.FragmentOnlineTempleBinding;
import com.example.akafist.viewmodel.OnlineTempleViewModel;

/**
 * Класс фрагмента с прямым эфиром
 * @author Nastya Izotina
 * @version 1.0.0
 */
public class OnlineTempleFragment extends Fragment {

    public FragmentOnlineTempleBinding onlineTempleBinding;
    private String urlSound;
    private MediaPlayer mediaPlayer;
    private OnlineTempleViewModel onlineTempleViewModel;

    /**
     * Обязательный конструктор класса
     */
    public OnlineTempleFragment() { }

    /**
     * Этот метод отвечает за создание класса фрагмента с прямым эфиром
     * @return OnlineTempleFragment
     */
    public static OnlineTempleFragment newInstance() {
        return new OnlineTempleFragment();
    }

    /**
     * Этот метод подготавливает активность к работе фрагмента
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(this);
        onlineTempleViewModel = provider.get(OnlineTempleViewModel.class);
        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Трансляция общины");
    }

    /**
     * Этот метод создаёт фрагмент
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onlineTempleBinding = FragmentOnlineTempleBinding.inflate(getLayoutInflater());
        return onlineTempleBinding.getRoot();
    }

    /**
     * Этот метод определяет основные переменные после создания фрагмента
     * @param view View
     * @param savedInstanceState Bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            if (getActivity().getApplicationContext() != null) {
                MainActivity.networkConnection.observe(getViewLifecycleOwner(), isChecked->{
                    if (isChecked){
                        onlineTempleBinding.noInternet2.setVisibility(View.INVISIBLE);
                        urlSound = getArguments().getString("urlToSound");
                        Log.d("ONLINE_TEMPLE_ERROR", urlSound);
                        String soundTitle = getArguments().getString("soundTitle");
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(soundTitle);
                        onlineTempleBinding.stopPlayButton.setOnClickListener(view1 -> onlineTempleViewModel.play(getLayoutInflater(), getView(), urlSound));
                    }else {
                        onlineTempleBinding.noInternet2.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

    /**
     * Этот метод уничтожает фрагмент
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        onlineTempleViewModel.checkPlaying();
    }

    /**
     * Этот метод обрабатывает фоновую активность фрагмента
     */
    @Override
    public void onPause() {
        super.onPause();
        onlineTempleViewModel.checkPlaying();
    }
}