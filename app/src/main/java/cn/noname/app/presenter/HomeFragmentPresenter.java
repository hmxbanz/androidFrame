package cn.noname.app.presenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;

import java.util.ArrayList;

import cn.noname.app.NnyConst;
import cn.noname.app.common.json.JsonMananger;
import cn.noname.app.loader.GlideImageLoader;
import cn.noname.app.server.HttpException;
import cn.noname.app.server.async.OnDataListener;
import cn.noname.app.server.response.ADsResponse;
import cn.noname.app.view.activity.MainActivity;
import cn.noname.app.widget.ACache;

public class HomeFragmentPresenter extends BasePresenter implements OnDataListener{
    private MainActivity activity;
    private Banner banner;
    private ADsResponse aDs;
    private ArrayList<String>  images=new ArrayList<>();

    public HomeFragmentPresenter(Context context){
        super(context);
        activity = (MainActivity) context;
    }

    public void init(Banner banner) {
        this.banner=banner;
        String aDCache = aCache.getAsString("ADCache");
        Logger.d("ADCache %s:", aDCache);
        if (aDCache!=null && !("null").equals(aDCache)) {
            try {
                aDs=JsonMananger.jsonToBean(aDCache, ADsResponse.class);
                //setBanner();

            } catch (HttpException e) {
                e.printStackTrace();
            }
        }
        else
            atm.request(NnyConst.GETADS,this);
    }

    private void setBanner() {
        for (ADsResponse.ADsBean bean:aDs.getADs()) {
            if(bean.getRemoteADPhoto()!=null)
                images.add(bean.getRemoteADPhoto());
            else
                images.add(NnyConst.SERVERURI+bean.getADPhoto());
        }
        //简单使用
        banner.setImages(images);//设置图片集合
        banner.setImageLoader(new GlideImageLoader());//设置图片加载器
        banner.start();
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case NnyConst.GETADS:
                return userAction.getAds("3");
        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (result == null) return;
            switch (requestCode) {
                case NnyConst.GETADS:
                    ADsResponse aDsResponse = (ADsResponse) result;
                    if (aDsResponse.getState() == NnyConst.SUCCESS) {
                 //       String[] urls = getResources().getStringArray(R.array.url);
                //      String[] tips = getResources().getStringArray(R.array.title);
                 //       List urlList = Arrays.asList(urls);
                  //      images = new ArrayList(urlList);

                        aDs = aDsResponse;
                        try {
                            String cache = null;
                            cache = JsonMananger.beanToJson(aDsResponse);
                            aCache.put("ADCache", cache, 10* ACache.TIME_DAY);
                        } catch (HttpException e) {
                            e.printStackTrace();
                        }
                        //setBanner();
                    }
                    break;

            }
        }
    }
