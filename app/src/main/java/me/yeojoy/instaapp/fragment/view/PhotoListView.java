package me.yeojoy.instaapp.fragment.view;

import android.widget.ImageView;

import me.yeojoy.instaapp.model.service.InstaPhoto;

/**
 * Created by yeojoy on 2017. 1. 29..
 */

public interface PhotoListView {
    /**
     * 사진 목록에서 사진을 선택 함.
     * @param instaPhoto
     */
    void onSelectPhoto(ImageView imageView, InstaPhoto instaPhoto);
}
