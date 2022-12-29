package com.example.akafist.service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.akafist.R;
import com.example.akafist.databinding.FragmentLinksBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;

public class DownloadFromYandexTask extends AsyncTask<String,String,String> {

    FragmentLinksBinding binding;
    public File outFile;

    private String token = "y0_AgAAAABUVpeiAADLWwAAAADXqEoa0KX1_myOSvS6tU-k0yc2A_S4C7o";

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL urlDownload = new URL(strings[0]);
            HttpsURLConnection downConn = (HttpsURLConnection) urlDownload.openConnection();
            downConn.setRequestMethod("GET");
            downConn.setRequestProperty("Authorization: OAuth ",token);
            downConn.setRequestProperty("User-Agent","akafist_app/1.0.0");
            downConn.setRequestProperty("Connection", "keep-alive");
            downConn.setConnectTimeout(5000);
            //downConn.setReadTimeout(1000);
            downConn.connect();

            File androidStorage;

            androidStorage = new File( strings[2] + "/links_records");
            Log.i("FILES_AND_STORAGE", androidStorage.getPath());
            if(!androidStorage.exists()){
                androidStorage.mkdir();
                Log.i("FILES_AND_STORAGE", "Directory created");
            }

            String downloadName = strings[1];
            outFile = new File(androidStorage, downloadName);
            Log.i("FILES_AND_STORAGE", outFile.getPath());
            if(!outFile.exists()){
                outFile.createNewFile();
                Log.i("FILES_AND_STORAGE", "File created");
            }

            FileOutputStream fos = new FileOutputStream(outFile);
            InputStream is = downConn.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;//init length
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }

            fos.close();
            is.close();
            downConn.disconnect();

            Log.i("FILES_AND_STORAGE", "Download complete");

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FILES_AND_STORAGE", "Download Error Exception " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            if (outFile == null) {
                binding.textView15.setText(R.string.failDownload);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.textView15.setEnabled(true);
                        binding.textView15.setText(R.string.againDownload);
                    }
                }, 2000);

                Log.e("FILES_AND_STORAGE", "Download Failed");

            }
        } catch (Exception e) {
            e.printStackTrace();
            binding.textView15.setText(R.string.failDownload);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.textView15.setEnabled(true);
                    binding.textView15.setText(R.string.againDownload);
                }
            }, 3000);
            Log.e("FILES_AND_STORAGE", "Download Failed with Exception - " + e.getLocalizedMessage());

        }

        super.onPostExecute(s);
    }
}
