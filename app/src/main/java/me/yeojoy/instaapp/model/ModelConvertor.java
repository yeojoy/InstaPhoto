package me.yeojoy.instaapp.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.yeojoy.instaapp.model.api.Photo;
import me.yeojoy.instaapp.model.service.InstaPhoto;

/**
 * Created by yeojoy on 2017. 1. 21..
 */

public class ModelConvertor {
    private static final String TAG = ModelConvertor.class.getSimpleName();
    private static final String TYPE_VIDEO = "video";

    public static List<InstaPhoto> convertPhotoToInstaPhoto(Photo[] photos) {
        if (photos == null) return null;

        List<InstaPhoto> instaPhotos = new ArrayList<>();
        for (Photo photo : photos) {
            // Video인 경우 다음으로 넘긴다.
            if (photo.mType.equals(TYPE_VIDEO)) continue;

            instaPhotos.add(new InstaPhoto(photo.mId,
                    photo.mImages.mStandardResolution.mImageUrl,
                    photo.mType,
                    photo.mImages.mStandardResolution.mImageWidth,
                    photo.mImages.mStandardResolution.mImageHeight));
        }
        Log.d(TAG, "instaPhoto Size : " + instaPhotos.size());
        return instaPhotos;
    }
}
