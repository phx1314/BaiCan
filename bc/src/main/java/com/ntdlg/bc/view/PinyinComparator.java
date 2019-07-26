package com.ntdlg.bc.view;

import java.util.Comparator;

import static com.framewidget.F.toPinYin;


/**
 * @author xiaanming
 */
public class PinyinComparator implements Comparator<SortModel> {

    public int compare(SortModel o1, SortModel o2) {
        if (toPinYin(o2.name.charAt(0)).equals("#")) {
            return -1;
        } else if (toPinYin(o1.name.charAt(0)).equals("#")) {
            return 1;
        } else {
            return toPinYin(o1.name.charAt(0)).compareTo(toPinYin(o2.name.charAt(0)));
        }
    }

}
