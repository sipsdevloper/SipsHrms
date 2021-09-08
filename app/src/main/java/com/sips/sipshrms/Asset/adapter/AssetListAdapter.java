package com.sips.sipshrms.Asset.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sips.sipshrms.Asset.model.AssetListModel;
import com.sips.sipshrms.R;

import java.util.ArrayList;

public class AssetListAdapter extends RecyclerView.Adapter<AssetListAdapter.ViewHolder> {

    Context context;
    private ArrayList<AssetListModel> asset_list;



    public AssetListAdapter(ArrayList<AssetListModel> asset_list,Context context) {

        this.asset_list = asset_list;
        this.context = context;
    }

    @NonNull
    @Override
    public AssetListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset_items, parent, false);
        return new AssetListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AssetListAdapter.ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return asset_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {

            super(itemView);

        }
    }
}
