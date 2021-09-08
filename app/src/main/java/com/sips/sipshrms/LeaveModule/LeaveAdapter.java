package com.sips.sipshrms.LeaveModule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.sips.sipshrms.Common.ChangePassword;
import com.sips.sipshrms.Common.NewMainActivity;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.ViewHolder> {

    Context context;
    private ArrayList<Leavepojo> leave_list;

    String sel_leave_id;

    public LeaveAdapter(ArrayList<Leavepojo> leave_list,Context context) {

        this.leave_list = leave_list;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave, parent, false);
        return new LeaveAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeaveAdapter.ViewHolder holder, int position) {
        holder.leavetype.setText(leave_list.get(position).getLeaveTypeName());

        if(holder.leavetype.getText().toString().matches("Discrepancy")) {
            holder.fromDate.setText("From: "+leave_list.get(position).getDateFrom());
            holder.toDate.setText("To: "+leave_list.get(position).getDateTo());
            holder.reason.setText(leave_list.get(position).getReason());
            holder.status.setText(leave_list.get(position).getStatusName());
            holder.fromShift.setText(leave_list.get(position).getIn_time_a()+" ("+leave_list.get(position).getIn_time_d()+ ")");
            holder.toShift.setText(leave_list.get(position).getOut_time_a()+" ("+leave_list.get(position).getOut_time_d()+ ")");

            holder.leaveid.setText(leave_list.get(position).getLeave_id());
        }
        else {
            holder.fromDate.setText("From: "+leave_list.get(position).getDateFrom());
            holder.toDate.setText("To: "+leave_list.get(position).getDateTo());
            holder.reason.setText(leave_list.get(position).getReason());
            holder.status.setText(leave_list.get(position).getStatusName());

            holder.fromShift.setText(leave_list.get(position).getFrom_session_name());
            holder.toShift.setText(leave_list.get(position).getTo_session_name());
            holder.leaveid.setText(leave_list.get(position).getLeave_id());
        }

        holder.ll_main_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.status.getText().toString().matches("Pending")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    alertDialog.setTitle("Cancel Leave");

                    alertDialog.setMessage("Do you want to cancel your leave?");

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            sel_leave_id= holder.leaveid.getText().toString();
                            updatefunc();
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alertDialog.show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return leave_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reason,fromDate,toDate,status,fromShift,toShift,leavetype,leaveid;
        CardView ll_main_view;

        public ViewHolder(View itemView) {

            super(itemView);
            reason = itemView.findViewById(R.id.reason);
            fromDate = itemView.findViewById(R.id.fromDate);
            toDate = itemView.findViewById(R.id.toDate);
            status = itemView.findViewById(R.id.status);
            leavetype = itemView.findViewById(R.id.leavetype);
            toShift = itemView.findViewById(R.id.toShift);
            fromShift = itemView.findViewById(R.id.fromShift);
            leaveid = itemView.findViewById(R.id.leaveid);
            ll_main_view = itemView.findViewById(R.id.ll_main_view);

        }
    }

    private void updatefunc() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ((ViewLeaveActivity) context).urlmain+"updateLeaveUser";

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

                params.put("leave_id",sel_leave_id);
                params.put("leave_status","59");

                return params;
            }
        };
        queue.add(postRequest);
    }
}
