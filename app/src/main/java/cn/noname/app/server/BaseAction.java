package cn.noname.app.server;

import android.content.Context;

import com.alibaba.fastjson.JSONException;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.noname.app.NnyConst;
import cn.noname.app.common.json.JsonMananger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;


/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
public class BaseAction {
    public static String DOMAIN = NnyConst.SERVERURI;
    private final OkHttpUtils OkHttpUtil;
    protected Context mContext;
    protected OkHttpClient okHttpClient;
    public  String token="";
    private final String TAG=BaseAction.class.getSimpleName();
    private final String CONTENT_TYPE = "application/json";
    private final String ENCODING = "charset=utf-8";
    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public BaseAction(Context context) {
        this.mContext = context;
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(mContext));
        okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).connectTimeout(10000, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtil=OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * JSON转JAVA对象方法
     *
     * @param json json
     * @param cls  class
     * @throws HttpException
     */
    public <T> T jsonToBean(String json, Class<T> cls) throws HttpException {
        return JsonMananger.jsonToBean(json, cls);
    }

    /**
     * JSON转JAVA数组方法
     *
     * @param json json
     * @param cls  class
     * @throws HttpException
     */
    public <T> List<T> jsonToList(String json, Class<T> cls) throws HttpException {
        return JsonMananger.jsonToList(json, cls);
    }

    /**
     * JAVA对象转JSON方法
     *
     * @param obj object
     * @throws HttpException
     */
    public String BeanTojson(Object obj) throws HttpException {
        return JsonMananger.beanToJson(obj);
    }


    /**
     * 获取完整URL方法
     *
     * @param url url
     */
    protected String getURL(String url) {
        return getURL(url, new String[] {});
    }

    /**
     * 获取完整URL方法
     *
     * @param url    url
     * @param params params
     */
    protected String getURL(String url, String... params) {
        StringBuilder urlBuilder = new StringBuilder(NnyConst.SERVERURI).append(url);
        if (params != null) {
            for (String param : params) {
                if (!urlBuilder.toString().endsWith("/")) {
                    urlBuilder.append("/");
                }
                urlBuilder.append(param);
            }
        }
        return urlBuilder.toString();
    }

    //get请求
    public <T> T getRequest(Class<T> t, LinkedHashMap map, String uri) throws HttpException {
        String result = "";
        Response response = null;
        try {
            response = OkHttpUtil
                    .get()
                    .addHeader(NnyConst.ACCESS_TOKEN, token)
                    .params(map)
                    .url(uri)
                    .build()
                    .execute();
            result = response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("请求的：%s", map);
        Logger.d(t.getSimpleName() + "：" + result);
        T beanResponse = null;
        try {
            if (t.getSimpleName().equals("SystemObjResponse") || t.getSimpleName().equals("CityResponse"))
                beanResponse = new Gson().fromJson(result, t);
            else if(t.getSimpleName().equals("String") )
                beanResponse = (T)result;
            else
                beanResponse = JsonMananger.jsonToBean(result, t);
        } catch (JSONException e) {
            Logger.e(TAG, t.getSimpleName() + " occurs JSONException e=" + e.toString());
            return null;
        }
        return beanResponse;
    }
    //post请求
    public <T> T postRequest(Class<T> t, String json, String uri)throws HttpException
    {
        String result="";
        Response response=null;
        try {
//            HashMap<String,String> headers=new HashMap<String,String>();
//            headers.put(NnyConst.ACCESS_TOKEN,token);
//            headers.put("Cookie","ASP.NET_SessionId=");

            response=OkHttpUtil
                    .postString()
                    .addHeader(NnyConst.ACCESS_TOKEN, token)
                    .url(uri)
                    .mediaType(MediaType.parse(CONTENT_TYPE+";"+ENCODING))
                    .content(json)//.content(new Gson().toJson(new User("zhy", "123")))
                    .build().execute();
            result =response.body().string();

//            Headers responseHeaders = response.headers();
//            int responseHeadersLength = responseHeaders.size();
//            for (int i = 0; i < responseHeadersLength; i++){
//                String headerName = responseHeaders.name(i);
//                String headerValue = responseHeaders.get(headerName);
//                System.out.print("TAG----------->Name:"+headerName+"------------>Value:"+headerValue+"\n");
//            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpException("timeout");
        }
        Logger.d("请求的：%s",json);
        Logger.d(t.getSimpleName()+"："+ result);

        T beanResponse = null;
        try {
            beanResponse = JsonMananger.jsonToBean(result, t);
        } catch (JSONException e) {
            Logger.e(e.toString());
            return null;
        }
        return beanResponse;
    }
    //post请求
    public <T> T postFormRequest(Class<T> t, Map<String, String> params, String fileKey, String fileName, File file, String uri)throws HttpException
    {
        String result="";
        Response response=null;
        try {
            response=OkHttpUtil
                    .post()
                    .addHeader(NnyConst.ACCESS_TOKEN, token)
                    .params(params)
                    .addFile(fileKey, fileName,file)
                    .url(uri)
                    .build()
                    .connTimeOut(10000)
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.d("请求的：%s",params);
        Logger.d(t.getSimpleName()+"："+ result);

        T beanResponse = null;
        try {
            beanResponse = JsonMananger.jsonToBean(result, t);
        } catch (JSONException e) {
            Logger.e(TAG, t.getSimpleName()+" occurs JSONException e=" + e.toString());
            return null;
        }
        return beanResponse;
    }
}
