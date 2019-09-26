package cn.noname.app.server.request;

public class MessageRequest {
    private String ObjectUserInfoId;
    private String OwnerUserInfoId;
    private String Content;

    public MessageRequest(String objectUserInfoId, String ownerUserInfoId, String content) {
        ObjectUserInfoId = objectUserInfoId;
        OwnerUserInfoId = ownerUserInfoId;
        Content = content;
    }

    public String getObjectUserInfoId() {
        return ObjectUserInfoId;
    }

    public void setObjectUserInfoId(String objectUserInfoId) {
        ObjectUserInfoId = objectUserInfoId;
    }

    public String getOwnerUserInfoId() {
        return OwnerUserInfoId;
    }

    public void setOwnerUserInfoId(String ownerUserInfoId) {
        OwnerUserInfoId = ownerUserInfoId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
