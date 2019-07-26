package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelSQJE implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";

    public List<AmountRecordsBean> amountRecords;

    public static class AmountRecordsBean implements Serializable {
        public String code = "";
        public String value = "";
    }

    public List<DeadlineRecordsBean> deadlineRecords;

    public static class DeadlineRecordsBean implements Serializable {
        public String code = "";
        public String value = "";
    }

}
