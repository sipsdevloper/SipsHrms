package com.sips.sipshrms.Common;

public class CompanyAncModel {

    private String full_name;

    private String attachment;

    private String message;

    private String profileImg;

    private String userid;

    public String getFull_name ()
    {
        return full_name;
    }

    public void setFull_name (String full_name)
    {
        this.full_name = full_name;
    }

    public String getAttachment ()
    {
        return attachment;
    }

    public void setAttachment (String attachment)
    {
        this.attachment = attachment;
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
}
