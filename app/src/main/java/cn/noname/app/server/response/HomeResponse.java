package cn.noname.app.server.response;


import java.util.List;

/**
 * Created by AMing on 15/12/24.
 * Company RongCloud
 */
public class HomeResponse extends CommonResponse{

    private List<ResultEntity> UserList;

    public void setUserList(List<ResultEntity> UserList) {
        this.UserList = UserList;
    }
    public List<ResultEntity> getUserList() {
        return UserList;
    }

    public static class ResultEntity {
        private int UserInfoID;
        private String NickName;
        private String IconSmall;
        private String CityName;
        private String Age;

        public void setUserInfoID(int userInfoID) {
            this.UserInfoID = userInfoID;
        }
        public void setIconSmall(String iconSmall) {  this.IconSmall = iconSmall;
        }
        public void setNickName(String nickName) { this.NickName = nickName;     }

        public int getUserInfoID() {            return UserInfoID;        }
        public String getIconSmall() { return IconSmall;   }
        public String getNickName() { return NickName;   }

        public String getCityName() {
            return CityName;
        }
        public void setCityName(String cityName) {
            CityName = cityName;
        }
        public String getAge() {
            return Age;
        }
        public void setAge(String age) {
            Age = age;
        }
    }
}
