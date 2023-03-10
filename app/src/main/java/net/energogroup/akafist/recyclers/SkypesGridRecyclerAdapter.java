package net.energogroup.akafist.recyclers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.energogroup.akafist.R;
import net.energogroup.akafist.models.SkypesConfs;

import net.energogroup.akafist.fragments.SkypesBlocksFragment;

import java.util.List;

/**
 * Класс адаптера RecyclerView для класса {@link SkypesBlocksFragment}
 * @author Nastya Izotina
 * @version 1.0.0
 */
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

    /**
     * Этот метод отвечает за логику, происходящую в каждом элементе RecyclerView
     * @param holder Элемент списка
     * @param position Позиция в списке
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SkypesGridViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getSkypesListItem().setText(skypesConfs.get(position).getName());
        holder.getSkypesListItem().setOnClickListener(view -> {
            holder.getSkypesListItem().setBackgroundColor(R.color.white);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(skypesConfs.get(position).getUrl()));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return skypesConfs.size();
    }

    /**
     * Внутренний класс, отвечающий за правильной отображение элемента RecyclerView
     */
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
