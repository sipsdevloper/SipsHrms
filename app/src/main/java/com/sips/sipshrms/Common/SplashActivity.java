package com.sips.sipshrms.Common;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sips.sipshrms.BuildConfig;
import com.sips.sipshrms.Constant;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.SessionManager;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends BaseActivity {

    private static int WELCOME_TIMEOUT = 3000;
    ImageView mImageView,mImageViewname;

    SessionManager sessionManager;
    SharedPresencesUtility sharedPresencesUtility;

    SwipeRefreshLayout swipeToRefresh;

    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    String[] permissionsQ = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };
    String permissionsDenied = "";
    RelativeLayout ll_relmain;
    Runnable runa;
    Handler handler;
    boolean  myDialog = false;

    void myPermissionDialig(){
        if (!sessionManager.isLoggedIn()  &&  !preferenceUtils.getMuDialog()) {
            new AlertDialog.Builder(this)
                    .setTitle("Background Permission Needed")
                    .setMessage("This app needs the background notification permission, please accept to use background permission")
                    .setPositiveButton("OK",
                            (dialog, which) -> {
                                myDialog = true;
                                Log.e("TAG", "dialog   true");
                                dialog.dismiss();
                            })
                     .setNegativeButton("Cancel",
                    (dialog, which) -> {
                        myDialog = false;
                        Log.e("TAG", "dialog canceled ");
                        dialog.dismiss();
                        myPermissionDialig();
                    }).create().show();
        }else{
            myDialog = true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d("TAG", "splash  "+ Constant.getInstance().type);

        mImageView = (ImageView) findViewById(R.id.sips_logo);
        mImageViewname = (ImageView) findViewById(R.id.sips_name);
        ll_relmain = findViewById(R.id.ll_relmain);
        swipeToRefresh = findViewById(R.id.swipeToRefresh);
        sharedPresencesUtility = new SharedPresencesUtility(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        myPermissionDialig();

        handler =  new Handler();
        handler.postDelayed(runa = new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "runnable");
                if (myDialog) {
                    Log.e("TAG", "myDialog true");
                    if (checkPermissions()) {
                        preferenceUtils.setmuDialog(true);
                        handler.removeCallbacks(runa);
                        Log.e("TAG", "handler stop");
                        if (sessionManager.isLoggedIn()) {
                            Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    } else {
                        preferenceUtils.setmuDialog(false);
                        handler.postDelayed(runa , 2000);
//                        Snackbar snackbar1 = Snackbar.make(ll_relmain, "Alow permission and refresh", Snackbar.LENGTH_LONG);
//                        snackbar1.show();
                    }
                }else{
                    Log.e("TAG", "myDialog false  with handle");
                    handler.postDelayed(runa , 2000);
                }
            }
        }, 2000);
//        runa = () -> {
//
//        };




//        new CountDownTimer(3000, 1000)
//        {
//
//            boolean flag = true;
//
//            @Override
//            public void onTick(long millisUntilFinished)
//            {
//
//                if (flag)
//                {
//
//                    AlphaAnimation ar = new AlphaAnimation(0.2f, 1.0f);
//                    ar.setInterpolator(new BounceInterpolator());
//                    ar.setDuration(500);
//                    mImageView.setAnimation(ar);
//                    ar.start();
//                    flag = false;
//                }
//                if (!flag)
//                {
//
//                    AlphaAnimation ar1 = new AlphaAnimation(1.0f, 0.2f);
//                    ar1.setInterpolator(new BounceInterpolator());
//                    ar1.setDuration(2500);
//
//                    mImageView.setAnimation(ar1);
//                    ar1.start();
//                    flag = true;
//
//                }
//
//                mImageView.invalidate();
//
//            }
//
//            @Override
//            public void onFinish()
//            {
//                if (checkPermissions())
//                {
//                    if (sessionManager.isLoggedIn()){
//                        Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                    else {
//                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                        startActivity(intent);
//                        SplashActivity.this.finish();
//                    }
//
//                }
//                else
//                {
//                    Snackbar snackbar1 = Snackbar.make(ll_relmain, "Alow permission and refresh", Snackbar.LENGTH_LONG);
//                    snackbar1.show();
//
//                }
//                swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        Intent refresh = new Intent(SplashActivity.this, SplashActivity.class);
//                        startActivity(refresh);
//                        SplashActivity.this.finish();
//                        swipeToRefresh.setRefreshing(false);
//                    }
//                });
//
//
//            }
//        }.start();
    }

    private boolean checkLocationPermission() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the background notification permission, please accept to use background permission")
                        .setPositiveButton("OK",
                                (dialog, which) -> { checkPermissions(); }).create().show();

//            } else {
//                // No explanation needed, we can request the permission.
//              return   checkPermissions();
//            }
//        }
        return false;
    }

    private  boolean checkPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : permissionsQ) {
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
    public void showaleartview()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                //set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
                //set title
                .setTitle("")
                //set message
                .setMessage("Allow permission and then retry")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        Intent intentsetting = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                        startActivity(intentsetting);
                        finish();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);



    }
}
