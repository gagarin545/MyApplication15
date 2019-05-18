package com.example.ura.myapplication_15;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.ura.myapplication_15.MainActivity.debug;
import static com.example.ura.myapplication_15.MainString.head;


public class thirdActivity extends AppCompatActivity implements View.OnClickListener {
    static String word;
    Control control;
    TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        String[] field = {
                "Номер: ",       // номер записи  "head"
                "Интервал: ",    // Интервал решения 1
                "Адрес: ",       // Адрес            2
                "Пом. ",         // Пом.             3
                "Услуга: ",      // Услуга           4
                "Заявлено: ",    // Заявлено         5
                "Технология: ",  // Технология       6
                "Телефон: ",     // Телефон          7
                "Примечание: ",  // Прим. оп наряду  8
                "Комментарий: ", // Комментарии      9
                "Тех.данные: ",  // Тех.данные       10
                "Работник: ",    // Работник         11
                "КС: ",          // Контрольный срок 12
                "Тип клиента: "  // Тип  клиента     13
        };

        Button bt5 = (Button) findViewById(R.id.BT5); //определение кнопки
        bt5.setOnClickListener(this);

        setTitle(Html.fromHtml("<small><b>" + String.format(getString(R.string.app_name), head) + "</font>"));

        tv3 = (TextView) findViewById(R.id.TV3);
        word = getIntent().getStringExtra("word");

        tv3.setText("");
        int i = 0;
        for(ArrayList <String> e : MainString.buf) {
            Log.e(debug, "!" + e.get(0) + "!" + word);
            if(e.get(0).equals(word)){
                for(String s : e) {
                    tv3.append(Html.fromHtml("<font COLOR=\'BLUE\'><b>" + field[i++] + "</b></font>"));
                    switch(i) {
                        case 5:
                        case 8:
                            Append_str(s);
                            break;
                        case 11:
                            if( s.contains("DSL") & s.contains("[")) {
                                tv3.append(Html.fromHtml(s.substring(0, s.indexOf('['))));
                                Append_str("!" + s.substring(s.indexOf('[')));
                                break;
                            }
                        default:
                            tv3.append(Html.fromHtml( s));
                    }   tv3.append( "\n");
              }
            }
        }
        control = new Control();
    }

    void Append_str(String s) {
     //   Log.e(debug, "DSL -> " + s);
        SpannableString commentsContent = new SpannableString(s);
        commentsContent.setSpan(new CalloutLink(this, Color.parseColor(MainActivity.col)), 0, s.length(), 0);
        tv3.setMovementMethod(LinkMovementMethod.getInstance());
        tv3.append(commentsContent);
    }

    @Override
    public void onClick(View v) {
        control.SetCommand(new CallCommand( new Icommand(this )));
        control.press(v.getId());
    }
}
