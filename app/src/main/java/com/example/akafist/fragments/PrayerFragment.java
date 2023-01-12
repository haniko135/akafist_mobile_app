package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentPrayerBinding;
import com.google.android.material.navigation.NavigationBarView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrayerFragment extends Fragment {

    private TextView textPrayer;
    private float textSize;
    private int prevMenu;
    private int largeText;
    FragmentPrayerBinding binding;

    public PrayerFragment() {
        // Required empty public constructor
    }

    public static PrayerFragment newInstance() {
        return new PrayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            prevMenu = getArguments().getInt("prevMenu");
            largeText = getArguments().getInt("largeText");
        }
        //textPrayer = getActivity().findViewById(R.id.text_prayer);
        //textSize = getResources().getDimension(R.dimen.text_prayer);
       // textPrayer.setTextSize(convertToPx());
       // textPrayer.setText(largeText);
       // Log.i("PRAYER", getResources().getString(largeText));
        Log.i("PRAYER", Float.toString(textSize));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null){
            prevMenu = getArguments().getInt("prevMenu");
            largeText = getArguments().getInt("largeText");
            if(getArguments().get("textSize")!=null){
                textSize = getArguments().getFloat("textSize");
            } else{
                textSize = getResources().getDimension(R.dimen.text_prayer);
            }
        }

        binding = FragmentPrayerBinding.inflate(getLayoutInflater());
        Log.i("PRAYER", getResources().getString(largeText));
        binding.textPrayer.setTextSize(convertToPx());
        binding.textPrayer.setText(largeText);

        binding.prayerOptions.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.zoom_out:
                        textSize--;
                        binding.textPrayer.setTextSize(convertToPx());
                        Log.i("PRAYER", Float.toString(textSize));
                        return true;
                    case R.id.to_menu:
                        FragmentKt.findNavController(getParentFragment()).navigate(prevMenu);
                        return true;
                    case R.id.zoom_in:
                        textSize++;
                        binding.textPrayer.setTextSize(convertToPx());
                        Log.i("PRAYER", Float.toString(textSize));
                        return true;
                    case R.id.next_prayer:
                        Bundle bundle = new Bundle();
                        bundle.putInt("prevMenu", prevMenu);
                        bundle.putInt("largeText", R.string.large_text_4);
                        bundle.putFloat("textSize", textSize);
                        FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_prayerFragment_self, bundle);
                        return true;
                }
                return true;
            }
        });

        return binding.getRoot();
    }

    private float convertToPx(){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, textSize, getContext().getResources().getDisplayMetrics());
    }
}