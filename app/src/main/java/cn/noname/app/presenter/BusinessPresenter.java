package cn.noname.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.noname.app.NnyConst;
import cn.noname.app.adapter.SearchAdapter;
import cn.noname.app.common.NToast;
import cn.noname.app.listener.AlertDialogCallBack;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.async.OnDataListener;
import cn.noname.app.server.response.SearchResponse;
import cn.noname.app.view.activity.LoginActivity;
import cn.noname.app.view.activity.MainActivity;
import cn.noname.app.widget.ExpandSearchView;
import cn.noname.app.widget.DialogYesOrNo;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class BusinessPresenter extends BasePresenter implements OnDataListener,SearchAdapter.OnItemClick{
    private SearchAdapter searchListAdapter;
    private MainActivity activity;

    //搜索参数
    private boolean gender=true;
    private boolean marry=true;
    private String ageRange="18-50岁";
    private String area="";
    private int pageIndex=1;
    private int pageSize=20;
    private TextView emptyView;
    private List<SearchResponse.ResultEntity> mUserList=new ArrayList<>();
    private int totalPages;

    public BusinessPresenter(Context context){
        super(context);
        activity = (MainActivity) context;
    }

    public void init(TextView emptyView) {

    }

    public void search(boolean gender, boolean marry, String ageRange, String area, TextView layout_search_text, ImageView mImageView, ExpandSearchView mExpandView) {
        pageIndex=1;
        this.mUserList.clear();
        super.initData();
        if(!isLogin)
        {
            DialogYesOrNo dialog= DialogYesOrNo.getInstance();
            dialog.showDialog(context, "请先登录", new AlertDialogCallBack() {
                @Override
                public void executeEvent() {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }

            });
            dialog.setConfirmText("前往登录");
            return;
        }
        layout_search_text.setText("点击展开搜索条件");
        mImageView.setVisibility(View.VISIBLE);
        mExpandView.collapse();
        this.gender=gender;
        this.marry=marry;
        this.ageRange=ageRange;
        this.area=area;
        search();
    }

    private void search() {
        atm.request(NnyConst.SEARCH,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case NnyConst.SEARCH:
                return userAction.search(gender,marry,ageRange,area,pageIndex,pageSize);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
        switch (requestCode) {
            case NnyConst.SEARCH:
                SearchResponse searchResponse=(SearchResponse)result;
                if (searchResponse.getState() == NnyConst.SUCCESS) {
                        totalPages=searchResponse.getTotalPages();
                    if (searchResponse.getUserList().size() > -1) {
                        mUserList.addAll(searchResponse.getUserList());
                        searchListAdapter.notifyDataSetChanged();

                    } else {
                        this.emptyView.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    if(searchResponse.getState() == NnyConst.CHECKFAILURE)
                        NToast.shortToast(context,"您的资料审核未通过，不允许搜索");
                    else
                        NToast.shortToast(context,searchResponse.getMsg());
                }

                break;
        }
    }


    @Override
    public boolean onItemClick(int position, View view, SearchResponse.ResultEntity entity) {
        //GetUserActivity.StartActivity(context, String.valueOf(entity.getUserInfoID()));
        return false;
    }
}