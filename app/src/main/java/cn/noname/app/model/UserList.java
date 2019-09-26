package cn.noname.app.model;

import java.util.ArrayList;
import java.util.List;

import cn.noname.app.widget.pinyin.CharacterParser;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class UserList {
    private static final String[] tiles=   {       "android", "iphone", "winphone"    };
    private static final String[] imgUrl=   {       "http://oss.nannvyou.cn//Images/User/2017/02/02/2017020223391971_s.jpg", "http://oss.nannvyou.cn//Images/User/2017/01/05/2017010508510057_s.jpg", "http://oss.nannvyou.cn//Images/User/2016/12/02/2016120213492393_s.jpg"    };
    private static final int[] icons=   {  android.R.drawable.ic_dialog_map, android.R.drawable.ic_btn_speak_now, android.R.drawable.ic_dialog_alert    };

    public static  List<User> getData ()
    {
        List<User> listItems = new ArrayList<>();

        for (int i=0;i<12;i++){
            for(int j = 0; j< UserList.icons.length; j++)
            {
                User listItem=new User();
                listItem.setNickName(UserList.tiles[j]);
                listItem.setAvatar(UserList.imgUrl[j]);
                listItem.setImgResource(UserList.icons[j]);
                listItem.setNameSpelling(CharacterParser.getInstance().getSpelling(UserList.tiles[j]));
                listItems.add(listItem);
            }
        }

        return listItems;

    }
}
