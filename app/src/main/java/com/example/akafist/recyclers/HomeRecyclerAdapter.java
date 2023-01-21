package com.example.akafist.recyclers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.fragments.Home;
import com.example.akafist.models.HomeBlocksModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder> {
    private List<HomeBlocksModel> homeBlocksModels;
    private Fragment fragment;

    public HomeRecyclerAdapter(List<HomeBlocksModel> homeBlocksModels, Home fragment) {
        this.homeBlocksModels = homeBlocksModels;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_blocks_list, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tom = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = dateFormat.format(tom);

        holder.getHomeBlockTextUp().setText(homeBlocksModels.get(position).getDateTxt());
        holder.getHomeBlockTextDown().setText(homeBlocksModels.get(position).getName());
        if (homeBlocksModels.get(position).getDate().equals("skypeConfs")){
            homeBlocksModels.get(position).setLinks(R.id.action_home2_to_skypesFragment);
        }
        if (homeBlocksModels.get(position).getDate().equals("onlineMichael")){
            homeBlocksModels.get(position).setLinks(R.string.link_Michael);
            homeBlocksModels.get(position).setAdditions("Трансляция арх. Михаил");
        }
        if (homeBlocksModels.get(position).getDate().equals("onlineVarvara")){
            homeBlocksModels.get(position).setLinks(R.string.link_Varvara);
            homeBlocksModels.get(position).setAdditions("Трансляция св. Варвара");
        }
        if (homeBlocksModels.get(position).getDate().equals("molitvyOfflain")){
            homeBlocksModels.get(position).setLinks(R.id.action_home2_to_molitvyOfflineFragment);
        }
        if (homeBlocksModels.get(position).getDate().equals("links")){
            homeBlocksModels.get(position).setLinks(R.id.action_home2_to_linksFragment);
        }
        if (homeBlocksModels.get(position).getDate().equals("notes")){
            homeBlocksModels.get(position).setLinks(R.string.link_notes);
        }
        if (homeBlocksModels.get(position).getDate().equals("talks")){
            homeBlocksModels.get(position).setLinks(R.string.link_talks);
        }
        if (homeBlocksModels.get(position).getDate().equals("now")){
            homeBlocksModels.get(position).setLinks(-1);
        }
        if (homeBlocksModels.get(position).getDate().equals(tomorrow)){
            homeBlocksModels.get(position).setLinks(-2);
        }
        if (homeBlocksModels.get(position).getDate().equals("everyday")){
            homeBlocksModels.get(position).setLinks(R.id.action_home2_to_everydayFragment);
        }
        if(homeBlocksModels.get(position).getDate().equals("psaltir")){
            homeBlocksModels.get(position).setLinks(R.id.action_home2_to_psaltirFragment);
        }
        if(homeBlocksModels.get(position).getDate().equals("needs")){
            homeBlocksModels.get(position).setLinks(R.id.action_home2_to_needsFragment);
        }

        if(homeBlocksModels.get(position).getDate().equals("onlineMichael") || homeBlocksModels.get(position).getDate().equals("onlineVarvara")){
            holder.getHomeBlockLinear().setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("urlToSound", fragment.getResources().getString(homeBlocksModels.get(position).getLinks()));
                bundle.putString("soundTitle", homeBlocksModels.get(position).getAdditions());
                FragmentKt.findNavController(fragment).navigate(R.id.onlineTempleFragment, bundle);
            });
        }else if (homeBlocksModels.get(position).getDate().equals("notes") || homeBlocksModels.get(position).getDate().equals("talks")){
            holder.getHomeBlockLinear().setOnClickListener(view -> {
                Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse(fragment.getResources().getString(homeBlocksModels.get(position).getLinks())));
                fragment.getContext().startActivity(toSite);
            });
        } else {
            holder.getHomeBlockLinear().setOnClickListener(view -> {
                if (homeBlocksModels.get(position).getLinks() > 0)
                    FragmentKt.findNavController(fragment).navigate(homeBlocksModels.get(position).getLinks());
                Log.e("Button", homeBlocksModels.get(position).getDate());
                //FragmentKt.findNavController(fragment).navigate(homeBlocksModels.get(position).getLinks());
            });
        }
    }

    @Override
    public int getItemCount() {
        return homeBlocksModels.size();
    }


    static class HomeViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout homeBlockLinear;
        private TextView homeBlockTextUp;
        private TextView homeBlockTextDown;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.homeBlockLinear = itemView.findViewById(R.id.home_block_list);
            this.homeBlockTextUp = itemView.findViewById(R.id.home_block_text_up);
            this.homeBlockTextDown = itemView.findViewById(R.id.home_block_text_down);
        }

        public LinearLayout getHomeBlockLinear() {
            return homeBlockLinear;
        }

        public TextView getHomeBlockTextUp() {
            return homeBlockTextUp;
        }

        public TextView getHomeBlockTextDown() {
            return homeBlockTextDown;
        }
    }
}
