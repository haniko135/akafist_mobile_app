package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akafist.MainActivity;
import com.example.akafist.databinding.FragmentSkypesBinding;
import com.example.akafist.models.SkypesConfs;
import com.example.akafist.recyclers.SkypesRecyclerAdapter;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkypesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkypesFragment extends Fragment {

    private List<SkypesConfs> skypeModels = new ArrayList<>();
    private List<SkypesConfs> confsModels = new ArrayList<>();
    private MutableLiveData<List<SkypesConfs>> skypesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<SkypesConfs>> confsMutableLiveData = new MutableLiveData<>();
    FragmentSkypesBinding skypesBinding;

    public SkypesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SkypesFragment.
     */
    public static SkypesFragment newInstance() {
        return new SkypesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((AppCompatActivity)getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Конференции по группам");
            getJson();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        skypesBinding = FragmentSkypesBinding.inflate(getLayoutInflater());

        skypesBinding.skypesList.setLayoutManager(new LinearLayoutManager(getContext()));
        skypesMutableLiveData.observe(getViewLifecycleOwner(), skypesConfs -> skypesBinding.skypesList.setAdapter(new SkypesRecyclerAdapter(skypeModels, this)));

        skypesBinding.confsList.setLayoutManager(new LinearLayoutManager(getContext()));
        confsMutableLiveData.observe(getViewLifecycleOwner(), skypesConfs -> skypesBinding.confsList.setAdapter(new SkypesRecyclerAdapter(confsModels ,this)));

        return skypesBinding.getRoot();
    }

    private void getJson(){
        String urlToGet2 = "https://pr.energogroup.org/api/church/skype";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //получение данных
                urlToGet2, null, response -> {
            JSONArray confs, blocks;
            JSONObject jsonObject;
            int id;
            String  name, url;
            try {
                confs = response.getJSONArray("confs");
                int i = 0;
                while (i < confs.length()) {
                    jsonObject = confs.getJSONObject(i);
                    id = jsonObject.getInt("id");
                    name = StringEscapeUtils.unescapeJava(jsonObject.getString("name"));
                    url = StringEscapeUtils.unescapeJava(jsonObject.getString("url"));
                    confsModels.add(new SkypesConfs(id, name, url));
                    confsMutableLiveData.setValue(confsModels);
                    Log.e("PARSING", name);
                    i++;
                }
                i=0;
                blocks = response.getJSONArray("blocks");
                while (i < (blocks).length()) {
                    jsonObject = blocks.getJSONObject(i);
                    id = jsonObject.getInt("id");
                    name = StringEscapeUtils.unescapeJava(jsonObject.getString("name"));
                    skypeModels.add(new SkypesConfs(id, name));
                    skypesMutableLiveData.setValue(skypeModels);
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