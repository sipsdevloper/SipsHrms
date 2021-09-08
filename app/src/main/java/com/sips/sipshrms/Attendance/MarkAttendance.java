package com.sips.sipshrms.Attendance;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.sips.sipshrms.Common.LoginActivity;
import com.sips.sipshrms.Common.MainActivity;
import com.sips.sipshrms.Common.MyTeamActivity;
import com.sips.sipshrms.Common.NewMainActivity;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.Connectivity;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.Helper.VolleyMultipartRequest;
import com.sips.sipshrms.MRF.GenerateMRF_Activity;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MarkAttendance extends BaseActivity {

    TextView current_address;
    LinearLayout ll_main_layout;
    Button imageButton;
    SimpleDateFormat sdf;
    SimpleDateFormat tdf;
    String currentDateandTime;
    String IMEI;
    TextView tv_outtime, tv_intime;
    String myFormat = "yyyy-MM-dd HH:mm:ss ";
    String todayFormat = "yyyy-MM-dd";
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 5;
    String str_latitude, str_longitude, str_address;
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };


    SharedPresencesUtility sharedPresence;
    ImageView back;
    private static final int CAMERA_REQUEST = 1888;
    Bitmap bitimg;
    TextView uploadimage;
    TextView tv_checkimgempty;
    ImageView imgpreview;
    String lastpunchtext;
    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    };
    String permissionsDenied = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        current_address = findViewById(R.id.current_address);
        ll_main_layout = findViewById(R.id.ll_main_layout);
        imageButton = findViewById(R.id.imageButton);
        tv_outtime = findViewById(R.id.tv_outtime);
        uploadimage = findViewById(R.id.uploadimage);
        tv_checkimgempty = findViewById(R.id.tv_checkimgempty);
        imgpreview = findViewById(R.id.imgpreview);
        back = findViewById(R.id.back);
        tv_intime = findViewById(R.id.tv_intime);
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        tdf = new SimpleDateFormat(todayFormat, Locale.US);
        sharedPresence = new SharedPresencesUtility(getApplicationContext());
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            checkPermissions();
        }

//        try {
//            IMEI = telephonyManager.getDeviceId();
//            if (!IMEI.isEmpty()) {
//                IMEI = telephonyManager.getDeviceId();
//            } else {
//                Snackbar snackbar1 = Snackbar.make(ll_main_layout, "IMEI not supported", Snackbar.LENGTH_LONG);
//                snackbar1.show();
//            }
//        }
//        catch(Exception e) {
//            Snackbar snackbar1 = Snackbar.make(ll_main_layout, "IMEI not supported", Snackbar.LENGTH_LONG);
//            snackbar1.show();
//        }
        initializeLocationManager();
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

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_FRONT);  // Tested on API 24 Android version 7.0(Samsung S6)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_FRONT); // Tested on API 27 Android version 8.0(Nexus 6P)
                    cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                } else {
                    cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);  // Tested API 21 Android version 5.0.1(Samsung S4)

                }
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentDateandTime = sdf.format(new Date());

                Log.i("AAJKATIME", currentDateandTime);
                if (!Connectivity.isConnected(getApplicationContext())) {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                } else if (current_address.getText().toString().isEmpty()) {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please wait we are fetching your location", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else if (imgpreview.getDrawable() == null) {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please attach image", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                } else {
                    //  addattendance();
                    uploadBitmap();


                }
            }
        });
        if (!Connectivity.isConnected(getApplicationContext())) {
            Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar1.show();
        } else {
            loadattendance();
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mphoto.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

            bitimg = mphoto;
            imgpreview.setImageBitmap(bitimg);
            tv_checkimgempty.setText(String.valueOf(mphoto));
            Log.i("All is well", imageEncoded);

        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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


            try {

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

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
            if(!isFinishing()){
                showSettingsAlert();
            }

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

    private void addattendance() {
        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain + "addAttendance";

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
                                loadattendance();
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
                params.put("attendance_mode_id", "2");
                params.put("punchdate_time", currentDateandTime);
                // params.put("punchdate_time", sdf.format(Calendar.getInstance().getTime()));
                params.put("imeino", IMEI);

                params.put("lat", str_latitude);
                params.put("long", str_longitude);
                params.put("users_id", sharedPresence.getEmpId(getApplicationContext()));
                params.put("remarks", str_address);
                // params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void loadattendance() {
        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain + "gettodayinout";

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
                                JSONObject heroObject = obj.getJSONObject("Response");

                                String in_time = heroObject.getString("in_time");
                                String out_time = heroObject.getString("out_time");

                                if (in_time.matches("0")) {
                                    tv_intime.setText("");
                                    tv_outtime.setText("");
                                    lastpunchtext="IN";
                                } else if (out_time.matches("0")) {
                                    imageButton.setText("PUNCH OUT");
                                    tv_intime.setText("IN TIME: " + in_time);
                                    tv_outtime.setText("");
                                    lastpunchtext="OUT";
                                } else {
                                    imageButton.setText("PUNCH OUT");
                                    tv_intime.setText("IN TIME: " + in_time);
                                    tv_outtime.setText("OUT TIME: " + out_time);
                                    lastpunchtext ="OUT";
                                }

                                hideProgressBar();


                            } else {
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
                params.put("emp_id", sharedPresence.getUserId(getApplicationContext()));
                // params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));
                // params.put("t_date","2019-12-13");
                params.put("t_date", tdf.format(new Date()));

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void uploadBitmap() {
        //our custom volley request
        Log.i("Resp1234", currentDateandTime+" "+sharedPresence.getIMEI(getApplicationContext())
                +str_latitude+" "+str_longitude+" "+sharedPresence.getEmpId(getApplicationContext())+" "+str_address);
        showProgressBar();
        String url = urlmain + "addAttendance";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {

                            Log.e("REsponce22", response.toString());
                            JSONObject obj = new JSONObject(new String(response.data));

                            Log.e("REsponce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");
                            Log.i("Resp1234", success);
                            if (success.equals("200")) {

                                tv_outtime.setText("");
                                tv_intime.setText("");
                                loadattendance();
                                hideProgressBar();
                                Snackbar snackbar1 = Snackbar.make(ll_main_layout, respmessage, Snackbar.LENGTH_LONG);
                                snackbar1.show();
                                Intent intent = new Intent(MarkAttendance.this, NewMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            } else {
                                hideProgressBar();

                                Snackbar snackbar1 = Snackbar.make(ll_main_layout, respmessage, Snackbar.LENGTH_LONG);
                                snackbar1.show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressBar();
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("attendance_mode_id", "2");
                params.put("punchdate_time", currentDateandTime);
                // params.put("punchdate_time", sdf.format(Calendar.getInstance().getTime()));
                params.put("imeino", sharedPresence.getIMEI(getApplicationContext()));

                params.put("lat", str_latitude);
                params.put("long", str_longitude);
                params.put("users_id", sharedPresence.getEmpId(getApplicationContext()));
                params.put("remarks", str_address);

                return params;
            }


            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                String  imagename = sharedPresence.getUserId(getApplicationContext())+tdf.format(new Date())+lastpunchtext ;
                params.put("photo", new VolleyMultipartRequest.DataPart(  "ATT_" + imagename + ".png", getFileDataFromDrawable(bitimg)));
                return params;
            }
        };

        //adding the request to volley
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MarkAttendance.this);

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


