package com.example.akafist.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.viewmodel.ChurchViewModel;
import com.example.akafist.databinding.FragmentChurchBinding;
import com.example.akafist.recyclers.ServicesRecyclerAdapter;
import com.example.akafist.recyclers.TypesRecyclerAdapter;

import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChurchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChurchFragment extends Fragment {

    private String date, dateTxt, name;
    public static ServicesRecyclerAdapter servicesRecyclerAdapter;
    public FragmentChurchBinding churchBinding;
    private ChurchViewModel churchViewModel;


    public ChurchFragment() {
        // Required empty public constructor
    }

    public static ChurchFragment newInstance() {
        return new ChurchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString("date");
        }
        ViewModelProvider provider = new ViewModelProvider(this);
        churchViewModel = provider.get(ChurchViewModel.class);
        if((AppCompatActivity)getActivity() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(dateTxt);
            churchViewModel.getJson(date);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        churchBinding = FragmentChurchBinding.inflate(inflater, container, false);

        churchViewModel.getLiveDataTxt().observe(getViewLifecycleOwner(), s -> {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
            churchBinding.churchDateTxt.setText(s);
        });
        churchViewModel.getLiveNameTxt().observe(getViewLifecycleOwner(), s -> churchBinding.churchName.setText(s));

        churchBinding.upRvChurch.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        churchViewModel.getMutableTypesList().observe(getViewLifecycleOwner(), typesModels -> churchBinding.upRvChurch.setAdapter(new TypesRecyclerAdapter(typesModels, this)));

        churchBinding.downRvChurch.setLayoutManager(new LinearLayoutManager(getContext()));

        churchViewModel.getCurId().observe(getViewLifecycleOwner(), integer -> churchViewModel.getMutableServicesList().observe(getViewLifecycleOwner(), servicesModels -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                servicesRecyclerAdapter = new ServicesRecyclerAdapter(servicesModels.stream().filter(servicesModel ->
                    servicesModel.getType() == integer
                ).collect(Collectors.toList()));
                churchBinding.downRvChurch.setAdapter(servicesRecyclerAdapter);
                servicesRecyclerAdapter.setFragment(this);
            }
        }));

        return churchBinding.getRoot();
    }
}