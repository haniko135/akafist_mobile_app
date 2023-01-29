package com.example.akafist.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.akafist.R;
import com.example.akafist.databinding.FragmentLinksBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadFromYandexTask extends AsyncTask<String,String,String> {

    FragmentLinksBinding binding;
    public File outFile;

    private final String tag = "FILES_AND_STORAGE";
    private final String CHANNEL_ID = "downloadNote";
    private final int NOTIFICATION_ID = 101;

    public DownloadFromYandexTask(LayoutInflater inflater, ViewGroup viewGroup){
        this.binding = FragmentLinksBinding.inflate(inflater,viewGroup,false);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL urlDownload = new URL(strings[0]);
            HttpsURLConnection downConn = (HttpsURLConnection) urlDownload.openConnection();
            downConn.setRequestMethod("GET");
            String token = "y0_AgAAAABUVpeiAADLWwAAAADXqEoa0KX1_myOSvS6tU-k0yc2A_S4C7o";
            downConn.setRequestProperty("Authorization: OAuth ", token);
            downConn.setRequestProperty("User-Agent","akafist_app/1.0.0");
            downConn.setRequestProperty("Connection", "keep-alive");
            downConn.setConnectTimeout(5000);
            //downConn.setReadTimeout(1000);
            downConn.connect();

            if(downConn.getResponseCode() == 200){

                File androidStorage;

                androidStorage = new File( strings[2] + "/links_records");
                Log.i(tag, androidStorage.getPath());
                if(!androidStorage.exists()){
                    androidStorage.mkdir();
                    Log.i(tag, "Directory created");
                }

                String downloadName = strings[1];
                outFile = new File(androidStorage, downloadName);
                Log.i("FILES_AND_STORAGE", outFile.getPath());
                if(!outFile.exists()){
                    outFile.createNewFile();
                    Log.i(tag, "File created");
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

                Log.i(tag, "Download complete");
            } else if (downConn.getResponseCode() == 403) {
                Log.i(tag, "Token is invalid");
            } else if (downConn.getResponseCode() == 404){
                Log.i(tag, "Resource not found");
            } else if(downConn.getResponseCode() == 406){
                Log.i(tag, "Invalid response format");
            } else if(downConn.getResponseCode() == 413) {
                Log.i(tag, "Too big file. Can't download");
            } else if (downConn.getResponseCode() == 503){
                Log.i(tag, "Server's error");
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(tag, "Download Error Exception " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        createNotificationChannel();

        try {
            if (outFile == null) {
                //Toast.makeText(binding.getRoot().getContext(),R.string.failDownload, Toast.LENGTH_LONG).show();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(binding.getRoot().getContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_download_24)
                        .setContentTitle("Помощник чтеца")
                        .setContentText("Ошибка при скачивании")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(binding.getRoot().getContext());
                managerCompat.notify(NOTIFICATION_ID, builder.build());

                new Handler().postDelayed(() -> {
                    //Toast.makeText(binding.getRoot().getContext(),R.string.againDownload, Toast.LENGTH_LONG).show();
                    NotificationCompat.Builder builder1 = new NotificationCompat.Builder(binding.getRoot().getContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_baseline_download_24)
                            .setContentTitle("Помощник чтеца")
                            .setContentText("Попробуйте скачать заново")
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat managerCompat1 = NotificationManagerCompat.from(binding.getRoot().getContext());
                    managerCompat1.notify(NOTIFICATION_ID, builder1.build());

                    Log.i(tag,"Download Again");
                }, 2000);

                Log.e(tag, "Download Failed");

            }
            else{
                //Toast.makeText(binding.getRoot().getContext(), "Файл скачан", Toast.LENGTH_LONG).show();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(binding.getRoot().getContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_download_24)
                        .setContentTitle("Помощник чтеца")
                        .setContentText("Файл скачан")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(binding.getRoot().getContext());
                managerCompat.notify(NOTIFICATION_ID, builder.build());

                Log.i(tag, "Download Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(binding.getRoot().getContext(),R.string.failDownload,Toast.LENGTH_LONG).show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(binding.getRoot().getContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_download_24)
                    .setContentTitle("Помощник чтеца")
                    .setContentText("Ошибка при скачивании")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(binding.getRoot().getContext());
            managerCompat.notify(NOTIFICATION_ID, builder.build());

            new Handler().postDelayed(() -> {
                //Toast.makeText(binding.getRoot().getContext(),R.string.againDownload,Toast.LENGTH_LONG).show();

                NotificationCompat.Builder builder12 = new NotificationCompat.Builder(binding.getRoot().getContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_download_24)
                        .setContentTitle("Помощник чтеца")
                        .setContentText("Попробуйте скачать заново")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat managerCompat12 = NotificationManagerCompat.from(binding.getRoot().getContext());
                managerCompat12.notify(NOTIFICATION_ID, builder12.build());
            }, 3000);
            Log.e(tag, "Download Failed with Exception - " + e.getLocalizedMessage());

        }

        super.onPostExecute(s);
    }


    @Override
    protected void onPreExecute() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(binding.getRoot().getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_download_24)
                .setContentTitle("Помощник чтеца")
                .setContentText("Загрузка начата")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(binding.getRoot().getContext());
        managerCompat.notify(NOTIFICATION_ID, builder.build());
        super.onPreExecute();
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "For downloading audio files";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = binding.getRoot().getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
