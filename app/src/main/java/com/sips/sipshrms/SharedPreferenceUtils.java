package com.sips.sipshrms;

import android.content.Context;
import android.content.SharedPreferences;

import com.sips.sipshrms.Url.BaseUrlActivity;

import javax.inject.Inject;

public class SharedPreferenceUtils {
    Context mContext;
    private static SharedPreferenceUtils sharedPreference_main;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;

    @Inject
    public SharedPreferenceUtils(Context context) {
        sharedPreference = context.getSharedPreferences("SHARED_PREFS_FILE_NAME", Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public static SharedPreferenceUtils getInstance(Context ctx) {
        if (sharedPreference_main == null) {
            sharedPreference_main = new SharedPreferenceUtils(ctx);
            return sharedPreference_main;
        }
        return sharedPreference_main;
    }

    public void removePreference() {
        editor = sharedPreference.edit();
        editor.clear().apply();
    }

    public void setTheme(int mTheme) {
        editor = sharedPreference.edit();
        editor.putInt("mTheme", mTheme);
        editor.apply();
    }

    public int getTheme() {
        return sharedPreference.getInt("mTheme", 0);
    }

    public void setBaseUrl(String baseUrl) {
        editor = sharedPreference.edit();
        editor.putString("baseUrl", baseUrl);
        editor.apply();
    }

    public String getBaseUrl() {
        return sharedPreference.getString("baseUrl", BaseUrlActivity.getInstance().sips);
    }

    public void setType(String type) {
        editor = sharedPreference.edit();
        editor.putString("type", type);
        editor.apply();
    }

    public boolean getMuDialog() {
        return sharedPreference.getBoolean("muDialog",false);
    }

    public void setmuDialog(boolean muDialog) {
        editor = sharedPreference.edit();
        editor.putBoolean("muDialog", muDialog);
        editor.apply();
    }

    public String getType() {
        return sharedPreference.getString("type","login");
    }
}

