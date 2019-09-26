package cn.noname.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.noname.app.R;
import cn.noname.app.presenter.MinePresenter;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class MineFragment extends Fragment  {
    public static MineFragment instance = null;
    private MinePresenter minePresenter;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;


    public static MineFragment getInstance() {
        if (instance == null) {
            instance = new MineFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        minePresenter = new MinePresenter(getActivity(),this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission Granted
//                minePresenter.onRequestPermissionsResult();
//            } else {
//                // Permission Denied
//            }
//        }
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        minePresenter.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        minePresenter.onActivityResult(requestCode, resultCode, data);
    }

}
