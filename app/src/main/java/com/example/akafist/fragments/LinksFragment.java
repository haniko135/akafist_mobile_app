package com.example.akafist.fragments;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinksFragment extends Fragment {

    private final String secToken = "y0_AgAAAABUVpeiAADLWwAAAADXqEoa0KX1_myOSvS6tU-k0yc2A_S4C7o";
    public RequestQueue mRequestQueue;
    private MediaPlayer mediaPlayer;
    private ImageButton playStopButton;
    private boolean isChecked;
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

        binding.warningToUser.setVisibility(View.VISIBLE);
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
        });


        mRequestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        binding.imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAndStop();
            }
        });

        binding.links1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getLink("https://disk.yandex.ru/d/kirIe36-Zxb2Bg"); //https://disk.yandex.ru/d/kirIe36-Zxb2Bg  https://disk.yandex.ru/d/PbvK1eWqBS9J3A
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.links2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getLink("https://disk.yandex.ru/d/3ZhWMsKDeRj9Kw");
                } catch (MalformedURLException e){
                    e.printStackTrace();
                }
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

    public void getLink(String url) throws MalformedURLException {
        String urlToGet = "https://cloud-api.yandex.net/v1/disk/public/resources?public_key=" + url;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //получение данных
                urlToGet, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String resName;
                String resLink;
                try {
                    resName = response.getString("name");
                    Log.i("YANDEX",resName);
                    TextView textView;
                    textView = getActivity().findViewById(R.id.textView2);
                    textView.setText(resName);

                    resLink = response.getString("file");

                    new DownloadFromYandexTask().execute(resLink, resName, getContext().getCacheDir().getPath());
                    Log.i("YANDEX",getContext().getCacheDir().getPath());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            play(getContext().getCacheDir().getPath()+"/links_records/"+resName);
                        }
                    }, 5000);

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


    @SuppressLint("ClickableViewAccessibility")
    public void play(String name){
        mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(name));

        SeekBar seekBar = getView().findViewById(R.id.durationBarMolitvy);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mediaPlayer.isPlaying()){
                    SeekBar sb = (SeekBar)v;
                    mediaPlayer.seekTo(sb.getProgress());
                }
                return false;
            }
        });
        playAndStop();
    }

    public void playAndStop(){
        if (!mediaPlayer.isPlaying()) {
            playStopButton = getView().findViewById(R.id.imageButtonPlay);
            playStopButton.setImageResource(android.R.drawable.ic_media_pause);
            mediaPlayer.start();
        }else {
            playStopButton = getView().findViewById(R.id.imageButtonPlay);
            playStopButton.setImageResource(android.R.drawable.ic_media_play);
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}