package com.example.ura.myapplication_15;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import static com.example.ura.myapplication_15.MainActivity.debug;

public class InitSocket extends MainString {

    private InitSocket() {}


   // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    static void getSock() throws IOException {
        int portNumber = 11111;
        String  Host =   "192.168.100.4";
       // Log.e(debug, "Socket "  + portNumber + '|' + Host  );
        if (sock == null) {
            sock = new Socket(Host, portNumber);
            sock.setKeepAlive(true);
            
            //sock = new Socket(String.valueOf(R.string.HostName), Integer.valueOf(R.string.PortNumber));
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new PrintWriter(sock.getOutputStream(), true);
        }
      //   Log.e(debug, "Socket "  + sock.getPort()  );
        //return sock;
    }


    static String read() throws IOException { return reader.readLine(); }

    static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
            return true;

        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
            return true;

        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
            return true;

        return false;
    }

}




