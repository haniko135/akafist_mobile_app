package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MornAndEvenMolitvyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MornAndEvenMolitvyFragment extends Fragment {

    public MornAndEvenMolitvyFragment() {
        // Required empty public constructor
    }

    public static MornAndEvenMolitvyFragment newInstance() {
        MornAndEvenMolitvyFragment fragment = new MornAndEvenMolitvyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Утренние и вечерние молитвы");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_mornAndEvenMolitvyFragment_to_home2);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_morn_and_even_molitvy, container, false);
    }
}