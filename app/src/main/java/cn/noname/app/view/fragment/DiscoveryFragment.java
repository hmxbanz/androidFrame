package cn.noname.app.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import cn.noname.app.R;
import cn.noname.app.presenter.BusinessPresenter;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.OptionPicker;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class DiscoveryFragment extends Fragment implements View.OnClickListener,AddressPicker.OnAddressPickListener {
    private static final String TAG = DiscoveryFragment.class.getSimpleName() ;
    public static DiscoveryFragment instance = null;
    private LinearLayout layoutTitle;
    private TextView txtSearchTitle;
    private ImageView imageArrow;
    private View view;
    public Button btnAge, btnArea, btnSearch;
    private SwitchButton sbMarray, sbGender;
    private BusinessPresenter businessPresenter;


    public static DiscoveryFragment getInstance() {
        if (instance == null) {
            instance = new DiscoveryFragment();
        }
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_business, null);
        initViews();
        //initData();
        businessPresenter = new BusinessPresenter(getActivity());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    public void initViews(){
          }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_age:
                String[] ageRange2=new String[]{"18-25岁", "26-30岁", "31-35岁","36-40岁","40岁以上"};
                OptionPicker picker = new OptionPicker(getActivity(),ageRange2 );
                picker.setOffset(1);
                picker.setSelectedIndex(1);
                picker.setTextSize(16);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int position, String option) {
                        btnAge.setText(option);
                    }
                });
                picker.show();
                break;
            case R.id.btn_area:

        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void initData() {
        sbGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
        sbMarray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
    }

    @Override
    public void onAddressPicked(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {
        if (county == null) {
            //NLog.w(TAG,"province : " + province + ", city: " + city);
        } else {
            //NLog.w(TAG,"province : " + province + ", city: " + city + ", county: " + county);
        }
        btnArea.setText(province.getAreaName() + "-" + city.getAreaName());
    }
}