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


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.akafist.MainActivity;
import com.example.akafist.R;
import com.example.akafist.databinding.FragmentMenuBinding;
import com.example.akafist.models.HomeBlocksModel;
import com.example.akafist.recyclers.MenuRecyclerAdapter;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu extends Fragment {

    private List<HomeBlocksModel> blocksModelList = new ArrayList<>();
    private MutableLiveData<List<HomeBlocksModel>> mutableLiveData = new MutableLiveData<>();
    public FragmentMenuBinding menuBinding;

    public Menu() {
        // Required empty public constructor
    }

    public static Menu newInstance() {
        return new Menu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Меню");
        blocksModelList.add(new HomeBlocksModel("skypeConfs", "Онлайн конференции"));
        blocksModelList.add(new HomeBlocksModel("onlineMichael", "Онлайн-трансляция общины арх. Михаила"));
        blocksModelList.add(new HomeBlocksModel("onlineVarvara", "Онлайн-трансляция общины вмц. Варвары"));
        blocksModelList.add(new HomeBlocksModel("molitvyOfflain", "Молитвы оффлайн"));
        blocksModelList.add(new HomeBlocksModel("links", "Записи просветительских бесед"));
        blocksModelList.add(new HomeBlocksModel("notes", "Подать записку онлайн"));
        blocksModelList.add(new HomeBlocksModel("talks", "Задать вопрос Священнику"));
        getJson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        menuBinding = FragmentMenuBinding.inflate(getLayoutInflater());

        Menu fr = this;
        menuBinding.menuList.setLayoutManager(new LinearLayoutManager(getContext()));
        mutableLiveData.observe(getViewLifecycleOwner(), homeBlocksModels -> menuBinding.menuList.setAdapter(new MenuRecyclerAdapter(blocksModelList,fr)));

        return menuBinding.getRoot();
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
                    String finalDate = date;
                    String finalDateTxt = dateTxt;
                    Log.e("date", finalDate);
                    blocksModelList.add(new HomeBlocksModel(finalDate, finalDateTxt));
                    mutableLiveData.setValue(blocksModelList);
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