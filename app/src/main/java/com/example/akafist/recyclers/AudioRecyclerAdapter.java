package com.example.akafist.recyclers;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akafist.R;
import com.example.akafist.fragments.LinksFragment;
import com.example.akafist.models.LinksModel;
import com.example.akafist.service.PlayAudios;

import java.util.List;

public class AudioRecyclerAdapter extends RecyclerView.Adapter<AudioRecyclerAdapter.AudioViewHolder> {

    private MediaPlayer mediaPlayer;
    public PlayAudios playAudios;
    private String urlForLink;
    private ProgressDialog progressDialog;

    private final LinksFragment fragment;
    private final List<LinksModel> audios;

    public AudioRecyclerAdapter(List<LinksModel> audios, LinksFragment fragment){
        this.fragment = fragment;
        this.audios = audios;
        progressDialog = new ProgressDialog(fragment.getContext());
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
            new PlayAudiosLoad().doInBackground(Integer.toString(position));
            //playAudios = new PlayAudios("https://getfile.dokpub.com/yandex/get/" + urlForLink + "?alt=media", fragment.getContext(),
                    //fragment.getView(), audios.get(position).getName());
            /*mediaPlayer = playAudios.getMediaPlayer();
            playAudios.playAndStop();*/
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

    class PlayAudiosLoad extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            playAudios = new PlayAudios("https://getfile.dokpub.com/yandex/get/" + urlForLink + "?alt=media", fragment.getContext(),
                    fragment.getView(), audios.get(Integer.parseInt(strings[0])).getName());
            mediaPlayer = playAudios.getMediaPlayer();
            playAudios.playAndStop();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Загружается...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (progressDialog.isShowing()){
                progressDialog.cancel();
            }
        }
    }

}
