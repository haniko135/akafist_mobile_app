package com.example.akafist.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.FragmentKt;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentPrayerBinding;
import com.example.akafist.viewmodel.PrayerViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrayerFragment extends Fragment {

    private TextView textPrayer;
    private float textSize;
    private String prevMenu;
    private int prayerId;
    private PrayerViewModel prayerViewModel;
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
        if(getArguments() != null){
            prevMenu = getArguments().getString("prevMenu");
            prayerId = getArguments().getInt("prayerId");
        }
        ViewModelProvider provider = new ViewModelProvider(this);
        prayerViewModel = provider.get(PrayerViewModel.class);
        prayerViewModel.getJson(prevMenu, prayerId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            prevMenu = getArguments().getString("prevMenu");
            prayerId = getArguments().getInt("prayerId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getArguments() != null){
            prevMenu = getArguments().getString("prevMenu");
            prayerId = getArguments().getInt("largeText");
            if(getArguments().get("textSize")!=null){
                textSize = getArguments().getFloat("textSize");
            } else{
                textSize = getResources().getDimension(R.dimen.text_prayer);
            }
        }

        prayerViewModel.getPrayersModelsMutableLiveData().observe(getViewLifecycleOwner(), prayersModels -> ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(prayersModels.getNamePrayer()));

        binding = FragmentPrayerBinding.inflate(getLayoutInflater());
        binding.textPrayer.setTextSize(convertToPx());
        binding.textPrayer.setMovementMethod(new ScrollingMovementMethod());
        prayerViewModel.getPrayersModelsMutableLiveData().observe(getViewLifecycleOwner(), prayersModels -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.textPrayer.setText(Html.fromHtml(prayersModels.getTextPrayer(), Html.FROM_HTML_MODE_COMPACT));
            } else{
                binding.textPrayer.setText(Html.fromHtml(prayersModels.getTextPrayer()));
            }
        });

        binding.prayerOptions.getMenu().getItem(0).setChecked(false);

        binding.prayerOptions.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.zoom_out:
                    textSize--;
                    binding.textPrayer.setTextSize(convertToPx());
                    Log.i("PRAYER", Float.toString(textSize));
                    return true;
                case R.id.to_menu:
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("date",prevMenu);
                    FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_prayerFragment_to_churchFragment, bundle1);
                    return true;
                case R.id.zoom_in:
                    textSize++;
                    binding.textPrayer.setTextSize(convertToPx());
                    Log.i("PRAYER", Float.toString(textSize));
                    return true;
                case R.id.next_prayer:
                    if(prayerViewModel.getPrayersModel().getNext() == 0){
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("date",prevMenu);
                        FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_prayerFragment_to_churchFragment, bundle3);
                        return true;
                    } else {
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("prevMenu", prevMenu);
                        bundle4.putInt("prayerId", prayerViewModel.getPrayersModel().getNext());
                        bundle4.putFloat("textSize", textSize);
                        FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_prayerFragment_self, bundle4);
                        return true;
                    }
                case R.id.prev_prayer:
                    if(prayerViewModel.getPrayersModel().getPrev() == 0){
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("date",prevMenu);
                        FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_prayerFragment_to_churchFragment, bundle5);
                        return true;
                    }else {
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("prevMenu", prevMenu);
                        bundle2.putInt("prayerId", prayerViewModel.getPrayersModel().getPrev());
                        bundle2.putFloat("textSize", textSize);
                        FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_prayerFragment_self, bundle2);
                        return true;
                    }
            }
            return false;
        });

        return binding.getRoot();
    }

    private float convertToPx(){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, textSize, getContext().getResources().getDisplayMetrics());
    }
}