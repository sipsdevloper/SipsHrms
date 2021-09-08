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

public class HolidayListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ArrayList<HolidayModel> holarray;
    private HolidayListAdapter hollistAdapter;
    SharedPresencesUtility sharedPresence;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_list);
        recyclerView = findViewById(R.id.recycler_view);
        back = findViewById(R.id.back);
        holarray = new ArrayList<HolidayModel>();
        sharedPresence = new SharedPresencesUtility(getApplicationContext());
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
        loadLeaveList() ;
    }

    private void loadLeaveList() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getholidayslist";

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
                                    String holiday_date = heroObject.getString("holiday_date");
                                    String holiday_name = heroObject.getString("holiday_name");
                                    String holiday_day = heroObject.getString("week_day");




                                    HolidayModel homeVersion = new HolidayModel();
                                    homeVersion.setHoliday_date(holiday_date);
                                    homeVersion.setHoliday_name(holiday_name);
                                    homeVersion.setHoliday_day(holiday_day);


                                    holarray.add(homeVersion);
                                    hollistAdapter = new HolidayListAdapter(holarray, HolidayListActivity.this);
                                    recyclerView.setAdapter(hollistAdapter);
                                }


                            }
                            else if (success.equals("0"))
                            {

                                holarray.clear();
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

                params.put("users_id","E-1135");
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
