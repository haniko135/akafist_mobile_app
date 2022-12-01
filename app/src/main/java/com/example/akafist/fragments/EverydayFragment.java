package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EverydayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EverydayFragment extends Fragment {

    public EverydayFragment() {
        // Required empty public constructor
    }

    public static EverydayFragment newInstance() {
        EverydayFragment fragment = new EverydayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ежедневные молитвы");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_everyday, container, false);
    }
}