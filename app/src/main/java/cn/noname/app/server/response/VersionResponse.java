package cn.noname.app.server.response;


public class VersionResponse extends CommonResponse{

    /**
     * state : 1
     * msg:"成功"
     * result : {"Android":{"VersionCode":20170526,"VersionName":"第二版本","DownloadUrl":"http:test.noname.cn/tae-demo.apk"},"state":1,"msg":"成功"}
     */

    private ResultEntity result;
    public ResultEntity getAndroid() {
        return result;
    }
    public void setAndroid(ResultEntity result) {
        this.result = result;
    }
    public class ResultEntity {
        private String VersionInfo;
        private int VersionCode;
        private String VersionName;
        private String DownloadUrl;

        public String getVersionInfo() {
            return VersionInfo;
        }

        public void setVersionInfo(String versionInfo) {
            VersionInfo = versionInfo;
        }

        public int getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(int versionCode) {
            VersionCode = versionCode;
        }

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String versionName) {
            VersionName = versionName;
        }

        public String getDownloadUrl() {
            return DownloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            DownloadUrl = downloadUrl;
        }
    }
}
