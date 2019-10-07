package com.ntdlg.bc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class ModelPub implements Serializable {
    public String devicecode = "";
    public String errorcode = "";
    public String errormsg = "";
    public String sign = "";

    public String accountId = "";
    public String token = "";
    public String reftoken = "";

    public List<NextProcessBean> nextProcess;

    public static class NextProcessBean implements Serializable {
        public String type = "";
        public String name = "";
        public String imageUrl = "";
        public String prompt = "";
        public String code = "";
    }
}
