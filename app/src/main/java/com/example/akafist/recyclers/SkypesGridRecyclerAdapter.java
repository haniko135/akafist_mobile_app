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

public class SkypesGridRecyclerAdapter extends RecyclerView.Adapter<SkypesGridRecyclerAdapter.SkypesGridViewHolder> {

    private List<SkypesConfs> skypesConfs;

    public SkypesGridRecyclerAdapter(List<SkypesConfs> skypesConfs) {
        this.skypesConfs = skypesConfs;
    }

    @NonNull
    @Override
    public SkypesGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prayers_list_grid, parent, false);
        return new SkypesGridViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SkypesGridViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getSkypesListItem().setText(skypesConfs.get(position).getName());
        holder.getSkypesListItem().setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(skypesConfs.get(position).getUrl()));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return skypesConfs.size();
    }


    static class SkypesGridViewHolder extends RecyclerView.ViewHolder{
        private TextView skypesListItem;

        public SkypesGridViewHolder(@NonNull View itemView) {
            super(itemView);
            this.skypesListItem = itemView.findViewById(R.id.prayers_list_item_grid);
        }

        public TextView getSkypesListItem() {
            return skypesListItem;
        }
    }
}
