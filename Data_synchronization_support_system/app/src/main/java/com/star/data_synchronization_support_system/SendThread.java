package com.star.data_synchronization_support_system;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 * Created by Star on 2016/4/23.
 */
public class SendThread extends Thread {
    private static final String TAG = "SendThread";
    private static Object lock = new Object();
    private static SendThread sendThread;
    private Vector<byte[]> sendDataByte;
    private InfoOfWifi info = new InfoOfWifi();
    private String ip = info.getIp();
    private String serverAddress = info.getSeverAddress();
    private int mServerPort;
    DatagramSocket socket;

    private SendThread(){
        sendDataByte = new Vector<>();
        try{
            socket = new DatagramSocket();
        }catch (SocketException e){
            e.printStackTrace();
        }
    }

    public static SendThread getInstance() {
        if (sendThread == null) {
            synchronized (lock) {
                if (sendThread == null) {
                    sendThread = new SendThread();
                }
            }
        }
        return sendThread;
    }

    public void startSend(int serverPort,byte[] data){
        mServerPort = serverPort;
        sendDataByte.add(data);
        start();
    }

    @Override
    public void run(){
        if(!sendDataByte.isEmpty()) {
            try {
                InetAddress inetAddress = InetAddress.getByName(serverAddress);
                byte[] data = sendDataByte.remove(0);
                DatagramPacket packet = new DatagramPacket(data, data.length, inetAddress, mServerPort);
                Log.d(TAG, "Waiting for sending...");
                socket.send(packet);
                Log.d(TAG, "Send the packet " + packet.getLength() + " byte");
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
