package com.sips.sipshrms.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.sips.sipshrms.Attendance.Attendancepojo;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    Context context;
    private ArrayList<EventModel> event_list;
    String strmessage="";
    String sel_empid="";
    SharedPresencesUtility sharedPresences;


    public EventsListAdapter(ArrayList<EventModel> event_list,Context context) {

        this.event_list = event_list;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_celebration, parent, false);
        return new EventsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventsListAdapter.ViewHolder holder, int position) {
        holder.tv_fname.setText(event_list.get(position).getFull_name());
        holder.tv_dept.setText(event_list.get(position).getDepartment_name());
        holder.tv_loc.setText(event_list.get(position).getLocation_name());
        holder.tv_event.setText(event_list.get(position).getEvent_name());
        holder.tv_emp_id.setText(event_list.get(position).getEmployee_id());
        Glide.with(context).load(((NewMainActivity)context).urlimage+event_list.get(position).getProfileImg()).into(holder.circularTextView);

        holder.ll_celab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_empid = holder.tv_emp_id.getText().toString();
                showChangeLangDialog();
            }
        });

    }


    @Override
    public int getItemCount() {
        return event_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_fname,tv_dept,tv_loc,tv_event,tv_emp_id;
        ImageView circularTextView;

        LinearLayout ll_celab;

        public ViewHolder(View itemView) {

            super(itemView);
            tv_fname = itemView.findViewById(R.id.tv_fname);
            tv_emp_id = itemView.findViewById(R.id.tv_emp_id);
            tv_dept = itemView.findViewById(R.id.tv_dept);
            tv_loc = itemView.findViewById(R.id.tv_loc);
            tv_event = itemView.findViewById(R.id.tv_event);
            ll_celab = itemView.findViewById(R.id.ll_celab);
            circularTextView = itemView.findViewById(R.id.circularTextView);

        }
    }
    public void showChangeLangDialog() {
        View ss = LayoutInflater.from(context).inflate(R.layout.alert_custom_dialog,null);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(context);


        builderDialog.setView(ss)

                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText et = (EditText) ss.findViewById(R.id.editmesseage);
                        strmessage = et.getText().toString();
                        if(strmessage.isEmpty())
                        {
                            Toast.makeText(context,"Please type your message",Toast.LENGTH_LONG).show();
                        }else
                        {
                            wishesfunction();
                        }


                    }
                }).setNegativeButton("Cancel", null)
                .setCancelable(false);
        EditText et = (EditText) ss.findViewById(R.id.editmesseage);
        TextView first_tv = (TextView) ss.findViewById(R.id.first_tv);
        TextView second_tv = (TextView) ss.findViewById(R.id.second_tv);
        TextView third_tv = (TextView) ss.findViewById(R.id.third_tv);

        first_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                et.setText(first_tv.getText().toString());
            }
        });
        second_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                et.setText(second_tv.getText().toString());
            }
        });
        third_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                et.setText(third_tv.getText().toString());
            }
        });

        AlertDialog alert = builderDialog.create();
        alert.show();
    }

    private void wishesfunction() {

        sharedPresences = new SharedPresencesUtility(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ((NewMainActivity)context).urlmain+"addwishes";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("Responseee", response);


                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("REsponce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");
                            Log.i("Resp1", success);
                            if (success.equals("200")) {

                                Intent intent = new Intent(context, NewMainActivity.class);
                                context.startActivity(intent);

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

                params.put("emp_id",sel_empid);
                params.put("notif_type","1");
                params.put("message",strmessage);
                params.put("status","1");
                params.put("createdate","");
                params.put("userid",sharedPresences.getUserId(context));

                return params;
            }
        };
        queue.add(postRequest);
    }
}
