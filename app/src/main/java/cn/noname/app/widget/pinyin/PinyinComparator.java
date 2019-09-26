package cn.noname.app.widget.pinyin;


import java.util.Comparator;

import cn.noname.app.server.response.ContactsResponse;
import cn.noname.app.server.response.CountryResponse;


/**
 *
 * @author
 *
 */
public class PinyinComparator implements Comparator<CountryResponse> {


    public static PinyinComparator instance = null;

    public static PinyinComparator getInstance() {
        if (instance == null) {
            instance = new PinyinComparator();
        }
        return instance;
    }

    public int compare(CountryResponse o1, CountryResponse o2) {
        if (o1.getLetters().equals("@")
                || o2.getLetters().equals("#")) {
            return -1;
        } else if (o1.getLetters().equals("#")
                   || o2.getLetters().equals("@")) {
            return 1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }

}
