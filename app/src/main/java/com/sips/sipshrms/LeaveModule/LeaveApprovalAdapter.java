package com.sips.sipshrms.LeaveModule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.sips.sipshrms.Common.NewMainActivity;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaveApprovalAdapter extends RecyclerView.Adapter<LeaveApprovalAdapter.ViewHolder> {

    Context context;
    private ArrayList<TeamLeaveModel> leave_list;

    String sel_leave_id,sel_type,sel_empid;
    SharedPresencesUtility sharedPresences;

    public LeaveApprovalAdapter(ArrayList<TeamLeaveModel> leave_list,Context context) {

        this.leave_list = leave_list;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaveApprovalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_leave, parent, false);
        return new LeaveApprovalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeaveApprovalAdapter.ViewHolder holder, int position) {

        holder.leavetype.setText(leave_list.get(position).getLeaveTypeName());

        if(holder.leavetype.getText().toString().matches("Discrepancy")){
            holder.fromDate.setText("From: "+leave_list.get(position).getDateFrom());
            holder.toDate.setText("To: "+leave_list.get(position).getDateTo());
            holder.reason.setText(leave_list.get(position).getReason());
            holder.status.setText(leave_list.get(position).getStatusName());
            holder.fromShift.setText(leave_list.get(position).getIn_time_a()+" ("+leave_list.get(position).getIn_time_d()+ ")");
            holder.toShift.setText(leave_list.get(position).getOut_time_a()+" ("+leave_list.get(position).getOut_time_d()+ ")");
            holder.leaveid.setText(leave_list.get(position).getLeave_id());
            holder.tv_emp_fname.setText(leave_list.get(position).getFull_name());
            holder.tv_emp_id.setText(leave_list.get(position).getEmployee_id());
        }
        else{
            holder.fromDate.setText("From: "+leave_list.get(position).getDateFrom());
            holder.toDate.setText("To: "+leave_list.get(position).getDateTo());
            holder.reason.setText(leave_list.get(position).getReason());
            holder.status.setText(leave_list.get(position).getStatusName());
            holder.fromShift.setText(leave_list.get(position).getFrom_session_name());
            holder.toShift.setText(leave_list.get(position).getTo_session_name());
            holder.leaveid.setText(leave_list.get(position).getLeave_id());
            holder.tv_emp_fname.setText(leave_list.get(position).getFull_name());
            holder.tv_emp_id.setText(leave_list.get(position).getEmployee_id());
        }


        holder.bt_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.status.getText().toString().matches("Pending")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    alertDialog.setTitle("Approve Leave");

                    alertDialog.setMessage("Do you want to approve leave?");

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            sel_leave_id= holder.leaveid.getText().toString();
                            sel_type= "61";
                            sel_empid = holder.tv_emp_id.getText().toString();
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
        holder.bt_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.status.getText().toString().matches("Pending")){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    alertDialog.setTitle("Reject Leave");

                    alertDialog.setMessage("Do you want to reject leave?");

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            sel_leave_id= holder.leaveid.getText().toString();
                            sel_type= "60";
                            sel_empid = holder.tv_emp_id.getText().toString();
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

        TextView reason, fromDate, toDate, status, fromShift, toShift, leavetype, leaveid,tv_emp_fname,tv_emp_id;
        CardView ll_main_view;

        Button bt_reject,bt_approve;

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
            tv_emp_fname = itemView.findViewById(R.id.tv_emp_fname);
            bt_reject = itemView.findViewById(R.id.bt_reject);
            bt_approve = itemView.findViewById(R.id.bt_approve);
            tv_emp_id = itemView.findViewById(R.id.tv_emp_id);

            ll_main_view = itemView.findViewById(R.id.ll_main_view);
        }
    }

    private void updatefunc() {

        sharedPresences = new SharedPresencesUtility(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ((LeaveApprovalActivity) context).urlmain+"updateLeaveManager";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("Responseee", response);


                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.e("REsponceXXXXX", obj.toString());

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
                params.put("leave_status",sel_type);
                params.put("sel_emp_id",sel_empid);
                params.put("approver_id",sharedPresences.getUserId(context));

                return params;
            }
        };
        queue.add(postRequest);
    }
}
