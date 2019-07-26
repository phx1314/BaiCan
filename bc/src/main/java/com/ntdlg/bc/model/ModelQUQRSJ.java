package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelQUQRSJ implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";
    public String accountId = "";
    public String token = "";
    public String reftoken = "";
    public String applyId = "";
    public String applyAmount = "";
    public String applyTerm = "";
    public String applyTermType = "";
    public String applyRaio = "";
    public String repayAmount = "";
    public String repayAmounyType = "";
    public String repayModes = "";
    public String bankNo = "";
    public String url = "";
    public List<RecordsBean> records;

    public static class RecordsBean implements Serializable {
        public String currentPeriords = "";
        public String totalPeriords = "";
        public String endDate = "";
        public String bqyhje = "";
    }
}
