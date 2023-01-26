package com.example.akafist.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akafist.MainActivity;
import com.example.akafist.models.PrayersModels;
import com.example.akafist.models.ServicesModel;
import com.example.akafist.models.TypesModel;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class PrayerViewModel extends ViewModel {

    private PrayersModels prayersModel;
    private MutableLiveData<PrayersModels> prayersModelsMutableLiveData = new MutableLiveData<>();

    public PrayersModels getPrayersModel() {
        return prayersModel;
    }

    public MutableLiveData<PrayersModels> getPrayersModelsMutableLiveData() {
        return prayersModelsMutableLiveData;
    }

    public void getJson(String date, int id){
        String urlToGet = "https://pr.energogroup.org/api/church/"+date+"/"+id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                urlToGet, null, response -> {
            int prev, next;
            String  name, html;
            try {
                name = StringEscapeUtils.unescapeJava(response.getString("name"));
                html = StringEscapeUtils.unescapeJava(response.getString("html"));
                prev = response.getInt("prev");
                next = response.getInt("next");
                prayersModel = new PrayersModels(name,html,prev, next);
                prayersModelsMutableLiveData.setValue(prayersModel);
                Log.e("PARSING", name);

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
