package com.example.akafist.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akafist.MainActivity;
import com.example.akafist.R;
import com.example.akafist.databinding.FragmentLinksBinding;
import com.example.akafist.models.AudioModel;
import com.example.akafist.models.LinksModel;
import com.example.akafist.recyclers.AudioRecyclerAdapter;
import com.example.akafist.service.DownloadFromYandexTask;
import com.example.akafist.service.PlayAudios;
import com.example.akafist.viewmodel.LinksViewModel;

import org.json.JSONException;

import java.io.File;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinksFragment extends Fragment {

    //public static final String secToken = "y0_AgAAAABUVpeiAADLWwAAAADXqEoa0KX1_myOSvS6tU-k0yc2A_S4C7o";
    private String audioFilesDir;
    private LinksViewModel linksViewModel;
    private AudioRecyclerAdapter recyclerAdapter;
    public String urlForLink;
    private boolean isChecked; //для пользовательского соглашения
    public FragmentLinksBinding binding;

    public LinksFragment() {
        // Required empty public constructor
    }

    public static LinksFragment newInstance() {
        return new LinksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(this);
        linksViewModel = provider.get(LinksViewModel.class);
        if((AppCompatActivity)getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Записи бесед");
            linksViewModel.getJson();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLinksBinding.inflate(inflater,container,false);

        audioFilesDir = getContext().getFilesDir().getPath();

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);


        //пользовательское соглашение
        /*binding.warningToUser.setVisibility(View.VISIBLE);
        binding.molitvyPlayer.setVisibility(View.INVISIBLE);
        binding.linksRv.setVisibility(View.INVISIBLE);
        binding.linksRoot.setBackgroundColor(getResources().getColor(R.color.greyGrad));

        binding.warningToUserYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.molitvyPlayer.setVisibility(View.VISIBLE);
                binding.linksRv.setVisibility(View.VISIBLE);
                binding.warningToUser.setVisibility(View.INVISIBLE);
                binding.linksRoot.setBackgroundColor(getResources().getColor(R.color.white));
                isChecked = true;
                MainActivity.isChecked = isChecked;
            }
        });
        binding.warningToUserNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChecked = false;
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_linksFragment_to_home2);
            }
        });*/
        //linksViewModel.getMutableLinksDate().observe(getViewLifecycleOwner(), linksModels -> recyclerAdapter = new AudioRecyclerAdapter(linksModels, this));

        binding.downloadLinkButton.setOnClickListener(view -> {
            linksViewModel.getLinkDownload(urlForLink, inflater, container, audioFilesDir);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        binding.linksRv.setLayoutManager(linearLayoutManager);
        linksViewModel.getMutableLinksDate().observe(getViewLifecycleOwner(), linksModels -> {
            recyclerAdapter = new AudioRecyclerAdapter(linksModels, this);
            binding.linksRv.setAdapter(recyclerAdapter);
        });

        binding.imageButtonPlay.setOnClickListener(view -> {
            if (recyclerAdapter.playAudios != null){
                recyclerAdapter.playAudios.playAndStop();
            }
        });

        return binding.getRoot();
    }

    public List<AudioModel> getAudios(){
        List<String> audioName = Arrays.asList(getResources().getStringArray(R.array.links_audio_name));
        List<String> audioLinks = Arrays.asList(getResources().getStringArray(R.array.links_audio_link));
        List<AudioModel> audios = new ArrayList<>();
        for(int i = 0; i< audioName.size(); i++){
            audios.add(new AudioModel(audioName.get(i), audioLinks.get(i)));
        }
        return audios;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(recyclerAdapter.playAudios != null) {
            recyclerAdapter.playAudios.destroyPlayAudios();
        }
    }


}