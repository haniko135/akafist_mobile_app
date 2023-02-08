package com.example.akafist.viewmodel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akafist.MainActivity;
import com.example.akafist.models.LinksModel;
import com.example.akafist.service.DownloadFromYandexTask;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinksViewModel extends ViewModel {
    private List<LinksModel> linksModelList = new ArrayList<>();
    private MutableLiveData<List<LinksModel>> mutableLinksDate = new MutableLiveData<>();

    public void getLinkDownload(String url, LayoutInflater inflater, ViewGroup container, String audioFilesDir) {
        String urlToGet = "https://cloud-api.yandex.net/v1/disk/public/resources?public_key=" + url;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //получение данных
                urlToGet, null, response -> {
            String resName, resLink;
            try {
                resName = response.getString("name");
                Log.i("YANDEX",resName);

                File newFile = new File(audioFilesDir  + "/links_records/"+ resName);

                if(!newFile.exists()) {
                    resLink = response.getString("file");
                    new DownloadFromYandexTask(inflater,container).execute(resLink, resName, audioFilesDir);
                    Log.i("YANDEX",audioFilesDir);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization: Bearer ", MainActivity.secToken);
                headers.put("User-Agent", "akafist_app_1.0.0");
                headers.put("Connection", "keep-alive");
                return headers;
            }

        };
        MainActivity.mRequestQueue.add(request);
    }
}
