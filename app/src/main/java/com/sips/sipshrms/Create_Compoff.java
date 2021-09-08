package com.sips.sipshrms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.sips.sipshrms.Common.ChangePassword;
import com.sips.sipshrms.Common.LoginActivity;
import com.sips.sipshrms.Common.NewMainActivity;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.SessionManager;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.LeaveModule.AddleaveActivity;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Create_Compoff extends BaseActivity implements View.OnClickListener {
    private int mYear, mMonth, mDay;
EditText comoffreasions;
Button comoffadd,comoffdate,comoffcanceluser;
    SharedPresencesUtility sharedPresence;
    SessionManager sessionManager;
  //  String EMP_ID="E-3150";

   // int DATE=2021-2-7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__compoff);
        comoffdate = (Button) findViewById(R.id.comoffed_date);
        comoffcanceluser = (Button) findViewById(R.id.compoffcancel);
        comoffreasions = (EditText) findViewById(R.id.comoffed_reason);
        comoffadd = (Button) findViewById(R.id.save);
        comoffdate.setOnClickListener(Create_Compoff.this);
        comoffcanceluser.setOnClickListener(Create_Compoff.this);
        comoffadd.setOnClickListener(Create_Compoff.this);
        sessionManager = new SessionManager(getApplicationContext());
        sharedPresence =  new SharedPresencesUtility(getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        if (v == comoffdate) {

// Get Current Date
            final Calendar c = Calendar.getInstance();
            mDay = c.get(Calendar.DAY_OF_MONTH);
            mMonth = c.get(Calendar.MONTH);
            mYear = c.get(Calendar.YEAR);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            comoffdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


//                            Date date = null;
//                            try {
//                                date = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(comoffdate));
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setCalendarViewShown(false);
            datePickerDialog.getDatePicker().setSpinnersShown(true);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime() - 10000);
            datePickerDialog.show();
        }
        //   if (v == btnTimePicker) {

// Get Current Time
        //  final Calendar c = Calendar.getInstance();
        //   mHour = c.get(Calendar.HOUR_OF_DAY);
        //  mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        // TimePickerDialog timePickerDialog = new TimePickerDialog(this,
        //    new TimePickerDialog.OnTimeSetListener() {

        //        @Override
        //          public void onTimeSet(TimePicker view, int hourOfDay,
        //                              int minute) {

        //        txtTime.setText(hourOfDay + ":" + minute);
        //     }
        //  }, mHour, mMinute, false);
        //  timePickerDialog.show();
        //  }

        if (v == comoffcanceluser) {

            Intent intent = new Intent(Create_Compoff.this, NewMainActivity.class);
           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
        if (v == comoffadd) {

            if (comoffreasions.getText().toString().isEmpty()) {
                Toast.makeText(Create_Compoff.this, "Please Fill All fields", Toast.LENGTH_SHORT).show();
                return;
            }

           compofffunc();

        }

    }

    private void compofffunc() {

        final ProgressDialog progressDialog = new ProgressDialog(Create_Compoff.this);
        progressDialog.show();
        progressDialog.setMessage("Progress is continue Your activity");

        //  showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain +"addCompOff";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        // response
                        Log.d("Response", response);
                        Log.i("Responseee", response);
                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("Response", obj.toString());
                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");
                            JSONObject json = new JSONObject(response);
                            JSONObject body = json.getJSONObject("Response");
                            String msg = body.getString("msg_out");
                            Toast.makeText(Create_Compoff.this, msg, Toast.LENGTH_LONG).show();


                            Log.i("Resp1", success);
                            if (success.equals("0")) {

                                progressDialog.dismiss();


                             //   hideProgressBar();
                        //        Snackbar snackbar1 = Snackbar.make(ll_main_layout, respmessage, Snackbar.LENGTH_LONG);
                        //        snackbar1.show();
                                //sessionManager.logoutUser();
//                                Intent intent = new Intent(Create_Compoff.this, Create_Compoff.class);
//                                startActivity(intent);
//                                finish();
                            } else {
                              //  hideProgressBar();

                             //   Snackbar snackbar1 = Snackbar.make(ll_main_layout, respmessage, Snackbar.LENGTH_LONG);
                            //    snackbar1.show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                         //   hideProgressBar();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

               // params.put("emp_id",EMP_ID);
               params.put("date",comoffdate.getText().toString().trim());
               params.put("emp_id",sharedPresence.getEmpId(getApplicationContext()));
               params.put("reason",  comoffreasions.getText().toString().trim());
                return params;
            }
        };
        queue.add(postRequest);
    }

//String TAG = "CreateCompoff";

    }
