package cn.noname.app.server.response;


public class GetUserMessagesCountResponse extends CommonResponse {

    /**
     * MessagesCount : 0
     * state : 1
     * msg : 成功
     */

    private int MessagesCount;

    public int getMessagesCount() {
        return MessagesCount;
    }

    public void setMessagesCount(int MessagesCount) {
        this.MessagesCount = MessagesCount;
    }

}
