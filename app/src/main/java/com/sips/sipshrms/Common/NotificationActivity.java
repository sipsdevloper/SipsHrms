package com.sips.sipshrms.Common;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



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

public class NotificationActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ArrayList<NotificationModel> notifarray;
    private NotificationAdapter notiflistAdapter;

    SharedPresencesUtility sharedPresence ;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification);
        recyclerView = findViewById(R.id.recycler_view);
        back = findViewById(R.id.back);
        sharedPresence =  new SharedPresencesUtility(getApplicationContext());
        notifarray = new ArrayList<NotificationModel>();
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
        loadnotifList() ;
    }

    private void loadnotifList() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getNotif";

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

                                    String id = heroObject.getString("id");
                                    String employee_id = heroObject.getString("employee_id");
                                    String notification_type = heroObject.getString("notification_type");
                                    String message = heroObject.getString("message");
                                    String status = heroObject.getString("status");
                                    String userid = heroObject.getString("userid");
                                    String full_name = heroObject.getString("full_name");
                                    String profileImg = heroObject.getString("profileImg");
                                    if(profileImg.matches("0"))
                                    {
                                        profileImg = "man.png";
                                    }



                                    NotificationModel homeVersion = new NotificationModel();

                                    homeVersion.setId(id);
                                    homeVersion.setEmployee_id(employee_id);
                                    homeVersion.setNotification_type(notification_type);
                                    homeVersion.setMessage(message);
                                    homeVersion.setStatus(status);
                                    homeVersion.setUserid(userid);
                                    homeVersion.setFull_name(full_name);
                                    homeVersion.setProfileImg(profileImg);


                                    notifarray.add(homeVersion);
                                    notiflistAdapter = new NotificationAdapter(notifarray, NotificationActivity.this);
                                    recyclerView.setAdapter(notiflistAdapter);
                                }


                            }
                            else if (success.equals("0"))
                            {

                                notifarray.clear();
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

                params.put("emp_id",sharedPresence.getUserId(getApplicationContext()));

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
