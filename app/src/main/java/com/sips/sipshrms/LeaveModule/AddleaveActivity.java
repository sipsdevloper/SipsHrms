package com.sips.sipshrms.LeaveModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.sips.sipshrms.Attendance.ViewAttendanceCalender;
import com.sips.sipshrms.Common.LeaveBalanceCAdapter;
import com.sips.sipshrms.Common.LeaveBalanceCMoodel;
import com.sips.sipshrms.Common.MainActivity;
import com.sips.sipshrms.Common.ManpowerPlanningForm;
import com.sips.sipshrms.Common.NewMainActivity;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddleaveActivity extends BaseActivity {

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Spinner spin_ltype;
    EditText ed_datefrom,ed_dateto,ed_leavecount,ed_lreason,ed_balance;
    ArrayList<HashMap<String, String>> arryleavetype;
    String strltype,strltypeid;
    LinearLayout ll_main_layout;
    long diff;
    Button bt_save;
    double countdays;

    Spinner spin_shiftfrom,spin_shiftto;
    String[] shiftlist = { "First Half", "Second Half"};

    LinearLayout ll_otherlayout,ll_displayout;
    EditText ed_dispdatae,ed_intime,ed_outtime;

    String strshiftfromid,strshifttoid;

    SharedPresencesUtility sharedPresence ;
    ImageView back;
    TextView ed_intimeorg,ed_outtimeorg;

    private RecyclerView leavecrecyclerView;
    private ArrayList<LeaveBalanceCMoodel> leavecarray;
    private LeaveBalanceCAdapter leavelistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addleave);
        spin_ltype = findViewById(R.id.spin_ltype);
        ed_datefrom = findViewById(R.id.ed_datefrom);
        ed_dateto = findViewById(R.id.ed_dateto);
        ed_leavecount = findViewById(R.id.ed_leavecount);
        ed_lreason = findViewById(R.id.ed_lreason);
        ed_balance = findViewById(R.id.ed_balance);
        ll_main_layout = findViewById(R.id.ll_main_layout);
        spin_shiftfrom = findViewById(R.id.spin_shiftfrom);
        spin_shiftto = findViewById(R.id.spin_shiftto);
        ed_intimeorg = findViewById(R.id.ed_intimeorg);
        ed_outtimeorg = findViewById(R.id.ed_outtimeorg);
        back = findViewById(R.id.back);
        leavecrecyclerView = findViewById(R.id.rec_leave_count);
        leavecarray = new ArrayList<LeaveBalanceCMoodel>();

        sharedPresence =  new SharedPresencesUtility(getApplicationContext());


        ll_otherlayout = findViewById(R.id.ll_otherlayout);
        ll_displayout = findViewById(R.id.ll_displayout);
        ed_dispdatae = findViewById(R.id.ed_dispdatae);
        ed_intime = findViewById(R.id.ed_intime);
        ed_outtime = findViewById(R.id.ed_outtime);


        bt_save = findViewById(R.id.bt_save);
        myCalendar = Calendar.getInstance();
        arryleavetype = new ArrayList<HashMap<String, String>>();
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,shiftlist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_shiftto.setAdapter(aa);
        spin_shiftfrom.setAdapter(aa);
        loadLeaveTypeList();
        locinitteamccount();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ed_datefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        updateFromdate();
                    }

                };

                new DatePickerDialog(AddleaveActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        ed_dateto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date = new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        updateTodate();

                    }


                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddleaveActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();




            }
        });
        ed_dispdatae.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date = new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        ed_datefrom.setText("");
                        ed_dateto.setText("");
                        updateDispdate();

                    }


                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddleaveActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();




            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(ed_leavecount.getText().toString().matches("Invalid date")){
                   Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please select proper date", Snackbar.LENGTH_LONG);
                   snackbar1.show();
               }
               else if(Float.parseFloat(ed_leavecount.getText().toString())<=0){
                   Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please select proper date  ", Snackbar.LENGTH_LONG);
                   snackbar1.show();
               }
               else if(ed_lreason.getText().toString().isEmpty()){
                   Snackbar snackbar1 = Snackbar.make(ll_main_layout, "Please enter reason ", Snackbar.LENGTH_LONG);
                   snackbar1.show();
               }

               else{
                   addleave();
               }

            }
        });

        ed_intime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddleaveActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ed_intime.setText(String.format("%02d:%02d", selectedHour, selectedMinute) );
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        ed_outtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddleaveActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ed_outtime.setText( String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



        spinnersetmethodshiftfrom();
        spinnersetmethodshiftto();
    }

    private void updateFromdate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String reqdate = sdf.format(myCalendar.getTime());
        ed_datefrom.setText(reqdate);
        converdeiffdate();
    }

    private void updateTodate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String reqdate = sdf.format(myCalendar.getTime());
        ed_dateto.setText(reqdate);
        converdeiffdate();
    }
    private void updateDispdate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String reqdate = sdf.format(myCalendar.getTime());
        ed_dispdatae.setText(reqdate);
        ed_datefrom.setText(reqdate);
        ed_dateto.setText(reqdate);
        ed_leavecount.setText("1");
        loadinoutorignal();
    }
    private void converdeiffdate()
    {
        ed_leavecount.setText("");
        if (!ed_datefrom.getText().toString().isEmpty()&& !ed_dateto.getText().toString().isEmpty())
        {

          SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
           try {
                Date date1 = myFormat.parse(ed_datefrom.getText().toString().trim());
                Date date2 = myFormat.parse(ed_dateto.getText().toString().trim());
                diff = date2.getTime() - date1.getTime();
                countdays = (diff / (1000*60*60*24));
                countdays=  countdays+1;


                if(countdays <= 0.0 )
                {
                    ed_leavecount.setText("Invalid date");
                }else if(countdays==1 && strshiftfromid.matches("2") && strshifttoid.matches("1") )
                {
                    ed_leavecount.setText("Invalid date");
                }
                else if(countdays>1 && strshiftfromid.matches("2") && strshifttoid.matches("1") )
                {
                    countdays = countdays - 1 ;
                    ed_leavecount.setText(String.valueOf(countdays));
                }
                else if(strshiftfromid.matches(strshifttoid))
                {
                  countdays = countdays -  0.5 ;
                  ed_leavecount.setText(String.valueOf(countdays));
                }
                else
                {
                    ed_leavecount.setText(String.valueOf(countdays));
                }



            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    private void locinitteamccount() {


        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(AddleaveActivity.this, LinearLayoutManager.HORIZONTAL, false);
        leavecrecyclerView.setLayoutManager(horizontalLayoutManager);
        leavecrecyclerView.setHasFixedSize(true);
        leavecarray.clear();
        loadLeaveBalanceCount();
    }
    private void loadLeaveTypeList() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+ "getleavetype";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("Responce", obj.toString());
                            hideProgressBar();
                            String success = obj.getString("ResponseCode");
                            //String respmessage = obj.getString("ResponseMessage");


                            Log.i("Resp1", success);
                            if (success.equals("200")) {

                                JSONArray heroArray = obj.getJSONArray("Response");
                                for (int i = 0; i < heroArray.length(); i++) {

                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    String leave_type_id = heroObject.getString("leave_type_id");
                                    String leave_type_name = heroObject.getString("leave_type_name");
                                    String leave_type_code = heroObject.getString("leave_type_code");
                                    leave_type_name = leave_type_name+" ("+ leave_type_code +") ";

                                    HashMap<String, String> citynameagain = new HashMap<>();

                                    citynameagain.put("leave_type_id", leave_type_id);
                                    citynameagain.put("leave_type_name", leave_type_name);
                                    arryleavetype.add(citynameagain);

                                }
                                SimpleAdapter adep = new SimpleAdapter(AddleaveActivity.this, arryleavetype, R.layout.black_spinner_item, new String[]{"leave_type_id", "leave_type_name"}, new int[]{R.id.brid2, R.id.brid});
                                spin_ltype.setAdapter(adep);
                                spinnersetmethodleave();

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
                // params.put("t_date","2019-12-13");
                //  params.put("t_date",tdf.format(new Date()));

                return params;
            }
        };
        queue.add(postRequest);
    }


    void spinnersetmethodleave() {
        spin_ltype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strltype = spin_ltype.getItemAtPosition(position).toString();
                ed_balance.setText("");

                String[] elements = strltype.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                strltypeid = elej[1].toString();
                Log.i("checkmeup", strltypeid);

                if (strltypeid.matches("19"))
                {
                    ed_leavecount.setText("1");
                    ll_displayout.setVisibility(View.VISIBLE);
                    ll_otherlayout.setVisibility(View.GONE);


                }else{
                    ed_leavecount.setText("0");
                    ll_displayout.setVisibility(View.GONE);
                    ll_otherlayout.setVisibility(View.VISIBLE);

                    loadLeaveBalance();
                }






            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                String[] elements = strltype.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                strltypeid = elej[1].toString();

                Log.i("checkmeup", strltypeid);

            }
        });

    }
    void spinnersetmethodshiftfrom() {
        spin_shiftfrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String strshiftfrom = spin_shiftfrom.getItemAtPosition(position).toString();
                if (strshiftfrom.matches("First Half"))
                {
                    strshiftfromid = "1";
                }
                else
                {
                    strshiftfromid = "2";
                }
                ed_leavecount.setText("");

                converdeiffdate();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    void spinnersetmethodshiftto() {
        spin_shiftto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String strshiftto = spin_shiftto.getItemAtPosition(position).toString();
                Log.i("SHIFT",strshiftto);
                if (strshiftto.matches("First Half"))
                {

                    strshifttoid = "1";
                }
                else
                {
                    strshifttoid = "2";
                }

                ed_leavecount.setText("");
                converdeiffdate();




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadLeaveBalance() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+ "getleavecount";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responce7676", response);
                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("Responce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");
                            hideProgressBar();

                            Log.i("Resp1", success);
                            if (success.equals("200")) {

                                JSONObject heroArray = obj.getJSONObject("Response");
                                String balance = heroArray.getString("Balance");

                                ed_balance.setText(balance);

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
                params.put("emp_id",sharedPresence.getEmpId(getApplicationContext()));
                params.put("leave_type",strltypeid);
                return params;
            }
        };
        queue.add(postRequest);
    }
    private void loadinoutorignal() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+ "getempdate_time";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responce7676", response);
                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("Responce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");
                            hideProgressBar();

                            Log.i("Resp1", success);
                            if (success.equals("200")) {

                                JSONObject heroArray = obj.getJSONObject("Response");
                                String in_time = heroArray.getString("in_time");
                                String out_time = heroArray.getString("out_time");

                                ed_intimeorg.setText(in_time);
                                ed_outtimeorg.setText(out_time);

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
                params.put("emp_id",sharedPresence.getEmpId(getApplicationContext()));
                params.put("sel_date",ed_dispdatae.getText().toString().trim());
                return params;
            }
        };
        queue.add(postRequest);
    }

    private void addleave() {

        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+ "addLeave";

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

                                JSONObject heroArray = obj.getJSONObject("Response");
                                String msg_out = heroArray.getString("msg_out");
                                if (msg_out.matches("Leave applied sucessfully")){
                                    Intent intent = new Intent(AddleaveActivity.this, ViewLeaveActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                else{

                                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, msg_out, Snackbar.LENGTH_LONG);
                                    snackbar1.show();
                                    hideProgressBar();
                                }



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

                Log.i("Responseee1",ed_datefrom.getText().toString().trim()+" "+ ed_dateto.getText().toString().trim()
                + "  "+strshiftfromid+" "+strshifttoid+"  "+ ed_intime.getText().toString()+"  "+ed_outtime.getText().toString()+"   "+ed_leavecount.getText().toString()
                        +ed_leavecount.getText().toString()+"   "+strltypeid+"  "+ed_lreason.getText().toString().trim() );

                params.put("emp_id",sharedPresence.getUserId(getApplicationContext()));
                params.put("date_from",ed_datefrom.getText().toString().trim());
                params.put("date_to",ed_dateto.getText().toString().trim());
                params.put("from_ses_id",strshiftfromid);
                params.put("to_ses_id",strshifttoid);
                params.put("in_time",ed_intime.getText().toString());
                params.put("out_time",ed_outtime.getText().toString());
                params.put("leave_count",ed_leavecount.getText().toString());
                params.put("leave_type_id",strltypeid);
                params.put("status_id","1");
                params.put("reason",ed_lreason.getText().toString().trim());
                return params;
            }
        };
        queue.add(postRequest);
    }

    private void loadLeaveBalanceCount() {
        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getleavebalance";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("REsponce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");
                            Log.i("Resp1", success);
                            if (success.equals("200")) {
                                JSONArray heroArray = obj.getJSONArray("Response");
                                for (int i = 0; i < heroArray.length(); i++) {

                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    String leave_type_name = heroObject.getString("leave_type_name");
                                    String balance_leave_count = heroObject.getString("balance_leave_count");

                                    LeaveBalanceCMoodel homeVersion = new LeaveBalanceCMoodel();
                                    homeVersion.setBalance_leave_count(balance_leave_count);
                                    homeVersion.setLeave_type_name(leave_type_name);



                                    leavecarray.add(homeVersion);
                                    leavelistAdapter = new LeaveBalanceCAdapter(leavecarray, AddleaveActivity.this);
                                    leavecrecyclerView.setAdapter(leavelistAdapter);
                                }




                                hideProgressBar();



                            } else {


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
                params.put("emp_id",sharedPresence.getUserId(getApplicationContext()));
                // params.put("t_date","2019-12-13");
                //  params.put("t_date",tdf.format(new Date()));

                return params;
            }
        };
        queue.add(postRequest);
    }
}
