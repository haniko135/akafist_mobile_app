package com.example.akafist.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.models.ServicesModel;

import java.util.List;

public class ServicesRecyclerAdapter extends RecyclerView.Adapter<ServicesRecyclerAdapter.ServicesViewHolder> {
    private List<ServicesModel> servicesModels;
    private int typeId;

    public ServicesRecyclerAdapter(List<ServicesModel> servicesModels) {
        this.servicesModels = servicesModels;
    }

    public ServicesRecyclerAdapter(List<ServicesModel> servicesModels, int typeId) {
        this.servicesModels = servicesModels;
        this.typeId = typeId;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prayers_list, parent, false);
        return new ServicesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        holder.getServiceListItem().setText(servicesModels.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return servicesModels.size();
    }

    static class ServicesViewHolder extends RecyclerView.ViewHolder{
        private TextView serviceListItem;

        public TextView getServiceListItem() {
            return serviceListItem;
        }

        public ServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.serviceListItem = itemView.findViewById(R.id.prayers_list_item);
        }
    }
}
