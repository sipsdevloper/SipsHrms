package com.sips.sipshrms.Common;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sips.sipshrms.Attendance.Attendancepojo;
import com.sips.sipshrms.Attendance.ListAttendanceviewAdapter;
import com.sips.sipshrms.Attendance.MarkAttendance;
import com.sips.sipshrms.Attendance.SMSAttendanceActivity;
import com.sips.sipshrms.Attendance.TeamAttendanceActivity;
import com.sips.sipshrms.Attendance.ViewAttendanceCalender;
import com.sips.sipshrms.Attendance.ViewAttendanceList;
import com.sips.sipshrms.Constant;
import com.sips.sipshrms.Create_Compoff;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.CenterZoomLayoutManager;
import com.sips.sipshrms.Helper.Connectivity;
import com.sips.sipshrms.Helper.SessionManager;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.LeaveModule.AddleaveActivity;
import com.sips.sipshrms.LeaveModule.HolidayListActivity;
import com.sips.sipshrms.LeaveModule.LeaveApprovalActivity;
import com.sips.sipshrms.LeaveModule.ViewLeaveActivity;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewMainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvguserName,tvgreeting;
    String str_pempid, str_pempname, str_pempdesig;
    TextView tvusername_nav,tvuseremail_nav;

    Button bt_markat;
    RelativeLayout r2_module,r1_module,r4_module,r6_module,r5_module;
    TextView tv_atindic;
    SimpleDateFormat tdf ;
    String todayFormat = "yyyy-MM-dd";

    private RecyclerView eventrecyclerView;
    private ArrayList<EventModel> eventarray;
    private EventsListAdapter eventlistAdapter;


    private RecyclerView newjoinrecyclerView;
    private ArrayList<NewJoiningModel> njoinarray;
    private NewJoiningListAdapter njoinlistAdapter;
    final public String APP_VERSION = "1";




    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    String jsonStr;

    SharedPresencesUtility sharedPresence ;
    SessionManager sessionManager;
    TextView birthtext,newjtext,newanctext;
    LinearLayout ll_main_layout;
    ImageView imageViewprofile;
    RelativeLayout badge_layout;
    TextView badge_notification;

    Integer countnotif=0;
    Button button_notif;

    RecyclerView compancrecyclerView;
    private ArrayList<CompanyAncModel> comparray;
    private CompanyAncAdapter complistAdapter;
    String strSMsmper="";

    SwipeRefreshLayout swipeToRefresh;

    FloatingActionButton fab;

    String strnameevents,sreeventinfo;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);


        tvguserName = findViewById(R.id.tvguserName);

        tvgreeting = findViewById(R.id.tvgreeting);
        bt_markat = findViewById(R.id.bt_markat);
        r2_module = findViewById(R.id.r2_module);
        r1_module = findViewById(R.id.r1_module);
        r4_module = findViewById(R.id.r4_module);
        r6_module = findViewById(R.id.r6_module);
        r5_module = findViewById(R.id.r5_module);
        birthtext = findViewById(R.id.birthtext);
        compancrecyclerView = findViewById(R.id.compancrecyclerView);
        newjtext = findViewById(R.id.newjtext);
        newanctext = findViewById(R.id.newanctext);
        ll_main_layout = findViewById(R.id.ll_main_layout);
        badge_layout = findViewById(R.id.badge_layout);
        badge_notification = findViewById(R.id.badge_notification);
        button_notif = findViewById(R.id.button_notif);
        swipeToRefresh = findViewById(R.id.swipeToRefresh);

        tv_atindic = findViewById(R.id.tv_atindic);
        eventrecyclerView = findViewById(R.id.eventrecyclerView);
        newjoinrecyclerView = findViewById(R.id.newjoinrecyclerView);
        eventarray = new ArrayList<EventModel>();
        njoinarray = new ArrayList<NewJoiningModel>();
        comparray = new ArrayList<CompanyAncModel>();
        sessionManager = new SessionManager(getApplicationContext());
        sharedPresence =  new SharedPresencesUtility(getApplicationContext());


        tdf=  new SimpleDateFormat(todayFormat, Locale.US);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvusername_nav = headerView.findViewById(R.id.tvusername_nav);
        tvuseremail_nav = headerView.findViewById(R.id.tvuseremail_nav);
        imageViewprofile = headerView.findViewById(R.id.imageViewprofile);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewMainActivity.this, MarkAttendance.class);
                startActivity(intent);
            }
        });
        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!Connectivity.isConnected(getApplicationContext()))
                {
                    //  bt_markat.setVisibility(View.GONE);
                    birthtext.setVisibility(View.GONE);
                    newjtext.setVisibility(View.GONE);
                    newanctext.setVisibility(View.GONE);

                    if (sharedPresence.getPunchpermission(getApplicationContext()).toString().matches("102"))
                    {
                        bt_markat.setVisibility(View.VISIBLE);
                        bt_markat.setText("SMS PUNCH");
                    }

                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else{
                    birthtext.setVisibility(View.VISIBLE);
                    newjtext.setVisibility(View.VISIBLE);
                    newanctext.setVisibility(View.VISIBLE);


                    loadnotifList();
                    loadattendance();
                    locinitViewsNew();
                    locinitViewsEvent();
                    locinitAncViews();

                }
                swipeToRefresh.setRefreshing(false);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        expandableListView = findViewById(R.id.expandableListView);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        getmyTime();
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    collapsingToolbar.setTitle("");
                }
                if (Math.abs(scrollRange + verticalOffset) < 20) {
                    collapsingToolbar.setTitle(str_pempname);

                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });




        bt_markat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connectivity.isConnected(getApplicationContext()))
                {
                    Intent intent = new Intent(NewMainActivity.this, SMSAttendanceActivity.class);
                    startActivity(intent);
//                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
//                    snackbar1.show();
                }else{
                    Intent intent = new Intent(NewMainActivity.this, MarkAttendance.class);
                    startActivity(intent);
                }

            }
        });
        button_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connectivity.isConnected(getApplicationContext()))
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }
                else{

                 updatenotif();

                }

            }
        });
        r2_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connectivity.isConnected(getApplicationContext()))
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else{
                    Intent intent = new Intent(NewMainActivity.this, ViewAttendanceCalender.class);
                    startActivity(intent);
                }

            }
        });
        r1_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connectivity.isConnected(getApplicationContext()))
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else{
                    Intent intent = new Intent(NewMainActivity.this, ViewLeaveActivity.class);
                    startActivity(intent);
                }

            }
        });
        r4_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connectivity.isConnected(getApplicationContext()))
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else{
                    Intent intent = new Intent(NewMainActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                }

            }
        });
        r6_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connectivity.isConnected(getApplicationContext()))
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else{
                    Intent intent = new Intent(NewMainActivity.this, HolidayListActivity.class);
                    startActivity(intent);
                }

            }
        });
        r5_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connectivity.isConnected(getApplicationContext()))
                {
                    Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else{
                    Intent intent = new Intent(NewMainActivity.this, MyTeamActivity.class);
                    startActivity(intent);
                }

            }
        });
        tvguserName.setText(sharedPresence.getUserName(getApplicationContext()));
        tvusername_nav.setText(sharedPresence.getUserName(getApplicationContext()));
        tvuseremail_nav.setText(sharedPresence.getUserDesignation(getApplicationContext()));
        Glide.with(getApplicationContext()).load(urlimage+sharedPresence.getProfileImg(getApplicationContext())).into(imageViewprofile);


        if(!Connectivity.isConnected(getApplicationContext()))
        {
          //  bt_markat.setVisibility(View.GONE);
            birthtext.setVisibility(View.GONE);
            newjtext.setVisibility(View.GONE);
            newanctext.setVisibility(View.GONE);

            if (sharedPresence.getPunchpermission(getApplicationContext()).toString().matches("102"))
            {
                bt_markat.setVisibility(View.VISIBLE);
                bt_markat.setText("SMS PUNCH");
            }

            Snackbar snackbar1 = Snackbar.make(ll_main_layout, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar1.show();
        }else{
            loadnotifList();

            loadMenu();
            loadattendance();
            locinitViewsNew();
            locinitViewsEvent();
            locinitAncViews();

        }



    }


    private void locinitViewsNew() {


        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3,GridLayoutManager.HORIZONTAL,false);
       // newjoinrecyclerView.setLayoutManager(mLayoutManager);
        CenterZoomLayoutManager horizontalLayoutManager = new CenterZoomLayoutManager(NewMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        newjoinrecyclerView.setLayoutManager(horizontalLayoutManager);
        newjoinrecyclerView.setHasFixedSize(true);
        njoinarray.clear();
        loadNewjoiningList(); ;
    }

    private void locinitViewsEvent() {


        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(NewMainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        eventrecyclerView.setLayoutManager(horizontalLayoutManager);
        eventrecyclerView.setHasFixedSize(true);
        eventarray.clear();
        loadEventsList(); ;
    }


    private void getmyTime() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        //Set greeting
        String greeting = null;
        if (hour >= 12 && hour < 17) {
            greeting = "Good Afternoon";
        } else if (hour >= 17 && hour < 21) {
            greeting = "Good Evening";
        } else if (hour >= 21 && hour < 24) {
            greeting = "Good Night";
        } else {
            greeting = "Good Morning";
        }


        tvgreeting.setText(greeting);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            clickDone();
        }
    }
    public void clickDone() {


        new AlertDialog.Builder(this)
                .setIcon(R.drawable.sips_logo_circle)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_privacy) {
            Intent intent = new Intent(NewMainActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
            return true;

        }   if (id == R.id.action_settings) {

            sessionManager.logoutUser();
            preferenceUtils.setType("login");
            Intent intent = new Intent(NewMainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void loadattendance() {
        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"gettodayinout";

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
                                JSONObject heroObject = obj.getJSONObject("Response");

                                String in_time = heroObject.getString("in_time");
                                String out_time = heroObject.getString("out_time");

                                if ((in_time.matches("0") )&& (out_time.matches("0" )))
                                {

                                 tv_atindic.setText(" Attendance not punched yet");
                                }else if (out_time.matches("0"))
                                {
                                    bt_markat.setText("PUNCH OUT");
                                    tv_atindic.setText("  Attendance marked at In-Time "+in_time);

                                }else
                                {
                                    bt_markat.setText("PUNCH OUT");
                                    if (in_time.matches("0"))
                                    {
                                        in_time="";
                                    }
                                    tv_atindic.setText(" Attendance marked at "+in_time+" - "+out_time);

                                }

                                hideProgressBar();



                            } else {
                                hideProgressBar();


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
                //params.put("emp_id","E-0708");
               // params.put("t_date","2020-07-03");
                params.put("t_date",tdf.format(new Date()));
               // params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void loadEventsList() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"geteventdetails";

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

                                    String full_name = heroObject.getString("full_name");
                                    String department_name = heroObject.getString("department_name");
                                    String designation_name = heroObject.getString("designation_name");
                                    String location_name = heroObject.getString("location_name");
                                    String event_name = heroObject.getString("event_name");
                                    String profileImg = heroObject.getString("profileImg");
                                    String employee_id = heroObject.getString("employee_id");
                                    if(profileImg.matches("0"))
                                    {
                                        profileImg = "man.png";
                                    }

                                    if(location_name.matches("0"))
                                    {
                                        location_name="";
                                    }
                                    if(employee_id.equalsIgnoreCase(sharedPresence.getUserId(getApplicationContext())))
                                    {
                                        strnameevents = full_name;
                                        sreeventinfo = event_name;
                                        showDialogForm();
                                    }
                                    EventModel homeVersion = new EventModel();
                                    homeVersion.setFull_name(full_name);
                                    homeVersion.setEmployee_id(employee_id);
                                    homeVersion.setDepartment_name(department_name);
                                    homeVersion.setDesignation_name(designation_name);
                                    homeVersion.setLocation_name(location_name);
                                    homeVersion.setEvent_name(event_name);
                                    homeVersion.setProfileImg(profileImg);
                                    eventarray.add(homeVersion);
                                    eventlistAdapter = new EventsListAdapter(eventarray, NewMainActivity.this);
                                    eventrecyclerView.setAdapter(eventlistAdapter);
                                }


                            }
                            else if (success.equals("0"))
                            {

                                eventarray.clear();
                                birthtext.setVisibility(View.GONE);
                                hideProgressBar();
                            }else {
                                birthtext.setVisibility(View.GONE);
                                hideProgressBar();
                                Toast.makeText(getApplicationContext(), respmessage, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            birthtext.setVisibility(View.GONE);
                            e.printStackTrace();
                            hideProgressBar();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        hideProgressBar();
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
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    private void loadNewjoiningList() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getnewjoiningdetails";

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

                                    String full_name = heroObject.getString("full_name");
                                    String department_name = heroObject.getString("department_name");
                                    String designation_name = heroObject.getString("designation_name");
                                    String location_name = heroObject.getString("location_name");

                                    String profileImg = heroObject.getString("profileImg");
                                    if(profileImg.matches("0"))
                                    {
                                        profileImg = "man.png";
                                    }
                                    if(location_name.matches("0"))
                                    {
                                        location_name="";
                                    }

                                    NewJoiningModel homeVersion = new NewJoiningModel();
                                    homeVersion.setFull_name(full_name);
                                    homeVersion.setDepartment_name(department_name);
                                    homeVersion.setDesignation_name(designation_name);
                                    homeVersion.setLocation_name(location_name);
                                    homeVersion.setProfileImg(profileImg);

                                    njoinarray.add(homeVersion);
                                    njoinlistAdapter = new NewJoiningListAdapter(njoinarray, NewMainActivity.this);
                                    newjoinrecyclerView.setAdapter(njoinlistAdapter);
                                }


                            }
                            else if (success.equals("0"))
                            {

                                newjtext.setVisibility(View.GONE);
                                njoinarray.clear();
                                hideProgressBar();
                            }else {
                                newjtext.setVisibility(View.GONE);
                                hideProgressBar();
                                Toast.makeText(getApplicationContext(), respmessage, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            hideProgressBar();
                            newjtext.setVisibility(View.GONE);
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
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void loadMenu() {
        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url=urlmenuload+"user_menu";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Log.i("Responseee11", response);


                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("REsponce", obj.toString());
                            hideProgressBar();
                            JSONArray loginarray = obj.getJSONArray("menu");

                            // looping through All Contacts
                            for (int i = 0; i < loginarray.length(); i++) {
                                JSONObject  c = loginarray.getJSONObject(i);

                                String username = c.getString("menu_name");
                                Log.i("See",username);
                                MenuModel menuModel = new MenuModel(username, true, true, ""); //Menu of Java Tutorials
                                headerList.add(menuModel);
                                List<MenuModel> childModelsList = new ArrayList<>();
                                JSONArray jsonsubarray = c.getJSONArray("sub_menu");

                                for (int j = 0; j < jsonsubarray.length(); j++){
                                    JSONObject  sub = jsonsubarray.getJSONObject(j);

                                    String submenuname1 = sub.getString("sub_menu_name");
                                    Log.i("API1234",submenuname1);


                                    if (submenuname1.matches("Mobile Punch")) {

                                        strSMsmper = "Mobile Punch";
                                    }

                                    MenuModel childModel = new MenuModel(submenuname1, false, false, "");
                                    childModelsList.add(childModel);



                                }
                                if (strSMsmper.matches("Mobile Punch")){

                                    Log.i("A9898988","102");
                                    fab.show();
                                    sharedPresence.setPunchpermission(getApplicationContext(),"102");
                                }
                                else {
                                    sharedPresence.setPunchpermission(getApplicationContext(),"101");

                                }
                                if (menuModel.hasChildren) {
                                    Log.d("API123","here");
                                    childList.put(menuModel, childModelsList);
                                }

                            }
                            populateExpandableList();



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
                params.put("user_id",sharedPresence.getEmpId(getApplicationContext()));
                params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));
                return params;
            }
        };
        queue.add(postRequest);
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                      //  Toast.makeText(NewMainActivity.this,headerList.get(groupPosition).menuName,Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.menuName.length() > 0) {
                     //   Toast.makeText(NewMainActivity.this,model.menuName,Toast.LENGTH_LONG).show();
                        if((model.menuName).matches("Team Attendance"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, TeamAttendanceActivity.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("Leave Approval"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, LeaveApprovalActivity.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("Change Password"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, ChangePassword.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("Personal Information"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, MyProfileActivity.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("My Attendance"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, ViewAttendanceCalender.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("Change Password"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, ChangePassword.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("Team List"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, MyTeamActivity.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("Gallery"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, GalleryActivity.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("Apply Leave"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, AddleaveActivity.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("Mobile Punch"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, MarkAttendance.class);
                            startActivity(intent);

                        }else if((model.menuName).matches("View Leave"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, ViewLeaveActivity.class);
                            startActivity(intent);

                        }
                        else if((model.menuName).matches("Create Comp Off"))
                        {
                            Intent intent = new Intent(NewMainActivity.this, Create_Compoff.class);
                            startActivity(intent);

                        }




                        onBackPressed();
                    }
                }

                return false;
            }
        });
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
                            Log.e("Responc00100e", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");



                            Log.i("Resp1", success);
                            if (success.equals("200")) {


                                JSONArray heroArray = obj.getJSONArray("Response");
                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);

                                    String status = heroObject.getString("status");

                                    if(status.matches("1"))
                                    {
                                        countnotif++;
                                    }


                                }
                                badge_notification.setText(String.valueOf(countnotif));


                            }
                            else if (success.equals("0"))
                            {

                                badge_notification.setText(String.valueOf(countnotif));
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


    private void locinitAncViews() {


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        compancrecyclerView.setLayoutManager(mLayoutManager);
        compancrecyclerView.setHasFixedSize(true);
        comparray.clear();
        loadAncList() ;
    }

    private void loadAncList() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getannouncement";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            hideProgressBar();
                            JSONObject obj = new JSONObject(response);
                            Log.e("Responce87878", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");



                            Log.i("Resp1", success);
                            if (success.equals("200")) {


                                JSONArray heroArray = obj.getJSONArray("Response");
                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);

                                    String message = heroObject.getString("message");
                                    String userid = heroObject.getString("userid");
                                    String full_name = heroObject.getString("full_name");
                                    String profileImg = heroObject.getString("profileImg");
                                    String attachment = heroObject.getString("attachment");
                                    if(profileImg.matches("0"))
                                    {
                                        profileImg = "man.png";
                                    }



                                    CompanyAncModel homeVersion = new CompanyAncModel();

                                    homeVersion.setMessage(message);
                                    homeVersion.setUserid(userid);
                                    homeVersion.setFull_name(full_name);
                                    homeVersion.setProfileImg(profileImg);
                                    homeVersion.setAttachment(attachment);


                                    comparray.add(homeVersion);
                                    complistAdapter = new CompanyAncAdapter(comparray, NewMainActivity.this);
                                    compancrecyclerView.setAdapter(complistAdapter);
                                }


                            }
                            else if (success.equals("0"))
                            {

                                newanctext.setVisibility(View.GONE);
                                comparray.clear();
                                hideProgressBar();
                            }else {
                                newanctext.setVisibility(View.GONE);
                                hideProgressBar();
                                Toast.makeText(getApplicationContext(), respmessage, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            newanctext.setVisibility(View.GONE);
                            hideProgressBar();
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

                params.put("comp_id","1");

                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void updatenotif() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"updatenotifstatus";

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

                                badge_notification.setText("0");
                                Intent intent = new Intent(NewMainActivity.this, NotificationActivity.class);
                                startActivity(intent);

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

                params.put("EMP_ID",sharedPresence.getUserId(getApplicationContext()));
                return params;
            }
        };
        queue.add(postRequest);
    }
    public void showDialogForm(){

        final View ss = LayoutInflater.from(NewMainActivity.this).inflate(R.layout.item_wishes,null);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(NewMainActivity.this);


        builderDialog.setView(ss)
                .setCancelable(false);
        ImageView  img_cancel = (ImageView) ss.findViewById(R.id.img_cancel);
        TextView tv_eventinfo = (TextView) ss.findViewById(R.id.tv_eventinfo);
        TextView tv_username = (TextView) ss.findViewById(R.id.tv_username);
        tv_eventinfo.setText(sreeventinfo);
        tv_username.setText(strnameevents);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(800); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        tv_eventinfo.startAnimation(anim);

//        tv_username.setText("Randhir");
//        tv_eventinfo.setText("Birthday");
        final AlertDialog alert = builderDialog.create();
        if (alert.getWindow() != null)
            alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;

        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

            }
        });




        alert.show();
    }


//    private void checklatestVersion() {
//
//        showProgressBar();
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String url = BaseUrlActivity.getInstance().urlmain+ "getcurrentversion";
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Responce7676", response);
//                        try {
//
//                            JSONObject obj = new JSONObject(response);
//                            Log.e("Responce", obj.toString());
//
//                            String success = obj.getString("ResponseCode");
//                            String respmessage = obj.getString("ResponseMessage");
//                            hideProgressBar();
//
//                            Log.i("Resp1", success);
//                            if (success.equals("200")) {
//
//                                JSONObject heroArray = obj.getJSONObject("Response");
//                                String version_code = heroArray.getString("version_code");
//
//                                   //Log.i("AAAAAAAAAAAAAAAAAAA",Uri.parse(BaseUrlActivity.getInstance().urlversionupdate).toString());
//                                    if (Integer.parseInt(version_code) > Integer.parseInt(APP_VERSION)) {
//                                        Snackbar snackbar = Snackbar
//                                                .make(ll_main_layout, "New version available", Snackbar.LENGTH_LONG)
//                                                .setAction("Update Now", new View.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(View view) {
//                                                        ClipboardManager cm = (ClipboardManager)getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
//                                                        cm.setText(BaseUrlActivity.getInstance().urlversionupdate);
//                                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BaseUrlActivity.getInstance().urlversionupdate));
//                                                        startActivity(browserIntent);
//                                                    }
//                                                });
//
//                                        snackbar.show();
//                                    }
//                                    else
//                                    {
//
//                                    }
//
//
//
//
//                            } else {
//                                // Toast.makeText(getApplicationContext(), respmessage, Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d("Error.Response", error.toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("comp_id",sharedPresence.getCompId(getApplicationContext()));
//
//                return params;
//            }
//        };
//        queue.add(postRequest);
//    }
}
