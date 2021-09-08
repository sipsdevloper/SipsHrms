package com.sips.sipshrms.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sips.sipshrms.Attendance.Attendancepojo;
import com.sips.sipshrms.Attendance.ListAttendanceviewAdapter;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import java.util.ArrayList;

public class NewJoiningListAdapter extends RecyclerView.Adapter<NewJoiningListAdapter.ViewHolder> {

    Context context;
    private ArrayList<NewJoiningModel> njoin_list;


    public NewJoiningListAdapter(ArrayList<NewJoiningModel> njoin_list,Context context) {

        this.njoin_list = njoin_list;
        this.context = context;
    }

    @NonNull
    @Override
    public NewJoiningListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_joining, parent, false);
        return new NewJoiningListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewJoiningListAdapter.ViewHolder holder, int position) {

        holder.tv_fname.setText(njoin_list.get(position).getFull_name());
        holder.tv_dept.setText(njoin_list.get(position).getDepartment_name());
        holder.tv_loc.setText(njoin_list.get(position).getLocation_name());
        Glide.with(context).load(((NewMainActivity)context).urlimage+njoin_list.get(position).getProfileImg()).into(holder.circularTextView);
    }

    @Override
    public int getItemCount() {
        return njoin_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_fname,tv_dept,tv_loc;
        ImageView circularTextView;

        public ViewHolder(View itemView) {

            super(itemView);
            tv_fname = itemView.findViewById(R.id.tv_fname);
            tv_dept = itemView.findViewById(R.id.tv_dept);
            tv_loc = itemView.findViewById(R.id.tv_loc);
            circularTextView = itemView.findViewById(R.id.circularTextView);

        }
    }
}
