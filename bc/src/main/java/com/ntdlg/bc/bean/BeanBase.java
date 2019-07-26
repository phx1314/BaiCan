package com.ntdlg.bc.bean;

import com.framewidget.util.AbDateUtil;
import com.mdx.framework.utility.Device;
import com.ntdlg.bc.F;

import java.io.Serializable;

import static com.ntdlg.bc.F.getVersionName;

/**
 * Created by DELL on 2017/6/19.
 */

public class BeanBase implements Serializable {
    public String terminalid = "A";
    public String devicecode = Device.getId();
    public String timestamp = AbDateUtil.getCurrentDate("yyyyMMddHHmmss");
    public String version = getVersionName();
    public String sign = "";


    public String accountId = com.ntdlg.bc.F.UserId;
    public String token = com.ntdlg.bc.F.token;
    public String reftoken = F.reftoken;


}
