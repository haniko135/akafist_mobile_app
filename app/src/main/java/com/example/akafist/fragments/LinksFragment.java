package com.example.akafist.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.akafist.service.NetworkConnection;
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

    private String audioFilesDir;
    private LinksViewModel linksViewModel;
    private AudioRecyclerAdapter recyclerAdapter;
    public String urlForLink;
    private final int NOTIFICATION_ID = 101;
    private NetworkConnection networkConnection;
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
            if(((AppCompatActivity) getActivity()).getApplicationContext() != null){
                networkConnection = new NetworkConnection(((AppCompatActivity)getActivity()).getApplicationContext());
            }
            /*MainActivity.networkConnection.observe(getActivity(), isChecked1->{
                if(isChecked1)
                    linksViewModel.getJson();
            });*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLinksBinding.inflate(inflater, container, false);

        audioFilesDir = getContext().getFilesDir().getPath();

        /*if(((AppCompatActivity) getActivity()).getApplicationContext() != null){
            networkConnection = new NetworkConnection(getActivity().getApplicationContext());
        }*/

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

        if(getActivity().getApplicationContext() != null) {
            networkConnection.observe(getViewLifecycleOwner(), isCheckeds -> {
                if (isCheckeds) {
                    binding.downloadLinkButton.setOnClickListener(view -> {
                        preNotification();
                        linksViewModel.getLinkDownload(urlForLink, inflater, container, audioFilesDir);
                    });

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    binding.linksRv.setLayoutManager(linearLayoutManager);
                    linksViewModel.getMutableLinksDate().observe(getViewLifecycleOwner(), linksModels -> {
                        recyclerAdapter = new AudioRecyclerAdapter(linksModels, this);
                        binding.linksRv.setAdapter(recyclerAdapter);
                    });

                    binding.imageButtonPlay.setOnClickListener(view -> {
                        if (recyclerAdapter.playAudios != null) {
                            recyclerAdapter.playAudios.playAndStop();
                        }
                    });
                } else {
                    binding.linksRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerAdapter = new AudioRecyclerAdapter(linksViewModel.getDownload(audioFilesDir), this);
                    binding.linksRv.setAdapter(recyclerAdapter);
                }
            });
        }

        return binding.getRoot();
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

    private void preNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(binding.getRoot().getContext(), MainActivity.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Помощник чтеца")
                .setContentText("Загрузка начата")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(binding.getRoot().getContext());
        managerCompat.notify(NOTIFICATION_ID, builder.build());
    }

}