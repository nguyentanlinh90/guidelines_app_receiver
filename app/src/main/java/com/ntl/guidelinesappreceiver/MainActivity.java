package com.ntl.guidelinesappreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String MY_BROADCAST_ACTION = "com.ntl.MY_ACTION";
    private String MY_BROADCAST_OBJECT_ACTION = "com.ntl.MY_OBJECT_ACTION";
    public static final String MY_KEY = "my_key";
    private String MY_KEY_LIST_OBJECT = "my_key_list_object";

    private TextView tvReceiver, tvReceiverObject;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MY_BROADCAST_ACTION.equals(intent.getAction())) {
                String strText = intent.getStringExtra(MY_KEY);
                tvReceiver.setText(strText);
            }

            if (MY_BROADCAST_OBJECT_ACTION.equals(intent.getAction())) {
                Gson gson = new Gson();

                //todo case 1: get OBJECT
                String strUser = intent.getStringExtra(MY_KEY);
                User user = gson.fromJson(strUser, User.class);

                //todo case 2: get LIST OBJECT with logic below
                String strJson = intent.getStringExtra(MY_KEY_LIST_OBJECT);
                List<User> list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(strJson);
                    JSONObject jsonObject;
                    User user1;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        user1 = gson.fromJson(jsonObject.toString(), User.class);
                        list.add(user1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                tvReceiverObject.setText(user.getName() + " : " + user.getAddress() + "\n" +
                        "size list = " + list.size());

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvReceiver = findViewById(R.id.tv_receiver_from_broadcast_guidelines_app);
        tvReceiverObject = findViewById(R.id.tv_receiver_from_broadcast_guidelines_app_object);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(MY_BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);

        IntentFilter intentFilterObject = new IntentFilter(MY_BROADCAST_OBJECT_ACTION);
        registerReceiver(broadcastReceiver, intentFilterObject);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}