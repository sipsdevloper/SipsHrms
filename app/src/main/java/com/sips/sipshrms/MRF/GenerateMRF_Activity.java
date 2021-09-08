package com.sips.sipshrms.MRF;

import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Common.MainActivity;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateMRF_Activity extends BaseActivity {

    LinearLayout ll_main_layout;
    Spinner spin_designation,spin_rebyclient,spin_gender;
    String strdesign,strdesignid;
    ArrayList<HashMap<String, String>> arrydesignation;

    EditText ed_no_vac,ed_consd,ed_reqdur,ed_location,ed_vac_reason,ed_replacement,ed_keyresp;
    EditText ed_eduqual,ed_agelmt,ed_maxdate,ed_maxyr,ed_minyr,ed_budget;
    Button bt_save;
    String strrbyclnt,strgender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_mrf_);
        ll_main_layout = findViewById(R.id.ll_main_layout);
        spin_designation = findViewById(R.id.spin_designation);
        spin_rebyclient = findViewById(R.id.spin_rebyclient);
        spin_gender = findViewById(R.id.spin_gender);
        bt_save = findViewById(R.id.bt_save);
        ed_no_vac = findViewById(R.id.ed_no_vac);
        ed_consd = findViewById(R.id.ed_consd);
        ed_reqdur = findViewById(R.id.ed_reqdur);
        ed_location = findViewById(R.id.ed_location);
        ed_vac_reason = findViewById(R.id.ed_vac_reason);
        ed_replacement = findViewById(R.id.ed_replacement);
        ed_keyresp = findViewById(R.id.ed_keyresp);
        ed_eduqual = findViewById(R.id.ed_eduqual);
        ed_agelmt = findViewById(R.id.ed_agelmt);
        ed_maxdate = findViewById(R.id.ed_maxdate);
        ed_maxyr = findViewById(R.id.ed_maxyr);
        ed_minyr = findViewById(R.id.ed_minyr);
        ed_budget = findViewById(R.id.ed_budget);


        arrydesignation = new ArrayList<HashMap<String, String>>();
        List<String> gendercat = new ArrayList<String>();
        gendercat.add("Select");
        gendercat.add("Male");
        gendercat.add("Female");
        gendercat.add("Any");
        List<String> reqarise = new ArrayList<String>();
        reqarise.add("Select");
        reqarise.add("Yes");
        reqarise.add("No");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gendercat);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, reqarise);

        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_rebyclient.setAdapter(dataAdapter2);
        spin_gender.setAdapter(dataAdapter);
        spin_rebyclient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strrbyclnt = spin_rebyclient.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strgender = spin_gender.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadDesignationList();
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatemrffun();
            }
        });

    }

    private void generatemrffun() {
        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"insertmrf";

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

                                Snackbar snackbar1 = Snackbar.make(ll_main_layout, respmessage, Snackbar.LENGTH_LONG);
                                snackbar1.show();

                                Intent intent = new Intent(GenerateMRF_Activity.this, MainActivity.class);
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
                params.put("ind_department_id","1");
                params.put("ind_designation_id","1");
                params.put("req_designation_id",strdesignid);
                params.put("required_by_date","");
                params.put("vacancies",ed_no_vac.getText().toString().trim());
                params.put("project_bidding",ed_consd.getText().toString().trim());
                params.put("req_duration",ed_reqdur.getText().toString().trim());
                params.put("location",ed_location.getText().toString().trim());
                params.put("raised_by_client",strrbyclnt);
                params.put("reason",ed_consd.getText().toString().trim());
                params.put("is_replacement",ed_vac_reason.getText().toString().trim());
                params.put("replacement_in_place","");
                params.put("key_responsibilities",ed_keyresp.getText().toString().trim());
                params.put("req_education",ed_eduqual.getText().toString().trim());
                params.put("age_limit",ed_agelmt.getText().toString().trim());
                params.put("gender",strgender);
                params.put("budget_amount",ed_budget.getText().toString().trim());
                params.put("min_experience",ed_minyr.getText().toString().trim());
                params.put("max_experience",ed_maxyr.getText().toString().trim());
                params.put("userid","");

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void loadDesignationList() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getdesignationname";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
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
                                    String DESIGNATION_ID = heroObject.getString("DESIGNATION_ID");
                                    String DESIGNATION_NAME = heroObject.getString("DESIGNATION_NAME");

                                    // strarrydeptname.add(DEPT_ID + " " + DEPT_NAME);

                                    HashMap<String, String> citynameagain = new HashMap<>();

                                    citynameagain.put("DESIGNATION_ID", DESIGNATION_ID);
                                    citynameagain.put("DESIGNATION_NAME", DESIGNATION_NAME);
                                    arrydesignation.add(citynameagain);

                                }
                                SimpleAdapter adep = new SimpleAdapter(GenerateMRF_Activity.this, arrydesignation, R.layout.black_spinner_item, new String[]{"DESIGNATION_ID", "DESIGNATION_NAME"}, new int[]{R.id.brid2, R.id.brid});
                                spin_designation.setAdapter(adep);

                                spinnersetmethoddesignation();

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
                return params;
            }
        };
        queue.add(postRequest);
    }

    void spinnersetmethoddesignation() {
        spin_designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strdesign = spin_designation.getItemAtPosition(position).toString();

                String[] elements = strdesign.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                strdesignid = elej[1].toString();
                Log.i("checkmeup", strdesignid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                String[] elements = strdesign.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                String strdesignid = elej[1].toString();

                Log.i("checkmeup", strdesignid);

            }
        });

    }
}
