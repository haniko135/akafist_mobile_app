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
    AppCompatActivity fragActivity;

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
                fragActivity = (AppCompatActivity)getActivity();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.home_title));
                homeBlocksModels.add(new HomeBlocksModel("skypeConfs", "Онлайн конференции", "для групп"));
                homeBlocksModels.add(new HomeBlocksModel("onlineMichael", "Онлайн-трансляция", "общины арх. Михаила"));
                homeBlocksModels.add(new HomeBlocksModel("onlineVarvara", "Онлайн-трансляция", "общины вмц. Варвары"));
                homeBlocksModels.add(new HomeBlocksModel("molitvyOfflain", "Молитвы", "оффлайн"));
                homeBlocksModels.add(new HomeBlocksModel("links", "Записи", "просветительских бесед"));
                homeBlocksModels.add(new HomeBlocksModel("notes", "Подать записку", "онлайн"));
                homeBlocksModels.add(new HomeBlocksModel("talks", "Задать вопрос", "Священнику или в Духовный Блок"));
                getJson();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (fragActivity != null){
            fragActivity.getSupportActionBar().setTitle(getResources().getString(R.string.home_title));
        }

        homeBinding = FragmentHomeBinding.inflate(getLayoutInflater());

        Home fr = this;
        homeBinding.homeRv.setLayoutManager(new LinearLayoutManager(getContext()));
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