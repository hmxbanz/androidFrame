package cn.noname.app.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.adapter.FriendListAdapter;
import cn.noname.app.common.NToast;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.response.ContactsResponse;
import cn.noname.app.server.response.CountryResponse;
import cn.noname.app.view.activity.ContactsActivity;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.widget.pinyin.CharacterParser;
import cn.noname.app.widget.pinyin.PinyinComparator;
import cn.noname.app.widget.pinyin.SideBar;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getSupportedCountries;

public class ContactsPresenter extends BasePresenter implements FriendListAdapter.ItemClickListener {
    private SideBar sideBar;
    private List<CountryResponse> countryList;
    private List<CountryResponse> filteredFriendList;


    private TextView nameTextView;
    private TextView txtNoFriends;
    private TextView unreadTextView;
    private View headView;
    private EditText searchEditText;
    /**
     * 中部展示的字母提示
     */
    private TextView dialogTxtView;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private ListView listView;
    /**
     * 好友列表的 friendListAdapter
     */
    private FriendListAdapter friendListAdapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;

    public ContactsPresenter(Context context){
        super(context);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = PinyinComparator.getInstance();
        init();
        //updateFriendsList(mFriendList);
        SMSSDK.registerEventHandler(eh); //注册短信回调
        getSupportedCountries();
    }


    public EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                    countryList=new ArrayList<>();
                    for (Map.Entry<Character, ArrayList<String[]>> ent : SMSSDK
                            .getGroupedCountryList().entrySet()) {
                        ArrayList<String[]> cl = ent.getValue();
                        for (String[] paire : cl) {

                            CountryResponse entity=new CountryResponse();
                            entity.setCountryName(paire[0]);
                            entity.setCountryCode(paire[1]);
                            countryList.add(entity);
                            //Log.e("sdada", "国家 (" + paire[0] + ")---(" + "区号:" + paire[1] + ")\n");
                        }
                    }

                    for(CountryResponse entity:countryList)
                    {
                        entity.setNameSpelling(CharacterParser.getInstance().getSpelling(entity.getCountryName()));
                    }

                    ((ContactsActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            friendListAdapter = new FriendListAdapter(context, countryList);
                            listView.setAdapter(friendListAdapter);
                            friendListAdapter.setItemClickListener(ContactsPresenter.this);
                            updateFriendsList(countryList);
                        }
                    });



                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };

    public void init() {
        searchEditText = activity.findViewById(R.id.search);
        listView = activity.findViewById(R.id.listview);
        txtNoFriends = activity.findViewById(R.id.show_no_friend);
        sideBar = activity.findViewById(R.id.sidrbar);
        dialogTxtView = activity.findViewById(R.id.group_dialog);
        sideBar.setTextView(dialogTxtView);

        pinyinComparator = PinyinComparator.getInstance();
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = friendListAdapter.getPositionForSection(s.charAt(0));
                if (position != -1 && ContactsPresenter.this.listView.getCount()>1) {
                    ContactsPresenter.this.listView.setSelection(position);
                }
            }
        });
    }

//        ContactsResponse contactsResponse = (ContactsResponse) result;
//
//        if(contactsResponse.getState() == NnyConst.SUCCESS){
//            List<ContactsResponse.ResultEntity> tempContaccts = contactsResponse.getFriends();
//            for(ContactsResponse.ResultEntity entity:tempContaccts)
//            {
//                entity.setNameSpelling(CharacterParser.getInstance().getSpelling(entity.getObjectNickName()));
//            }
//            contacts =tempContaccts;
//
//        }

//    private void initData() {
//        filteredFriendList = new ArrayList<>();//这个变量用于搜索过滤好友
//    }
//
    private void updateFriendsList(List<CountryResponse> friendsList) {
        //updateUI fragment初始化和好友信息更新时都会调用,isReloadList表示是否是好友更新时调用
        boolean isReloadList = false;
        if (countryList != null && countryList.size() > 0) {
            //contacts.clear();
            //isReloadList = true;
        }
        countryList = friendsList;
        if (countryList != null && countryList.size() > 0) {
            handleFriendDataForSort();
            txtNoFriends.setVisibility(View.GONE);
        } else {
            txtNoFriends.setVisibility(View.VISIBLE);
        }
        sideBar.setVisibility(View.VISIBLE);
        // 根据a-z进行排序源数据
        Collections.sort(countryList, pinyinComparator);
        if (isReloadList) {
            friendListAdapter.updateListView(countryList);
        } else {
            friendListAdapter = new FriendListAdapter(context, countryList);
            listView.setAdapter(friendListAdapter);
            friendListAdapter.setItemClickListener(ContactsPresenter.this);

        }
    }

    private void handleFriendDataForSort() {
        for (CountryResponse friend : countryList) {
            String letters = replaceFirstCharacterWithUppercase(friend.getNameSpelling());
            friend.setLetters(letters);
        }
    }

    private String replaceFirstCharacterWithUppercase(String spelling) {
        if (!TextUtils.isEmpty(spelling)) {
            char first = spelling.charAt(0);
            char newFirst = first;
            if (first >= 'a' && first <= 'z') {
                newFirst -= 32;
            }
            return spelling.replaceFirst(String.valueOf(first), String.valueOf(newFirst));
        } else {
            return "#";
        }
    }

    public void onDestroy() {

    }

    public void start() {
        updateFriendsList(countryList);
    }

    @Override
    public void OnItemClick(CountryResponse entity) {
        //NToast.shortToast(context,entity.getCountryName());
        Intent intent=new Intent();
        intent.putExtra("countryName",entity.getCountryName());
        intent.putExtra("countryCode",entity.getCountryCode());
        activity.setResult(Activity.RESULT_OK,intent);
        activity.finish();
    }
}
