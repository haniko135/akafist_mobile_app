package com.example.akafist.recyclers;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import com.example.akafist.fragments.SkypesFragment;
import com.example.akafist.models.SkypesConfs;

import java.util.List;

public class SkypesRecyclerAdapter extends RecyclerView.Adapter<SkypesRecyclerAdapter.SkypesViewHolder>{
    private List<SkypesConfs> skypesConfs;
    private Fragment fragment;

    public SkypesRecyclerAdapter(List<SkypesConfs> skypesConfs, SkypesFragment fragment) {
        this.skypesConfs = skypesConfs;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public SkypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prayers_list, parent, false);
        return new SkypesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SkypesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.skypesListItem.setText(skypesConfs.get(position).getName());
        if(skypesConfs.get(position).isUrl()) {
            holder.skypesListItem.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(skypesConfs.get(position).getUrl()));
                view.getContext().startActivity(intent);
            });
        }else {
            holder.skypesListItem.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("nameTitle", skypesConfs.get(position).getName());
                bundle.putInt("urlId", skypesConfs.get(position).getId());
                FragmentKt.findNavController(fragment).navigate(R.id.action_skypesFragment_to_skypesBlocksFragment, bundle);
            });
        }
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
