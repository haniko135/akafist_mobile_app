package com.example.akafist.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akafist.ChurchViewModel;
import com.example.akafist.MainActivity;
import com.example.akafist.R;
import com.example.akafist.databinding.FragmentChurchBinding;
import com.example.akafist.models.ServicesModel;
import com.example.akafist.models.SkypesConfs;
import com.example.akafist.models.TypesModel;
import com.example.akafist.recyclers.ServicesRecyclerAdapter;
import com.example.akafist.recyclers.TypesRecyclerAdapter;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChurchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChurchFragment extends Fragment {

    private String date, dateTxt, name;
    private List<TypesModel> typesModelList = new ArrayList<>();
    private MutableLiveData<List<TypesModel>> mutableTypesList = new MutableLiveData<>();
    private List<ServicesModel> servicesModelList = new ArrayList<>();
    private MutableLiveData<List<ServicesModel>> mutableServicesList = new MutableLiveData<>();
    private ViewModelProvider provider;
    public static ServicesRecyclerAdapter servicesRecyclerAdapter;
    public FragmentChurchBinding churchBinding;
    private ChurchViewModel churchViewModel;
    public ChurchFragment() {
        // Required empty public constructor
    }

    public static ChurchFragment newInstance() {
        return new ChurchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString("date");
            dateTxt = getArguments().getString("dateTxt");
            name = getArguments().getString("name");
        }
        provider = new ViewModelProvider(this);
        churchViewModel = provider.get(ChurchViewModel.class);
        if((AppCompatActivity)getActivity() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(dateTxt);
            //getJson();
            churchViewModel.getJson(date);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        churchBinding = FragmentChurchBinding.inflate(inflater, container, false);

        churchBinding.churchDateTxt.setText(dateTxt);
        churchBinding.churchName.setText(name);
        //churchBinding.upRvChurch.setLayoutManager(new GridLayoutManager(getContext(), GridLayoutManager.));
        churchBinding.upRvChurch.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        churchViewModel.getMutableTypesList().observe(getViewLifecycleOwner(), typesModels -> {
            churchBinding.upRvChurch.setAdapter(new TypesRecyclerAdapter(typesModels, this));
        });
        //mutableTypesList.observe(getViewLifecycleOwner(), typesModels -> churchBinding.upRvChurch.setAdapter(new TypesRecyclerAdapter(typesModelList, this)));

        churchBinding.downRvChurch.setLayoutManager(new LinearLayoutManager(getContext()));
       /* mutableServicesList.observe(getViewLifecycleOwner(), servicesModels -> {
            servicesRecyclerAdapter = new ServicesRecyclerAdapter(servicesModelList);
            churchBinding.downRvChurch.setAdapter(servicesRecyclerAdapter);
        });*/

        churchViewModel.getCurId().observe(getViewLifecycleOwner(), integer -> {
           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                servicesRecyclerAdapter = new ServicesRecyclerAdapter(Objects.requireNonNull(churchViewModel.getMutableServicesList().getValue()).stream().filter(servicesModel ->
                        servicesModel.getType() == integer
                ).collect(Collectors.toList()));
                //servicesRecyclerAdapter = new ServicesRecyclerAdapter(churchViewModel.getMutableServicesList().getValue());
                churchBinding.downRvChurch.setAdapter(servicesRecyclerAdapter);
            }*/


            churchViewModel.getMutableServicesList().observe(getViewLifecycleOwner(), servicesModels -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    servicesRecyclerAdapter = new ServicesRecyclerAdapter(servicesModels.stream().filter(servicesModel ->
                        servicesModel.getType() == integer
                    ).collect(Collectors.toList()));
                    churchBinding.downRvChurch.setAdapter(servicesRecyclerAdapter);
                }

            });
        });

        return churchBinding.getRoot();
    }

    private void getJson(){
        String urlToGet = "https://pr.energogroup.org/api/church/"+date;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //получение данных
                urlToGet, null, response -> {
            JSONArray types, services;
            JSONObject jsonObject;
            int id, type;
            String  name;
            try {
                types = response.getJSONArray("types");
                int i = 0;
                while (i < types.length()) {
                    jsonObject = types.getJSONObject(i);
                    id = jsonObject.getInt("id");
                    name = StringEscapeUtils.unescapeJava(jsonObject.getString("name"));
                    typesModelList.add(new TypesModel(id, name));
                    mutableTypesList.setValue(typesModelList);
                    Log.e("PARSING", name);
                    i++;
                }
                i=0;
                services = response.getJSONArray("services");
                while (i < services.length()) {
                    jsonObject = services.getJSONObject(i);
                    id = jsonObject.getInt("id");
                    name = StringEscapeUtils.unescapeJava(jsonObject.getString("name"));
                    type = jsonObject.getInt("type");
                    servicesModelList.add(new ServicesModel(id, name, type));
                    mutableServicesList.setValue(servicesModelList);
                    Log.e("PARSING", name);
                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "akafist_app_1.0.0");
                headers.put("Connection", "keep-alive");
                return headers;
            }

        };
        MainActivity.mRequestQueue.add(request);
    }
}