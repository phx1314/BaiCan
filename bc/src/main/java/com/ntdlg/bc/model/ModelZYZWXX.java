package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelZYZWXX implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";
    public String isExist = "";
    public String trade = "";
    public String position = "";
    public String profession = "";
    public String company = "";
    public String companyAddress = "";
    public String companyPhone = "";
    public String income = "";
    public String companyCity = "";
    public List<BaseData> zyRecords;

    public List<BaseData> zwRecords;

    public List<BaseData> hyRecords;

    public List<BaseData> ysrRecords;

}
