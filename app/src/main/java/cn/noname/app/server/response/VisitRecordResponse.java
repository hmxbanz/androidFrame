package cn.noname.app.server.response;


import java.util.List;

public class VisitRecordResponse extends CommonResponse{

    /**
     *
     *{"TotalPages":1,"state":1,"msg":"成功","VisitRecords":[{"CreateDate":"\/Date(1493266962793)\/","VisitRecordID":1658,"ObjectUserInfoID":2,"ObjectUserID":2,"ObjectIcon":"/Images/User/2016/11/18/2016111823255252_s.jpg","ObjectNickName":"管理员","OwnerUserID":501,"OwnerUserInfoID":501,"OwnerNickName":"小蔡","OwnerIcon":"/Images/User/2017/04/05/2017040521420012_s.jpg"}]}
     */

    private int totalPages;
    private List<ResultEntity> result;

    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public List<ResultEntity> getVisitRecords() {
        return result;
    }
    public void setVisitRecords(List<ResultEntity> result) {
        this.result = result;
    }
    public static class ResultEntity {
        private String VisitRecordID;
        private String OwnerUserInfoID;
        private String OwnerIcon;
        private String OwnerNickName;
        private String CreateDate;

        public String getVisitRecordID() {
            return VisitRecordID;
        }

        public void setVisitRecordID(String visitRecordID) {
            VisitRecordID = visitRecordID;
        }

        public String getOwnerUserInfoID() {
            return OwnerUserInfoID;
        }

        public void setOwnerUserInfoID(String ownerUserInfoID) {
            OwnerUserInfoID = ownerUserInfoID;
        }

        public String getOwnerIcon() {
            return OwnerIcon;
        }

        public void setOwnerIcon(String ownerIcon) {
            OwnerIcon = ownerIcon;
        }

        public String getOwnerNickName() {
            return OwnerNickName;
        }

        public void setOwnerNickName(String ownerNickName) {
            OwnerNickName = ownerNickName;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }
    }

}

