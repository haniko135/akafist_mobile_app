package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.FragmentKt;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.akafist.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Помощник чтеца");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.skype_confs_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_skypesFragment);
                //getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("Home").commit();
            }
        });
        view.findViewById(R.id.online_Michael_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlToMichael =  "http://radio.zakonbozhiy.ru:8000/live.mp3";
                Bundle bundle = new Bundle();
                bundle.putString("urlToSound", urlToMichael);
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
            }
        });
        view.findViewById(R.id.online_Varvara_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "online_Varvara", Toast.LENGTH_SHORT).show();
                String urlToVarvara =  "http://radio.zakonbozhiy.ru:8010/kem.mp3";
                Bundle bundle = new Bundle();
                bundle.putString("urlToSound", urlToVarvara);
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
            }
        });
        view.findViewById(R.id.everyday_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_everydayFragment);
            }
        });
        view.findViewById(R.id.morn_and_even_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_mornAndEvenMolitvyFragment);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            FragmentKt.findNavController(getParentFragment()).navigate(R.id.home2);
        }
        return true;
    }
}