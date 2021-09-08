package com.sips.sipshrms.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

public class ShowOnWebActivity extends BaseActivity {

    TextView title;
    WebView webView;
    String callfrom,filename;
    String urlmain ="";
    ProgressDialog progressDialog;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_on_web);
        title = findViewById(R.id.title);
        webView = findViewById(R.id.wev_view);
        back = findViewById(R.id.back);
        Intent intent = getIntent();
        callfrom = intent.getStringExtra("From");
        filename = intent.getStringExtra("Filename");
        Toast.makeText(getApplicationContext(),filename,Toast.LENGTH_LONG).show();
        if (callfrom.matches("Attach"))
        {
            title.setText("ATTACHMENT");
            urlmain = urlancattachimage+filename;
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        progressDialog = ProgressDialog.show(ShowOnWebActivity.this, "", "Loading...",true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);

        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url"+urlmain); // Here You can put your Url
        webView.setWebChromeClient(new WebChromeClient() {
        });

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
                //Toast.makeText(context, "Page Load Finished", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
