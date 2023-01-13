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
import com.example.akafist.databinding.FragmentSkypesBinding;
import com.example.akafist.models.SkypesConfs;
import com.example.akafist.recyclers.SkypesRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkypesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkypesFragment extends Fragment {

    private RecyclerView skypesList;
    FragmentSkypesBinding skypesBinding;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Конференции по группам");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_skypesFragment_to_home2);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        skypesBinding = FragmentSkypesBinding.inflate(getLayoutInflater());

        skypesBinding.skypesList.setLayoutManager(new LinearLayoutManager(getContext()));
        skypesBinding.skypesList.setAdapter(new SkypesRecyclerAdapter(setSkypeConfs()));

        return skypesBinding.getRoot();
    }

    private List<SkypesConfs> setSkypeConfs(){
        List<String> blockNames = Arrays.asList(getResources().getStringArray(R.array.skype_confs_list_names));
        List<String> skypeLinks = Arrays.asList(getResources().getStringArray(R.array.skype_confs_list_links_fake));
        List<SkypesConfs> skypesConfs = new ArrayList<>();
        for (int i = 0; i < blockNames.size(); i++){
            skypesConfs.add(new SkypesConfs(blockNames.get(i), skypeLinks.get(i)));
        }
        return skypesConfs;
    }

}