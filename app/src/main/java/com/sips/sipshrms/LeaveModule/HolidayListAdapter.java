package com.sips.sipshrms.LeaveModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sips.sipshrms.R;

import java.util.ArrayList;

public class HolidayListAdapter extends RecyclerView.Adapter<HolidayListAdapter.ViewHolder> {

    Context context;
    private ArrayList<HolidayModel> hol_list;


    public HolidayListAdapter(ArrayList<HolidayModel> hol_list,Context context) {

        this.hol_list = hol_list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolidayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holiday_list, parent, false);
        return new HolidayListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolidayListAdapter.ViewHolder holder, int position) {

        holder.holiday_name.setText(hol_list.get(position).getHoliday_name());
        holder.date.setText(hol_list.get(position).getHoliday_date());
        holder.holiday_day.setText(hol_list.get(position).getHoliday_day());

    }

    @Override
    public int getItemCount() {
        return hol_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date,holiday_name,holiday_day;

        public ViewHolder(View itemView) {

            super(itemView);
            date = itemView.findViewById(R.id.date);
            holiday_name = itemView.findViewById(R.id.holiday_name);
            holiday_day = itemView.findViewById(R.id.holiday_day);


        }
    }
}
