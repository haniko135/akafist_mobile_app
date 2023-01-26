package com.example.akafist.recyclers;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.ChurchViewModel;
import com.example.akafist.R;
import com.example.akafist.fragments.ChurchFragment;
import com.example.akafist.models.ServicesModel;
import com.example.akafist.models.TypesModel;

import java.util.List;

public class TypesRecyclerAdapter extends RecyclerView.Adapter<TypesRecyclerAdapter.TypesViewHolder>{
    private List<TypesModel> typesModels;
    private List<ServicesModel> servicesModels;
    private ViewModelProvider provider;
    private ChurchViewModel churchViewModel;
    Fragment fragment;

    public TypesRecyclerAdapter(List<TypesModel> typesModels, Fragment fragment) {
        this.typesModels = typesModels;
        this.fragment = fragment;
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
        provider = new ViewModelProvider(fragment);
        churchViewModel = provider.get(ChurchViewModel.class);


        holder.getHorizontalItem().setText(typesModels.get(position).getName());
        holder.getHorizontalItem().setOnClickListener(view -> {
            churchViewModel.setCurId(typesModels.get(position).getId());
            Log.e("AdapterId", String.valueOf(typesModels.get(position).getId()));
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
