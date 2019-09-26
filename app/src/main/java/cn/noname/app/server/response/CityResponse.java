package cn.noname.app.server.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityResponse extends CommonResponse{

    /**
     * code : 200
     * result : {"state":0,"msg":"无提示消息","sysObj":[{"DictionaryTypeId":null,"Name":"学历","ParentId":null,"ParentDictionary":null,"ChildDictionaries":[{"DictionaryTypeId":null,"Name":"高中","ParentId":1,"ParentDictionary":null,"ChildDictionaries":[],"Id":2}]}
     */

    private List<ResultEntity> cities;

    public List<ResultEntity> getCities() {
        return cities;
    }
    public void setCities(List<ResultEntity> result) {
        this.cities = result;
    }
    public class ResultEntity {
        @SerializedName("ProvinceName")
        private String areaName;
        @SerializedName("Id")
        private String areaId;
        @SerializedName("Cities")
        private List<CityEntity> cities;

        public String getAreaName() {
            return areaName;
        }
        public void setAreaName(String areaName) {
            areaName = areaName;
        }
        public String getAreaId() {
            return areaId;
        }
        public void setAreaId(String areaId) {
            areaId = areaId;
        }
        public List<CityEntity> getCities() {
            return cities;
        }
        public void setCities(List<CityEntity> cities) {
            cities = cities;
        }

        private class CityEntity {
            @SerializedName("CityName")
            private String areaName;
            @SerializedName("Id")
            private String areaId;
            @SerializedName("Areas")
            private List<AreaEntity> counties;

            public String getAreaName() {
                return areaName;
            }
            public void setAreaName(String cityName) {
                areaName = areaName;
            }
            public String getAreaId() {
                return areaId;
            }
            public void setAreaId(String areaId) {
                areaId = areaId;
            }
            public List<AreaEntity> getCounties() {
                return counties;
            }
            public void setCounties(List<AreaEntity> counties) {
                counties = counties;
            }

            private class AreaEntity {
                @SerializedName("AreaName")
                private String areaName;
                @SerializedName("Id")
                private String areaId;

                public String getAreaName() {
                    return areaName;
                }
                public void setAreaName(String cityName) {
                    areaName = areaName;
                }
                public String getAreaId() {
                    return areaId;
                }
                public void setAreaId(String areaId) {
                    areaId = areaId;
                }
            }
        }
    }
}
