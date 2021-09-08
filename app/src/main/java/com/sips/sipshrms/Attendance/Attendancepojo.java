package com.sips.sipshrms.Attendance;

public class Attendancepojo {

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getIn_out_time() {
        return in_out_time;
    }

    public void setIn_out_time(String in_out_time) {
        this.in_out_time = in_out_time;
    }

    public String getLeave_type_code() {
        return leave_type_code;
    }

    public void setLeave_type_code(String leave_type_code) {
        this.leave_type_code = leave_type_code;
    }

    String day;
    String weekday;
    String out_time;
    String in_time;
    String in_out_time;
    String leave_type_code;

}
