package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.akafist.MainActivity;
import com.example.akafist.R;
import com.example.akafist.databinding.FragmentSkypesBinding;
import com.example.akafist.models.HomeBlocksModel;
import com.example.akafist.models.SkypesConfs;
import com.example.akafist.recyclers.SkypesRecyclerAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkypesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkypesFragment extends Fragment {

    private List<HomeBlocksModel> homeBlocksModels;
    public RequestQueue mRequestQueue;
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Конференции по группам");

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_skypesFragment_to_home2);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //mRequestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        skypesBinding = FragmentSkypesBinding.inflate(getLayoutInflater());

        skypesBinding.skypesList.setLayoutManager(new LinearLayoutManager(getContext()));
        skypesBinding.skypesList.setAdapter(new SkypesRecyclerAdapter(setSkypeConfs()));

        skypesBinding.forTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJson();
            }
        });

        return skypesBinding.getRoot();
    }

    private List<SkypesConfs> setSkypeConfs(){
        List<String> blockNames = Arrays.asList(getResources().getStringArray(R.array.skype_confs_list_names));
        List<String> skypeLinks = Arrays.asList(getResources().getStringArray(R.array.skype_confs_list_links_fake));
        List<SkypesConfs> skypesConfs = new ArrayList<>();
        for (int i = 0; i < blockNames.size(); i++){
            skypesConfs.add(new SkypesConfs(blockNames.get(i), skypeLinks.get(i)));
        }
        return skypesConfs;
    }

    private void getJson(){
        String urlToGet = "https://pr.energogroup.org/api/church/";
        String urlToGet2 = "https://pr.energogroup.org/api/church/skype";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, //получение данных
                urlToGet, null, response -> {
            JSONObject jsonObject;
            String dateTxt, date, name;
            homeBlocksModels = new ArrayList<>();
            try {
                int i = response.length()-1;
                while (i > 0) {
                    jsonObject = response.getJSONObject(i);
                    date = jsonObject.getString("date");
                    dateTxt = StringEscapeUtils.unescapeJava(jsonObject.getString("dateTxt"));
                    name = StringEscapeUtils.unescapeJava(jsonObject.getString("name"));
                    homeBlocksModels.add(new HomeBlocksModel(date, dateTxt, name));
                    Log.e("PARSING", dateTxt);
                    i--;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "akafist_app_1.0.0");
                headers.put("Connection", "keep-alive");
                return headers;
            }

        };

        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, //получение данных
                urlToGet2, null, response -> {
            String confs, confsAfter, blocks, blocksAfter;
            try {
                confs = response.getString("confs");
                confsAfter = StringEscapeUtils.unescapeJava(confs);
                Log.e("PARSING", confsAfter);
                blocks = response.getString("blocks");
                blocksAfter = StringEscapeUtils.unescapeJava(blocks);
                Log.e("PARSING", blocksAfter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "akafist_app_1.0.0");
                headers.put("Connection", "keep-alive");
                return headers;
            }

        };

        MainActivity.mRequestQueue.add(request);
        MainActivity.mRequestQueue.add(request2);
    }

}