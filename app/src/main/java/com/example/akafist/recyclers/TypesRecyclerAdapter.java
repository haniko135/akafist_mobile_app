package com.example.akafist.recyclers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.fragments.ChurchFragment;
import com.example.akafist.models.ServicesModel;
import com.example.akafist.models.TypesModel;

import java.util.List;

public class TypesRecyclerAdapter extends RecyclerView.Adapter<TypesRecyclerAdapter.TypesViewHolder>{
    private List<TypesModel> typesModels;
    private List<ServicesModel> servicesModels;

    public TypesRecyclerAdapter(List<TypesModel> typesModels) {
        this.typesModels = typesModels;
    }

    @NonNull
    @Override
    public TypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_horizontal_list, parent, false);
        return new TypesViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TypesViewHolder holder, int position) {
        holder.getHorizontalItem().setText(typesModels.get(position).getName());
        holder.getHorizontalItem().setOnClickListener(view -> {
            holder.getHorizontalItem().setBackgroundColor(R.color.beige_200);
            ServicesRecyclerAdapter servicesRecyclerAdapter = new ServicesRecyclerAdapter(servicesModels, typesModels.get(position).getId());
            ChurchFragment.servicesRecyclerAdapter = servicesRecyclerAdapter;
        });
    }

    @Override
    public int getItemCount() {
        return typesModels.size();
    }


    static class TypesViewHolder extends RecyclerView.ViewHolder{

        private TextView horizontalItem;

        public TypesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.horizontalItem = itemView.findViewById(R.id.horizontal_item);
        }

        public TextView getHorizontalItem() {
            return horizontalItem;
        }
    }
}
