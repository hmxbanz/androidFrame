package cn.noname.app.server.response;


public class UserConfigResponse extends CommonResponse{

    /**
     *"state":1,
     * "msg":"成功",
     * "userConfig":{"UserConfigID":609,"UserInfoID":614,"Status":true,"SearchConfig":null,"Check":52,"OutDate":null,"IsRealPerson":null,"RongCloudToken":"r6le0DTgHy8dshz2x5ky4xTOPovWtxlso/j7CJHiRI2huN8IaEyjC2/mVpTmxMXxlMdKn6VLqrGVjDFJH5Fphw==","IsHomePage":null,"NickName":"斌娜扎"}
     */

    private ResultEntity result;

    public ResultEntity getUserConfig() {
        return result;
    }
    public void setUserConfig(ResultEntity result) {
        this.result = result;
    }
    public static class ResultEntity{
        private String UserInfoID;
        private String RongCloudToken;
        private String NickName;
        private boolean Status;
        private String SearchConfig;
        private String CheckName;
        private String IsRealPerson;
        private String IsHomePage;

        public String getUserInfoID() {
            return UserInfoID;
        }

        public void setUserInfoID(String userInfoID) {
            UserInfoID = userInfoID;
        }

        public String getRongCloudToken() {
            return RongCloudToken;
        }

        public void setRongCloudToken(String rongCloudToken) {
            RongCloudToken = rongCloudToken;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public boolean getStatus() {
            return Status;
        }

        public void setStatus(boolean status) {
            Status = status;
        }

        public String getSearchConfig() {
            return SearchConfig;
        }

        public void setSearchConfig(String searchConfig) {
            SearchConfig = searchConfig;
        }

        public String getCheckName() {
            return CheckName;
        }

        public void setCheckName(String checkName) {
            CheckName = checkName;
        }

        public String getIsRealPerson() {
            return IsRealPerson;
        }

        public void setIsRealPerson(String isRealPerson) {
            IsRealPerson = isRealPerson;
        }

        public String getIsHomePage() {
            return IsHomePage;
        }

        public void setIsHomePage(String isHomePage) {
            IsHomePage = isHomePage;
        }
    }
}
