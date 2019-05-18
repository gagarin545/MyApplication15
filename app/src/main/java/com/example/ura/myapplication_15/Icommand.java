package com.example.ura.myapplication_15;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import static com.example.ura.myapplication_15.InitSocket.hasConnection;
import static com.example.ura.myapplication_15.MainActivity.debug;
import static com.example.ura.myapplication_15.MainActivity.et1;
import static com.example.ura.myapplication_15.MainActivity.intentserv;
import static com.example.ura.myapplication_15.SecondActivity.et2;
import static com.example.ura.myapplication_15.fourActivity.change;
import static com.example.ura.myapplication_15.thirdActivity.word;

class Icommand extends MainString{
    Context context;
    private static String message;

    Icommand(Context ctx) {
        super();
        this.context = ctx;
    }

    Icommand(Context ctx, String s) {
        super();
        this.context = ctx;
    }

    void commit(int i) {
        Intent intent = new Intent(context, SecondActivity.class);

        switch (i) {
            case R.id.Rest:
                if ( sock == null && hasConnection(context)) {
                    if(intentserv == null) {
                        intentserv = new Intent(context, ServiceInit.class);
                        context.startService(intentserv);
                    }
                    else {
                        context.stopService(intentserv);
                        context.startService(intentserv);
                    }
                }
                else
                   Send_Message("#inc");
                break;
            case R.id.BT1:
                Log.e(debug, "Послать" + et1.getText());
                if (et1.getText().toString().startsWith("#77")) {
                    intent.putExtra("word", et1.getText().toString());
                    context.startActivity(intent);
                }
                else
                    Send_Message(et1.getText().toString());
                et1.setText("");
                break;
            case R.id.BT2:  // Log.e(debug, "Тестировать" + et1.getText());
                intent.putExtra("word", et1.getText().toString());
                context.startActivity(intent);
                break;
            case R.id.BT3:  // Log.e(debug, "Послать" + et2.getText().toString());
                message = et2.getText().toString();
                Send_Message(message);
                break;
            case R.id.BT4:  // Log.e(debug, "Повторить");
                Send_Message(message);
                break;
            case R.id.BT5:  // Log.e(debug, "Заказать");
                Send_Message("#reg" + word.substring(word.indexOf('-') + 1));
                break;
            case R.id.BT6:
                Send_Message(change);
                break;
            case R.id.BT7:
                Send_Message("#his");
                break;
        }
    }
}

