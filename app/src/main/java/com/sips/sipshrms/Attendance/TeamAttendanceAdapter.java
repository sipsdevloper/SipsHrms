package com.sips.sipshrms.Attendance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sips.sipshrms.Common.MyTeamModel;
import com.sips.sipshrms.Helper.TeamSelectedBroadcast;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import java.util.ArrayList;

public class TeamAttendanceAdapter extends RecyclerView.Adapter<TeamAttendanceAdapter.ViewHolder> {

   public   Context context;
    private ArrayList<MyTeamModel> team_list;

    int selectedPosition=-1;

    public TeamAttendanceAdapter(ArrayList<MyTeamModel> team_list,Context context) {

        this.team_list = team_list;
        this.context = context;
    }

    @NonNull
    @Override
    public TeamAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myteam_namelist, parent, false);
        return new TeamAttendanceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TeamAttendanceAdapter.ViewHolder holder, int position) {
        holder.emp_name.setText(team_list.get(position).getFull_name());
        holder.emp_desg.setText(team_list.get(position).getDesignation_name());
        holder.emp_id.setText(team_list.get(position).getEmployee_id());
        Glide.with(context).load(((TeamAttendanceActivity)context).urlimage+team_list.get(position).getProfileImg()).into(holder.circularTextView);


        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.ll_teamattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
                String selectedid = holder.emp_id.getText().toString();


                Intent intent = new Intent(context, TeamSelectedBroadcast.class);
                intent.putExtra("extra", selectedid);
                ((Activity) context).sendBroadcast(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return team_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView emp_name,emp_desg,emp_id;
        LinearLayout ll_teamattend;
        ImageView circularTextView;


        public ViewHolder(View itemView) {

            super(itemView);
            emp_name = itemView.findViewById(R.id.emp_name);
            emp_id = itemView.findViewById(R.id.emp_id);
            emp_desg = itemView.findViewById(R.id.emp_desg);
            ll_teamattend = itemView.findViewById(R.id.ll_teamattend);
            circularTextView = itemView.findViewById(R.id.circularTextView);

        }
    }
}
