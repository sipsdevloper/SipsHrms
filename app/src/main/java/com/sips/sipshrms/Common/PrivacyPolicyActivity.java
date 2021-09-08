package com.sips.sipshrms.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.sips.sipshrms.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        web =(WebView)findViewById(R.id.webView);
        web.loadUrl("file:///android_asset/app_privacy.html");
    }
}
