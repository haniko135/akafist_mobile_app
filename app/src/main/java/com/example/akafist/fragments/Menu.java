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
        getJson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        menuBinding = FragmentMenuBinding.inflate(getLayoutInflater());

        blocksModelList.add(new HomeBlocksModel("skypeConfs", "Онлайн конференции"));
        blocksModelList.add(new HomeBlocksModel("onlineMichael", "Онлайн-трансляция общины арх. Михаила"));
        blocksModelList.add(new HomeBlocksModel("onlineVarvara", "Онлайн-трансляция общины вмц. Варвары"));
        blocksModelList.add(new HomeBlocksModel("molitvyOfflain", "Молитвы оффлайн"));
        blocksModelList.add(new HomeBlocksModel("links", "Записи просветительских бесед"));
        blocksModelList.add(new HomeBlocksModel("notes", "Подать записку онлайн"));
        blocksModelList.add(new HomeBlocksModel("talks", "Задать вопрос Священнику"));

        Menu fr = this;
        menuBinding.menuList.setLayoutManager(new LinearLayoutManager(getContext()));
        mutableLiveData.observe(getViewLifecycleOwner(), homeBlocksModels -> menuBinding.menuList.setAdapter(new MenuRecyclerAdapter(blocksModelList,fr)));

        /*

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //конференции в скайпе
        view.findViewById(R.id.skype_confs).setOnClickListener(view1 -> FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_skypesFragment));

        //прямая трансляция из храма Михаила
        view.findViewById(R.id.online_Michael).setOnClickListener(view12 -> {
            Bundle bundle = new Bundle();
            bundle.putString("urlToSound", "http://radio.zakonbozhiy.ru:8000/live.mp3");
            bundle.putString("soundTitle", "Трансляция арх. Михаил");
            FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
        });

        //прямая трансляция из храма Варвары
        view.findViewById(R.id.online_Varvara).setOnClickListener(view13 -> {
            Bundle bundle = new Bundle();
            bundle.putString("urlToSound", "http://radio.zakonbozhiy.ru:8010/kem.mp3");
            bundle.putString("soundTitle", "Трансляция св. Варвара");
            FragmentKt.findNavController(getParentFragment()).navigate(R.id.onlineTempleFragment, bundle);
        });

        //аудио-молитвы оффлайн
        view.findViewById(R.id.offline_molitvy).setOnClickListener(view14 -> FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_molitvyOfflineFragment));

        //ссылка на беседы
        view.findViewById(R.id.links).setOnClickListener(view15 -> FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_linksFragment));

        //подача записок
        view.findViewById(R.id.notes).setOnClickListener(view16 -> {
            Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pr.energogroup.org/notes/note/add"));
            startActivity(toSite);
        });

        //задать вопрос
        view.findViewById(R.id.talks).setOnClickListener(view17 -> {
            Intent toSite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pr.energogroup.org/talks/talk"));
            startActivity(toSite);
        });

        //ежедневный храм михаила
        view.findViewById(R.id.day_Michael).setOnClickListener(view18 -> Toast.makeText(getContext(), "day_Michael", Toast.LENGTH_SHORT).show());

        //ежедневные молитвы
        view.findViewById(R.id.everyday_title).setOnClickListener(view19 -> FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_everydayFragment));


        //псалтырь
        view.findViewById(R.id.psaltir_title).setOnClickListener(view110 -> FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_psaltirFragment));

        //молитвы
        view.findViewById(R.id.molitvy_title).setOnClickListener(view111 -> FragmentKt.findNavController(getParentFragment()).navigate(R.id.action_menu_to_needsFragment));
*/
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