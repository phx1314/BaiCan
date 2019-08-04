package com.ntdlg.bc.model;

import java.io.Serializable;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelLoginUrl implements Serializable {


    /**
     * dataObject : {"type":null,"success":true,"code":"0000","message":"获取免登陆地址成功","name":null,"method":null,"data":"http://www.zilvguoyuan.com/vip-html/#/member?returnPath=&channel=150501&customerId=tiloan&sign=ee505e3df43498490cfa62a7775fd0ec&timestamp=1564822507257"}
     */

    public DataObjectBean dataObject;

    public static class DataObjectBean {
        /**
         * type : null
         * success : true
         * code : 0000
         * message : 获取免登陆地址成功
         * name : null
         * method : null
         * data : http://www.zilvguoyuan.com/vip-html/#/member?returnPath=&channel=150501&customerId=tiloan&sign=ee505e3df43498490cfa62a7775fd0ec&timestamp=1564822507257
         */

        public Object type;
        public boolean success;
        public String code;
        public String message;
        public Object name;
        public Object method;
        public String data;
    }
}
