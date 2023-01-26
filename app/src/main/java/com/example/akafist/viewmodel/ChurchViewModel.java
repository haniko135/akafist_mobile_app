package com.example.akafist.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akafist.MainActivity;
import com.example.akafist.models.ServicesModel;
import com.example.akafist.models.TypesModel;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChurchViewModel extends ViewModel{
    private List<TypesModel> typesModelList = new ArrayList<>();
    private MutableLiveData<List<TypesModel>> mutableTypesList = new MutableLiveData<>();
    private List<ServicesModel> servicesModelList = new ArrayList<>();
    private MutableLiveData<List<ServicesModel>> mutableServicesList = new MutableLiveData<>();
    private MutableLiveData<Integer> curId = new MutableLiveData<>();

    public void setCurId(int id){
        curId.setValue(id);
    }

    public MutableLiveData<List<TypesModel>> getMutableTypesList() {
        return mutableTypesList;
    }

    public List<TypesModel> getTypesModelList() {
        return typesModelList;
    }

    public MutableLiveData<Integer> getCurId() {
        return curId;
    }

    public MutableLiveData<List<ServicesModel>> getMutableServicesList() {
        return mutableServicesList;
    }

    public void getJson(String date){
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
