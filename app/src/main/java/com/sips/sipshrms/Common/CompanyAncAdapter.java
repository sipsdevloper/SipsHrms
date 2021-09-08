package com.sips.sipshrms.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import java.util.ArrayList;

public class CompanyAncAdapter extends RecyclerView.Adapter<CompanyAncAdapter.ViewHolder> {

    Context context;
    private ArrayList<CompanyAncModel> anc_list;
    String selecttxt;

    public CompanyAncAdapter(ArrayList<CompanyAncModel> anc_list,Context context) {

        this.anc_list = anc_list;
        this.context = context;
    }

    @NonNull
    @Override
    public CompanyAncAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comp_ann, parent, false);
        return new CompanyAncAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompanyAncAdapter.ViewHolder holder, int position) {
        holder.emp_name.setText(anc_list.get(position).getFull_name());
        String mimeType = "text/html";
        String encoding = "utf-8";

        String text = "<html dir=" + "><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/roboto_regular.ttf\")}body{font-family: MyFont;color: #5d5d5d;text-align:left;line-height:1.2}"
                + "</style></head>"
                + "<body>"
                + anc_list.get(position).getMessage()
                + "</body></html>";
        holder.emp_desg.loadDataWithBaseURL(null, text, mimeType, encoding, null);
        holder.tv_attchname.setText(anc_list.get(position).getAttachment());
        if (holder.tv_attchname.getText().toString().matches("0"))
        {
            holder.iv_attach.setVisibility(View.GONE);
        }
        else {
            holder.iv_attach.setVisibility(View.VISIBLE);
        }
        Glide.with(context).load(((NewMainActivity)context).urlimage+anc_list.get(position).getProfileImg()).into(holder.circularTextView);
        holder.iv_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = holder.tv_attchname.getText().toString();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((NewMainActivity)context).urlancattachimage+fn));
                context.startActivity(browserIntent);
            }
        });
        holder.ll_main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecttxt =  anc_list.get(position).getMessage();
                showDialogForm();
            }
        });


    }

    @Override
    public int getItemCount() {
        return anc_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView emp_name,tv_attchname;
        ImageView circularTextView,iv_attach;
        WebView emp_desg;
        LinearLayout ll_main_layout;


        public ViewHolder(View itemView) {

            super(itemView);
            emp_name = itemView.findViewById(R.id.emp_name);
            emp_desg = itemView.findViewById(R.id.emp_desg);
            tv_attchname = itemView.findViewById(R.id.tv_attchname);
            circularTextView = itemView.findViewById(R.id.circularTextView);
            iv_attach = itemView.findViewById(R.id.iv_attach);
            ll_main_layout = itemView.findViewById(R.id.ll_main_layout);

        }
    }

    public void showDialogForm(){

        final View ss = LayoutInflater.from(context).inflate(R.layout.item_alert_form,null);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(context);


        builderDialog.setView(ss)
                .setCancelable(false);
        final WebView webtxt = (WebView) ss.findViewById(R.id.webView);
        ImageView  img_cancel = (ImageView) ss.findViewById(R.id.img_cancel);

        String mimeType = "text/html";
        String encoding = "utf-8";

        String text = "<html dir=" + "><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/roboto_regular.ttf\")}body{font-family: MyFont;color: #5d5d5d;text-align:left;line-height:1.2}"
                + "</style></head>"
                + "<body>"
                + selecttxt
                + "</body></html>";
        webtxt.loadDataWithBaseURL(null, text, mimeType, encoding, null);

        final AlertDialog alert = builderDialog.create();
        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

            }
        });




        alert.show();
    }

}
