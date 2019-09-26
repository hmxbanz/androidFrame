package cn.noname.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class User implements Parcelable {
    private String nickName;
    private String avatar;
    private int ImgResource;
    private String nameSpelling;
    private String letters;

    public User (){
    }

    public User (String userName,String avatar){
        this.nickName=userName;
        this.avatar =avatar;
    }

    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {  this.avatar = avatar;   }

    public int getImgResource() {
        return ImgResource;
    }
    public void setImgResource(int imgResource) {
        ImgResource = imgResource;
    }

    public String getNameSpelling() {
        return nameSpelling;
    }
    public void setNameSpelling(String nameSpelling) {
        this.nameSpelling = nameSpelling;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        ParcelUtils.writeToParcel(dest, this.getPhotoInfo());
//        ParcelUtils.writeToParcel(dest, this.getLetters());
//        ParcelUtils.writeToParcel(dest, this.getAvatar());
    }
}
