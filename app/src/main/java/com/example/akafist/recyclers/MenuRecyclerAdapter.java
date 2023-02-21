package com.example.akafist.recyclers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.fragments.Menu;
import com.example.akafist.models.HomeBlocksModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuViewHolder>{
    private List<HomeBlocksModel> modelList;
    private Menu fragment;

    public MenuRecyclerAdapter(List<HomeBlocksModel> modelList, Menu fragment) {
        this.modelList = modelList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_blocks_list, parent,false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tom = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
        String tomorrow = dateFormat.format(tom);

        holder.getMenuListBlock().setText(modelList.get(position).getDateTxt());
        if (modelList.get(position).getDate().equals("skypeConfs")){
            modelList.get(position).setLinks(R.id.action_menu_to_skypesFragment);
        }
        if (modelList.get(position).getDate().equals("onlineMichael")){
            modelList.get(position).setLinks(R.string.link_Michael);
            modelList.get(position).setAdditions("Трансляция арх. Михаил");
        }
        if (modelList.get(position).getDate().equals("onlineVarvara")){
            modelList.get(position).setLinks(R.string.link_Varvara);
            modelList.get(position).setAdditions("Трансляция св. Варвара");
        }
        if (modelList.get(position).getDate().equals("molitvyOfflain")){
            modelList.get(position).setLinks(R.id.action_menu_to_linksFragment);
        }
        if (modelList.get(position).getDate().equals("links")){
            modelList.get(position).setLinks(R.id.action_menu_to_linksFragment);
        }
        if (modelList.get(position).getDate().equals("notes")){
            modelList.get(position).setLinks(R.string.link_notes);
        }
        if (modelList.get(position).getDate().equals("talks")){
            modelList.get(position).setLinks(R.string.link_talks);
        }
        if (modelList.get(position).getDate().equals("now")){
            modelList.get(position).setLinks(R.id.action_menu_to_churchFragment);
        }
        if (modelList.get(position).getDate().equals(tomorrow)){
            modelList.get(position).setLinks(R.id.action_menu_to_churchFragment);
        }
        if (modelList.get(position).getDate().equals("everyday")){
            modelList.get(position).setLinks(R.id.action_menu_to_churchFragment);
        }
        if (modelList.get(position).getDate().equals("psaltir")){
            modelList.get(position).setLinks(R.id.action_menu_to_churchFragment);
        }
        if (modelList.get(position).getDate().equals("needs")){
            modelList.get(position).setLinks(R.id.action_menu_to_churchFragment);
        }

        if(modelList.get(position).getDate().equals("onlineMichael") || modelList.get(position).getDate().equals("onlineVarvara")){
            holder.getMenuListBlock().setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("urlToSound", fragment.getResources().getString(modelList.get(position).getLinks()));
                bundle.putString("soundTitle", modelList.get(position).getAdditions());
                FragmentKt.findNavController(fragment).navigate(R.id.onlineTempleFragment, bundle);
            });
        }else if (modelList.get(position).getDate().equals("notes") || modelList.get(position).getDate().equals("talks")){
            holder.getMenuListBlock().setOnClickListener(view -> {
                Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse(fragment.getResources().getString(modelList.get(position).getLinks())));
                fragment.getContext().startActivity(toSite);
            });
        } else {
            holder.getMenuListBlock().setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("date", modelList.get(position).getDate());
                bundle.putString("dateTxt", modelList.get(position).getDateTxt());
                //bundle.putString("name", modelList.get(position).getName());
                FragmentKt.findNavController(fragment).navigate(modelList.get(position).getLinks(), bundle);
                //Log.e("Button", modelList.get(position).getDate());
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    static public class MenuViewHolder extends RecyclerView.ViewHolder{
        private final TextView menuListBlock;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            this.menuListBlock = itemView.findViewById(R.id.menu_list_block);
        }

        public TextView getMenuListBlock() {
            return menuListBlock;
        }
    }
}
