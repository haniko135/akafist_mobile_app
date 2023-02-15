package com.example.akafist.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.akafist.MainActivity;
import com.example.akafist.models.HomeBlocksModel;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuViewModel extends ViewModel {
    private List<HomeBlocksModel> blocksModelList = new ArrayList<>();
    private MutableLiveData<List<HomeBlocksModel>> mutableLiveData = new MutableLiveData<>();
    private List<HomeBlocksModel> cashedBlocksModelList = new ArrayList<>();

    public List<HomeBlocksModel> getBlocksModelList() {
        return blocksModelList;
    }

    public List<HomeBlocksModel> getCashedBlocksModelList() {
        return cashedBlocksModelList;
    }

    public MutableLiveData<List<HomeBlocksModel>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void firstSet(){
        blocksModelList.add(new HomeBlocksModel("skypeConfs", "Онлайн конференции", "для групп"));
        blocksModelList.add(new HomeBlocksModel("onlineMichael", "Онлайн-трансляция", "общины арх. Михаила"));
        blocksModelList.add(new HomeBlocksModel("onlineVarvara", "Онлайн-трансляция", "общины вмц. Варвары"));
        blocksModelList.add(new HomeBlocksModel("molitvyOfflain", "Молитвы", "оффлайн"));
        blocksModelList.add(new HomeBlocksModel("links", "Записи", "просветительских бесед"));
        blocksModelList.add(new HomeBlocksModel("notes", "Подать записку", "онлайн"));
        blocksModelList.add(new HomeBlocksModel("talks", "Задать вопрос", "Священнику или в Духовный Блок"));
        mutableLiveData.setValue(blocksModelList);
    }

    public void getJson(String cas){
        String urlToGet = "https://pr.energogroup.org/api/church/";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                urlToGet, null, response -> {
            JSONObject jsonObject;
            String dateTxt, date, name;
            try {
                int i = 0;
                while (i <= response.length()-1) {
                    jsonObject = response.getJSONObject(i);
                    if(cas.equals("menu")) {
                        date = jsonObject.getString("date");
                        dateTxt = StringEscapeUtils.unescapeJava(jsonObject.getString("dateTxt"));
                        blocksModelList.add(new HomeBlocksModel(date, dateTxt));
                        mutableLiveData.setValue(blocksModelList);
                    }else {
                        date = jsonObject.getString("date");
                        dateTxt = StringEscapeUtils.unescapeJava(jsonObject.getString("dateTxt"));
                        name = StringEscapeUtils.unescapeJava(jsonObject.getString("name"));
                        blocksModelList.add(new HomeBlocksModel(date, dateTxt, name));
                        mutableLiveData.setValue(blocksModelList);
                    }
                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "akafist_app_1.0.0");
                headers.put("Connection", "keep-alive");
                return headers;
            }

        };
        MainActivity.mRequestQueue.add(request);

        cashedBlocksModelList = mutableLiveData.getValue();
    }
}
