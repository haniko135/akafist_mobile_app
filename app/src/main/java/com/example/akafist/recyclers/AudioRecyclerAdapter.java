package com.example.akafist.recyclers;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.akafist.MainActivity;
import com.example.akafist.R;
import com.example.akafist.fragments.LinksFragment;
import com.example.akafist.models.AudioModel;
import com.example.akafist.models.LinksModel;
import com.example.akafist.service.PlayAudios;

import java.util.List;

public class AudioRecyclerAdapter extends RecyclerView.Adapter<AudioRecyclerAdapter.AudioViewHolder> {

    private MediaPlayer mediaPlayer;
    public PlayAudios playAudios;
    private String urlForLink;
    private final String urlPattern = "https://getfile.dokpub.com/yandex/get/";

    private LinksFragment fragment;
    private List<LinksModel> audios;

    public AudioRecyclerAdapter(List<LinksModel> audios, LinksFragment fragment){
        this.fragment = fragment;
        this.audios = audios;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.audios_list, parent, false);
        return new AudioViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.audiosListItem.setText(audios.get(position).getName());
        holder.audiosListItem.setOnClickListener(view -> {
            checkPlaying();
            urlForLink = audios.get(position).getUrl();
            fragment.urlForLink = urlForLink;
            MainActivity.networkConnection.observe(fragment.getViewLifecycleOwner(), isChecked->{
                if (isChecked){
                    fragment.binding.downloadLinkButton.setVisibility(View.VISIBLE);
                    if (playAudios == null) {
                        playAudios = new PlayAudios(urlPattern + urlForLink + "?alt=media", fragment.getContext(),
                                fragment.getView(), audios.get(position).getName());
                    }else {
                        playAudios.destroyPlayAudios();
                        playAudios = new PlayAudios(urlPattern + urlForLink + "?alt=media", fragment.getContext(),
                                fragment.getView(), audios.get(position).getName());
                    }
                    mediaPlayer = playAudios.getMediaPlayer();
                    playAudios.playAndStop();
                }else {
                    fragment.binding.downloadLinkButton.setVisibility(View.GONE);
                    if(playAudios == null) {
                        playAudios = new PlayAudios(urlForLink, fragment.getContext(),
                                fragment.getView(), audios.get(position).getName());
                    }else {
                        playAudios.destroyPlayAudios();
                        playAudios = new PlayAudios(urlForLink, fragment.getContext(),
                                fragment.getView(), audios.get(position).getName());
                    }
                    mediaPlayer = playAudios.getMediaPlayer();
                    playAudios.playAndStop();
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return audios.size();
    }

    static class AudioViewHolder extends RecyclerView.ViewHolder{
        public TextView audiosListItem;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            this.audiosListItem = itemView.findViewById(R.id.audio_list_item);
        }
    }

    private void checkPlaying(){
        if (mediaPlayer != null)
            if(mediaPlayer.isPlaying()) {
                PlayAudios.runnable = null;
                mediaPlayer.stop();
            }
    }

}
