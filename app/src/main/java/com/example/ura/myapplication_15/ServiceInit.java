package com.example.ura.myapplication_15;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import static com.example.ura.myapplication_15.MainActivity.debug;
import static com.example.ura.myapplication_15.MainActivity.handclear;
import static com.example.ura.myapplication_15.MainActivity.handtv;
import static com.example.ura.myapplication_15.MainActivity.handet;
import static com.example.ura.myapplication_15.MainActivity.imei;
import static com.example.ura.myapplication_15.SecondActivity.handtv2;
import static com.example.ura.myapplication_15.fourActivity.handtv4;


public class ServiceInit extends Service {
    static ArrayList<String> list = new ArrayList<String>();

    public ServiceInit() {
        Thread initThread = new Thread(new InitSock());
        initThread.start();
        // Log.e(debug, initThread.getName() + initThread.getId());
    }

    public class InitSock extends MainString implements Runnable {
        public void run() {

            try {
              //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    InitSocket.getSock();
               // }
                //Thread SendThread = new Thread(new SendMessage("#usr" + imei + '#' + getString(R.string.codShpd) + getString(R.string.codSity)));
                Thread SendThread = new Thread(new SendMessage("#usr" + imei + getString(R.string.codSity)));
                SendThread.start();

                buf.clear();

                while (true) {
                    msg = new Message();
                    String s = InitSocket.read();
                    Log.e(debug, "read -> " + s );
                    if( s.startsWith("&"))
                        switch (s.substring(1, 2)) {
                            case "+":
                                buf.add(compare(s));
                                continue;
                            case "-":
                                msg.obj = buf;
                                handet.sendMessage(msg);
                                continue;
                            case "&":
                                buf.clear();
                            case "~":
                                msg.obj = s.substring(2);
                                handclear.sendMessage(msg);
                                continue;
                            case "#":
                                msg.obj = s.substring(2).replace("|", "\n");
                                handtv2.sendMessage(msg);
                                continue;
                            case "$":
                                list = compare(s);
                                Log.e(debug, "s = " + s.length());
                                msg.obj = list;
                                handtv4.sendMessage(msg);
                                continue;
                            case "*":
                                msg.obj = s;
                                handclear.sendMessage(msg);
                                Log.e(debug, "s = " + s.substring(2));
                                continue;
                            default:
                                Log.e(debug, "s = " + s);
                    };
                    msg.obj = s;
                    handtv.sendMessage(msg);
                }
            } catch (Exception ex) {
                sock = null;
                msg.obj = "Откл";
                handclear.sendMessage(msg);
                ex.printStackTrace();
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Log.e(debug, "onStartCommand Flag: " + flags + " Id: " + startId );
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
