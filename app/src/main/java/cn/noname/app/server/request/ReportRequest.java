package cn.noname.app.server.request;

public class ReportRequest {

    private String objectUserInfoId;
    private String title;
    private String content;

    public ReportRequest(String objectUserInfoId, String title, String content) {
        this.objectUserInfoId = objectUserInfoId;
        this.title = title;
        this.content = content;
    }

    public String getObjectUserInfoId() {
        return objectUserInfoId;
    }

    public void setObjectUserInfoId(String objectUserInfoId) {
        this.objectUserInfoId = objectUserInfoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
