package com.example.ura.myapplication_15;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.ura.myapplication_15.MainActivity.debug;
import static com.example.ura.myapplication_15.MainString.head;


public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    public static Handler handtv2;
    static EditText et2;
    TextView tv2;
    Control control;
    String word;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        Button bt3 = (Button) findViewById(R.id.BT3); // определение кнопки send
        bt3.setOnClickListener(this);

        Button bt4 = (Button) findViewById(R.id.BT4); // определение кнопки Repit
        bt4.setOnClickListener(this);

        setTitle(Html.fromHtml("<small><b>" + String.format(getString(R.string.app_name), head) + "</font>"));

        et2 = (EditText) findViewById(R.id.ET2);
        word = getIntent().getStringExtra("word");
        Log.e(debug, "Word ->" + word);
        control = new Control();

        tv2 = (TextView) findViewById(R.id.TV2);

        if (word.startsWith(" #") || word.startsWith("#")) {
            et2.setText( word.trim());
            bt3.callOnClick(); // нажать
        }


        handtv2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String s = String.valueOf(msg.obj);
                tv2.append("\n");
                Log.e(MainActivity.debug, "second " + s);
                for(int i = 0; i  < s.length();) {
                   tv2.append(Html.fromHtml("<font COLOR='#33B5E5'><b>" + s.substring(i, s.indexOf(':', i) + 1) + "</b></font>" ));
                   tv2.append( s.substring(s.indexOf(':', i) + 1, s.indexOf( '\n', i) + 1).replace("\t", "      "));
                   i = s.indexOf('\n', i) + 1 ;
               }
            }
        };
    }

    @Override
    public void onClick(View v) {
        tv2.setText(Html.fromHtml("<font COLOR=\'BLUE\'><b>" + " Ждите тестирую " + et2.getText() + "</b></font>"));
        control.SetCommand(new CallCommand( new Icommand(this)));
        control.press(v.getId());
    }

}

