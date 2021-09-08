package com.sips.sipshrms.Common;

public class NotificationModel {

    private String notification_type;

    private String full_name;

    private String employee_id;

    private String id;

    private String message;

    private String profileImg;

    private String userid;

    private String status;

    public String getNotification_type ()
    {
        return notification_type;
    }

    public void setNotification_type (String notification_type)
    {
        this.notification_type = notification_type;
    }

    public String getFull_name ()
    {
        return full_name;
    }

    public void setFull_name (String full_name)
    {
        this.full_name = full_name;
    }

    public String getEmployee_id ()
    {
        return employee_id;
    }

    public void setEmployee_id (String employee_id)
    {
        this.employee_id = employee_id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getProfileImg ()
    {
        return profileImg;
    }

    public void setProfileImg (String profileImg)
    {
        this.profileImg = profileImg;
    }

    public String getUserid ()
    {
        return userid;
    }

    public void setUserid (String userid)
    {
        this.userid = userid;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }
}
