package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.MainActivity;
import com.example.akafist.R;
import com.example.akafist.databinding.FragmentLinksBinding;
import com.example.akafist.recyclers.AudioRecyclerAdapter;
import com.example.akafist.viewmodel.LinksViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinksFragment extends Fragment {

    private String linksAudiosFilesDir, molitvyOfflainFilesDir, date, dateTxt;
    private String finalPath;
    private LinksViewModel linksViewModel;
    private AudioRecyclerAdapter recyclerAdapter;
    public String urlForLink;
    private final int NOTIFICATION_ID = 101;
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
        if (getArguments() != null){
            date = getArguments().getString("date");
            if(getArguments().getString("dateTxt")!=null){
                dateTxt = getArguments().getString("dateTxt");
            }
        }
        ViewModelProvider provider = new ViewModelProvider(this);
        linksViewModel = provider.get(LinksViewModel.class);
        if((AppCompatActivity)getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(dateTxt);
            linksViewModel.getJson(date, getLayoutInflater());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLinksBinding.inflate(inflater, container, false);

        linksAudiosFilesDir = getContext().getFilesDir().getPath()+"/links_records";
        molitvyOfflainFilesDir = getContext().getFilesDir().getPath()+"/prayers_records";

        switch (date) {
            case "links":
                finalPath = linksAudiosFilesDir;
            case "molitvyOfflain":
                finalPath = molitvyOfflainFilesDir;
        }

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
            MainActivity.networkConnection.observe(getViewLifecycleOwner(), isCheckeds -> {
                if (isCheckeds) {
                    binding.downloadLinkButton.setOnClickListener(view -> {
                        preNotification();
                        linksViewModel.getLinkDownload(urlForLink, inflater, container, finalPath);
                    });

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    binding.linksRv.setLayoutManager(linearLayoutManager);
                    linksViewModel.getMutableLinksDate().observe(getViewLifecycleOwner(), linksModels -> {
                        if (recyclerAdapter == null)
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
                    if (recyclerAdapter == null)
                        recyclerAdapter = new AudioRecyclerAdapter(linksViewModel.getDownload(finalPath), this);
                    binding.linksRv.setAdapter(recyclerAdapter);
                }
            });
        }

        binding.linksRoot.setOnRefreshListener(() -> {
            binding.linksRoot.setRefreshing(true);
            linksViewModel.retryGetJson(date, getLayoutInflater());
            binding.linksRoot.setRefreshing(false);
        });

        return binding.getRoot();
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