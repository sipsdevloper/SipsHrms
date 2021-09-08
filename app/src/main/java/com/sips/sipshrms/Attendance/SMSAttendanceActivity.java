package com.sips.sipshrms.Attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.sips.sipshrms.Common.MainActivity;
import com.sips.sipshrms.Helper.AES;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.Connectivity;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SMSAttendanceActivity extends BaseActivity {

    TextView current_address;
    LinearLayout ll_main_layout;
    Button imageButton;
    SimpleDateFormat sdf ;
    SimpleDateFormat tdf ;
    String currentDateandTime;
    String IMEI;
    TextView tv_outtime,tv_intime;
    String myFormat = "yyyy-MM-dd HH:mm:ss ";
    String todayFormat = "yyyy-MM-dd";
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 5;
    String str_latitude, str_longitude, str_address;
    SMSAttendanceActivity.LocationListener[] mLocationListeners = new SMSAttendanceActivity.LocationListener[]{
            new SMSAttendanceActivity.LocationListener(LocationManager.GPS_PROVIDER),
            new SMSAttendanceActivity.LocationListener(LocationManager.NETWORK_PROVIDER)
    };


    SharedPresencesUtility sharedPresence ;
    ImageView back;

    Boolean isgpsOn = false;

    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    };
    String permissionsDenied = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsattendance);
        current_address = findViewById(R.id.current_address);
        ll_main_layout = findViewById(R.id.ll_main_layout);
        imageButton = findViewById(R.id.imageButton);
        tv_outtime = findViewById(R.id.tv_outtime);
        back = findViewById(R.id.back);
        tv_intime = findViewById(R.id.tv_intime);
        sdf=  new SimpleDateFormat(myFormat, Locale.US);
        tdf=  new SimpleDateFormat(todayFormat, Locale.US);
        sharedPresence =  new SharedPresencesUtility(getApplicationContext());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if(checkPermissions());
            { Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Allow permissions", Snackbar.LENGTH_LONG);
               snackbar1.show();
            }
        }
        initializeLocationManager();
//        try {
//            IMEI = telephonyManager.getDeviceId();
//            if (!IMEI.isEmpty()) {
//                IMEI = telephonyManager.getDeviceId();
//                tv_intime.setText(IMEI);
//            } else {
//                tv_intime.setText("");
//                Snackbar snackbar1 = Snackbar.make(ll_main_layout, "IMEI not supported", Snackbar.LENGTH_LONG);
//                snackbar1.show();
//            }
//        }
//        catch(Exception e) {
//            Snackbar snackbar1 = Snackbar.make(ll_main_layout, "IMEI not supported", Snackbar.LENGTH_LONG);
//            snackbar1.show();
//        }



        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_address.getText().toString().isEmpty()) {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Getting address wait...", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }  else {


                    currentDateandTime = sdf.format(new Date());


                    Log.i("AAJKATIME", currentDateandTime);
                    final String secretKey = "ssshhhhhhhhhhh!!!!";

                    //String originalString = "SIPSMS";
                    String originalString = "SIPSMS" + "/" + currentDateandTime + "/" + sharedPresence.getUserId(getApplicationContext()) + "/" + str_latitude + "/" + str_longitude + "/" + sharedPresence.getIMEI(getApplicationContext());
                    String encryptedString = AES.encrypt(originalString, secretKey);

                    String decryptedString = AES.decrypt(encryptedString, secretKey);
                    tv_intime.setText(encryptedString);
                    tv_outtime.setText(decryptedString);

                    sendSMS("+91-7303592914", encryptedString);

                }
            }
        });

    }


    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }

    }
    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);

        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            str_latitude = Double.toString(location.getLatitude());
            str_longitude = Double.toString(location.getLongitude());
            Log.i("Localityyy", str_latitude + " " + str_longitude);
            tv_outtime.setText(str_latitude+"      "+ str_longitude);
            current_address.setText(str_latitude+"      "+ str_longitude);


            try {
                Log.i("localitytyty","333333333");
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                Log.i("localitytyty1","333333333");
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Log.i("localitytyty2","333333333");
                str_address = addresses.get(0).getAddressLine(0) + ", " +
                        addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2);
                Log.i("Localityyy1", str_address);
                str_address = str_address.replaceAll(", null", "");
                Log.i("Localityyy2", str_address);

                current_address.setText(str_address);


            } catch (Exception e) {
                Log.e(TAG, "onNetworkChange: " + e.getMessage());
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
            showSettingsAlert();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SMSAttendanceActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                 startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

//    public void sendSMS(String phoneNo, String msg) {
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
//            Toast.makeText(getApplicationContext(), "Message Sent",
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//    }
    public void sendSMS(String phoneNo, String msg) {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , new String (phoneNo));
        smsIntent.putExtra("sms_body"  , msg);

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SMSAttendanceActivity.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
    private void addattendance() {
        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"addAttendance";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Log.i("Responseee", response);


                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("REsponce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");
                            Log.i("Resp1", success);
                            if (success.equals("200")) {

                                tv_outtime.setText("");
                                tv_intime.setText("");

                                hideProgressBar();
                                Snackbar snackbar1 = Snackbar.make(ll_main_layout, respmessage, Snackbar.LENGTH_LONG);
                                snackbar1.show();


                            } else {



                                hideProgressBar();

                                Snackbar snackbar1 = Snackbar.make(ll_main_layout, respmessage, Snackbar.LENGTH_LONG);
                                snackbar1.show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressBar();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("attendance_mode_id","2");
                params.put("punchdate_time", currentDateandTime);
                // params.put("punchdate_time", sdf.format(Calendar.getInstance().getTime()));
                params.put("imeino",IMEI);

                params.put("lat",str_latitude);
                params.put("long",str_longitude);
                params.put("users_id",sharedPresence.getEmpId(getApplicationContext()));
                params.put("remarks",str_address);
                // params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));

                return params;
            }
        };
        queue.add(postRequest);
    }
    private  boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : permissions) {
                result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
                return false;
            }
            return true;
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissionsList[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {

                    for (String per : permissionsList) {
                        for (int i= 0;i<permissionsList.length;i++)
                        {
                            if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                                permissionsDenied = "\n" + per;

                            }
                        }

                    }

                    // Show permissionsDenied

                }



            }
            Log.i("getpermission",permissionsDenied);



        }
    }
}
