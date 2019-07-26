package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelWDJK implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";
    public String accountId = "";
    public String token = "";
    public String reftoken = "";
    public String startpage = "";
    public String endpage = "";
    public String count = "";
    public List<BillsBean> bills;

    public static class BillsBean implements Serializable {
        public String billId = "";
        public String billNo = "";
        public String billAmount = "";
        public String date = "";
        public String periods = "";
        public String term = "";
        public String radio = "";
        public String rapayAmount = "";
        public String beginData = "";
        public String endData = "";
        public String billStatus = "";
        public String whbj = "";
        public String amountRecords = "";
    }
}
