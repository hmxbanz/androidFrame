package cn.noname.app.server.response;


import java.util.List;

public class ADsResponse extends CommonResponse {


    /**
     * state : 0
     * msg : 无提示消息
     * ADs : [{"AdvertisementID":4,"ADName":"第三张广告图","ADPhoto":"/Images/AD/2017/11/02/2017110211504190","RemoteADPhoto":"http://oss.nannvyou.cn/Images/AD/banner3.jpg","Url":null,"Type":null,"OnOff":true,"CreateDate":"/Date(1509594641903)/"},{"AdvertisementID":3,"ADName":"第二张广告图","ADPhoto":"/Images/AD/2017/11/02/2017110211481317","RemoteADPhoto":"http://oss.nannvyou.cn/Images/AD/banner2.jpg","Url":null,"Type":null,"OnOff":true,"CreateDate":"/Date(1509594493177)/"},{"AdvertisementID":2,"ADName":"第一张广告图","ADPhoto":"/Images/AD/2016/04/11/2016041122005125.jpg","RemoteADPhoto":"http://oss.nannvyou.cn/Images/AD/banner1.jpg","Url":null,"Type":null,"OnOff":true,"CreateDate":"/Date(1460383251260)/"}]
     */


    private List<ADsBean> ADs;

    public List<ADsBean> getADs() {
        return ADs;
    }

    public void setADs(List<ADsBean> ADs) {
        this.ADs = ADs;
    }

    public static class ADsBean {
        /**
         * AdvertisementID : 4
         * ADName : 第三张广告图
         * ADPhoto : /Images/AD/2017/11/02/2017110211504190
         * RemoteADPhoto : http://oss.nannvyou.cn/Images/AD/banner3.jpg
         * Url : null
         * Type : null
         * OnOff : true
         * CreateDate : /Date(1509594641903)/
         */

        private int AdvertisementID;
        private String ADName;
        private String ADPhoto;
        private String RemoteADPhoto;
        private Object Url;
        private Object Type;
        private boolean OnOff;
        private String CreateDate;

        public int getAdvertisementID() {
            return AdvertisementID;
        }

        public void setAdvertisementID(int AdvertisementID) {
            this.AdvertisementID = AdvertisementID;
        }

        public String getADName() {
            return ADName;
        }

        public void setADName(String ADName) {
            this.ADName = ADName;
        }

        public String getADPhoto() {
            return ADPhoto;
        }

        public void setADPhoto(String ADPhoto) {
            this.ADPhoto = ADPhoto;
        }

        public String getRemoteADPhoto() {
            return RemoteADPhoto;
        }

        public void setRemoteADPhoto(String RemoteADPhoto) {
            this.RemoteADPhoto = RemoteADPhoto;
        }

        public Object getUrl() {
            return Url;
        }

        public void setUrl(Object Url) {
            this.Url = Url;
        }

        public Object getType() {
            return Type;
        }

        public void setType(Object Type) {
            this.Type = Type;
        }

        public boolean isOnOff() {
            return OnOff;
        }

        public void setOnOff(boolean OnOff) {
            this.OnOff = OnOff;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }
    }
}
