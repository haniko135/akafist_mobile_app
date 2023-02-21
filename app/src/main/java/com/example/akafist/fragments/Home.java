package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentHomeBinding;
import com.example.akafist.recyclers.HomeRecyclerAdapter;
import com.example.akafist.service.NetworkConnection;
import com.example.akafist.viewmodel.MenuViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private MenuViewModel menuViewModel;
    public FragmentHomeBinding homeBinding;
    AppCompatActivity fragActivity;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance() {
        return new Home();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((AppCompatActivity)getActivity() != null) {
            if (((AppCompatActivity)getActivity()).getSupportActionBar() != null){
                fragActivity = (AppCompatActivity)getActivity();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.home_title));
            }
            ViewModelProvider provider = new ViewModelProvider(this);
            menuViewModel = provider.get(MenuViewModel.class);
            menuViewModel.firstSet();
            menuViewModel.getJson("home");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (fragActivity != null){
            fragActivity.getSupportActionBar().setTitle(getResources().getString(R.string.home_title));
        }

        homeBinding = FragmentHomeBinding.inflate(getLayoutInflater());

        Home fr = this;
        homeBinding.homeRv.setLayoutManager(new LinearLayoutManager(getContext()));
        menuViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), homeBlocksModels -> homeBinding.homeRv.setAdapter(new HomeRecyclerAdapter(homeBlocksModels, fr)));

        homeBinding.homeSwipe.setOnRefreshListener(() -> {
            homeBinding.homeSwipe.setRefreshing(true);
            if(menuViewModel.getBlocksModelList().size() == 7) {
                menuViewModel.getJson("home");
                menuViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), homeBlocksModels -> homeBinding.homeRv.setAdapter(new HomeRecyclerAdapter(homeBlocksModels, fr)));
            }
            homeBinding.homeSwipe.setRefreshing(false);
        });

        return homeBinding.getRoot();
    }


}