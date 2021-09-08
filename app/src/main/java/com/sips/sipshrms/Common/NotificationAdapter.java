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
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    private ArrayList<NotificationModel> notif_list;
    String strmessage="";
    String sel_empid="";
    SharedPresencesUtility sharedPresence ;


    public NotificationAdapter(ArrayList<NotificationModel> notif_list,Context context) {

        this.notif_list = notif_list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ViewHolder holder, int position) {
        holder.emp_name.setText(notif_list.get(position).getFull_name());
        holder.emp_desg.setText(notif_list.get(position).getMessage());
        holder.tv_emp_id.setText(notif_list.get(position).getUserid());
        holder.notif_type.setText(notif_list.get(position).getNotification_type());
        Glide.with(context).load(((NotificationActivity)context).urlimage+notif_list.get(position).getProfileImg()).into(holder.circularTextView);
        holder.ll_main_view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (holder.notif_type.getText().toString().matches("1"))
               {
                   sel_empid = holder.tv_emp_id.getText().toString();
                   showChangeLangDialog();
               }
           }
        });


    }

    @Override
    public int getItemCount() {
        return notif_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView emp_name,emp_desg,notif_type,tv_emp_id;
        ImageView circularTextView;
        LinearLayout ll_main_view;


        public ViewHolder(View itemView) {

            super(itemView);
            emp_name = itemView.findViewById(R.id.emp_name);
            emp_desg = itemView.findViewById(R.id.emp_desg);
            tv_emp_id = itemView.findViewById(R.id.tv_emp_id);
            notif_type = itemView.findViewById(R.id.notif_type);
            ll_main_view = itemView.findViewById(R.id.ll_main_view);
            circularTextView = itemView.findViewById(R.id.circularTextView);

        }
    }
    public void showChangeLangDialog() {
        View ss = LayoutInflater.from(context).inflate(R.layout.alert_custom_dialog_thanks,null);
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

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ((NotificationActivity) context).urlmain+"addwishes";

        sharedPresence = new SharedPresencesUtility(context);
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
                params.put("notif_type","4");
                params.put("message",strmessage);
                params.put("status","1");
                params.put("createdate","");
                params.put("userid",sharedPresence.getUserId(context));

                return params;
            }
        };
        queue.add(postRequest);
    }
}
