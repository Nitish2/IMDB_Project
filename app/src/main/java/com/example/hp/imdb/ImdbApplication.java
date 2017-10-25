package com.example.hp.imdb;

import android.app.Application;


public class ImdbApplication extends Application {

    private static ImdbApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static synchronized ImdbApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(Connectivity_Receiver.ConnectivityReceiverListener listener) {
        Connectivity_Receiver.connectivityReceiverListener = listener;
    }
}

