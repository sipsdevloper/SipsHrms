package com.sips.sipshrms.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.sips.sipshrms.Url.BaseUrlActivity;

public class SharedPresencesUtility {
    //SharedPreferences file name
    private static String SHARED_PREFS_FILE_NAME = "my_app_shared_prefs";
    public static String KEY_MY_SHARED_BOOLEAN = "my_shared_boolean";
    public static String KEY_MY_SHARED_FOO = "my_shared_foo";
    public static String KEY_BASEURL = "base_url";
    public static String KEY_BASEURLIMAGE = "base_url_image";
    private Context context;


    public SharedPresencesUtility(Context context) {
        this.context = context;
    }

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }



    public static String getBaseURL(Context context) {
        return getPrefs(context).getString("base_url", "1.6.10.112");
    }

    //Strings
    public static void setBaseURL(Context context, String value) {
        getPrefs(context).edit().putString("base_url", value).commit();
    }
    public static String getPunchpermission(Context context) {
        return getPrefs(context).getString("punch_per", " ");
    }

    //Strings
    public static void setPunchpermission(Context context, String value) {
        getPrefs(context).edit().putString("punch_per", value).commit();
    }
    public static String getUserId(Context context) {
        return getPrefs(context).getString("user_id_str", "");
    }

    //Strings
    public static void setUserId(Context context, String value) {
        getPrefs(context).edit().putString("user_id_str", value).commit();
    }
    public static String getUserName(Context context) {
        return getPrefs(context).getString("user_name_str", "");
    }
    public static void setEmpId(Context context, String value) {
        getPrefs(context).edit().putString("emp_id_str", value).commit();
    }
    public static String getEmpId(Context context) {
        return getPrefs(context).getString("emp_id_str", "");
    }

    //Strings
    public static void setUserName(Context context, String value) {
        getPrefs(context).edit().putString("user_name_str", value).commit();
    }
    public static String getUserDesignation(Context context) {
        return getPrefs(context).getString("user_designation_str", "");
    }


    //Strings
    public static void setUserDesignation(Context context, String value) {
        getPrefs(context).edit().putString("user_designation_str", value).commit();
    }
    public static void setCompId(Context context, String value) {
        getPrefs(context).edit().putString("user_comp_id", value).commit();
    }
    public static String getCompId(Context context) {
        return getPrefs(context).getString("user_comp_id", "1");
    }

    public static void setProfileImg(Context context, String value) {
        getPrefs(context).edit().putString("user_image", value).commit();
    }
    public static String getProfileImg(Context context) {
        return getPrefs(context).getString("user_image", "man.png");
    }

    public static void setIMEI(Context context, String value) {
        getPrefs(context).edit().putString("user_imei", value).commit();
    }
    public static String getIMEI(Context context) {
        return getPrefs(context).getString("user_imei", "man.png");
    }

  public static void setBaseUrl(Context context, String value) {
        getPrefs(context).edit().putString("BASEURL", value).commit();
    }
    public static String isBaseUrl(Context context) {
        return getPrefs(context).getString("BASEURL", BaseUrlActivity.getInstance().sips);
    }


}
