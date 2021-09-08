package com.sips.sipshrms.Attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class ViewAttendanceList extends BaseActivity
{
    private RecyclerView recyclerView;
    private ArrayList<Attendancepojo> attendancearray;
    private ListAttendanceviewAdapter callistAdapter;


    ArrayList<HashMap<String, String>> arryyear,arrymonth;
    Spinner spin_years,spin_months;

    String strmonths,strmonthsid,stryears,stryearsid;

    ImageView img_cal;

    SharedPresencesUtility sharedPresence ;
    ImageView back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance_list);
        recyclerView = findViewById(R.id.recycler_calander);
        img_cal = findViewById(R.id.img_cal);
        spin_years = findViewById(R.id.spin_years);
        spin_months = findViewById(R.id.spin_months);
        back = findViewById(R.id.back);

        sharedPresence =  new SharedPresencesUtility(getApplicationContext());
        attendancearray = new ArrayList<Attendancepojo>();
        arryyear = new ArrayList<HashMap<String, String>>();
        arrymonth = new ArrayList<HashMap<String, String>>();
        loadYearsList();
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAttendanceList.this, ViewAttendanceCalender.class);
                startActivity(intent);
                finish();
            }
        });

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
        attendancearray.clear();
        loadAttendanceList() ;
    }

    private void loadAttendanceList() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getattendancebyusermonth";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("Response7676", response);

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

                                    String day = heroObject.getString("day");
                                    String weekday = heroObject.getString("weekday");
                                    String out_time = heroObject.getString("out_time");
                                    String in_time = heroObject.getString("in_time");
                                    String in_out_time = heroObject.getString("in_out_time");
                                    String leave_type_code = heroObject.getString("leave_type_code");

                                    String[] prompts = {"#3857e2","#208e4c","#9711a3","#ed761c","ed1c45"};



                                    if(leave_type_code.matches("0"))
                                    {
                                        leave_type_code="";
                                    }
                                    if(in_out_time.matches("0"))
                                    {
                                        in_out_time="";
                                    }
                                    int x = new Random().nextInt(prompts.length);
                                    Attendancepojo homeVersion = new Attendancepojo();
                                    homeVersion.setDay(day);
                                    homeVersion.setIn_out_time(in_out_time);
                                    homeVersion.setIn_time(in_time);
                                    homeVersion.setOut_time(out_time);
                                    homeVersion.setLeave_type_code(leave_type_code);
                                    homeVersion.setWeekday(weekday);
                                    attendancearray.add(homeVersion);
                                    callistAdapter = new ListAttendanceviewAdapter(attendancearray, ViewAttendanceList.this);
                                    recyclerView.setAdapter(callistAdapter);
                                }


                            }
                            else if (success.equals("0"))
                            {

                                attendancearray.clear();
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
                params.put("month",strmonthsid);
                //params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void loadYearsList() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+ "getyearslist";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("Responce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");
                            hideProgressBar();

                            Log.i("Resp1", success);
                            if (success.equals("200")) {

                                JSONArray heroArray = obj.getJSONArray("Response");
                                for (int i = 0; i < heroArray.length(); i++) {

                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    String fy_id = heroObject.getString("fy_id");
                                    String fy_name = heroObject.getString("fy_name");

                                    HashMap<String, String> citynameagain = new HashMap<>();

                                    citynameagain.put("fy_id", fy_id);
                                    citynameagain.put("fy_name", fy_name);
                                    arryyear.add(citynameagain);

                                }
                                SimpleAdapter adep = new SimpleAdapter(ViewAttendanceList.this, arryyear, R.layout.black_spinner_item, new String[]{"fy_id", "fy_name"}, new int[]{R.id.brid2, R.id.brid});
                                spin_years.setAdapter(adep);
                                spinnersetyears();

                            } else {
                                // Toast.makeText(getApplicationContext(), respmessage, Toast.LENGTH_LONG).show();
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
                params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));
                return params;
            }
        };
        queue.add(postRequest);
    }


    void spinnersetyears() {
        spin_years.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                stryears = spin_years.getItemAtPosition(position).toString();

                String[] elements = stryears.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                stryearsid = elej[1].toString();
                Log.i("checkmeup", stryearsid);
                arrymonth.clear();
                loadMonthsList();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                String[] elements = stryears.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                stryearsid = elej[1].toString();
                Log.i("checkmeup", stryearsid);

            }
        });

    }

    private void loadMonthsList() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+ "getmonthslist";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("Responce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");
                            hideProgressBar();

                            Log.i("Resp1", success);
                            if (success.equals("200")) {

                                JSONArray heroArray = obj.getJSONArray("Response");
                                for (int i = 0; i < heroArray.length(); i++) {

                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    String month_code = heroObject.getString("month_code");
                                    String month_name = heroObject.getString("month_name");

                                    HashMap<String, String> citynameagain = new HashMap<>();

                                    citynameagain.put("month_code", month_code);
                                    citynameagain.put("month_name", month_name);
                                    arrymonth.add(citynameagain);

                                }
                                SimpleAdapter adep = new SimpleAdapter(ViewAttendanceList.this, arrymonth, R.layout.black_spinner_item, new String[]{"month_code", "month_name"}, new int[]{R.id.brid2, R.id.brid});
                                spin_months.setAdapter(adep);
                                spinnersetmonths();

                            } else {
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
                params.put("year_id",stryearsid);
                params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));
                return params;
            }
        };
        queue.add(postRequest);
    }


    void spinnersetmonths() {
        spin_months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strmonths = spin_months.getItemAtPosition(position).toString();
                String[] elements = strmonths.split(",");
                String brch = elements[1].toString();
                String[] elej = brch.split("=");
                strmonthsid = elej[1].toString();

                strmonthsid = strmonthsid.replace("}","");
                Log.i("checkmeup", strmonthsid);

                locinitViews();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String[] elements = strmonths.split(",");
                String brch = elements[1].toString();
                String[] elej = brch.split("=");
                strmonthsid = elej[1].toString();

                strmonthsid = strmonthsid.replace("}","");
            }
        });

    }
}
