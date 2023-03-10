package net.energogroup.akafist.recyclers;


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

import net.energogroup.akafist.R;
import net.energogroup.akafist.fragments.SkypesFragment;
import net.energogroup.akafist.models.SkypesConfs;

import java.util.List;

/**
 * Класс адаптера RecyclerView для класса {@link SkypesFragment}
 * @author Nastya Izotina
 * @version 1.0.0
 */
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

    /**
     * Этот метод отвечает за логику, происходящую в каждом элементе RecyclerView
     * @param holder Элемент списка
     * @param position Позиция в списке
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SkypesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.skypesListItem.setText(skypesConfs.get(position).getName());
        if(skypesConfs.get(position).isUrl()) {
            holder.skypesListItem.setOnClickListener(view -> {
                holder.skypesListItem.setBackgroundColor(R.color.white);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(skypesConfs.get(position).getUrl()));
                view.getContext().startActivity(intent);
            });
        }else {
            holder.skypesListItem.setOnClickListener(view -> {
                holder.skypesListItem.setBackgroundColor(R.color.white);
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


    /**
     * Внутренний класс, отвечающий за правильной отображение элемента RecyclerView
     */
    static class SkypesViewHolder extends RecyclerView.ViewHolder{

        public TextView skypesListItem;

        public SkypesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.skypesListItem = itemView.findViewById(R.id.prayers_list_item);
        }
    }
}
