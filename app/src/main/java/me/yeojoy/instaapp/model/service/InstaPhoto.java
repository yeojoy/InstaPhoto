package me.yeojoy.instaapp.model.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yeojoy on 2017. 1. 21..
 *
 * Service 단에서 사용할 Photo model로
 * 일단 image의 url만 추가한다.
 */

public class InstaPhoto implements Parcelable {
    public String mId;
    public String mImageUrl;
    public String mType;
    public float mImageWidth;
    public float mImageHeight;

    public InstaPhoto(String id, String imageUrl, String type, float width, float height) {
        mId = id;
        mImageUrl = imageUrl;
        mType = type;
        mImageWidth = width;
        mImageHeight = height;
    }

    protected InstaPhoto(Parcel in) {
        mId = in.readString();
        mImageUrl = in.readString();
        mType = in.readString();
        mImageWidth = in.readFloat();
        mImageHeight = in.readFloat();
    }

    public static final Creator<InstaPhoto> CREATOR = new Creator<InstaPhoto>() {
        @Override
        public InstaPhoto createFromParcel(Parcel in) {
            return new InstaPhoto(in);
        }

        @Override
        public InstaPhoto[] newArray(int size) {
            return new InstaPhoto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mImageUrl);
        dest.writeString(mType);
        dest.writeFloat(mImageWidth);
        dest.writeFloat(mImageHeight);
    }

    @Override
    public String toString() {
        return "InstaPhoto{" +
                "mId='" + mId + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mType='" + mType + '\'' +
                ", mImageWidth=" + mImageWidth +
                ", mImageHeight=" + mImageHeight +
                '}';
    }
}
