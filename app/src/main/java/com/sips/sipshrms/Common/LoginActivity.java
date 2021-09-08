package com.sips.sipshrms.Common;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sips.sipshrms.Constant;
import com.sips.sipshrms.Forgotpass_Activity;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.Connectivity;
import com.sips.sipshrms.Helper.SessionManager;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;
import com.sips.sipshrms.SharedPreferenceUtils;
import com.sips.sipshrms.Url.BaseUrlActivity;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.sips.sipshrms.Constant.login;

@AndroidEntryPoint
public class LoginActivity extends BaseActivity {

    Button bt_login,btnsipss,btnstll,btnsmas,btnsips;
    LinearLayout ll_main_layout;
    EditText ed_empid,ed_emppass;
    CheckBox chk_pass;
    String IMEI;
    SharedPresencesUtility sharedPresencesUtility;
    SessionManager sessionManager;
    String comp_prefix,comp_eid;
    String fcmToken;
    TextView forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotpass=(TextView) findViewById(R.id.forgot);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Forgotpass_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        bt_login = findViewById(R.id.bt_login);
        ll_main_layout = findViewById(R.id.ll_main_layout);
        ed_empid = findViewById(R.id.ed_empid);
        ed_emppass = findViewById(R.id.ed_emppass);
        chk_pass = findViewById(R.id.chk_pass);
        sessionManager = new SessionManager(getApplicationContext());
        sharedPresencesUtility = new SharedPresencesUtility(getApplicationContext());

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE) != PERMISSION_GRANTED) {
            return;
        }
        if(Constant.getInstance().type != null && !Constant.getInstance().type.isEmpty())
        Log.d("TAG", Constant.getInstance().type);

        try {
            IMEI = telephonyManager.getDeviceId();
            if (!IMEI.isEmpty()) {
                IMEI = telephonyManager.getDeviceId();
            } else {
                IMEI = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
//                Snackbar snackbar1 = Snackbar.make(ll_main_layout, "IMEI not supported", Snackbar.LENGTH_LONG);
//                snackbar1.show();
            }
        }
        catch(Exception e) {
            IMEI = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
//            Snackbar snackbar1 = Snackbar.make(ll_main_layout, "IMEI not supported", Snackbar.LENGTH_LONG);
//            snackbar1.show();
        }
       // IMEI="89898989898";

        chk_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    ed_emppass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    ed_emppass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                 fcmToken = instanceIdResult.getToken();
                Log.i("FID",fcmToken);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_empid.getText().toString().isEmpty())
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please enter employee id", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }if (ed_empid.getText().toString().length()<6)
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please enter valid id", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }
                else if(ed_emppass.getText().toString().isEmpty())
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please enter password", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else if(ed_emppass.getText().toString().length()<2)
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please enter valid passwoord", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }
                else if(!Connectivity.isConnected(getApplicationContext()))
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }
                else
                {
                    comp_prefix = ed_empid.getText().toString().substring(0, 4);  // gives "How ar"
                    comp_eid = ed_empid.getText().toString().substring(4,ed_empid.getText().toString().length());

                    loginuser();
                }

            }
        });
    }

    private void loginuser() {
        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
      // Constant.getInstance().type =Constant.getInstance().sips;

        String url = urlmain+"loginUser";

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


                                JSONObject userrespo = obj.getJSONObject("User_data");
                                String userid = userrespo.getString("user_id");
                                String emp_id = userrespo.getString("emp_id");
                                String full_name = userrespo.getString("full_name");
                                String email_off = userrespo.getString("email_off");
                                String department_name = userrespo.getString("department_name");

                                String designation_name = userrespo.getString("designation_name");
                                String company_id = userrespo.getString("company_id");
                                String profileImg = userrespo.getString("profileImg");
                                String type = userrespo.getString("type");
                                preferenceUtils.setType(type);

                                if(profileImg.matches("0"))
                                {
                                    profileImg = "man.png";
                                }

                                Snackbar snackbar1 = Snackbar.make(ll_main_layout, respmessage, Snackbar.LENGTH_LONG);
                                snackbar1.show();

                                sessionManager.createLoginSession(emp_id, email_off);

                                sharedPresencesUtility.setEmpId(getApplicationContext(),emp_id);
                                sharedPresencesUtility.setCompId(getApplicationContext(),company_id);
                                sharedPresencesUtility.setUserId(getApplicationContext(),userid);
                                sharedPresencesUtility.setUserName(getApplicationContext(),full_name);
                                sharedPresencesUtility.setProfileImg(getApplicationContext(),profileImg);
                                sharedPresencesUtility.setUserDesignation(getApplicationContext(),designation_name);
                                sharedPresencesUtility.setIMEI(getApplicationContext(),IMEI);
                                Intent intent = new Intent(LoginActivity.this, NewMainActivity.class);
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

               // params.put("IMEI_NO","866078040724915");
               // params.put("IMEI_NO","866783041257050");
                params.put("IMEI_NO",IMEI);
                params.put("EMP_ID",comp_eid);
                params.put("FCM_ID",fcmToken);
                params.put("EMP_PASSWORD",ed_emppass.getText().toString().trim());
                params.put("COMP_PREFIX",comp_prefix.toUpperCase());

                return params;
            }
        };
        queue.add(postRequest);
    }
}
