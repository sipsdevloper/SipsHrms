package com.sips.sipshrms.LeaveModule.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.LeaveModule.LeaveApprovalActivity;
import com.sips.sipshrms.LeaveModule.LeaveApprovalAdapter;
import com.sips.sipshrms.LeaveModule.LeaveRejectedAdapter;
import com.sips.sipshrms.LeaveModule.TeamLeaveModel;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApprovedFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<TeamLeaveModel> leavearray;
    private LeaveRejectedAdapter lvlistAdapter;
    SharedPresencesUtility sharedPresence ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View view= inflater.inflate(R.layout.fragment_approval, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        sharedPresence =  new SharedPresencesUtility(getActivity());
        leavearray = new ArrayList<TeamLeaveModel>();
        locinitViews();

        return view;
    }

    private void locinitViews() {


        Log.e("Responce", "HHHHHH");
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        loadLeaveList() ;
    }

    private void loadLeaveList() {
        Log.e("Responce", "HHHHHH");
        Log.e("Responce", sharedPresence.getUserId(getActivity()));
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = ((LeaveApprovalActivity) getContext()).urlmain+"getleaveslistmanager";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Responce", response.toString());
                        try {


                            JSONObject obj = new JSONObject(response);
                            Log.e("Responce", obj.toString());
                            Log.i("Responce", obj.toString());

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
                                    String full_name = heroObject.getString("full_name");
                                    String in_time_a = heroObject.getString("in_time_a");
                                    String out_time_a = heroObject.getString("out_time_a");
                                    String in_time_d = heroObject.getString("in_time_d");
                                    String out_time_d = heroObject.getString("out_time_d");

                                    if (status_name.matches("Approved")){



                                        TeamLeaveModel homeVersion = new TeamLeaveModel();
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
                                        homeVersion.setFull_name(full_name);
                                        homeVersion.setIn_time_a(in_time_a);
                                        homeVersion.setOut_time_a(out_time_a);
                                        homeVersion.setIn_time_d(in_time_d);
                                        homeVersion.setOut_time_d(out_time_d);

                                        leavearray.add(homeVersion);

                                    }
                                    lvlistAdapter = new LeaveRejectedAdapter(leavearray, getActivity());
                                    recyclerView.setAdapter(lvlistAdapter);
                                }


                            }
                            else if (success.equals("0"))
                            {

                                leavearray.clear();
                            }else {
                                Toast.makeText(getActivity(), respmessage, Toast.LENGTH_LONG).show();
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

                params.put("users_id",sharedPresence.getUserId(getActivity()));

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
