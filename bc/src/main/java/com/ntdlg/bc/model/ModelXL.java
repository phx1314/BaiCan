package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelXL implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";
    public String isExist = "";
    public String username = "";
    public String usercid = "";
    public String userResidence = "";
    public String userInhabiting = "";
    public String userMarriage = "";
    public String userEducation = "";
    public String userSSQ = "";
    public List<BaseData> xlRecords;
    public List<BaseData> hyRecords;
    public List<BaseData> jzqkRecords;


}
