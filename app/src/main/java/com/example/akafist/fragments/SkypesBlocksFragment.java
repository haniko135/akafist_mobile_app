package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.databinding.FragmentSkypesBlocksBinding;
import com.example.akafist.recyclers.SkypesGridRecyclerAdapter;
import com.example.akafist.viewmodel.SkypeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkypesBlocksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkypesBlocksFragment extends Fragment {

    private SkypeViewModel skypeViewModel;
    private String nameTitle;
    private int urlId;

    public FragmentSkypesBlocksBinding skypesBlocksBinding;

    public SkypesBlocksFragment() {
        // Required empty public constructor
    }

    public static SkypesBlocksFragment newInstance() {
        return new SkypesBlocksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nameTitle = getArguments().getString("nameTitle");
            urlId = getArguments().getInt("urlId");
        }
        if((AppCompatActivity)getActivity() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(nameTitle);
            ViewModelProvider provider = new ViewModelProvider(this);
            skypeViewModel = provider.get(SkypeViewModel.class);
            skypeViewModel.getJsonSkypeBlock(urlId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        skypesBlocksBinding = FragmentSkypesBlocksBinding.inflate(inflater, container, false);

        skypesBlocksBinding.groupBlocks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        skypeViewModel.getConfsMutableLiveData().observe(getViewLifecycleOwner(), view -> skypesBlocksBinding.groupBlocks.setAdapter(new SkypesGridRecyclerAdapter(view)));

        return skypesBlocksBinding.getRoot();
    }


}