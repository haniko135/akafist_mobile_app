package com.example.akafist.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.akafist.MainActivity;
import com.example.akafist.R;
import com.example.akafist.databinding.FragmentHomeBinding;
import com.example.akafist.models.HomeBlocksModel;
import com.example.akafist.recyclers.HomeRecyclerAdapter;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private List<HomeBlocksModel> homeBlocksModels = new ArrayList<>();
    private MutableLiveData<List<HomeBlocksModel>> mutableLiveData = new MutableLiveData<>();
    private String tag = "HOME";
    public FragmentHomeBinding homeBinding;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance() {
        return new Home();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((AppCompatActivity)getActivity() != null) {
            if (((AppCompatActivity)getActivity()).getSupportActionBar() != null){
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Дом");
                getJson();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(getLayoutInflater());

        //конференции в скайп
        homeBinding.skypeConfsBlock.setOnClickListener(view1 -> {
            FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_skypesFragment);
        });

        //прямая трансляция из храма Михаила
        homeBinding.onlineMichaelBlock.setOnClickListener(view12 -> {
            Bundle bundle = new Bundle();
            bundle.putString("urlToSound", "http://radio.zakonbozhiy.ru:8000/live.mp3");
            bundle.putString("soundTitle", "Трансляция арх. Михаил");
            FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
        });

        //прямая транслция из храма Варвары
        homeBinding.onlineVarvaraBlock.setOnClickListener(view13 -> {
            Bundle bundle = new Bundle();
            bundle.putString("urlToSound", "http://radio.zakonbozhiy.ru:8010/kem.mp3");
            bundle.putString("soundTitle", "Трансляция св. Варвара");
            FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
        });

        //аудио молитвы оффлайн
        homeBinding.molitvyOfflainBlock.setOnClickListener(view14 -> FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_molitvyOfflineFragment));

        //записи бесед
        homeBinding.linksBlock.setOnClickListener(view15 -> FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_home2_to_linksFragment));

        //подача записок
        homeBinding.notesBlock.setOnClickListener(view16 -> {
            Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pr.energogroup.org/notes/note/add"));
            startActivity(toSite);
        });

        //задать вопрос
        homeBinding.talksBlock.setOnClickListener(view17 -> {
            Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pr.energogroup.org/talks/talk"));
            startActivity(toSite);
        });

        Home fr = this;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //linearLayoutManager.setReverseLayout(false);
        homeBinding.homeRv.setLayoutManager(linearLayoutManager);
        mutableLiveData.observe(getViewLifecycleOwner(), homeBlocksModels -> homeBinding.homeRv.setAdapter(new HomeRecyclerAdapter(homeBlocksModels, fr)));

        return homeBinding.getRoot();
    }

    private void getJson(){
        String urlToGet = "https://pr.energogroup.org/api/church/";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, //получение данных
                urlToGet, null, response -> {
            JSONObject jsonObject;
            String dateTxt, date, name;
            try {
                int i = 0;
                while (i <= response.length()-1) {
                    jsonObject = response.getJSONObject(i);
                    date = jsonObject.getString("date");
                    dateTxt = StringEscapeUtils.unescapeJava(jsonObject.getString("dateTxt"));
                    name = StringEscapeUtils.unescapeJava(jsonObject.getString("name"));
                    String finalDate = date;
                    String finalDateTxt = dateTxt;
                    String finalName = name;
                    Log.e("date", finalDate);
                    homeBlocksModels.add(new HomeBlocksModel(finalDate, finalDateTxt, finalName));
                    mutableLiveData.setValue(homeBlocksModels);
                    Log.e("PARSING", dateTxt);
                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "akafist_app_1.0.0");
                headers.put("Connection", "keep-alive");
                return headers;
            }

        };
        MainActivity.mRequestQueue.add(request);
    }
}