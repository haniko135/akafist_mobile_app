package com.example.akafist.recyclers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.fragments.EverydayFragment;
import com.example.akafist.fragments.NeedsFragment;
import com.example.akafist.fragments.PsaltirFragment;
import com.example.akafist.models.PrayersModels;

import java.util.List;

public class PrayersRecyclerAdapter extends RecyclerView.Adapter<PrayersRecyclerAdapter.PrayersViewHolder> {

    private List<PrayersModels> prayers;
    private Fragment fragment;

    public PrayersRecyclerAdapter(List<PrayersModels> prayers, PsaltirFragment fragment) {
        this.prayers = prayers;
        this.fragment = fragment;
    }

    public PrayersRecyclerAdapter(List<PrayersModels> prayers, EverydayFragment fragment) {
        this.prayers = prayers;
        this.fragment = fragment;
    }

    public PrayersRecyclerAdapter(List<PrayersModels> prayers, NeedsFragment fragment) {
        this.prayers = prayers;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PrayersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prayers_list, parent, false);
        return new PrayersViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull PrayersViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.prayersListItem.setText(prayers.get(position).getNamePrayer());
        holder.prayersListItem.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            if(fragment.getClass() == EverydayFragment.class) {
                bundle.putInt("prevMenu", R.id.action_prayerFragment_to_everydayFragment);
            }else if(fragment.getClass() == PsaltirFragment.class){
                bundle.putInt("prevMenu", R.id.action_prayerFragment_to_psaltirFragment);
            }else if (fragment.getClass() == NeedsFragment.class){
                bundle.putInt("prevMenu", R.id.action_prayerFragment_to_needsFragment);
            }
            bundle.putInt("largeText", prayers.get(position).getTextPrayer());
            FragmentKt.findNavController(fragment).navigate(R.id.prayerFragment,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return prayers.size();
    }

    static class PrayersViewHolder extends RecyclerView.ViewHolder {

        public TextView prayersListItem;

        public PrayersViewHolder(@NonNull View itemView) {
            super(itemView);
            this.prayersListItem = itemView.findViewById(R.id.prayers_list_item);
        }
    }
}
