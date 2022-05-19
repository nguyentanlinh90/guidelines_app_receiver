package com.ntl.guidelinesappreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getStringExtra(MainActivity.MY_KEY);
        Toast.makeText(context, "MyBroadcastReceiver1 " + text, Toast.LENGTH_SHORT).show();
    }
}
