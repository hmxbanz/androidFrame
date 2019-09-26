package cn.noname.app.server.response;


import java.util.List;

public class SystemObjResponse extends CommonResponse{

    /**
     * code : 200
     * result : {"state":0,"msg":"无提示消息","sysObj":[{"DictionaryTypeId":null,"Name":"学历","ParentId":null,"ParentDictionary":null,"ChildDictionaries":[{"DictionaryTypeId":null,"Name":"高中","ParentId":1,"ParentDictionary":null,"ChildDictionaries":[],"Id":2}]}
     */

    private List<ResultEntity> sysObj;
    public List<ResultEntity> getSysObj() {
        return sysObj;
    }
    public void setSysObj(List<ResultEntity> result) {
        this.sysObj = result;
    }
    public class ResultEntity {
        private int DictionaryTypeId;
        private String Name;
        private int ParentId;
        private List<ResultEntity> ParentDictionary;
        private List<ResultEntity> ChildDictionaries;
        private int Id;

        public int getDictionaryTypeId() {
            return DictionaryTypeId;
        }

        public void setDictionaryTypeId(int dictionaryTypeId) {
            DictionaryTypeId = dictionaryTypeId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int parentId) {
            ParentId = parentId;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public List<ResultEntity> getChildDictionaries() {
            return ChildDictionaries;
        }

        public void setChildDictionaries(List<ResultEntity> childDictionaries) {
            ChildDictionaries = childDictionaries;
        }

        public List<ResultEntity> getParentDictionary() {
            return ParentDictionary;
        }

        public void setParentDictionary(List<ResultEntity> parentDictionary) {
            ParentDictionary = parentDictionary;
        }
    }
}
