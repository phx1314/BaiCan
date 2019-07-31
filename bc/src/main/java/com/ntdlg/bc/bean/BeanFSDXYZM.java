package com.ntdlg.bc.bean;

import com.framewidget.util.AbDateUtil;
import com.mdx.framework.utility.Device;

import java.io.Serializable;

import static com.ntdlg.bc.F.getVersionName;

/**
 * Created by DELL on 2017/6/19.
 */

public class BeanFSDXYZM implements Serializable {
    public String terminalid = "A";
    public String devicecode = Device.getId();
    public String timestamp = AbDateUtil.getCurrentDate("yyyyMMddHHmmss");
    public String version = getVersionName();
    public String sign ="";


//    public String type = "";
    public String phone = "";



}
