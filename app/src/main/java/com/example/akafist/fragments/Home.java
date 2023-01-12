package com.example.akafist.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        return new Home();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //конференции в скайп
        view.findViewById(R.id.skype_confs_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_skypesFragment);
                //getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("Home").commit();
            }
        });

        //прямая трансляция из храма Михаила
        view.findViewById(R.id.online_Michael_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("urlToSound", "http://radio.zakonbozhiy.ru:8000/live.mp3");
                bundle.putString("soundTitle", "Трансляция арх. Михаил");
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
            }
        });

        //прямая транслция из храма Варвары
        view.findViewById(R.id.online_Varvara_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("urlToSound", "http://radio.zakonbozhiy.ru:8010/kem.mp3");
                bundle.putString("soundTitle", "Трансляция св. Варвара");
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
            }
        });

        //аудио молитвы оффлайн
        view.findViewById(R.id.molitvy_offlain_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_molitvyOfflineFragment);
            }
        });

        //записи бесед
        view.findViewById(R.id.links_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_linksFragment);
            }
        });

        //подача записок
        view.findViewById(R.id.notes_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pr.energogroup.org/notes/note/add"));
                startActivity(toSite);
            }
        });

        //задать вопрос
        view.findViewById(R.id.talks_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pr.energogroup.org/talks/talk"));
                startActivity(toSite);
            }
        });

        //ежедневные молитвы
        view.findViewById(R.id.everyday_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_everydayFragment);
            }
        });

        //утренние и вечерние молитвы
        view.findViewById(R.id.morn_and_even_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_mornAndEvenMolitvyFragment);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}