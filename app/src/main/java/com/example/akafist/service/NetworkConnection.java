package com.example.akafist.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class NetworkConnection extends LiveData<Boolean> {
    private Context context;
    private ConnectivityManager connectivityManager;

    public NetworkConnection(Context context){
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private NetworkCallback networkCallback = new NetworkCallback();

    private void updateNetworkConnection(){
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null ) {
            postValue(networkInfo.isConnected());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d("NETWORK_CHECK", String.valueOf(connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork()).getLinkDownstreamBandwidthKbps()));
            }
        }
        else postValue(false);
    }

    private NetworkCallback connectivityManagerCallback(){
        networkCallback = new NetworkCallback();
        return networkCallback;
    }

    @Override
    protected void onActive() {
        super.onActive();
        updateNetworkConnection();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback());
        }else {
            context.registerReceiver(new NetworkReciever(), new IntentFilter(connectivityManager.CONNECTIVITY_ACTION));
        }

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        //connectivityManager.unregisterNetworkCallback(connectivityManagerCallback());
    }

    class NetworkCallback extends ConnectivityManager.NetworkCallback{
        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            postValue(false);
        }

        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            postValue(true);
        }
    }

    class NetworkReciever extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            updateNetworkConnection();
        }
    }
}
