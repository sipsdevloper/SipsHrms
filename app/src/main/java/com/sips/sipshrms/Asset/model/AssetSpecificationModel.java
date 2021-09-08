package com.sips.sipshrms.Asset.model;

public class AssetSpecificationModel {
    private String specification_id;

    private String specification_name;

    private String specification_type_id;

    private String specification_type_name;

    private String asset_id;

    public String getSpecification_id ()
    {
        return specification_id;
    }

    public void setSpecification_id (String specification_id)
    {
        this.specification_id = specification_id;
    }

    public String getSpecification_name ()
    {
        return specification_name;
    }

    public void setSpecification_name (String specification_name)
    {
        this.specification_name = specification_name;
    }

    public String getSpecification_type_id ()
    {
        return specification_type_id;
    }

    public void setSpecification_type_id (String specification_type_id)
    {
        this.specification_type_id = specification_type_id;
    }

    public String getSpecification_type_name ()
    {
        return specification_type_name;
    }

    public void setSpecification_type_name (String specification_type_name)
    {
        this.specification_type_name = specification_type_name;
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
