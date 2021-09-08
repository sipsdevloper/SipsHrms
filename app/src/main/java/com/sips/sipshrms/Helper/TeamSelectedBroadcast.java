package com.sips.sipshrms.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TeamSelectedBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String state  = intent.getExtras().getString("extra");

        Intent i = new Intent("broadCastName");
        i.putExtra("message", state);
        context.sendBroadcast(i);
    }
}
