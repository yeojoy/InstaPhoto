package me.yeojoy.instaapp.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class Image implements Parcelable {
    @SerializedName("url")
    public String mImageUrl;
    @SerializedName("width")
    public float mImageWidth;
    @SerializedName("height")
    public float mImageHeight;

    protected Image(Parcel in) {
        mImageUrl = in.readString();
        mImageWidth = in.readFloat();
        mImageHeight = in.readFloat();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageUrl);
        dest.writeFloat(mImageWidth);
        dest.writeFloat(mImageHeight);
    }
}

