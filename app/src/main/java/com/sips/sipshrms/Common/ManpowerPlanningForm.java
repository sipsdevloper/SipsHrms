package com.sips.sipshrms.Common;

import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManpowerPlanningForm extends BaseActivity {

    LinearLayout ll_main_layout;
    Spinner spin_deptlist,spin_projectlist,spin_fylist,spin_complist;
    ArrayList<HashMap<String, String>> arrydeptname,arrycompname,arryfyname,arryprojectname;
    String strdept,strcomp,strfy,strprojname,strdeptid,strcompid,strfyid,strprojid;

    EditText ed_totcount,ed_totbudget;
    Button bt_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manpower_planning_form);
        ll_main_layout = findViewById(R.id.ll_main_layout);
        spin_deptlist = findViewById(R.id.spin_deptlist);
        spin_projectlist = findViewById(R.id.spin_projectlist);
        spin_fylist = findViewById(R.id.spin_fylist);
        spin_complist = findViewById(R.id.spin_complist);
        ed_totbudget = findViewById(R.id.ed_totbudget);
        ed_totcount = findViewById(R.id.ed_totcount);
        bt_save = findViewById(R.id.bt_save);

        arrydeptname = new ArrayList<HashMap<String, String>>();
        arrycompname = new ArrayList<HashMap<String, String>>();
        arryfyname = new ArrayList<HashMap<String, String>>();
        arryprojectname = new ArrayList<HashMap<String, String>>();
        loadDepartmentList();
        loadprojectnameList();
        loadcompanyList();
        loadfinanceyearList();
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
        String url = urlmain+ "addbudget";

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

                                Intent intent = new Intent(ManpowerPlanningForm.this, MainActivity.class);
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

                params.put("fy_id",strfyid);
                params.put("company_id",strcompid);
                params.put("department_id",strdeptid);
                params.put("project_id",strprojid);
                params.put("total_budget",ed_totbudget.getText().toString().trim());
                params.put("total_count",ed_totcount.getText().toString().trim());
                params.put("active_status","1");
                params.put("userid","E1045");

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void loadDepartmentList() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+ "getdepartment";
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
                                    String DEPT_ID = heroObject.getString("DEPT_ID");
                                    String DEPT_NAME = heroObject.getString("DEPT_NAME");

                                   // strarrydeptname.add(DEPT_ID + " " + DEPT_NAME);

                                    HashMap<String, String> citynameagain = new HashMap<>();

                                    citynameagain.put("DEPT_ID", DEPT_ID);
                                    citynameagain.put("DEPT_NAME", DEPT_NAME);
                                    arrydeptname.add(citynameagain);

                                }
                                SimpleAdapter adep = new SimpleAdapter(ManpowerPlanningForm.this, arrydeptname, R.layout.black_spinner_item, new String[]{"DEPT_ID", "DEPT_NAME"}, new int[]{R.id.brid2, R.id.brid});
                                spin_deptlist.setAdapter(adep);

//                                Log.i("Resp123", strarrydeptname.toString());
//                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, strarrydeptname);
//                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                                spin_deptlist.setAdapter(dataAdapter);
                                spinnersetmethoddept();

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

    void spinnersetmethoddept() {
        spin_deptlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 strdept = spin_deptlist.getItemAtPosition(position).toString();

                String[] elements = strdept.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                strdeptid = elej[1].toString();
                Log.i("checkmeup", strdeptid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                String[] elements = strdept.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                strdeptid = elej[1].toString();

                Log.i("checkmeup", strdeptid);

            }
        });

    }

    private void loadprojectnameList() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getprojectname";
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
                                    String PROJECT_ID = heroObject.getString("PROJECT_ID");
                                    String PROJECT_NAME = heroObject.getString("PROJECT_NAME");
                                    HashMap<String, String> citynameagain = new HashMap<>();

                                    citynameagain.put("PROJECT_ID", PROJECT_ID);
                                    citynameagain.put("PROJECT_NAME", PROJECT_NAME);
                                    arryprojectname.add(citynameagain);

                                }
                                SimpleAdapter adep = new SimpleAdapter(ManpowerPlanningForm.this, arryprojectname, R.layout.black_spinner_item, new String[]{"PROJECT_ID", "PROJECT_NAME"}, new int[]{R.id.brid2, R.id.brid});
                                spin_projectlist.setAdapter(adep);
                                spinnersetmethodproject();

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

    void spinnersetmethodproject() {

        spin_projectlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strprojname = spin_projectlist.getItemAtPosition(position).toString();

                String[] elements = strprojname.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                strprojid = elej[1].toString();
                Log.i("checkmeup", strprojid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                String[] elements = strprojname.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                strprojid = elej[1].toString();

                Log.i("checkmeup", strprojid);

            }
        });

    }
    private void loadcompanyList() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getcompanyname";
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
                                    String COMPANY_ID = heroObject.getString("COMPANY_ID");
                                    String COMPANY_NAME = heroObject.getString("COMPANY_NAME");
                                    HashMap<String, String> citynameagain = new HashMap<>();


                                    citynameagain.put("COMPANY_NAME",COMPANY_NAME);
                                    citynameagain.put("COMPANY_ID",COMPANY_ID);
                                    arrycompname.add(citynameagain);

                                }
                                SimpleAdapter adep = new SimpleAdapter(ManpowerPlanningForm.this, arrycompname, R.layout.black_spinner_item, new String[]{"COMPANY_NAME","COMPANY_ID"}, new int[]{R.id.brid, R.id.brid2});
                                spin_complist.setAdapter(adep);

                                spinnersetmethodcompany();

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

    void spinnersetmethodcompany() {

        spin_complist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strcomp = spin_complist.getItemAtPosition(position).toString();

                String[] elements = strcomp.split(",");
                String brch = elements[1].toString();
                String[] elej = brch.split("=");
                strcompid = elej[1].toString();

                strcompid = strcompid.replace("}","");
                Log.i("checkmeupcomp", strcompid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                String[] elements = strcomp.split(",");
                String brch = elements[1].toString();
                String[] elej = brch.split("=");
                strcompid = elej[1].toString();
                strcompid = strcompid.replace("}","");

                Log.i("checkmeup", strcompid);

            }
        });

    }
    private void loadfinanceyearList() {

        showProgressBar();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getfy";
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
                                    String FY_ID = heroObject.getString("FY_ID");
                                    String FY_NAME = heroObject.getString("FY_NAME");

                                    HashMap<String, String> citynameagain = new HashMap<>();

                                    citynameagain.put("FY_ID", FY_ID);
                                    citynameagain.put("FY_NAME", FY_NAME);
                                    arryfyname.add(citynameagain);

                                }
                                SimpleAdapter adep = new SimpleAdapter(ManpowerPlanningForm.this, arryfyname, R.layout.black_spinner_item, new String[]{"FY_ID", "FY_NAME"}, new int[]{R.id.brid2, R.id.brid});
                                spin_fylist.setAdapter(adep);
                                spinnersetmethodfy();

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

    void spinnersetmethodfy() {

        spin_fylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strfy = spin_fylist.getItemAtPosition(position).toString();

                String[] elements = strfy.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                strfyid = elej[1].toString();
                Log.i("checkmeup", strfyid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                String[] elements = strfy.split(",");
                String brch = elements[0].toString();
                String[] elej = brch.split("=");
                strfyid = elej[1].toString();
                Log.i("checkmeup", strfyid);

            }
        });

    }
}
