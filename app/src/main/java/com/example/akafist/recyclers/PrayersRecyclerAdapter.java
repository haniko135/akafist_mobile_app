package com.example.akafist.recyclers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.fragments.PsaltirFragment;
import com.example.akafist.models.PrayersModels;

import java.util.List;

public class PrayersRecyclerAdapter extends RecyclerView.Adapter<PrayersRecyclerAdapter.PrayersViewHolder> {

    private List<PrayersModels> prayers;
    private PsaltirFragment fragment;

    public PrayersRecyclerAdapter(List<PrayersModels> prayers, PsaltirFragment fragment) {
        this.prayers = prayers;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PrayersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prayers_list, parent, false);
        return new PrayersViewHolder(itemView);
    }


    // ошибка в передаче фрагмента сюда
    @Override
    public void onBindViewHolder(@NonNull PrayersViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.prayersListItem.setText(prayers.get(position).getNamePrayer());
        holder.prayersListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("prevMenu",R.id.action_prayerFragment_to_psaltirFragment);
                bundle.putInt("largeText", prayers.get(position).getTextPrayer());
                FragmentKt.findNavController(fragment).navigate(R.id.action_psaltirFragment_to_prayerFragment,bundle);
            }
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
