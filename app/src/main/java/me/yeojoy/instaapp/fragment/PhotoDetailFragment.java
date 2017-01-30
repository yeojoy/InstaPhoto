package me.yeojoy.instaapp.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.yeojoy.instaapp.Constants;
import me.yeojoy.instaapp.R;
import me.yeojoy.instaapp.adapter.PhotoPagerAdapter;
import me.yeojoy.instaapp.databinding.FragmentDetailBinding;
import me.yeojoy.instaapp.fragment.view.PhotoDetailView;
import me.yeojoy.instaapp.model.service.InstaPhoto;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class PhotoDetailFragment extends Fragment implements PhotoDetailView {
    private static final String TAG = PhotoDetailFragment.class.getSimpleName();

    private FragmentDetailBinding mBinding;

    public static Fragment getInstance(Bundle extras) {
        Log.i(TAG, "getInstance()");
        Fragment fragment = new PhotoDetailFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<InstaPhoto> photoList = getArguments().getParcelableArrayList(Constants.BundleKey.KEY_INSTA_PHOTOS);
        int photoIndex = getArguments().getInt(Constants.BundleKey.KEY_INSTA_PHOTO_INDEX);

        Log.d(TAG, "photoIndex : " + photoIndex);

        PhotoPagerAdapter pagerAdapter = new PhotoPagerAdapter(this, photoList);
        mBinding.viewPagerPhotos.setAdapter(pagerAdapter);
        // photo index와 viewpager index가 안 맞음 +1 해야함.
//        mBinding.viewPagerPhotos.setCurrentItem(photoIndex + 1);
        mBinding.viewPagerPhotos.setCurrentItem(photoIndex);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
