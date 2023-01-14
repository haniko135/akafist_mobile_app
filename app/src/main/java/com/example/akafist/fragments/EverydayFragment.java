package com.example.akafist.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentEverydayBinding;
import com.example.akafist.models.PrayersModels;
import com.example.akafist.recyclers.PrayersRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EverydayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EverydayFragment extends Fragment {

    FragmentEverydayBinding everydayBinding;

    public EverydayFragment() {
        // Required empty public constructor
    }

    public static EverydayFragment newInstance() {
        return new EverydayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ежедневные молитвы");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_everydayFragment_to_home2);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        everydayBinding = FragmentEverydayBinding.inflate(getLayoutInflater());

        everydayBinding.everydayTime8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                everydayBinding.everydayRv5.setVisibility(View.INVISIBLE);
                everydayBinding.everydayRv6.setVisibility(View.INVISIBLE);
                everydayBinding.everydayRv8.setVisibility(View.VISIBLE);
                everydayBinding.everydayTime5.setBackgroundColor(Color.TRANSPARENT);
                everydayBinding.everydayTime6.setBackgroundColor(Color.TRANSPARENT);
                everydayBinding.everydayTime8.setBackgroundColor(getResources().getColor(R.color.beige_200));
            }
        });

        everydayBinding.everydayTime5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                everydayBinding.everydayRv8.setVisibility(View.INVISIBLE);
                everydayBinding.everydayRv6.setVisibility(View.INVISIBLE);
                everydayBinding.everydayRv5.setVisibility(View.VISIBLE);
                everydayBinding.everydayTime8.setBackgroundColor(Color.TRANSPARENT);
                everydayBinding.everydayTime6.setBackgroundColor(Color.TRANSPARENT);
                everydayBinding.everydayTime5.setBackgroundColor(getResources().getColor(R.color.beige_200));
            }
        });

        everydayBinding.everydayTime6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                everydayBinding.everydayRv5.setVisibility(View.INVISIBLE);
                everydayBinding.everydayRv8.setVisibility(View.INVISIBLE);
                everydayBinding.everydayRv6.setVisibility(View.VISIBLE);
                everydayBinding.everydayTime5.setBackgroundColor(Color.TRANSPARENT);
                everydayBinding.everydayTime8.setBackgroundColor(Color.TRANSPARENT);
                everydayBinding.everydayTime6.setBackgroundColor(getResources().getColor(R.color.beige_200));
            }
        });

        List<PrayersModels> prayersModels1 = prayersNameList(1);
        everydayBinding.everydayRv8.setLayoutManager(new LinearLayoutManager(getContext()));
        everydayBinding.everydayRv8.setAdapter(new PrayersRecyclerAdapter(prayersModels1, this));

        List<PrayersModels> prayersModels2 = prayersNameList(2);
        everydayBinding.everydayRv5.setLayoutManager(new LinearLayoutManager(getContext()));
        everydayBinding.everydayRv5.setAdapter(new PrayersRecyclerAdapter(prayersModels2, this));

        List<PrayersModels> prayersModels3 = prayersNameList(3);
        everydayBinding.everydayRv6.setLayoutManager(new LinearLayoutManager(getContext()));
        everydayBinding.everydayRv6.setAdapter(new PrayersRecyclerAdapter(prayersModels3, this));

        return everydayBinding.getRoot();
    }

    private List<PrayersModels> prayersNameList(int n){
        List<PrayersModels> prayersModels = new ArrayList<>();
        List<Integer> prayersLinks = new ArrayList<>();
        switch (n){
            case 1:
                someFunc(prayersModels, prayersLinks, R.array.everyday_names_8, R.string.large_text_5);
                break;
            case 2:
                someFunc(prayersModels, prayersLinks, R.array.everyday_names_5, R.string.large_text_6);
                break;
            case 3:
                someFunc(prayersModels,prayersLinks, R.array.everyday_names_6, R.string.large_text_6);
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