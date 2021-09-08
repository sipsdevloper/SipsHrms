package com.sips.sipshrms.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.sips.sipshrms.R;
import com.sips.sipshrms.SharedPreferenceUtils;

import javax.inject.Inject;


public class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog = null;
    public BaseActivity baseActivity;
    public SharedPreferenceUtils preferenceUtils;
    public String urlmain;
    public String urlmenuload;
    public String urlimage;
    public String urlancattachimage;
    public String urlgalleryimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        baseActivity = this;
        preferenceUtils = new SharedPreferenceUtils(getApplicationContext());
        urlmain = BASE_URL();
        urlmenuload = urlmenuload();
        urlimage = urlimage();
        urlancattachimage = urlancattachimage();
        urlgalleryimage = urlgalleryimage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    public void checkForCrashes() {
//        CrashManager.register(this);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showProgressBar() {

        if (progressDialog != null && progressDialog.isShowing()) {
            // do nothing
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }
    }

    public void hideProgressBar() {
        if (this.progressDialog != null && !this.isFinishing()) {
            progressDialog.dismiss();
        }
    }

    public void statusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public void launchActivity(Class<? extends BaseActivity> clazz) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        }
    }

    public void launchActivity(Class<? extends BaseActivity> clazz, Bundle bundle) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    public void launchActivityNewTask(Class<? extends BaseActivity> clazz, Bundle bundle) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    public void showToast(String msg) {
        showToast(msg, true);
    }

    public void showToast(String msg, boolean isShort) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
        }
    }

    public void fullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    //    1
    public String BASE_URL() {
        String urlmain = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlmain = "http://sipshrms.in/smas/hrms_api/webservices/index.php?apicall=";
                break;
            case stll:
                urlmain = "http://sipshrms.in/stll/hrms_api/webservices/index.php?apicall=";
                break;
            case sips:
                urlmain = "http://sipshrms.in/hrms_api/webservices/index.php?apicall=";
                break;
            case login:
                urlmain = "http://sipshrms.in/hrms_test/webservices/index.php?apicall=";
                break;
            default:
                break;
        }
        return urlmain;
    }

    //    2

    public String urlgalleryimage() {
        String urlgalleryimage = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlgalleryimage = "http://sipshrms.in/smas/uploads/gallary/";
                break;
            case stll:
                urlgalleryimage = "http://sipshrms.in/stll/uploads/gallary/";
                break;
            case sips:
                urlgalleryimage = "http://sipshrms.in/uploads/gallary/";
                break;
            default:
                break;
        }
        return urlgalleryimage;
    }

    //    3

    public String urlimage() {
        String urlimage = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlimage = "http://sipshrms.in/smas/uploads/profile/";
                break;
            case stll:
                urlimage = "http://sipshrms.in/stll/uploads/profile/";
                break;
            case sips:
                urlimage = "http://sipshrms.in/uploads/profile/";
                break;
            default:
                break;
        }
        return urlimage;
    }

    //    4

    public String urlancattachimage() {
        String urlancattachimage = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlancattachimage = "http://sipshrms.in/smas/uploads/announcements/";
                break;
            case stll:
                urlancattachimage = "http://sipshrms.in/stll/uploads/announcements/";
                break;
            case sips:
                urlancattachimage = "http://sipshrms.in/uploads/announcements/";
                break;
            default:
                break;
        }
        return urlancattachimage;
    }

    //    5
    public String urlmenuload() {
        String urlmenuload = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlmenuload = "http://sipshrms.in/smas/apimobile/";
                break;
            case stll:
                urlmenuload = "http://sipshrms.in/stll/apimobile/";
                break;
            case sips:
                urlmenuload = "http://sipshrms.in/apimobile/";
                break;
            default:
                break;
        }
        return urlmenuload;
    }

    public final String smas = "SMAS";
    public final String stll = "STLL";
    public final String sips = "SIPS";
    public final String login = "login";

}
