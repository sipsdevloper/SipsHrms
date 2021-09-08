package com.sips.sipshrms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sips.sipshrms.Common.LoginActivity;

public class Forgotpass_Activity extends AppCompatActivity {
Button sendfemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        sendfemail=(Button)findViewById(R.id.bt_send);
        sendfemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forgotpass_Activity.this, confirmforget_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}