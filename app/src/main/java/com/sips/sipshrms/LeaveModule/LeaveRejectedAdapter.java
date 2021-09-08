package com.sips.sipshrms.LeaveModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sips.sipshrms.R;

import java.util.ArrayList;

public class LeaveRejectedAdapter extends RecyclerView.Adapter<LeaveRejectedAdapter.ViewHolder> {

    Context context;
    private ArrayList<TeamLeaveModel> leave_list;


    public LeaveRejectedAdapter(ArrayList<TeamLeaveModel> leave_list,Context context) {

        this.leave_list = leave_list;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaveRejectedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_leave_rejected, parent, false);
        return new LeaveRejectedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeaveRejectedAdapter.ViewHolder holder, int position) {

        holder.leavetype.setText(leave_list.get(position).getLeaveTypeName());
        if(holder.leavetype.getText().toString().matches("Discrepancy")){

            holder.fromShift.setText(leave_list.get(position).getIn_time_a()+" ("+leave_list.get(position).getIn_time_d()+ ")");
            holder.toShift.setText(leave_list.get(position).getOut_time_a()+" ("+leave_list.get(position).getOut_time_d()+ ")");
            holder.fromDate.setText("From: "+leave_list.get(position).getDateFrom());
            holder.toDate.setText("To: "+leave_list.get(position).getDateTo());
            holder.reason.setText(leave_list.get(position).getReason());
            holder.status.setText(leave_list.get(position).getStatusName());
            holder.leaveid.setText(leave_list.get(position).getLeave_id());
            holder.tv_emp_fname.setText(leave_list.get(position).getFull_name());
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
        }




    }

    @Override
    public int getItemCount() {
        return leave_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reason, fromDate, toDate, status, fromShift, toShift, leavetype, leaveid,tv_emp_fname;
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
            tv_emp_fname = itemView.findViewById(R.id.tv_emp_fname);

            ll_main_view = itemView.findViewById(R.id.ll_main_view);
        }
    }
}
