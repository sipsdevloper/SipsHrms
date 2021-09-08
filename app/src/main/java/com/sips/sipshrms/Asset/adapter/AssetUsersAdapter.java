package com.sips.sipshrms.Asset.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sips.sipshrms.Asset.model.UsersAssetListModel;
import com.sips.sipshrms.R;

import java.util.ArrayList;

public class AssetUsersAdapter extends RecyclerView.Adapter<AssetUsersAdapter.ViewHolder> {

    Context context;
    private ArrayList<UsersAssetListModel> user_list;



    public AssetUsersAdapter(ArrayList<UsersAssetListModel> user_list,Context context) {

        this.user_list = user_list;
        this.context = context;
    }

    @NonNull
    @Override
    public AssetUsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset_userslist, parent, false);
        return new AssetUsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AssetUsersAdapter.ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {

            super(itemView);

        }
    }
}
