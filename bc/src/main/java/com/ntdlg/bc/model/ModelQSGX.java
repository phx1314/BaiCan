package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelQSGX implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";
    public String isExist = "";
    public List<BaseData> qsgxRecords;
    public List<BaseData> shgxRecords;
    public List<LxrRecordsBean> lxrRecords;

    public static class LxrRecordsBean implements Serializable {
        public String name = "";
        public String phone = "";
        public String relation = "";
        public String id = "";
        public String type = "";
    }
}
