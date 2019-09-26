package cn.noname.app;

/**
 * Created by hmxbanz on 2017/3/3.
 */

public class NnyConst {
    public static final String EXIT = "EXIT";
    public static final String NNYVERSION = "2.0.1.3";
    public static final String GETVERSION = "http://www.nannvyou.cn/version.txt";
    public static final String LOGIN_ID = "loginid";
    public static final String LOGIN_USERNAME = "userName";
    public static final String LOGING_PASSWORD = "password";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String USERID = "userId";
    public static final String USERINFOID = "userInfoId";
    public static final String DYNAMICID = "dynamicId";
    public static final String IMGURI="http://oss.nannvyou.cn";
    public static final String JImUserName = "JImUserName";
    public static final String JImPass = "JImPass";
    public static String SERVERURI="http://m.nannvyou.cn";
    public static final String ISLOGIN = "isLogin";
    public static final String SMALLICON = "iconSmall";
    public static final String CHECKNAME = "checkName";
    public static final String ROLENAME = "roleName";

    public static final int CHOOSE_COUNTRY = 1;

    //Server Return Code
    public static final int SUCCESS = 1;
    public static final int FAILURE=2;
    public static final int CAPTCHAWRONG=3;
    public static final int NULL =4;
    public static final int NOTNULL =5;
    public static final int LOGINTIMEOUT =6;
    public static final int CHECKFAILURE = 7;
    public static final int UNAUTHORIZED = 8;
    public static final int SELFERROR = 9;
    public static final int BLACKLIST = 10;
    //极光IM
    public static final String TARGET_ID = "targetId";
    public static final String TARGET_APP_KEY = "targetAppKey";
    //接口常量代码
    public static final int GETALBUMS = 1001;//取相册
    public static final int GETCONTACTS = 1002;//取好友
    public static final int GETDYNAMICDETAIL = 1003;//取动态详情
    public static final int ADDDYNAMIC = 1004;//添加新动态
    public static final int GETFAVOR = 1005;//取收藏
    public static final int DELFAVOR = 1038;//删除收藏
    public static final int GETCAPTCHA = 1006;//取验证码
    public static final int FORGETPASSWORD = 1007;//忘记密码
    public static final int UPDATEFRIENDCONDITION = 1008;//择友条件
    public static final int GETUSER = 1009;//取用户
    public static final int THUMBSUP = 1011;//点赞
    public static final int ADDFAVOR = 1012;//收藏
    public static final int ADDFRIEND = 1013;//加好友
    public static final int ADDSESSION = 1014;//聊天
    public static final int ADDBLACKLIST = 1015;//加黑名单
    public static final int CHECKVERSION = 1016;//查新版本
    public static final int GETADS = 1017;//取广告轮播
    public static final int DELFRIEND = 1018;//删除好友
    public static final int QUERYFRIEND =1019;//查询好友
    public static final int LOGIN = 1020;//登录
    public static final int GET_TOKEN = 1021;//获取第三方聊天token
    public static final int SYNC_USER_INFO = 1022;//取用户信息
    public static final int UPLOADAVATOR = 1023;//上传头像
    public static final int GETUSERMESSAGESCOUNT = 1024;//取消息数
    public static final int MODITYPASSWORD = 1025;//修改密码
    public static final int GETNEWFRIEND = 1026;//取新的好友
    public static final int AGREENEWFRIEND = 1027;//同意新好友
    public static final int NEWPASSWORD = 1028;//新密码
    public static final int GETORDERS = 1029;//取订单
    public static final int GETPHOTOS = 1030;//取照片
    public static final int GETPROTOCOCOL = 1031;//取协议
    public static final int REGISTER = 1032;//注册
    public static final int REPORT = 1033;//举报
    public static final int SEARCH = 1034;//搜索
    public static final int GETCONFIG=1035;//取用户配置
    public static final int CLOSEINFO = 1036;//关闭资料
    public static final int LOGOFF = 1037;//登出
    public static final int GETSYSTEMOBJ = 1038;//取系统对象
    public static final int GETCITIES = 1039;//取城市
    public static final int UPDATEINFO = 1040;//修改用户资料
    public static final int UPLOAD = 1041;//上传图片
    public static final int GETVISITRECORDBYME = 1042;//取我看过谁
    public static final int GETVISITRECORD = 1043;//谁看过我
    public static final int THIRDPART_CHECK = 1044 ;//第三方登录检查
}
