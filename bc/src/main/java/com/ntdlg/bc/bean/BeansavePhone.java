package com.ntdlg.bc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/6/19.
 */

public class BeansavePhone extends BeanBase {
    public List<linkManBean> linkMan = new ArrayList<>();
    public String applyId = com.ntdlg.bc.F.applyId;

    public static class linkManBean {
        public linkManBean(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        public String name;
        public String phone;
    }

}
