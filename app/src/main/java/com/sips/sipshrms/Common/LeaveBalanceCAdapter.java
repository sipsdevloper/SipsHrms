package com.sips.sipshrms.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sips.sipshrms.LeaveModule.HolidayListAdapter;
import com.sips.sipshrms.LeaveModule.HolidayModel;
import com.sips.sipshrms.R;

import java.util.ArrayList;

public class LeaveBalanceCAdapter extends RecyclerView.Adapter<LeaveBalanceCAdapter.ViewHolder> {

    Context context;
    private ArrayList<LeaveBalanceCMoodel> leave_list;


    public LeaveBalanceCAdapter(ArrayList<LeaveBalanceCMoodel> leave_list,Context context) {

        this.leave_list = leave_list;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaveBalanceCAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance_c, parent, false);
        return new LeaveBalanceCAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeaveBalanceCAdapter.ViewHolder holder, int position) {

        holder.tv_lt1.setText(leave_list.get(position).getLeave_type_name());
        holder.tv_ltb1.setText(leave_list.get(position).getBalance_leave_count());

    }

    @Override
    public int getItemCount() {
        return leave_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_lt1,tv_ltb1;

        public ViewHolder(View itemView) {

            super(itemView);
            tv_ltb1 = itemView.findViewById(R.id.tv_ltb1);
            tv_lt1 = itemView.findViewById(R.id.tv_lt1);



        }
    }
}
