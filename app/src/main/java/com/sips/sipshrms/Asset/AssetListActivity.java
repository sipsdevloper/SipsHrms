package com.sips.sipshrms.Asset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sips.sipshrms.Asset.adapter.AssetListAdapter;
import com.sips.sipshrms.Asset.model.AssetListModel;
import com.sips.sipshrms.Common.MyTeamActivity;
import com.sips.sipshrms.Common.MyTeamAdapter;
import com.sips.sipshrms.Common.MyTeamModel;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.R;
import com.sips.sipshrms.Url.BaseUrlActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssetListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ArrayList<AssetListModel> assetlistarray;
    private AssetListAdapter assetlistAdapter;

    SharedPresencesUtility sharedPresences ;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aseet_list);
        sharedPresences = new SharedPresencesUtility(getApplicationContext());
        assetlistarray = new ArrayList<AssetListModel>();
        recyclerView = findViewById(R.id.recycler_view);
        back = findViewById(R.id.back);

        locinitViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void locinitViews() {


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        loadassetList() ;
    }
    private void loadassetList() {


        showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = urlmain+"getAssetList";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            hideProgressBar();
                            JSONObject obj = new JSONObject(response);
                            Log.e("Responce", obj.toString());

                            String success = obj.getString("ResponseCode");
                            String respmessage = obj.getString("ResponseMessage");



                            Log.i("Resp1", success);
                            if (success.equals("200")) {


                                JSONArray heroArray = obj.getJSONArray("Response");
                                for (int i = 0; i < heroArray.length(); i++) {

                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    String asset_id = heroObject.getString("asset_id");
                                    String asset_name = heroObject.getString("asset_name");
                                    String asset_type_name = heroObject.getString("asset_type_name");
                                    String location_name = heroObject.getString("location_name");
                                    String brand_name = heroObject.getString("brand_name");
                                    String model = heroObject.getString("model");
                                    String serial_no = heroObject.getString("serial_no");
                                    String other_details = heroObject.getString("other_details");
                                    String purchase_date = heroObject.getString("purchase_date");
                                    String invoice_no = heroObject.getString("invoice_no");
                                    String origional_value = heroObject.getString("origional_value");
                                    String is_warrenty = heroObject.getString("is_warrenty");
                                    String movement_type_id = heroObject.getString("movement_type_id");
                                    String status_id = heroObject.getString("status_id");
                                    String status_name = heroObject.getString("status_name");


                                        AssetListModel homeVersion = new AssetListModel();
                                        homeVersion.setAsset_id(asset_id);
                                        homeVersion.setAsset_name(asset_name);
                                        homeVersion.setAsset_type_name(asset_type_name);
                                        homeVersion.setBrand_name(brand_name);
                                        homeVersion.setLocation_name(location_name);
                                        homeVersion.setModel(model);
                                        homeVersion.setSerial_no(serial_no);
                                        homeVersion.setOther_details(other_details);
                                        homeVersion.setPurchase_date(purchase_date);
                                        homeVersion.setInvoice_no(invoice_no);
                                        homeVersion.setOrigional_value(origional_value);
                                        homeVersion.setIs_warrenty(is_warrenty);
                                        homeVersion.setMovement_type_id(movement_type_id);
                                        homeVersion.setStatus_id(status_id);
                                        homeVersion.setStatus_name(status_name);



                                        assetlistarray.add(homeVersion);
                                        assetlistAdapter = new AssetListAdapter(assetlistarray, AssetListActivity.this);
                                        recyclerView.setAdapter(assetlistAdapter);

                                }


                            }
                            else if (success.equals("0"))
                            {

                                assetlistarray.clear();
                            }else {
                                Toast.makeText(getApplicationContext(), respmessage, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("comp_id",sharedPresences.getCompId(getApplicationContext()));
                return params;
            }
        };
        queue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
