package com.sips.sipshrms.Asset.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sips.sipshrms.Asset.model.AssetSpecificationModel;

import com.sips.sipshrms.R;
import java.util.ArrayList;


public class AssetSpecificationAdapter extends RecyclerView.Adapter<AssetSpecificationAdapter.ViewHolder> {

    Context context;
    private ArrayList<AssetSpecificationModel> spec_list;



    public AssetSpecificationAdapter(ArrayList<AssetSpecificationModel> spec_list,Context context) {

        this.spec_list = spec_list;
        this.context = context;
    }

    @NonNull
    @Override
    public AssetSpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset_specification, parent, false);
        return new AssetSpecificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AssetSpecificationAdapter.ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return spec_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {

            super(itemView);

        }
    }
    

}
