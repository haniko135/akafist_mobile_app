package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentPrayerBinding;

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

    public PrayerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PrayerFragment newInstance() {
        PrayerFragment fragment = new PrayerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textPrayer = getActivity().findViewById(R.id.text_prayer);
        textSize = getResources().getDimension(R.dimen.text_prayer);
        textPrayer.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, textSize, getContext().getResources().getDisplayMetrics()));
        textPrayer.setText(largeText);
        Log.i("PRAYER", Float.toString(textSize));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null){
            prevMenu = getArguments().getInt("prevMenu");
            largeText = getArguments().getInt("largeText");
        }

        View view = inflater.inflate(R.layout.fragment_prayer, container, false);

        view.findViewById(R.id.zoom_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textSize--;
                textPrayer.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, textSize, getContext().getResources().getDisplayMetrics()));
                Log.i("PRAYER", Float.toString(textSize));
            }
        });

        view.findViewById(R.id.zoom_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textSize++;
                textPrayer.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, textSize, getContext().getResources().getDisplayMetrics()));
                Log.i("PRAYER", Float.toString(textSize));
            }
        });
        view.findViewById(R.id.to_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(getParentFragment()).navigate(prevMenu);
            }
        });
        view.findViewById(R.id.next_prayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("prevMenu", prevMenu);
                bundle.putInt("largeText", R.string.large_text);
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_prayerFragment_self, bundle);
            }
        });

        return view;
    }
}