package com.example.akafist.recyclers;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.fragments.LinksFragment;
import com.example.akafist.models.AudioModel;
import com.example.akafist.service.PlayAudios;

import java.util.List;

public class AudioRecyclerAdapter extends RecyclerView.Adapter<AudioRecyclerAdapter.AudioViewHolder> {

    private final String secToken = "y0_AgAAAABUVpeiAADLWwAAAADXqEoa0KX1_myOSvS6tU-k0yc2A_S4C7o";
    private MediaPlayer mediaPlayer;
    public PlayAudios playAudios;
    private String urlForLink;

    private LinksFragment fragment;
    List<AudioModel> audios;

    public AudioRecyclerAdapter(List<AudioModel> audios, LinksFragment fragment){
        this.fragment = fragment;
        this.audios = audios;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.audios_list, parent, false);
        return new AudioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.audiosListItem.setText(audios.get(position).getAudioName());
        holder.audiosListItem.setOnClickListener(view -> {
            checkPlaying();
            urlForLink = audios.get(position).getAudioLink();
            fragment.urlForLink = urlForLink;
            playAudios = new PlayAudios("https://getfile.dokpub.com/yandex/get/" + urlForLink + "?alt=media", fragment.getContext(), fragment.getView());
            mediaPlayer = playAudios.getMediaPlayer();
            playAudios.playAndStop();
            fragment.binding.downloadLinkButton.setVisibility(View.VISIBLE);
            Log.i("EEEEEE", playAudios.toString());
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
                mediaPlayer = null;
            }
    }

}
