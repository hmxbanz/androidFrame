package cn.noname.app.server.response;


import java.util.List;

public class DynamicDetailResponse extends CommonResponse {

    /**
     * code : 200
     * result : {"id":"t1hWCOGvX","token":"B0DA/kKanJviD5xxUzhwsEFIJad0/86YwGxBwz1417WFQi/Vr2OJay26s5IFDffGZaUYRMAkvN0ikvOcTl7RN9JilKZlosfQ"}
     */

    private ResultEntity result;

    public ResultEntity getLifeShare() {        return result;    }
    public void setLifeShare(ResultEntity result){this.result=result;}

    public class ResultEntity {
        private int LifeShareID;
        private int UserInfoID;
        private String NickName;
        private String IconSmall;
        private String IconBig;
        private String Content;
        private String CreateDate;
        private List<HomeDynamicResponse.ResultEntity.PhotoEntity> photoEntity;

        public List<HomeDynamicResponse.ResultEntity.PhotoEntity> getLifePhotoes() {
            return photoEntity;
        }

        public void setLifePhotoes(List<HomeDynamicResponse.ResultEntity.PhotoEntity> photoEntity) {
            this.photoEntity = photoEntity;
        }

        public int getLifeShareID() {
            return LifeShareID;
        }

        public void setLifeShareID(int lifeShareID) {
            LifeShareID = lifeShareID;
        }

        public int getUserInfoID() {
            return UserInfoID;
        }

        public void setUserInfoID(int userInfoID) {
            UserInfoID = userInfoID;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getIconSmall() {
            return IconSmall;
        }

        public void setIconSmall(String iconSmall) {
            IconSmall = iconSmall;
        }

        public String getIconBig() {
            return IconBig;
        }

        public void setIconBig(String iconBig) {
            IconBig = iconBig;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }
    }
}
