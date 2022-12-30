package com.example.akafist.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.FragmentKt;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentMenuBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu extends Fragment {

    public Menu() {
        // Required empty public constructor
    }

    public static Menu newInstance() {
        return new Menu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Меню");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //конференции в скайпе
        view.findViewById(R.id.skype_confs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_skypesFragment);
            }
        });

        //прямая трансляция из храма Михаила
        view.findViewById(R.id.online_Michael).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("urlToSound", "http://radio.zakonbozhiy.ru:8000/live.mp3");
                bundle.putString("soundTitle", "Трансляция арх. Михаил");
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
            }
        });

        //прямая трансляция из храма Варвары
        view.findViewById(R.id.online_Varvara).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("urlToSound", "http://radio.zakonbozhiy.ru:8010/kem.mp3");
                bundle.putString("soundTitle", "Трансляция св. Варвара");
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
            }
        });

        //аудио-молитвы оффлайн
        view.findViewById(R.id.offline_molitvy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(getContext(), "links", Toast.LENGTH_SHORT).show();*/
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_molitvyOfflineFragment);
            }
        });

        //ссылка на беседы
        view.findViewById(R.id.links).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "links", Toast.LENGTH_SHORT).show();
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_linksFragment);
            }
        });

        //подача записок
        view.findViewById(R.id.notes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pr.energogroup.org/notes/note/add"));
                startActivity(toSite);
            }
        });

        //задать вопрос
        view.findViewById(R.id.talks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pr.energogroup.org/talks/talk"));
                startActivity(toSite);
            }
        });

        //ежедневный храм михаила
        view.findViewById(R.id.day_Michael).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "day_Michael", Toast.LENGTH_SHORT).show();
            }
        });

        //ежедневные молитвы
        view.findViewById(R.id.everyday_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_everydayFragment);
            }
        });

        //утренние и вечерние
        view.findViewById(R.id.morn_and_even_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "morn_and_even_title", Toast.LENGTH_SHORT).show();
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_mornAndEvenMolitvyFragment);
            }
        });

        //псалтырь
        view.findViewById(R.id.psaltir_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "psaltir_title", Toast.LENGTH_SHORT).show();
            }
        });

        //молитвы
        view.findViewById(R.id.molitvy_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "molitvy_title", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}