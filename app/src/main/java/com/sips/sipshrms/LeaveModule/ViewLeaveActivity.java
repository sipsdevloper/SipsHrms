package com.sips.sipshrms.LeaveModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sips.sipshrms.Attendance.Attendancepojo;
import com.sips.sipshrms.Attendance.ListAttendanceviewAdapter;
import com.sips.sipshrms.Attendance.ViewAttendanceCalender;
import com.sips.sipshrms.Attendance.ViewAttendanceList;
import com.sips.sipshrms.Common.NewMainActivity;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ViewLeaveActivity extends BaseActivity {

    FloatingActionButton ft_addleave;
    private RecyclerView recyclerView;
    private ArrayList<Leavepojo> leavearray;
    private LeaveAdapter lvlistAdapter;
    SharedPresencesUtility sharedPresence ;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_leave);
        ft_addleave = findViewById(R.id.ft_addleave);
        recyclerView = findViewById(R.id.recycler_view);
        back = findViewById(R.id.back);
        sharedPresence =  new SharedPresencesUtility(getApplicationContext());
        leavearray = new ArrayList<Leavepojo>();
        locinitViews();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ft_addleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLeaveActivity.this, AddleaveActivity.class);
                startActivity(intent);
            }
        });
    }

    private void locinitViews() {


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        loadLeaveList() ;
    }

    private void loadLeaveList() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getmyleaveslist";

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
                                    String createdate = heroObject.getString("createdate");
                                    String date_from = heroObject.getString("date_from");
                                    String date_to = heroObject.getString("date_to");
                                    String leave_count = heroObject.getString("leave_count");

                                    String leave_type_name = heroObject.getString("leave_type_name");
                                    String reason = heroObject.getString("reason");
                                    String status_name = heroObject.getString("status_name");
                                    String leave_id = heroObject.getString("leave_id");
                                    String from_session_name = heroObject.getString("from_session_name");
                                    String to_session_name = heroObject.getString("to_session_name");
                                    String in_time_a = heroObject.getString("in_time_a");
                                    String out_time_a = heroObject.getString("out_time_a");
                                    String in_time_d = heroObject.getString("in_time_d");
                                    String out_time_d = heroObject.getString("out_time_d");


                                    Leavepojo homeVersion = new Leavepojo();
                                    homeVersion.setCreatedate(createdate);
                                    homeVersion.setDateFrom(date_from);
                                    homeVersion.setDateTo(date_to);
                                    homeVersion.setLeaveCount(leave_count);
                                    homeVersion.setLeaveTypeName(leave_type_name);
                                    homeVersion.setReason(reason);
                                    homeVersion.setStatusName(status_name);
                                    homeVersion.setLeave_id(leave_id);
                                    homeVersion.setFrom_session_name(from_session_name);
                                    homeVersion.setTo_session_name(to_session_name);
                                    homeVersion.setIn_time_a(in_time_a);
                                    homeVersion.setOut_time_a(out_time_a);
                                    homeVersion.setIn_time_d(in_time_d);
                                    homeVersion.setOut_time_d(out_time_d);


                                    leavearray.add(homeVersion);
                                    lvlistAdapter = new LeaveAdapter(leavearray, ViewLeaveActivity.this);
                                    recyclerView.setAdapter(lvlistAdapter);
                                }


                            }
                            else if (success.equals("0"))
                            {

                                leavearray.clear();
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
             //   params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));

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
