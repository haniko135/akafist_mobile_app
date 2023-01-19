package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentPsaltirBinding;
import com.example.akafist.models.PrayersModels;
import com.example.akafist.recyclers.PrayersRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PsaltirFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsaltirFragment extends Fragment {

    private RecyclerView recyclerView;
    FragmentPsaltirBinding psaltirBinding;

    public PsaltirFragment() {
        // Required empty public constructor
    }

    public static PsaltirFragment newInstance() {
        return new PsaltirFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Псалтирь");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_psaltirFragment_to_home2);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        psaltirBinding = FragmentPsaltirBinding.inflate(getLayoutInflater());

        psaltirBinding.psaltirRV.setLayoutManager(new LinearLayoutManager(getContext()));
        psaltirBinding.psaltirRV.setAdapter(new PrayersRecyclerAdapter(prayersNameList(), this));

        return psaltirBinding.getRoot();
    }

    private List<PrayersModels> prayersNameList(){
        List<String> prayersNames = Arrays.asList(getResources().getStringArray(R.array.psaltir_names));
        List<Integer> prayersLinks = new ArrayList<>();
        for(int i = 0; i < prayersNames.size(); i++){
            prayersLinks.add(R.string.large_text_2);
        }
        List<PrayersModels> prayersModels = new ArrayList<>();
        for (int i = 0; i < prayersNames.size(); i++){
            prayersModels.add(new PrayersModels(prayersNames.get(i), prayersLinks.get(i)));
        }
        return prayersModels;
    }
}