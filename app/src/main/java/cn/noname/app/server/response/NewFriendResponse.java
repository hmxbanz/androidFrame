package cn.noname.app.server.response;


import java.util.List;

public class NewFriendResponse extends CommonResponse {

    /**
     *         MessageID : 445
     //        Title : null
     //        Content : "系统管理员！"
     //        CreateDate : "/Date(1496130601590)/"
     //        Type : 71
     //        Check : 52
     //        CheckName : "已审核"
     //        Additional : null
     //        OwnerUserInfoID : 2
     //        OwnerNickName : "管理员"
     //        ObjectUserInfoID : 34
     //        ObjectNickName : "风一样的汉子"
     //        SessionID : 215
     //        OwnerIconSmall : "/Images/User/2016/11/18/2016111823255252_s.jpg"
     //        ObjectIconSmall : "/Images/User/2016/04/19/2016041915153603_s.jpeg"
     //        rownum : 1
     //        OwnerNoReadedCount : 0
     //        ObjectNoReadedCount : 8
     //
     //{"TotalPages":2,"UserInfoId":2,"UserMessages":[{"MessageID":445,"Title":null,"Content":"系统管理员！","CreateDate":"\/Date(1496130601590)\/","Type":71,"Check":52,"CheckName":"已审核","Additional":null,"OwnerUserInfoID":2,"OwnerNickName":"管理员","ObjectUserInfoID":34,"ObjectNickName":"风一样的汉子","SessionID":215,"OwnerIconSmall":"/Images/User/2016/11/18/2016111823255252_s.jpg","ObjectIconSmall":"/Images/User/2016/04/19/2016041915153603_s.jpeg","rownum":1,"OwnerNoReadedCount":0,"ObjectNoReadedCount":8}],"state":1,"msg":"成功"}
     */

    private int TotalPages;
    private List<ResultEntity> UserMessages;

    public int getTotalPages() {
        return TotalPages;
    }
    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }
    public List<ResultEntity> getUserMessages() {
        return UserMessages;
    }
    public void setUserMessages(List<ResultEntity> userMessages) {
        UserMessages = userMessages;
    }
public class ResultEntity{
    public int MessageID;
    public String Title;
    public String CreateDate;
    public int Type;
    public int Check;
    public int OwnerUserInfoID;
    public String OwnerNickName;
    public int ObjectUserInfoID;
    public String ObjectNickName;
    public int SessionID;
    public String OwnerIconSmall;
    public String ObjectIconSmall;
    public int rownum;
    public int OwnerNoReadedCount;
    public int ObjectNoReadedCount;

    public int getMessageID() {
        return MessageID;
    }

    public void setMessageID(int messageID) {
        MessageID = messageID;
    }

    public int getOwnerUserInfoID() {
        return OwnerUserInfoID;
    }

    public void setOwnerUserInfoID(int ownerUserInfoID) {
        OwnerUserInfoID = ownerUserInfoID;
    }

    public String getOwnerNickName() {
        return OwnerNickName;
    }

    public void setOwnerNickName(String ownerNickName) {
        OwnerNickName = ownerNickName;
    }

    public int getObjectUserInfoID() {
        return ObjectUserInfoID;
    }

    public void setObjectUserInfoID(int objectUserInfoID) {
        ObjectUserInfoID = objectUserInfoID;
    }

    public String getObjectNickName() {
        return ObjectNickName;
    }

    public void setObjectNickName(String objectNickName) {
        ObjectNickName = objectNickName;
    }

    public int getSessionID() {
        return SessionID;
    }

    public void setSessionID(int sessionID) {
        SessionID = sessionID;
    }

    public String getOwnerIconSmall() {
        return OwnerIconSmall;
    }

    public void setOwnerIconSmall(String ownerIconSmall) {
        OwnerIconSmall = ownerIconSmall;
    }

    public String getObjectIconSmall() {
        return ObjectIconSmall;
    }

    public void setObjectIconSmall(String objectIconSmall) {
        ObjectIconSmall = objectIconSmall;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public int getOwnerNoReadedCount() {
        return OwnerNoReadedCount;
    }

    public void setOwnerNoReadedCount(int ownerNoReadedCount) {
        OwnerNoReadedCount = ownerNoReadedCount;
    }

    public int getObjectNoReadedCount() {
        return ObjectNoReadedCount;
    }

    public void setObjectNoReadedCount(int objectNoReadedCount) {
        ObjectNoReadedCount = objectNoReadedCount;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getCheck() {
        return Check;
    }

    public void setCheck(int check) {
        Check = check;
    }
}

}
