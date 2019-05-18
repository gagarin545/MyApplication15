package com.example.ura.myapplication_15;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.function.Function;

import static com.example.ura.myapplication_15.MainActivity.debug;
import static com.example.ura.myapplication_15.MainString.Send_Message;
import static com.example.ura.myapplication_15.MainString.head;

public class fourActivity extends AppCompatActivity  implements View.OnClickListener {
    Control control;
    ArrayList<String> list;
    TextView tv4, tv5;
    static String change;
    String word;
    static Handler handtv4, handtv5;

    @SuppressLint({"SetTextI18n", "HandlerLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four);

        Button bt6 = (Button) findViewById(R.id.BT6); //определение кнопки
        bt6.setOnClickListener(this);

        tv4 = (TextView) findViewById(R.id.TV4);
        tv5 = (TextView) findViewById(R.id.TV5);
        setTitle(Html.fromHtml("<small><b>" + String.format(getString(R.string.app_name), head) + "</font>"));

        word = getIntent().getStringExtra("word");

        Send_Message("#ШПД" + word.substring(2, word.indexOf(']')) + word.substring(word.indexOf('-')).replace("-", "").replace(")","" ) + " ");

        handtv4 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                list = (ArrayList<String>) msg.obj;
                int col, n = Integer.parseInt(list.get(0)) + 1;
                Log.e(debug, "обновление");
                tv4.setText("");

                for (int i = 1 ; i < list.size(); i ++) {
                    if( i == n)
                        col = Color.DKGRAY;
                    else
                        col = Color.BLUE;
                    SpannableString commentsContent = new SpannableString(list.get(i) + "\n");
                    commentsContent.setSpan(new CalloutLink(fourActivity.this, col), 0, list.get(i).length(), 0);
                    tv4.setMovementMethod(LinkMovementMethod.getInstance());
                    tv4.append(commentsContent);
                }
            }
        };

        handtv5 = new Handler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void handleMessage(Message msg) {
                String s = (String) msg.obj;
                //change = "#ШПД" + word.substring(2, word.indexOf(']')) + word.substring(word.indexOf('-')).replace("-", "").replace(")","" ) + " ";
                change = ("#ШПД" + word.substring(2, word.indexOf(']')) + ' ' + sub(word.substring(word.indexOf('-') + 2)) + ' ' + sub(word.substring(word.indexOf('-' , word.indexOf('-') + 2) + 2))).replace(')', ' ') + " ";

                for (int i = 1; i < list.size(); i++)
                    if (list.get(i).equals(s))
                        change +=  String.valueOf( i - 1) + ' ';
            //    Log.e(debug, "change" + change);

                tv5.setTextColor(Color.BLUE);
                tv5.setText(s);

            }
        };
        control = new Control();
    }


    @Override
    public void onClick(View v) {
        control.SetCommand(new CallCommand( new Icommand(this)));
        control.press(v.getId());
    }

    String sub(String sem) {
        StringBuilder se = new StringBuilder();
        for(int i=0; sem.length() > i; i++) {
            if( sem.charAt(i) !=  ' ' & sem.charAt(i) != ';')
                se.append(sem.charAt(i));
            else
                break;
        }
        return se.toString();
    };
}
