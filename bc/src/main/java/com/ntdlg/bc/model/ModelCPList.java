package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelCPList implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";
    public List<RecordsBean> records;

    public static class RecordsBean implements Serializable {
        public String prcAmount = "";
        public String prcTerm = "";
        public String prcTermUnit = "";
        public String prcRadio = "";
        public String repayAmount = "";
    }
}
