package com.example.akafist.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;


import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.akafist.AkafistApplication;
import com.example.akafist.R;
import com.example.akafist.databinding.FragmentLinksBinding;
import com.example.akafist.service.DownloadFromYandexTask;
import com.example.akafist.service.PlayAudios;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinksFragment extends Fragment {

    private final String secToken = "y0_AgAAAABUVpeiAADLWwAAAADXqEoa0KX1_myOSvS6tU-k0yc2A_S4C7o";
    private String audioFilesDir;
    public RequestQueue mRequestQueue;
    private MediaPlayer mediaPlayer;
    private PlayAudios playAudios;
    private String urlForLink;
    private boolean isChecked; //для пользовательского соглашения
    FragmentLinksBinding binding;

    public LinksFragment() {
        // Required empty public constructor
    }

    public static LinksFragment newInstance() {
        return new LinksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Записи бесед");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLinksBinding.inflate(inflater,container,false);

        audioFilesDir = getContext().getFilesDir().getPath();

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);


        //пользовательское соглашение
        /*binding.warningToUser.setVisibility(View.VISIBLE);
        binding.molitvyPlayer.setVisibility(View.INVISIBLE);
        binding.textView2.setVisibility(View.INVISIBLE);
        binding.links1.setVisibility(View.INVISIBLE);
        binding.links2.setVisibility(View.INVISIBLE);
        binding.linksRoot.setBackgroundColor(getResources().getColor(R.color.greyGrad));

        binding.warningToUserYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.molitvyPlayer.setVisibility(View.VISIBLE);
                binding.textView2.setVisibility(View.VISIBLE);
                binding.links1.setVisibility(View.VISIBLE);
                binding.links2.setVisibility(View.VISIBLE);
                binding.warningToUser.setVisibility(View.INVISIBLE);
                binding.linksRoot.setBackgroundColor(getResources().getColor(R.color.white));
                isChecked = true;
            }
        });
        binding.warningToUserNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChecked = false;
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_linksFragment_to_home2);
            }
        });*/


        mRequestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        binding.imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudios.playAndStop();
            }
        });

        binding.downloadLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getLink(urlForLink, inflater, container); //https://disk.yandex.ru/d/kirIe36-Zxb2Bg  https://disk.yandex.ru/d/PbvK1eWqBS9J3A
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.links1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPlaying();
                Log.i("YANDEX", String.valueOf(connected));
                if(connected) {
                    urlForLink = "https://disk.yandex.ru/d/kirIe36-Zxb2Bg";
                    playAudios = new PlayAudios("https://getfile.dokpub.com/yandex/get/" + urlForLink + "?alt=media", getContext(), getView());
                    mediaPlayer = playAudios.getMediaPlayer();
                    playAudios.playAndStop();
                    binding.downloadLinkButton.setVisibility(View.VISIBLE);
                }else {
                    playAudios = new PlayAudios(getContext().getFilesDir().getPath() + "/links_records/Hellevator.mp3", getContext(), getView());
                }

            }
        });

        binding.links2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlForLink = "https://disk.yandex.ru/d/EKNGIvRoIXAiLw";
                checkPlaying();
                playAudios = new PlayAudios("https://getfile.dokpub.com/yandex/get/"+urlForLink+"?alt=media", getContext(),getView());
                mediaPlayer = playAudios.getMediaPlayer();
                playAudios.playAndStop();
                binding.downloadLinkButton.setVisibility(View.VISIBLE);
            }
        });


        return binding.getRoot();
    }

    public void checkPlaying(){
        if (mediaPlayer != null)
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public void getLink(String url, LayoutInflater inflater, ViewGroup container) throws MalformedURLException {
        String urlToGet = "https://cloud-api.yandex.net/v1/disk/public/resources?public_key=" + url;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //получение данных
                urlToGet, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String resName, resLink;
                try {
                    resName = response.getString("name");
                    Log.i("YANDEX",resName);

                    File newFile = new File(audioFilesDir  + "/links_records/"+ resName);

                   if(!newFile.exists()) {
                        resLink = response.getString("file");
                        new DownloadFromYandexTask(inflater,container).execute(resLink, resName, audioFilesDir);
                        Log.i("YANDEX",audioFilesDir);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // в случае возникновеня ошибки
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization: Bearer ", secToken);
                headers.put("User-Agent", "akafist_app_1.0.0");
                headers.put("Connection", "keep-alive");
                return headers;
            }

        };
        mRequestQueue.add(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        playAudios.destroyPlayAudios();
    }


}