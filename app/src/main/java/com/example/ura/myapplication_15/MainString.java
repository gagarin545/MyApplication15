package com.example.ura.myapplication_15;

import android.annotation.SuppressLint;
import android.os.Message;
import android.util.Log;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

abstract class MainString {
    volatile static Socket sock;
    static BufferedReader reader;
    static PrintWriter writer;
    static ArrayList<ArrayList> buf = new ArrayList<>();
    Message msg = null;
    static String head;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss]");

    MainString() {}

    ArrayList compare(String str) {
        int len = 0;
        ArrayList<String> list = new ArrayList<>();

        while(true) {
            len = str.indexOf('|', len + 1);
            if( str.indexOf('|', len + 1) < 0 )
                break;
            list.add( str.substring( len + 1 , str.indexOf('|', len + 2)));
        }
 //        Log.e(MainActivity.debug, "(add)Количество полей: " + list.size() + " Записей: " + buf.size());
        return list;
    }
    static void Send_Message(String message) {
        Log.e(MainActivity.debug,"send->" + message);
        Thread SendThread = new Thread(new SendMessage(message));
        SendThread.start();
    }
}
