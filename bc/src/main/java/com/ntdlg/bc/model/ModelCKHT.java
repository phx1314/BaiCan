package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelCKHT implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";
    public String accountId = "";
    public String token = "";
    public String reftoken = "";
    public List<ArrayBean> array;

    public static class ArrayBean implements Serializable {
        public String type = "";
        public String url = "";
    }

}
