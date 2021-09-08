package com.sips.sipshrms.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.SessionManager;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends BaseActivity {

    EditText ed_empcurrpass,ed_empnewpass,ed_empnewpassconf;
    Button bt_chgpass;

    LinearLayout ll_main_layout;
    SharedPresencesUtility sharedPresence;
    ImageView back;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ed_empcurrpass = findViewById(R.id.ed_empcurrpass);
        ed_empnewpass = findViewById(R.id.ed_empnewpass);
        ed_empnewpassconf = findViewById(R.id.ed_empnewpassconf);
        bt_chgpass = findViewById(R.id.bt_chgpass);
        ll_main_layout = findViewById(R.id.ll_main_layout);
        back = findViewById(R.id.back);
        sharedPresence = new SharedPresencesUtility(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bt_chgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed_empcurrpass.getText().toString().isEmpty())
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please enter current password", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }
                else if(ed_empnewpass.getText().toString().isEmpty())
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please enter new password", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else if(ed_empnewpassconf.getText().toString().isEmpty())
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please enter confirmed password", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else if(!ed_empnewpass.getText().toString().matches(ed_empnewpassconf.getText().toString()))
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Password do not matched", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else
                {
                    changepasswordfunc();
                }
            }
        });

    }

    private void changepasswordfunc() {
        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"changepassword";

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

                                hideProgressBar();
                                Snackbar snackbar1 = Snackbar.make(ll_main_layout, respmessage, Snackbar.LENGTH_LONG);
                                snackbar1.show();
                                //sessionManager.logoutUser();
                                Intent intent = new Intent(ChangePassword.this, NewMainActivity.class);
                                startActivity(intent);
                                finish();
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
                params.put("EMP_ID",sharedPresence.getUserId(getApplicationContext()));
                params.put("EMP_PASSWORD",ed_empcurrpass.getText().toString().trim());
                params.put("EMP_CHG_PASSWORD",ed_empnewpassconf.getText().toString().trim());
                params.put("EMP_COMP_ID",sharedPresence.getCompId(getApplicationContext()));

                return params;
            }
        };
        queue.add(postRequest);
    }
}
