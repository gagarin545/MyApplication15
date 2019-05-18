package com.example.ura.myapplication_15;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

import static com.example.ura.myapplication_15.InitSocket.hasConnection;
import static com.example.ura.myapplication_15.MainString.buf;
import static com.example.ura.myapplication_15.MainString.head;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String debug = "DBGMR";
    public static Handler handtv, handet, handclear;
    public static Intent intentserv;
    static String imei;
    static String col = "#AB47BC";
    static EditText et1;
    static TextView tv1;
    Button bRest;

    Control control;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imei = getImei();

        //Log.e(debug, "nam1 -> " + getApplicationInfo().loadLabel(getPackageManager()).toString());
        //getname();

        tv1 = (TextView) findViewById(R.id.TV1);
        et1 = (EditText) findViewById(R.id.ET1);

        Button bt1 = (Button) findViewById(R.id.BT1); // определение кнопки Send
        bt1.setOnClickListener(this);

        Button bt2 = (Button) findViewById(R.id.BT2); // определение кнопки Test
        bt2.setOnClickListener(this);

        Button bt7 = (Button) findViewById(R.id.BT7); // определение кнопки History
        bt7.setOnClickListener(this);

        bRest = (Button) findViewById(R.id.Rest); // определение кнопки test
        bRest.setOnClickListener(this);

        handclear = new Handler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                switch (String.valueOf(msg.obj)) {
                    case "Откл":
                        bRest.setBackgroundColor(Color.RED);
                        break;
                    case "Вкл":
                        bRest.setBackgroundColor(Color.GREEN);
                        break;
                    default:
                        if(String.valueOf(msg.obj).startsWith("&*")) {
                            head =  String.valueOf(msg.obj).substring(2);
                            setTitle(Html.fromHtml("<small><b>" + String.format(getString(R.string.app_name), head) + "</font>"));
                        }
                        else
                            tv1.setText(String.valueOf(msg.obj) + "\n");

                }
                ;
            }
        };

        handtv = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.e(debug, "msg -> " + msg.obj);
                tv1.append(msg.obj + "\n");
            }
        };

        handet = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int count_not_define = 0;
                for (ArrayList<String> list : buf) {
                    String color = "#4CAF50";

                    if (list.get(0).startsWith("Л"))
                        col = "#FF2C7EAE";
                    else
                        col = "#AB47BC";


                    SpannableString commentsContent = new SpannableString(list.get(0) + list.get(12) + list.get(1) + " " +
                            list.get(2) + " " + list.get(3) + " (" + list.get(13) + ")" + " " + list.get(11).replace("  ", "-") + "\n");

                    if (list.get(11).contains("  ")){
                        commentsContent.setSpan(new ForegroundColorSpan(Color.RED),
                                list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + list.get(13).length() + 6,
                                list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + list.get(13).length() + 8, 0);
                        count_not_define++; }
                    else
                        commentsContent.setSpan(new ForegroundColorSpan(Color.BLUE),
                                list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + list.get(13).length() + 6,
                                list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + list.get(13).length() + 6 + list.get(11).length(), 0);


                    if (list.get(13).contains("ЮЛ"))
                        commentsContent.setSpan(new ForegroundColorSpan(Color.RED),
                                list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + 3,
                                list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + list.get(13).length() + 5, 0);
                    else
                        commentsContent.setSpan(new ForegroundColorSpan(Color.parseColor(color)),
                                list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + 2 ,
                                list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + list.get(13).length() + 4, 0);

                    if (list.get(12).contains("СКВ"))
                        color = "#EF5350";

                    commentsContent.setSpan(new CalloutLink(MainActivity.this, Color.parseColor(col)), 0, list.get(0).length(), 0);
                    commentsContent.setSpan(new RelativeSizeSpan(0.7f), list.get(0).length(), list.get(0).length() + list.get(12).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    commentsContent.setSpan(new RelativeSizeSpan(0.7f),
                            list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + 2 ,
                            list.get(0).length() + list.get(1).length() + list.get(2).length() + list.get(3).length() + list.get(12).length() + list.get(13).length() + 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    commentsContent.setSpan(new ForegroundColorSpan(Color.parseColor(color)), list.get(0).length(), list.get(0).length() + list.get(12).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    // Log.e(debug, "!" + list.get(13) + "!");

                    tv1.setMovementMethod(LinkMovementMethod.getInstance());
                    tv1.append(commentsContent);
                }
                tv1.append("Инцидентов: " + buf.size() + " неназначено: " + count_not_define);
            }
        };
        control = new Control();

    }

    protected void onDestroy(Bundle savedInstanceState){
        super.onDestroy();
        savedInstanceState.putStringArrayList("list", ServiceInit.list);

        Log.e(debug, "init: onDestroy");
    }
    @Override
    public void onClick(View v) {
        control.SetCommand(new CallCommand(new Icommand(this)));
        control.press(v.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        control.SetCommand(new CallCommand(new Icommand(this)));
        control.press(R.id.Rest);
        setTitle(Html.fromHtml("<small><b>" + String.format(getString(R.string.app_name), head) + "</font>"));
        if (hasConnection(this))
            bRest.setBackgroundColor(Color.GREEN);
        else {
            bRest.setBackgroundColor(Color.RED);
            tv1.setText("Отсутсвует покдключение к сети.");
        }
        // Log.e(debug, "onStart: " + hasConnection(this));
    }

    private String getImei() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        return telephonyManager.getDeviceId();
    }
}

