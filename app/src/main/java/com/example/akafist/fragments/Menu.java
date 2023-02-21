package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.databinding.FragmentMenuBinding;
import com.example.akafist.recyclers.MenuRecyclerAdapter;
import com.example.akafist.viewmodel.MenuViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu extends Fragment {
    private MenuViewModel menuViewModel;
    public FragmentMenuBinding menuBinding;

    public Menu() {
        // Required empty public constructor
    }

    public static Menu newInstance() {
        return new Menu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProvider provider = new ViewModelProvider(this);
        menuViewModel = provider.get(MenuViewModel.class);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Меню");

        menuViewModel.firstSet();
        menuViewModel.getJson("menu");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        menuBinding = FragmentMenuBinding.inflate(getLayoutInflater());

        Menu fr = this;
        menuBinding.menuList.setLayoutManager(new LinearLayoutManager(getContext()));
        menuViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), homeBlocksModels -> menuBinding.menuList.setAdapter(new MenuRecyclerAdapter(homeBlocksModels,fr)));

        menuBinding.menuSwipe.setOnRefreshListener(() -> {
            menuBinding.menuSwipe.setRefreshing(true);
            if(menuViewModel.getBlocksModelList().size() == 7) {
                menuViewModel.getJson("menu");
                menuViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), homeBlocksModels -> menuBinding.menuList.setAdapter(new MenuRecyclerAdapter(homeBlocksModels,fr)));
            }
            menuBinding.menuSwipe.setRefreshing(false);
        });

        return menuBinding.getRoot();
    }

}