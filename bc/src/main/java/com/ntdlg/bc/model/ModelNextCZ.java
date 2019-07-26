package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelNextCZ implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";
    public String accountId = "";
    public String token = "";
    public String reftoken = "";
    public String applyId = "";
    public String isNameAuth = "";
    public String userName = "";
    public String userCard = "";
    public String issueAuthority = "";
    public String authStatus = "";
    public String vaildPriod = "";
    public List<RecordsBean> records;

    public static class RecordsBean implements Serializable {
        public String url = "";
        public String photoType = "";
    }
}
