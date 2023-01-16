package com.example.akafist.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentNeedsBinding;
import com.example.akafist.models.PrayersModels;
import com.example.akafist.recyclers.PrayersRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NeedsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NeedsFragment extends Fragment {

    FragmentNeedsBinding needsBinding;

    public NeedsFragment() {
        // Required empty public constructor
    }

    public static NeedsFragment newInstance() {
        return new NeedsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Молитвы");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        needsBinding = FragmentNeedsBinding.inflate(getLayoutInflater());

        needsBinding.needsHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                needsBinding.needsRvHealth.setVisibility(View.VISIBLE);
                needsBinding.needsRvPrichastiye.setVisibility(View.INVISIBLE);
                needsBinding.needsPrichastiye.setBackgroundColor(Color.TRANSPARENT);
                needsBinding.needsHealth.setBackgroundColor(getResources().getColor(R.color.beige_200));
            }
        });

        needsBinding.needsPrichastiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                needsBinding.needsRvPrichastiye.setVisibility(View.VISIBLE);
                needsBinding.needsRvHealth.setVisibility(View.INVISIBLE);
                needsBinding.needsHealth.setBackgroundColor(Color.TRANSPARENT);
                needsBinding.needsPrichastiye.setBackgroundColor(getResources().getColor(R.color.beige_200));
            }
        });

        List<PrayersModels> prayersModels1 = prayersNameList(1);
        needsBinding.needsRvHealth.setLayoutManager(new LinearLayoutManager(getContext()));
        needsBinding.needsRvHealth.setAdapter(new PrayersRecyclerAdapter(prayersModels1, this));

        List<PrayersModels> prayersModels2 = prayersNameList(2);
        needsBinding.needsRvPrichastiye.setLayoutManager(new LinearLayoutManager(getContext()));
        needsBinding.needsRvPrichastiye.setAdapter(new PrayersRecyclerAdapter(prayersModels2, this));

        return needsBinding.getRoot();
    }

    private List<PrayersModels> prayersNameList(int n){
        List<PrayersModels> prayersModels = new ArrayList<>();
        List<Integer> prayersLinks = new ArrayList<>();
        switch (n){
            case 1:
                someFunc(prayersModels, prayersLinks, R.array.needs_names_health, R.string.large_text_5);
                break;
            case 2:
                someFunc(prayersModels, prayersLinks, R.array.needs_names_prichastiye, R.string.large_text_6);
                break;
        }
        return prayersModels;
    }

    private void someFunc(List<PrayersModels> prayersModels, List<Integer> prayersLinks, int array, int string){
        List<String> prayersNames = Arrays.asList(getResources().getStringArray(array));
        for(int i = 0; i < prayersNames.size(); i++){
            prayersLinks.add(string);
        }
        for (int i = 0; i < prayersNames.size(); i++){
            prayersModels.add(new PrayersModels(prayersNames.get(i), prayersLinks.get(i)));
        }
    }

}