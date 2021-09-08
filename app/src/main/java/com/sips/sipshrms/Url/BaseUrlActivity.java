package com.sips.sipshrms.Url;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.sips.sipshrms.Constant;
import com.sips.sipshrms.Helper.AppController;
import com.sips.sipshrms.Helper.BaseActivity;
import com.sips.sipshrms.Helper.SharedPresencesUtility;
import com.sips.sipshrms.SharedPreferenceUtils;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;


public class BaseUrlActivity  {

   public SharedPreferenceUtils preferenceUtils;
    public BaseUrlActivity(@ApplicationContext Context context){
         preferenceUtils = new SharedPreferenceUtils(context);
     //   preferenceUtils.setType(login);
    }

    BaseUrlActivity(){}

    public static BaseUrlActivity instance;
    public static BaseUrlActivity getInstance() {
        if(instance == null)
          instance = new BaseUrlActivity();
        return instance;
    }

//    public static final String urlmain = "http://www.sipshrms.in/hrms_api/webservices/index.php?apicall=";
//
//    public static final String urlimage = "http://www.sipshrms.in/uploads/profile/";
//    public static final String urlgalleryimage = "http://www.sipshrms.in/uploads/gallary/";
//    public static final String urlancattachimage = "http://www.sipshrms.in/uploads/announcements/";
//   // public static final String urlversionupdate = "http://www.ipu.ac.in/Pubinfo2020/listinst120220.pdf";
//    //public static final String urlversionupdate = "http://sipshrms.in:8080/app_apk/sips_hrms.apk";
//    public static final String urlmenuload = "http://www.sipshrms.in/apimobile/";


    //    =========================================BASE URL======================================================================

    //    1
    public String urlmain = BASE_URL();
    public String BASE_URL() {
        String urlmain = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlmain = "http://sipshrms.in/smas/hrms_api/webservices/index.php?apicall=";
                break;
            case stll:
                urlmain = "http://sipshrms.in/stll/hrms_api/webservices/index.php?apicall=";
                break;
            case sips:
                urlmain = "http://sipshrms.in/hrms_api/webservices/index.php?apicall=";
                break;
            case login:
                urlmain = "http://sipshrms.in/hrms_test/webservices/index.php?apicall=";
                break;
            default:
                break;
        }
        return urlmain;
    }

    //    2
    public String urlgalleryimage = urlgalleryimage();

    public String urlgalleryimage() {
        String urlgalleryimage = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlgalleryimage = "http://sipshrms.in/smas/uploads/gallary/";
                break;
            case stll:
                urlgalleryimage = "http://sipshrms.in/stll/uploads/gallary/";
                break;
            case sips:
                urlgalleryimage = "http://sipshrms.in/uploads/gallary/";
                break;
            default:
                break;
        }
        return urlgalleryimage;
    }

    //    3
    public String urlimage = urlimage();

    public String urlimage() {
        String urlimage = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlimage = "http://sipshrms.in/smas/uploads/profile/";
                break;
            case stll:
                urlimage = "http://sipshrms.in/stll/uploads/profile/";
                break;
            case sips:
                urlimage = "http://sipshrms.in/uploads/profile/";
                break;
            default:
                break;
        }
        return urlimage;
    }

    //    4
    public String urlancattachimage = urlancattachimage();

    public String urlancattachimage() {
        String urlancattachimage = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlancattachimage = "http://sipshrms.in/smas/uploads/announcements/";
                break;
            case stll:
                urlancattachimage = "http://sipshrms.in/stll/uploads/announcements/";
                break;
            case sips:
                urlancattachimage = "http://sipshrms.in/uploads/announcements/";
                break;
            default:
                break;
        }
        return urlancattachimage;
    }

    //    5
    public String urlmenuload = urlmenuload();
    public String urlmenuload() {
        String urlmenuload = null;
        switch (preferenceUtils.getType()) {
            case smas:
                urlmenuload = "http://sipshrms.in/smas/apimobile/";
                break;
            case stll:
                urlmenuload = "http://sipshrms.in/stll/apimobile/";
                break;
            case sips:
                urlmenuload = "http://sipshrms.in/apimobile/";
                break;
            default:
                break;
        }
        return urlmenuload;
    }


   public  final String smas = "SMAS";
   public  final String stll = "STLL";
   public  final String sips = "SIPS";
   public  final String login = "login";



//    ============================================END BASE URL===================================================================


    // public static final String urlversionupdate = "http://www.ipu.ac.in/Pubinfo2020/listinst120220.pdf";
    //public static final String urlversionupdate = "http://sipshrms.in:8080/app_apk/sips_hrms.apk";

}
