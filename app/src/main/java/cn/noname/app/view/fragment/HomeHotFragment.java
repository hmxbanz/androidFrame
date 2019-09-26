package cn.noname.app.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import java.util.List;

import cn.noname.app.NnyConst;
import cn.noname.app.R;
import cn.noname.app.adapter.HomeRecyclerViewAdapter;
import cn.noname.app.common.json.JsonMananger;
import cn.noname.app.model.UserList;
import cn.noname.app.presenter.BasePresenter;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.async.OnDataListener;
import cn.noname.app.server.response.HomeResponse;
import cn.noname.app.widget.DialogLoading;
import cn.noname.app.view.activity.MainActivity;


public class HomeHotFragment extends Fragment implements HomeRecyclerViewAdapter.ItemClickListener,OnDataListener {
    public static HomeHotFragment instance = null;
    private RecyclerView recycleView;
    private HomeRecyclerViewAdapter dataAdapter;
    public ScrollView scrollView;
    private View view;
    private GridLayoutManager gridLayoutManager;
    public static final int GETHOTMEMBERS = 5;
    public static final String TAG = HomeHotFragment.class.getSimpleName();
    private List<HomeResponse.ResultEntity> memberList;
    private BasePresenter basePresenter;
    public static HomeHotFragment getInstance() {
        if (instance == null) {
            instance = new HomeHotFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_new, null);
        basePresenter = new BasePresenter(getActivity());
        initViews();
        //refresh();
        return view;
    }

    private void initViews() {
        recycleView= (RecyclerView) view.findViewById(R.id.home_new_recyclerView);
        gridLayoutManager=new GridLayoutManager(getContext(),3);
        recycleView.setLayoutManager(gridLayoutManager);
        dataAdapter = new HomeRecyclerViewAdapter(this.memberList, getContext());
        dataAdapter.setFooterView(LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_footer,null));
        dataAdapter.getFooterView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击应跳到搜索页
                MainActivity.StartActivity(getContext(),"toSearch");
            }
        });
        recycleView.setNestedScrollingEnabled(false);
        if(Build.VERSION.SDK_INT>=23)
            recycleView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (gridLayoutManager.findLastCompletelyVisibleItemPosition()==(UserList.getData().size()-1))
                    {}
                }
            });
        //recycleView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
        dataAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }

    @Override
    public void onItemClick(int position, String data) {
        //GetUserActivity.StartActivity(getActivity(), data);
    }

    public void Refresh(){
        String mMemberListCache = basePresenter.aCache.getAsString("HomeHotCache");
        if(mMemberListCache !=null && !("null").equals(mMemberListCache))
            try {
                memberList = JsonMananger.jsonToList(mMemberListCache,HomeResponse.ResultEntity.class);
            } catch (HttpException e) {
                e.printStackTrace();
            }
        if(memberList != null) updateData(memberList);
        DialogLoading.show(getContext());
        basePresenter.atm.request(GETHOTMEMBERS, this);
    }

    private void updateData(List<HomeResponse.ResultEntity> mMemberList) {
        dataAdapter.setListItems(this.memberList);
        recycleView.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }
    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case GETHOTMEMBERS:
                return basePresenter.userAction.getMembers("Hot", "汕头市");
        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        DialogLoading.dismiss(getContext());
        if (result != null) {
            switch (requestCode) {
                case GETHOTMEMBERS:
                    HomeResponse homeResponse = (HomeResponse) result;
                    if (homeResponse.getState() == NnyConst.SUCCESS) {
                        memberList =homeResponse.getUserList();
                        try {
                            String cache=JsonMananger.beanToJson(memberList);
                            basePresenter.aCache.put("HomeHotCache",cache);
                        } catch (HttpException e) {
                            e.printStackTrace();
                        }
                        updateData(memberList);
                    } else if (homeResponse.getState() == NnyConst.FAILURE) {

                    }

                    break;
            }
        }
    }
    @Override
    public void onFailure(int requestCode, int state, Object result) {

    }
}
