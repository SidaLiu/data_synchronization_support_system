package com.star.data_synchronization_support_system;

/**
 * Created by Star on 2016/4/23.
 */
public abstract class Synchronization {
    private static final String TAG = "Synchronization";
    private int mServerPort;

    public Synchronization(){
        mServerPort = getServerPort();
    }

    public abstract int getServerPort();
}
