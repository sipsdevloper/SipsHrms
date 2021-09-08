package com.sips.sipshrms.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.LeaveModule.HolidayListActivity;
import com.sips.sipshrms.LeaveModule.HolidayListAdapter;
import com.sips.sipshrms.LeaveModule.HolidayModel;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyTeamActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ArrayList<MyTeamModel> teamarray;
    private MyTeamAdapter teamlistAdapter;

    SharedPresencesUtility sharedPresence ;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        recyclerView = findViewById(R.id.recycler_view);
        back = findViewById(R.id.back);
        sharedPresence =  new SharedPresencesUtility(getApplicationContext());
        teamarray = new ArrayList<MyTeamModel>();
        locinitViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void locinitViews() {


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        loadmyteamList() ;
    }

    private void loadmyteamList() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getmyteam";

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
                                    String full_name = heroObject.getString("full_name").trim();
                                    String n_full_name = full_name.replaceAll("\\s+", " ");

                                    if (!n_full_name.matches(sharedPresence.getUserName(getApplicationContext()).toString())) {


                                        String joining_date = heroObject.getString("joining_date");
                                        String gender_name = heroObject.getString("gender_name");
                                        String department_name = heroObject.getString("department_name");
                                        String designation_name = heroObject.getString("designation_name");
                                        String own_contact1 = heroObject.getString("own_contact1");
                                        String email_off = heroObject.getString("email_off");
                                        String profileImg = heroObject.getString("profileImg");
                                        if (profileImg.matches("0")) {
                                            profileImg = "man.png";
                                        }


                                        if (designation_name.matches("0")) {
                                            designation_name = "";
                                        }

                                        MyTeamModel homeVersion = new MyTeamModel();
                                        homeVersion.setFull_name(full_name);
                                        homeVersion.setJoining_date(joining_date);
                                        homeVersion.setGender_name(gender_name);
                                        homeVersion.setDepartment_name(department_name);
                                        homeVersion.setDesignation_name(designation_name);
                                        homeVersion.setOwn_contact1(own_contact1);
                                        homeVersion.setEmail_off(email_off);
                                        homeVersion.setProfileImg(profileImg);


                                        teamarray.add(homeVersion);
                                        teamlistAdapter = new MyTeamAdapter(teamarray, MyTeamActivity.this);
                                        recyclerView.setAdapter(teamlistAdapter);
                                    }
                                }


                            }
                            else if (success.equals("0"))
                            {

                                teamarray.clear();
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

                params.put("emp_id",sharedPresence.getEmpId(getApplicationContext()));
                params.put("team_type","2");
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
