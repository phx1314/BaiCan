package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelYHKList implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";
    public String accountId = "";
    public String token = "";
    public String reftoken = "";
    public List<BankcardsBean> bankcards;

    public static class BankcardsBean implements Serializable {
        public String cardNo = "";
        public String bankDeposit = "";
        public String bankName = "";
        public String bankLogo = "";
        public String bankPhone = "";
        public String bankId = "";
        public String patientia = "";
    }
}
