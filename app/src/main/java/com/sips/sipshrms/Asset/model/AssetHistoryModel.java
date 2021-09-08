package com.sips.sipshrms.Asset.model;

public class AssetHistoryModel {
    private String returned_date;

    private String full_name;

    private String issued_date;

    private String employee_id;

    private String return_status;

    private String issue_status;

    private String asset_id;

    public String getReturned_date ()
    {
        return returned_date;
    }

    public void setReturned_date (String returned_date)
    {
        this.returned_date = returned_date;
    }

    public String getFull_name ()
    {
        return full_name;
    }

    public void setFull_name (String full_name)
    {
        this.full_name = full_name;
    }

    public String getIssued_date ()
    {
        return issued_date;
    }

    public void setIssued_date (String issued_date)
    {
        this.issued_date = issued_date;
    }

    public String getEmployee_id ()
    {
        return employee_id;
    }

    public void setEmployee_id (String employee_id)
    {
        this.employee_id = employee_id;
    }

    public String getReturn_status ()
    {
        return return_status;
    }

    public void setReturn_status (String return_status)
    {
        this.return_status = return_status;
    }

    public String getIssue_status ()
    {
        return issue_status;
    }

    public void setIssue_status (String issue_status)
    {
        this.issue_status = issue_status;
    }

    public String getAsset_id ()
    {
        return asset_id;
    }

    public void setAsset_id (String asset_id)
    {
        this.asset_id = asset_id;
    }

}
