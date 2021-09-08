package com.sips.sipshrms.Common;

public class EventModel {

    private String designation_name;

    private String location_name;

    private String full_name;

    private String department_name;

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    private String employee_id;

    private String event_name;
    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    private String profileImg;

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getDesignation_name ()
    {
        return designation_name;
    }

    public void setDesignation_name (String designation_name)
    {
        this.designation_name = designation_name;
    }

    public String getLocation_name ()
    {
        return location_name;
    }

    public void setLocation_name (String location_name)
    {
        this.location_name = location_name;
    }

    public String getFull_name ()
    {
        return full_name;
    }

    public void setFull_name (String full_name)
    {
        this.full_name = full_name;
    }

    public String getDepartment_name ()
    {
        return department_name;
    }

    public void setDepartment_name (String department_name)
    {
        this.department_name = department_name;
    }
}
