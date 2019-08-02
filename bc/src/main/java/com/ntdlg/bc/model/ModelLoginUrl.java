package com.ntdlg.bc.model;

import java.io.Serializable;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelLoginUrl implements Serializable {


    /**
     * sign : bab1230ce284aa05ca57404eee4793ee
     * loginUrl : http://www.zilvguoyuan.com/vip-html/#/member?returnPath=&channel=150501&customerId=tiloan&sign=d4f68c56e7a01e81badce8b12c06df88&timestamp=1564642155053
     * errorcode : 0000
     * errormsg : 获取免登陆地址成功
     */

    public String sign;
    public String loginUrl;
    public String errorcode;
    public String errormsg;
}
