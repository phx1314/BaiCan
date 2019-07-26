package com.ntdlg.bc.view;

import java.io.Serializable;

public class SortModel implements Serializable {

    public String name;
    public String number;
    public String pingyin;
    public String sortKey;

    public SortModel(String name, String number, String sortKey) {
        this.name = name;
        this.number = number;
        this.sortKey = sortKey;
    }

}
