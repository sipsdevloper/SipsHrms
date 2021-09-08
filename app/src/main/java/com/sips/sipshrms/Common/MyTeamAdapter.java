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
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import java.util.ArrayList;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.ViewHolder> {

    Context context;
    private ArrayList<MyTeamModel> team_list;


    public MyTeamAdapter(ArrayList<MyTeamModel> team_list,Context context) {

        this.team_list = team_list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyTeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myteam, parent, false);
        return new MyTeamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyTeamAdapter.ViewHolder holder, int position) {
        holder.emp_name.setText(team_list.get(position).getFull_name());
        holder.emp_desg.setText(team_list.get(position).getDesignation_name());
        Glide.with(context).load(((MyTeamActivity)context).urlimage+team_list.get(position).getProfileImg()).into(holder.circularTextView);
       // lide.with(context).load(((NotificationActivity)context).urlimage+notif_list.get(position).getProfileImg()).into(holder.circularTextView);


    }

    @Override
    public int getItemCount() {
        return team_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView emp_name,emp_desg;
        ImageView circularTextView;


        public ViewHolder(View itemView) {

            super(itemView);
            emp_name = itemView.findViewById(R.id.emp_name);
            emp_desg = itemView.findViewById(R.id.emp_desg);
            circularTextView = itemView.findViewById(R.id.circularTextView);

        }
    }

}
