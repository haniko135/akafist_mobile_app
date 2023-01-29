package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.databinding.FragmentSkypesBinding;
import com.example.akafist.recyclers.SkypesRecyclerAdapter;
import com.example.akafist.viewmodel.SkypeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkypesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkypesFragment extends Fragment {

    private SkypeViewModel skypeViewModel;
    public FragmentSkypesBinding skypesBinding;

    public SkypesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SkypesFragment.
     */
    public static SkypesFragment newInstance() {
        return new SkypesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((AppCompatActivity)getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Конференции по группам");
            ViewModelProvider provider = new ViewModelProvider(this);
            skypeViewModel = provider.get(SkypeViewModel.class);
            skypeViewModel.getJsonSkype();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if((AppCompatActivity)getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Конференции по группам");
        }

        skypesBinding = FragmentSkypesBinding.inflate(getLayoutInflater());

        skypesBinding.skypesList.setLayoutManager(new LinearLayoutManager(getContext()));
        skypeViewModel.getSkypesMutableLiveData().observe(getViewLifecycleOwner(), skypesConfs -> skypesBinding.skypesList.setAdapter(new SkypesRecyclerAdapter(skypesConfs, this)));

        skypesBinding.confsList.setLayoutManager(new LinearLayoutManager(getContext()));
        skypeViewModel.getConfsMutableLiveData().observe(getViewLifecycleOwner(), skypesConfs -> skypesBinding.confsList.setAdapter(new SkypesRecyclerAdapter(skypesConfs ,this)));

        return skypesBinding.getRoot();
    }

}