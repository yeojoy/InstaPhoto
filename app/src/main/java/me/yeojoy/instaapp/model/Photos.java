package me.yeojoy.instaapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class Photos implements Parcelable {
    @SerializedName("status")
    public String mStatus;
    @SerializedName("items")
    public Photo[] mItems;
    @SerializedName("more_available")
    public boolean mMoreAvailable;

    protected Photos(Parcel in) {
        mStatus = in.readString();
        mItems = in.createTypedArray(Photo.CREATOR);
        mMoreAvailable = in.readByte() != 0;
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel in) {
            return new Photos(in);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStatus);
        dest.writeTypedArray(mItems, flags);
        dest.writeByte((byte) (mMoreAvailable ? 1 : 0));
    }
}
