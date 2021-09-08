package com.sips.sipshrms.Attendance;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sips.sipshrms.R;

import java.util.ArrayList;

public class ListAttendanceviewAdapter extends RecyclerView.Adapter<ListAttendanceviewAdapter.ViewHolder> {

    Context context;
    private ArrayList<Attendancepojo> attendance_list;


    public ListAttendanceviewAdapter(ArrayList<Attendancepojo> attendance_list,Context context) {

        this.attendance_list = attendance_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAttendanceviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_calander, parent, false);
        return new ListAttendanceviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListAttendanceviewAdapter.ViewHolder holder, int position) {
        holder.date.setText(attendance_list.get(position).getDay());
        holder.in_out.setText(attendance_list.get(position).getIn_out_time());
        holder.datetype.setText(attendance_list.get(position).getLeave_type_code());
        holder.weekday.setText(attendance_list.get(position).getWeekday());
        if (holder.datetype.getText().toString().contains("P"))
        {
            holder.datetype.setTextColor(Color.parseColor("#24756D"));
        }else if (holder.datetype.getText().toString().contains("A"))
        {
            holder.datetype.setTextColor(Color.parseColor("#DE1D34"));
        }else
        {
            holder.datetype.setTextColor(Color.parseColor("#36B9CC"));
        }

    }

    @Override
    public int getItemCount() {
        return attendance_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date,in_out,weekday;

        TextView datetype;

        public ViewHolder(View itemView) {

            super(itemView);
            date = itemView.findViewById(R.id.date);
            in_out = itemView.findViewById(R.id.in_out);
            datetype = itemView.findViewById(R.id.datetype);
            weekday = itemView.findViewById(R.id.weekday);

        }
    }
}
