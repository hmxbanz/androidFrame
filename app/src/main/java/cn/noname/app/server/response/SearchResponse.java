package cn.noname.app.server.response;


import java.util.List;

public class SearchResponse extends CommonResponse{

    /**
     * code : 200
     * result : {"id":"t1hWCOGvX","token":"B0DA/kKanJviD5xxUzhwsEFIJad0/86YwGxBwz1417WFQi/Vr2OJay26s5IFDffGZaUYRMAkvN0ikvOcTl7RN9JilKZlosfQ"}
     */

    private int TotalPages;
    private List<ResultEntity> result;
    public int getTotalPages() {
        return TotalPages;
    }
    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }

    public List<ResultEntity> getUserList() {
        return result;
    }
    public void setUserList(List<ResultEntity> result) {
        this.result = result;
    }

    public class ResultEntity {
        private int UserInfoID;
        private int UserID;
        private String NickName;
        private String SelfDescribe;
        private String LastLogInDate;
        private String Age;
        private String ProvinceName;
        private String CityName;
        private String AreaName;
        private String NativeProvinceName;
        private String NativeCityName;
        private String NativeAreaName;
        private String IconSmall;

        public int getUserInfoID() {
            return UserInfoID;
        }

        public void setUserInfoID(int userInfoID) {
            UserInfoID = userInfoID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int userID) {
            UserID = userID;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getSelfDescribe() {
            return SelfDescribe;
        }

        public void setSelfDescribe(String selfDescribe) {
            SelfDescribe = selfDescribe;
        }

        public String getLastLogInDate() {
            return LastLogInDate;
        }

        public void setLastLogInDate(String lastLogInDate) {
            LastLogInDate = lastLogInDate;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String areaName) {
            AreaName = areaName;
        }

        public String getNativeProvinceName() {
            return NativeProvinceName;
        }

        public void setNativeProvinceName(String nativeProvinceName) {
            NativeProvinceName = nativeProvinceName;
        }

        public String getNativeCityName() {
            return NativeCityName;
        }

        public void setNativeCityName(String nativeCityName) {
            NativeCityName = nativeCityName;
        }

        public String getNativeAreaName() {
            return NativeAreaName;
        }

        public void setNativeAreaName(String nativeAreaName) {
            NativeAreaName = nativeAreaName;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String cityName) {
            CityName = cityName;
        }

        public String getProvinceName() {
            return ProvinceName;
        }

        public void setProvinceName(String provinceName) {
            ProvinceName = provinceName;
        }

        public String getIconSmall() {
            return IconSmall;
        }

        public void setIconSmall(String iconSmall) {
            IconSmall = iconSmall;
        }
    }
}

//AuthorityList : null
//        UserInfoID : 61
//        UserID : 61
//        PersonalityDescribe : null
//        SelfDescribe : "天蝎座乄剑臣 88年身高175cm，体重62kg。天蝎座，潮汕人在广州，工作私营企业主管，创业中，想找个合眼缘，善良，孝顺的女生，一起奋斗成家立业，喜欢温柔体贴小女人类型，活泼开朗！ "
//        IDnumber : null
//        Tel : null
//        CellPhone : "13888800020 "
//        Address : null
//        Sex : true
//        Birthday : "/Date(582825600000)/"
//        NickName : "良家少男"
//        RealName : "陈先生"
//        NationName : "汉族"
//        EducationName : "高中"
//        OccupationName : "客户经理"
//        IconSmall : "/Images/User/2016/06/21/2016062120341927_s.jpeg"
//        IconBig : "/Images/User/2016/06/21/2016062120341927_b.jpeg"
//        LastLogInDate : "/Date(1466513533670)/"
//        BodyHeight : 175
//        BodyWeight : 66
//        Industry : 116
//        Province : 1005
//        City : 1095
//        Area : 1551
//        NativeProvince : 1005
//        NativeCity : 1095
//        NativeArea : 1551
//        Occupation : 122
//        Nation : 6
//        Education : 2
//        IndustryName : "公司贸易"
//        ProvinceName : "广东"
//        CityName : "汕头"
//        AreaName : "金平区"
//        NativeProvinceName : "广东"
//        NativeCityName : "汕头"
//        NativeAreaName : "金平区"
//        MaritalStatus : 42
//        Salary : 27
//        House : 32
//        Car : 38
//        MaritalStatusName : "未婚"
//        SalaryName : "2000-5000"
//        HouseName : "未购房"
//        CarName : "暂未购车"
//        SexName : "男"
//        NoReadMessage : 0
//        CreateDate : "/Date(1466512439020)/"
//        UserName : "13888800020 "
//        Password : "123 "
//        Status : true
//        StatusName : "开通"
//        Check : 52
//        OutDate : null
//        OutDateCount : null
//        RoleName : "用户"
//        CheckName : "已审核"
//        Score : 2
//        QQ : "12345678"
//        Email : "12345678@qq.com"
//        VisitedCount : 11
//        IsRealPerson : null
//        ThumbUpCount : 0
//        FavorCount : 0
//        RoleID : 3
//        Age : 28
//        ModifyDate : null
//        IsHomePage : null
//        RongCloudToken : null