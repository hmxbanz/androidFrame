package cn.noname.app.server;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.noname.app.NnyConst;
import cn.noname.app.common.json.JsonMananger;
import cn.noname.app.server.request.AgreeFriendRequest;
import cn.noname.app.server.request.MessageRequest;
import cn.noname.app.server.request.NewPasswordRequest;
import cn.noname.app.server.request.RegisterRequest;
import cn.noname.app.server.request.ReportRequest;
import cn.noname.app.server.request.SearchRequest;
import cn.noname.app.server.response.CommonResponse;
import cn.noname.app.server.response.PhotoResponse;
import cn.noname.app.server.response.VersionResponse;
import cn.noname.app.server.request.LoginRequest;
import cn.noname.app.server.request.ModifyPasswordRequest;
import cn.noname.app.server.response.ADsResponse;
import cn.noname.app.server.response.AlbumResponse;
import cn.noname.app.server.response.CaptchaResponse;
import cn.noname.app.server.response.CityResponse;
import cn.noname.app.server.response.ContactsResponse;
import cn.noname.app.server.response.DynamicDetailResponse;
import cn.noname.app.server.response.FavorResponse;
import cn.noname.app.server.response.GetUserMessagesCountResponse;
import cn.noname.app.server.response.GetUserResponse;
import cn.noname.app.server.response.HomeDynamicResponse;
import cn.noname.app.server.response.HomeResponse;
import cn.noname.app.server.response.LoginResponse;
import cn.noname.app.server.response.NewFriendResponse;
import cn.noname.app.server.response.OrderResponse;
import cn.noname.app.server.response.RegisterResponse;
import cn.noname.app.server.response.SearchResponse;
import cn.noname.app.server.response.SystemObjResponse;
import cn.noname.app.server.response.UserConfigResponse;
import cn.noname.app.server.response.VisitRecordResponse;
import cn.noname.app.server.response.VisitedByMeResponse;


/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
@SuppressWarnings("deprecation")
public class UserAction extends BaseAction {
    private final String TAG=UserAction.class.getSimpleName();
    public static UserAction instance;
    //private  CountDownLatch latch = new CountDownLatch(1);

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public UserAction(Context context) {
        super(context);
    }
    public static UserAction getInstance(Context context) {
        if (instance == null) {
            synchronized (UserAction.class) {
                if (instance == null) {
                    instance = new UserAction(context);
                }
            }
        }
        else
            instance.mContext=context;
        return instance;
    }

    public Object getUserMessagesCount(String count)throws HttpException {
        String uri = getURL("/User/GetUserMessagesCount");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("type",count);
        return getRequest(GetUserMessagesCountResponse.class,map,uri);
    }

    public Object getAds(String pageSize) throws HttpException {
        String uri = getURL("/Home/GETADs");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("pageSize",pageSize);
        return getRequest(ADsResponse.class,map,uri);
    }


//    public class MyStringCallback extends StringCallback    {
//        @Override
//        public void onBefore(Request request, int id)
//        {
//        }
//
//        @Override
//        public void onAfter(int id)
//        {
//        }
//
//        @Override
//        public void onError(Call call, Exception e, int id)
//        {
//            e.printStackTrace();
//        }
//
//        @Override
//        public void onResponse(String response, int id)
//        {
//            NLog.e(TAG, "onResponse：complete");
//            NLog.e(TAG, response);
////            try {
////                latch.await();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }
//
//        @Override
//        public void inProgress(float progress, long total, int id)
//        {
//            Log.e(TAG, "inProgress:" + progress);
//        }
//    }

    //取好友列表
    public ContactsResponse getContacts(String pageIndex,String pageSize) throws HttpException {
        String uri = getURL("/User/GetFriends");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("access_token",token);
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        return getRequest(ContactsResponse.class,map,uri);
    }
    //取我的消息列表
    public NewFriendResponse getUserMessages(String pageIndex, String pageSize,String type) throws HttpException {
        String uri = getURL("/User/GetUserMessages");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        map.put("type",type);
        return getRequest(NewFriendResponse.class,map,uri);
    }
    //取我的收藏
    public FavorResponse getFavors(String pageIndex, String pageSize) throws HttpException {
        String uri = getURL("/User/GetFavors");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        return getRequest(FavorResponse.class,map,uri);
    }
    //取谁看了我
    public VisitRecordResponse getVisitRecords(String pageIndex, String pageSize) throws HttpException {
        String uri = getURL("/User/GetVisitRecords");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        return getRequest(VisitRecordResponse.class,map,uri);
    }
    //取我看了谁
    public VisitedByMeResponse getVisitRecordByMe(String pageIndex, String pageSize) throws HttpException {
        String uri = getURL("/User/GetVisitedByMe");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        return getRequest(VisitedByMeResponse.class,map,uri);
    }
    //取我的订单
    public OrderResponse getOrders(String pageIndex, String pageSize) throws HttpException {
        String uri = getURL("/User/GetOrders");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        return getRequest(OrderResponse.class,map,uri);
    }
    //取用户相册
    public AlbumResponse getAlbums(String userInfoId) throws HttpException {
        String uri = getURL("/User/GetAlbums");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("objectUserInfoId",userInfoId);
        return getRequest(AlbumResponse.class,map,uri);
    }
    //举报用户
    public CommonResponse report(String objectUserInfoId, String title, String content) throws HttpException{
        String uri = getURL("/Home/Report");
        String json = JsonMananger.beanToJson(new ReportRequest(objectUserInfoId, title, content));
        return postRequest(CommonResponse.class,json,uri);
    }
    //取用户协议
    public String getProtocol() throws HttpException{
        String uri = getURL("/Home/GetProtocol");
        LinkedHashMap map=new LinkedHashMap<>();
        return getRequest(String.class,map,uri);

//        String result="";
//        String uri = getURL("/Home/GetProtocol");
//        Response response=null;
//        try {
//            response=OkHttpUtils
//                    .get()
//                    .url(uri)
//                    .build()
//                    .execute();
//            result =response.body().string();
//            Log.w(TAG, "接收的："+ result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (!TextUtils.isEmpty(result)) {
//            NLog.e("FavorResponse", result);
//            return result;
//        }
//        return null;
    }
    //取动态详情
    public DynamicDetailResponse getDynamicDetail(String dynamicId) throws HttpException{
        String uri = getURL("/User/GetOneLifeShare");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("queryType","Json");
        map.put("lifeShareId",dynamicId);
        return getRequest(DynamicDetailResponse.class,map,uri);
    }
    //取检查版本
    public VersionResponse checkVersion() throws HttpException {
        String uri = NnyConst.GETVERSION;
        return getRequest(VersionResponse.class,null,uri);
    }
    //取系统对象
    public SystemObjResponse getSystemObj() throws HttpException {
        String uri = getURL("/Nny/GetSysObj");
        return getRequest(SystemObjResponse.class,null,uri);
    }
    //取地区对象
    public CityResponse getCities() throws HttpException {
        String uri = getURL("/Nny/GetCities");
        return getRequest(CityResponse.class,null,uri);
    }
    //修改密码
    public CommonResponse modifyPassword(String oldPassword, String newPassword) throws HttpException {
        String uri = getURL("/User/ChangePass");
        String json = JsonMananger.beanToJson(new ModifyPasswordRequest(oldPassword, newPassword));
        return postRequest(CommonResponse.class,json,uri);
    }
    //搜索
    public SearchResponse search(boolean gender, boolean marry, String ageRange, String area, int pageIndex, int pageSize) throws HttpException{
        String uri = getURL("/Home/Search");
        String json = JsonMananger.beanToJson(new SearchRequest(gender,marry,area,ageRange,pageIndex,pageSize));
        return postRequest(SearchResponse.class,json,uri);
    }
    //修改资料
    public CommonResponse updateInfo(String dataType, String data) throws HttpException {
        String uri = getURL("/User/Update");
        String json="{\"dataType\":\""+dataType+"\",\"data\":\"" + data + "\"}";
        return postRequest(CommonResponse.class,json,uri);
    }
    //修改择偶条件
    public CommonResponse updateFriendCondition(String dataType, String data) throws HttpException {
        String uri = getURL("/User/FriendCondition");
        String json="{\"dataType\":\""+dataType+"\",\"data\":\"" + data + "\"}";
        return postRequest(CommonResponse.class,json,uri);
    }
    //通过加为好友
    public CommonResponse addFriendAgreed(String messageId, String objectUserInfoId) throws HttpException{
        String uri = getURL("/User/AddFriendAgreed");
        String json = JsonMananger.beanToJson(new AgreeFriendRequest(messageId, objectUserInfoId));
        return postRequest(CommonResponse.class,json,uri);
    }
    //上传头像
    public CommonResponse uploadAvatar(File imgFile) throws HttpException {
        String uri = getURL("/User/UploadAvatar");
//        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("imgFile", "imgFile.jpg",RequestBody.create(MEDIA_TYPE_PNG, imgFile))
//                .build();
        HashMap<String,String> params= new HashMap<>();
       return postFormRequest(CommonResponse.class,params,"imgFile", "imgFile.jpg",imgFile,uri);
    }
    //登录
    public LoginResponse login(String region, String userName, String password) throws HttpException {
        String uri = getURL("/home/login");
        String json = JsonMananger.beanToJson(new LoginRequest(region, userName, password));
        return postRequest(LoginResponse.class,json,uri);
    }
    //用户注册
    public RegisterResponse register(String userName, String password,String captcha) throws HttpException {
        String uri = getURL("/home/register");
        String json = JsonMananger.beanToJson(new RegisterRequest(userName, password,captcha));
        return postRequest(RegisterResponse.class,json,uri);
    }
    //获取验证码
    public CaptchaResponse getCaptcha(String cellPhone) throws HttpException    {
        String uri = getURL("/Home/GetCaptcha");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("cellPhone",cellPhone);
        return getRequest(CaptchaResponse.class,map,uri);
    }
    //设置新密码
    public CommonResponse newPassword(String userName, String password,String captcha) throws HttpException {
        String uri = getURL("/Home/FindPassword");
        String json = JsonMananger.beanToJson(new NewPasswordRequest(userName, password,captcha));
        return postRequest(CommonResponse.class,json,uri);
    }
    //取首页
    public HomeResponse getMembers(String type, String cityName) throws HttpException {
        String uri = getURL("/Home/getMembers");
        LinkedHashMap map = new LinkedHashMap<>();
        map.put("type", type);
        map.put("cityName", cityName);
        return getRequest(HomeResponse.class, map, uri);
    }
    //获取首页动态
    public HomeDynamicResponse getDynamics(String pageIndex, String pageSize, String userInfoId) throws HttpException {
        String uri = getURL("/Home/getDynamics");
        LinkedHashMap map = new LinkedHashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("userInfoId", userInfoId);
        return getRequest(HomeDynamicResponse.class, map, uri);
    }
    //获取一个用户资料
    public GetUserResponse getUser(String userInfoId) throws HttpException {
        String uri = getURL("/Home/GetUser");
        LinkedHashMap map = new LinkedHashMap<>();
        map.put("queryType", "Json");
        map.put("userInfoId", userInfoId);
        return getRequest(GetUserResponse.class, map, uri);
    }
    //点赞一个用户
    public CommonResponse addThumbsUp(String userInfoId) throws HttpException{
        String uri = getURL("/User/AddThumbsUp");
        String json="{\"objectUserInfoId\":\""+userInfoId+"\"}";
        return postRequest(CommonResponse.class,json,uri);
    }
    //收藏一个用户
    public CommonResponse addAddFavor(String userInfoId) throws HttpException{
        String uri = getURL("/User/AddFavor");
        String json="{\"objectUserInfoId\":\""+userInfoId+"\"}";
        return postRequest(CommonResponse.class,json,uri);
    }
    //加好友
    public CommonResponse addFriend(String userInfoId) throws HttpException{
        String uri = getURL("/User/AddFriend");
        String json="{\"objectUserInfoId\":\""+userInfoId+"\"}";
        return postRequest(CommonResponse.class,json,uri);
    }
    //发送消息
    public CommonResponse addSession(String userInfoId) throws HttpException{
        String uri = getURL("/User/AddSession");
        String json="{\"objectUserInfoId\":\""+userInfoId+"\"}";
        return postRequest(CommonResponse.class,json,uri);
    }
    //拉黑一个用户
    public CommonResponse addBlackList(String userInfoId) throws HttpException {
        String uri = getURL("/User/AddBlackList");
        String json = "{\"objectUserInfoId\":\"" + userInfoId + "\"}";
        return postRequest(CommonResponse.class, json, uri);
    }
    //获取某个用户某个相册的照片
    public PhotoResponse getPhotoes(String userInfoId, String albumId) throws HttpException{
        String uri = getURL("/User/GetPhotoes");
        LinkedHashMap map = new LinkedHashMap<>();
        map.put("albumId", albumId);
        map.put("userInfoId", userInfoId);
        return getRequest(PhotoResponse.class, map, uri);
    }
    //获取用户配置参数
    public UserConfigResponse getUserConfig(String userInfoId) throws HttpException    {
        String uri = getURL("/User/GetUserConfig");
        LinkedHashMap map = new LinkedHashMap<>();
        map.put("userInfoId", userInfoId);
        return getRequest(UserConfigResponse.class, map, uri);
    }
    //修改用户配置参数
    public CommonResponse modifyConfig(String dataType, String data) throws HttpException{
        String uri = getURL("/User/Config");
        String json="{\"dataType\":\""+dataType+"\",\"data\":\"" + data + "\"}";
        return postRequest(CommonResponse.class,json,uri);
    }
    //发新动态
    public CommonResponse addDynamic(String content, File imgFile) throws HttpException {
        String uri = getURL("/User/AddLifeShare");
        HashMap<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("continueSend", "false");
        return postFormRequest(CommonResponse.class, params, "imgFile", "imgFile.jpg", imgFile, uri);
    }
    //上传相册图片
    public CommonResponse uploadPhoto(String photoInfo, String albumType, File imgFile) throws HttpException {
        String uri = getURL("/User/UploadPhoto");
        HashMap<String, String> params = new HashMap<>();
        params.put("photoInfo", photoInfo);
        params.put("albumType", albumType);
        return postFormRequest(CommonResponse.class, params, "imgFile", "imgFile.jpg", imgFile, uri);
    }
    //发信息
    public CommonResponse sendMessage(String fromUser, String toUser,String message) throws HttpException {
        String uri = getURL("/User/AddMessage22");
        String json = JsonMananger.beanToJson(new MessageRequest(fromUser,toUser,message));
        return postRequest(CommonResponse.class,json,uri);
    }
    //删除好友
    public CommonResponse delFriend(String objectUserInfoID)throws HttpException{
        String uri = getURL("/User/DelFriend");
        String json="{\"objectUserInfoId\":\""+objectUserInfoID+"\"}";
        return postRequest(CommonResponse.class,json,uri);
    }
    //删除收藏
    public CommonResponse delFavor(String objectUserInfoID)throws HttpException{
        String uri = getURL("/User/DelFavor");
        String json="{\"objectUserInfoId\":\""+objectUserInfoID+"\"}";
        return postRequest(CommonResponse.class,json,uri);
    }

    //开始聊天前先查询是否为好友
    public CommonResponse queryFriend(String objectUserInfoID)throws HttpException{
        String uri = getURL("/User/QueryFriend");
        String json="{\"objectUserInfoId\":\""+objectUserInfoID+"\"}";
        return postRequest(CommonResponse.class,json,uri);
    }

    //登出
    public CommonResponse logoff() throws HttpException    {
        String uri = getURL("/Home/LogOff");
        LinkedHashMap map = new LinkedHashMap<>();
        return getRequest(CommonResponse.class, map, uri);
    }
}

