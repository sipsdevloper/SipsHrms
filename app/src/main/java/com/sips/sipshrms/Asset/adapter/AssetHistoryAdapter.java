package com.sips.sipshrms.Asset.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sips.sipshrms.Asset.model.AssetHistoryModel;
import com.sips.sipshrms.R;

import java.util.ArrayList;

public class AssetHistoryAdapter extends RecyclerView.Adapter<AssetHistoryAdapter.ViewHolder> {

    Context context;
    private ArrayList<AssetHistoryModel> ahis_list;



    public AssetHistoryAdapter(ArrayList<AssetHistoryModel> ahis_list,Context context) {

        this.ahis_list = ahis_list;
        this.context = context;
    }

    @NonNull
    @Override
    public AssetHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset_history_item, parent, false);
        return new AssetHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AssetHistoryAdapter.ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return ahis_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {

            super(itemView);

        }
    }
}
