package com.sips.sipshrms.Attendance;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.sips.sipshrms.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Attendance {
//
//    List<EventDay> events;
//    TextView timeing;
//    CalendarView calendarView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_attendance_calender);
//        timeing = findViewById(R.id.timeing);
//        events = new ArrayList<>();
//
//
//        calendarView = (CalendarView) findViewById(R.id.calendarView);
//
//        Calendar min = Calendar.getInstance();
//        min.add(Calendar.MONTH, -6);
//
//
//        Calendar max = Calendar.getInstance();
//        max.add(Calendar.MONTH ,2);
//
//        calendarView.setMinimumDate(min);
//        calendarView.setMaximumDate(max);
//
//
//        calendarView.setSwipeEnabled(false);
//
//
//
//
//
//
//
//        calendarView.setOnDayClickListener(
//                eventDay ->timeing.setText(eventDay.getCalendar().getTime().toString()));
//
//        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
//            @Override
//            public void onChange() {
//                calendarView.setEvents(getHolidaysDays());
//                calendarView.setEvents(getAbsentDays());
//                calendarView.setEvents(getPresentDays());
//                calendarView.setEvents(getGLDays());
//
//                Toast.makeText(getApplicationContext(),calendarView.getCurrentPageDate().getTime().toString(),Toast.LENGTH_LONG).show();
//            }
//        });
//
//        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
//            @Override
//            public void onChange() {
//                Toast.makeText(getApplicationContext(),calendarView.getCurrentPageDate().getTime().toString(),Toast.LENGTH_LONG).show();
//
//
//            }
//        });
//
//
//    }
//
//    private List<EventDay> getHolidaysDays() {
//
//        int[] intArray = new int[]{-03,-02 };
//
//        for (int i = 0; i < intArray.length; i++) {
//            Calendar firstDisabled = DateUtils.getCalendar();
//
//            Log.i("Saaaaas",String.valueOf(intArray[i]));
//            firstDisabled.add(Calendar.DAY_OF_MONTH,intArray[i]);
//            events.add(new EventDay(firstDisabled, DrawableUtils.getCircleDrawableRWithText(this, "R")));
//        }
//
//
//        return events;
//    }
//    private List<EventDay> getPresentDays() {
//
//        int[] intArray = new int[]{2,3,4};
//
//
//        for (int i = 0; i < intArray.length; i++) {
//            Calendar firstDisabled = DateUtils.getCalendar();
//
//            Log.i("Saaaaas",String.valueOf(intArray[i]));
//            firstDisabled.add(Calendar.DAY_OF_MONTH,intArray[i]);
//            events.add(new EventDay(firstDisabled, DrawableUtils.getCircleDrawablePRWithText(this, "P")));
//        }
//
//
//        return events;
//    }
//    private List<EventDay> getAbsentDays() {
//
//        int[] intArray = new int[]{3,19};
//
//        List<Calendar> calendars = new ArrayList<>();
//        for (int i = 0; i < intArray.length; i++) {
//            Calendar firstDisabled = DateUtils.getCalendar();
//
//            Log.i("Saaaaas",String.valueOf(intArray[i]));
//            firstDisabled.add(Calendar.DAY_OF_MONTH,intArray[i]);
//            events.add(new EventDay(firstDisabled, DrawableUtils.getCircleDrawableABWithText(this, "AB")));
//        }
//
//
//        return events;
//    }
//    private List<EventDay> getGLDays() {
//
//        int[] intArray = new int[]{10,14};
//
//        List<Calendar> calendars = new ArrayList<>();
//        for (int i = 0; i < intArray.length; i++) {
//            Calendar firstDisabled = DateUtils.getCalendar();
//
//            Log.i("Saaaaas",String.valueOf(intArray[i]));
//            firstDisabled.add(Calendar.DAY_OF_MONTH,intArray[i]);
//            events.add(new EventDay(firstDisabled, DrawableUtils.getCircleDrawableGLWithText(this, "GL")));
//        }
//
//
//        return events;
//    }
//
//    private void converdatefromcurrent()
//    {
//
//    }
}
