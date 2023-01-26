package com.example.akafist.recyclers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.models.ServicesModel;

import java.util.List;

public class ServicesRecyclerAdapter extends RecyclerView.Adapter<ServicesRecyclerAdapter.ServicesViewHolder> {
    private List<ServicesModel> servicesModels;
    private Fragment fragment;


    public ServicesRecyclerAdapter(List<ServicesModel> servicesModels) {
        this.servicesModels = servicesModels;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
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
        holder.getServiceListItem().setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("prevMenu", servicesModels.get(position).getDate());
            bundle.putInt("prayerId", servicesModels.get(position).getId());
            FragmentKt.findNavController(fragment).navigate(R.id.action_churchFragment_to_prayerFragment, bundle);
        });
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
