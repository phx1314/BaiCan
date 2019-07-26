package com.ntdlg.bc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class BeanPLTJLXR extends BeanBase {

    public List<LinkManBean> linkMan;

    public static class LinkManBean implements Serializable {
        public String name = "";
        public String phone = "";
    }

}
