package com.sips.sipshrms.Attendance;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.sips.sipshrms.R;



public final class DrawableUtils {

    public static Drawable getCircleDrawableRWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_circle_white);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.black, 10);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }public static Drawable getCircleDrawablePRWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_green_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 10);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }public static Drawable getCircleDrawableGLWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_circle_white);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.black, 10);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }public static Drawable getCircleDrawableABWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 10);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }

    public static Drawable getThreeDots(Context context){
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_circle);

        //Add padding to too large icon
        return new InsetDrawable(drawable, 100, 0, 100, 0);
    }

    private DrawableUtils() {
    }
}