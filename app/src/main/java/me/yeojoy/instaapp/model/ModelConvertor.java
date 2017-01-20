package me.yeojoy.instaapp.model;

import java.util.ArrayList;
import java.util.List;

import me.yeojoy.instaapp.model.api.Photo;
import me.yeojoy.instaapp.model.service.InstaPhoto;

/**
 * Created by yeojoy on 2017. 1. 21..
 */

public class ModelConvertor {
    public static List<InstaPhoto> convertPhotoToInstaPhoto(Photo[] photos) {
        if (photos == null) return null;

        List<InstaPhoto> instaPhotos = new ArrayList<>();
        for (Photo photo : photos) {
            instaPhotos.add(new InstaPhoto(photo.mId, photo.mImages.mStandardResolution.mImageUrl,
                    photo.mImages.mStandardResolution.mImageWidth,
                    photo.mImages.mStandardResolution.mImageHeight));
        }
        return instaPhotos;
    }
}
