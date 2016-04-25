package com.star.data_synchronization_support_system;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Star on 2016/4/23.
 */
public class ReceiveThread extends Thread {
    private static final String TAG = "ReceiveThread";
    private final static int DATA_LENTH = 4 * 1024;
    private InfoOfWifi info = new InfoOfWifi();
    private String ip = info.getIp();
    private String serverAddress = info.getSeverAddress();
    private int mServerPort;
    DatagramSocket socket;
    byte[] data;
    //private SaveThread saveThread;

    private ReceiveThread() throws SocketException{
        //create a socket to listen the servetPort
        socket = new DatagramSocket(mServerPort);
    }

    public static void main(){
        try {
            new ReceiveThread().start();
        }catch (SocketException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        data = new byte[DATA_LENTH];
        DatagramPacket packet = new DatagramPacket(data,DATA_LENTH);
        try {
            Log.d(TAG,"Waiting for receive...");
            socket.receive(packet);
            Log.d(TAG,"Received packet "+packet.getLength()+" byte");
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
