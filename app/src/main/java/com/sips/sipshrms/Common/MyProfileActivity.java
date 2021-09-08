package com.sips.sipshrms.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.LeaveModule.LeaveAdapter;
import com.sips.sipshrms.LeaveModule.Leavepojo;
import com.sips.sipshrms.LeaveModule.ViewLeaveActivity;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends BaseActivity {


    TextView tv_name,tv_empid,tv_designation,tv_dob,tv_gender,tv_email,tv_location;

    SharedPresencesUtility sharedPresence ;
    ImageView profile;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        tv_name = findViewById(R.id.tv_name);
        tv_empid = findViewById(R.id.tv_empid);
        profile = findViewById(R.id.profile);
        tv_designation = findViewById(R.id.tv_designation);
        tv_dob = findViewById(R.id.tv_dob);
        tv_gender = findViewById(R.id.tv_gender);
        tv_email = findViewById(R.id.tv_email);
        tv_location = findViewById(R.id.tv_location);
        back = findViewById(R.id.back);
        sharedPresence =  new SharedPresencesUtility(getApplicationContext());

        loaddetails();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loaddetails() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getempdetails";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            hideProgressBar();
                            JSONObject obj = new JSONObject(response);
                            Log.e("Responce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");



                            Log.i("Resp1", success);
                            if (success.equals("200")) {


                                JSONArray heroArray = obj.getJSONArray("Response");
                                for (int i = 0; i < heroArray.length(); i++) {

                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    String full_name = heroObject.getString("full_name");
                                    String joining_date = heroObject.getString("joining_date");
                                    String gender_name = heroObject.getString("gender_name");
                                    String department_name = heroObject.getString("department_name");
                                    String employee_code = heroObject.getString("employee_code");
                                    String location_name = heroObject.getString("location_name");
                                    String email_off = heroObject.getString("email_off");
                                    String profileImg = heroObject.getString("profileImg");
                                    if(profileImg.matches("0"))
                                    {
                                        profileImg = "man.png";
                                    }


                                    Log.i("Responce1",full_name);
                                    tv_name.setText(full_name);
                                    tv_dob.setText(joining_date);
                                    tv_gender.setText(gender_name);
                                    tv_designation.setText(department_name);
                                    tv_empid.setText(employee_code);
                                    tv_email.setText(email_off);
                                    tv_location.setText(location_name);
                                    Glide.with(getApplicationContext()).load(urlimage+profileImg).into(profile);

                                }


                            }
                            else if (success.equals("0"))
                            {


                            }else {
                                Toast.makeText(getApplicationContext(), respmessage, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

                params.put("users_id",sharedPresence.getEmpId(getApplicationContext()));
                params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));
                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,

                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
