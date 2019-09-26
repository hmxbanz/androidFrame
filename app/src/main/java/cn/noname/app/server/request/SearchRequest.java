package cn.noname.app.server.request;

public class SearchRequest {

    private boolean Sex;
    private boolean Marry;
    private String Area;
    private String AgeRange;
    private int PageIndex;
    private int PageSize;

    public boolean getSex() {
        return Sex;
    }
    public void setSex(boolean sex) {
        Sex = sex;
    }
    public boolean getMarry() {
        return Marry;
    }
    public void setMarry(boolean marry) {
        Marry = marry;
    }
    public String getArea() {
        return Area;
    }
    public void setArea(String area) {
        Area = area;
    }
    public String getAgeRange() {
        return AgeRange;
    }
    public void setAgeRange(String ageRange) {
        AgeRange = ageRange;
    }
    public int getPageIndex() {
        return PageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.PageIndex = pageIndex;
    }
    public int getPageSize() {
        return PageSize;
    }
    public void setPageSize(int pageSize) {        this.PageSize = pageSize;
    }

    public SearchRequest(boolean sex, boolean marry, String area, String agerange, int pageIndex, int pageSize) {
        this.Sex=sex;
        this.Marry=marry;
        this.Area=area;
        this.AgeRange=agerange;
        this.PageIndex=pageIndex;
        this.PageSize=pageSize;
    }


}
