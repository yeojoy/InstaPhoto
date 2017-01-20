package me.yeojoy.instaapp.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class Images implements Parcelable {
    @SerializedName("low_resolution")
    public Image mLowResolution;
    @SerializedName("thumbnail")
    public Image mThumbnail;
    @SerializedName("standard_resolution")
    public Image mStandardResolution;

    protected Images(Parcel in) {
        mLowResolution = in.readParcelable(Image.class.getClassLoader());
        mThumbnail = in.readParcelable(Image.class.getClassLoader());
        mStandardResolution = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mLowResolution, flags);
        dest.writeParcelable(mThumbnail, flags);
        dest.writeParcelable(mStandardResolution, flags);
    }
}

