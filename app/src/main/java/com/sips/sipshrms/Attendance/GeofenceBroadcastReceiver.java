package com.sips.sipshrms.Attendance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "GeofenceBroadcastReceiver";

    public static final String GEOFENCE_ACTION = "com.epgeotrack.app.ep_geo_tracking.ACTION_RECEIVE_GEOFENCE";

    Context context;

    Intent broadcastIntent = new Intent();

    public GeofenceBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;


    }

    private void handleError(Intent intent) {
        // Get the error code


        // Set the action and error message for the broadcast intent
        broadcastIntent
                .setAction(GEOFENCE_ACTION)
                .putExtra("GEOFENCE_STATUS", "");

        // Broadcast the error *locally* to other components in this app
        LocalBroadcastManager.getInstance(context).sendBroadcast(
                broadcastIntent);
    }


}
