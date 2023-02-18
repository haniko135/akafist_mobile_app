package com.example.akafist.viewmodel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akafist.MainActivity;
import com.example.akafist.R;
import com.example.akafist.fragments.LinksFragment;
import com.example.akafist.models.LinksModel;
import com.example.akafist.service.DownloadFromYandexTask;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LinksViewModel extends ViewModel {
    private List<LinksModel> linksModelList = new ArrayList<>();
    private MutableLiveData<List<LinksModel>> mutableLinksDate = new MutableLiveData<>();
    private List<LinksModel> downloadAudio = new ArrayList<>();
    private OneTimeWorkRequest workRequest;

    public MutableLiveData<List<LinksModel>> getMutableLinksDate() {
        return mutableLinksDate;
    }

    public void getJson(String cas, LayoutInflater inflater){
        if (cas.equals("links")) {
            String urlToGet = "https://pr.energogroup.org/api/church/talks";

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, //получение данных
                    urlToGet, null, response -> {
                JSONObject jsonObject;
                int id;
                String url, name;
                try {
                    int i = 0;
                    while (i < response.length()) {
                        jsonObject = response.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        name = StringEscapeUtils.unescapeJava(jsonObject.getString("name"));
                        url = StringEscapeUtils.unescapeJava(jsonObject.getString("url"));
                        linksModelList.add(new LinksModel(id, url, name));
                        mutableLinksDate.setValue(linksModelList);
                        Log.e("PARSING", name);
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
        }
        else if(cas.equals("molitvyOfflain")){
            List<String> molitvyName = Arrays.asList(inflater.getContext().getResources().getStringArray(R.array.molitvy_offline_audio_name));
            List<String> molitvyLink = Arrays.asList(inflater.getContext().getResources().getStringArray(R.array.molitvy_offline_audio_link));
            for (int i=0; i<molitvyName.size(); i++){
                linksModelList.add(new LinksModel(molitvyLink.get(i), molitvyName.get(i)));
            }
            mutableLinksDate.setValue(linksModelList);
        }
    }

    public void retryGetJson(String cas, LayoutInflater inflater){
        if (cas.equals("links")) {
            linksModelList = new ArrayList<>();
            getJson(cas, inflater);
        }else if(cas.equals("molitvyOfflain")){
            linksModelList = new ArrayList<>();
            getJson(cas, inflater);
        }
    }

    public void getLinkDownload(String url, LayoutInflater inflater, ViewGroup container, String audioFilesDir) {
        String urlToGet = "https://cloud-api.yandex.net/v1/disk/public/resources?public_key=" + url;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //получение данных
                urlToGet, null, response -> {
            String resName, resLink;
            try {
                resName = response.getString("name");
                Log.i("YANDEX",resName);

                File newFile = new File(audioFilesDir  + "/"+ resName);

                if(!newFile.exists()) {
                    resLink = response.getString("file");
                    Data data = new Data.Builder().putString("URL", resLink)
                            .putString("FILENAME", resName)
                            .putString("FILE_DIR", audioFilesDir).build();
                    workRequest = new OneTimeWorkRequest.Builder(DownloadFromYandexTask.class)
                            .setInputData(data).build();
                    WorkManager.getInstance(inflater.getContext()).enqueue(workRequest);

                    WorkManager.getInstance(inflater.getContext()).getWorkInfoByIdLiveData(workRequest.getId())
                            .observe(Objects.requireNonNull(ViewTreeLifecycleOwner.get(container.getRootView().getRootView())), workInfo -> {
                                if(workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED){
                                    String audioName = workInfo.getOutputData().getString("AUDIO_NAME");
                                    String audioLink = workInfo.getOutputData().getString("AUDIO_LINK");
                                    //downloadAudio.add(new LinksModel(audioName, audioLink));
                                    Log.i("YANDEX", "Download file: " + audioName + ", " + audioLink);
                                }else{
                                    Log.i("YANDEX", "Is not yet");
                                }
                            });

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

    public List<LinksModel> getDownload(String audioFilesDir){
        downloadAudio.clear();
        String fullPath = audioFilesDir+"/";
        File directory = new File(fullPath);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            downloadAudio.add(new LinksModel(fullPath+files[i].getName(), files[i].getName()));
        }
        return downloadAudio;
    }
}
