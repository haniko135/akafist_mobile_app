package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akafist.MainActivity;
import com.example.akafist.databinding.FragmentSkypesBlocksBinding;
import com.example.akafist.models.SkypesConfs;
import com.example.akafist.recyclers.SkypesGridRecyclerAdapter;

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
 * Use the {@link SkypesBlocksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkypesBlocksFragment extends Fragment {

    private List<SkypesConfs> groupBlocks = new ArrayList<>();
    private MutableLiveData<List<SkypesConfs>> mutableGroupBlocks = new MutableLiveData<>();
    private String nameTitle;
    private int urlId;

    public FragmentSkypesBlocksBinding skypesBlocksBinding;

    public SkypesBlocksFragment() {
        // Required empty public constructor
    }

    public static SkypesBlocksFragment newInstance() {
        return new SkypesBlocksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nameTitle = getArguments().getString("nameTitle");
            urlId = getArguments().getInt("urlId");
        }
        if((AppCompatActivity)getActivity() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(nameTitle);
            getJson(urlId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        skypesBlocksBinding = FragmentSkypesBlocksBinding.inflate(inflater, container, false);

        skypesBlocksBinding.groupBlocks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mutableGroupBlocks.observe(getViewLifecycleOwner(), view -> skypesBlocksBinding.groupBlocks.setAdapter(new SkypesGridRecyclerAdapter(groupBlocks)));

        return skypesBlocksBinding.getRoot();
    }

    private void getJson(int urlId){
        String urlToGet = "https://pr.energogroup.org/api/church/skype/"+urlId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //получение данных
                urlToGet, null, response -> {
            JSONArray confs;
            JSONObject jsonObject;
            int id;
            String  name, url;
            try {
                confs = response.getJSONArray("confs");
                int i = 0;
                while (i <= confs.length()) {
                    jsonObject = confs.getJSONObject(i);
                    id = jsonObject.getInt("id");
                    name = StringEscapeUtils.unescapeJava(jsonObject.getString("name"));
                    url = StringEscapeUtils.unescapeJava(jsonObject.getString("url"));
                    groupBlocks.add(new SkypesConfs(id, name, url));
                    mutableGroupBlocks.setValue(groupBlocks);
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