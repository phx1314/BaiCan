package com.ntdlg.bc.bean;

import com.ab.util.AbDateUtil;
import com.mdx.framework.utility.Device;

import java.io.Serializable;

import static com.ntdlg.bc.F.getVersionName;

/**
 * Created by DELL on 2017/6/19.
 */

public class BeanLogin implements Serializable {
    public String terminalid = "A";
    public String devicecode = Device.getId();
    public String timestamp = AbDateUtil.getCurrentDate("yyyyMMddHHmmss");
    public String version = getVersionName();
    public String type = "1";
    public String phone = "";
    public String otherNo = "";
    public String code = "";


    public String sign ="";



}
