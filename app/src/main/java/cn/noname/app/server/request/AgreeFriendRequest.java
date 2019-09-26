package cn.noname.app.server.request;

public class AgreeFriendRequest {

    private String messageId;
    private String objectUserInfoId;

    public AgreeFriendRequest(String messageId, String objectUserInfoId) {
        this.messageId = messageId;
        this.objectUserInfoId = objectUserInfoId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getObjectUserInfoId() {
        return objectUserInfoId;
    }

    public void setObjectUserInfoId(String objectUserInfoId) {
        this.objectUserInfoId = objectUserInfoId;
    }
}
