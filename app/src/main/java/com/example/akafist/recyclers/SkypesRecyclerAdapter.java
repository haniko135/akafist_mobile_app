package com.example.akafist.recyclers;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.models.SkypesConfs;

import java.util.List;

public class SkypesRecyclerAdapter extends RecyclerView.Adapter<SkypesRecyclerAdapter.SkypesViewHolder>{
    private List<SkypesConfs> skypesConfs;

    public SkypesRecyclerAdapter(List<SkypesConfs> skypesConfs) {
        this.skypesConfs = skypesConfs;
    }

    @NonNull
    @Override
    public SkypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prayers_list, parent, false);
        return new SkypesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SkypesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.skypesListItem.setText(skypesConfs.get(position).getBlockName());
        holder.skypesListItem.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(skypesConfs.get(position).getSkypeLink()));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return skypesConfs.size();
    }


    static class SkypesViewHolder extends RecyclerView.ViewHolder{

        public TextView skypesListItem;

        public SkypesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.skypesListItem = itemView.findViewById(R.id.prayers_list_item);
        }
    }
}
