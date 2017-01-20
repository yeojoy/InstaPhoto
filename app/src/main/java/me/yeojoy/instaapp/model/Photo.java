package me.yeojoy.instaapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class Photo implements Parcelable {
    // 모든 정보를 다 가져올 필요가 없어서 필요한 정보만 가져올 예정
    @SerializedName("images")
    public Image[] mImages;
    @SerializedName("type")
    public String mType;
    @SerializedName("id")
    public String mId;

    protected Photo(Parcel in) {
        mImages = in.createTypedArray(Image.CREATOR);
        mType = in.readString();
        mId = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(mImages, flags);
        dest.writeString(mType);
        dest.writeString(mId);
    }
}
