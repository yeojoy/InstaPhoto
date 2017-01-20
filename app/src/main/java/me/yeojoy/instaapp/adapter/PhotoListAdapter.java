package me.yeojoy.instaapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import me.yeojoy.instaapp.R;
import me.yeojoy.instaapp.databinding.ItemPhotoBinding;
import me.yeojoy.instaapp.model.service.InstaPhoto;

/**
 * Created by yeojoy on 2017. 1. 21..
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoItemHolder> {

    private List<InstaPhoto> mInstaPhotos;

    private static final String DEFUALT_ID = "0";

    public PhotoListAdapter(List<InstaPhoto> instaPhotos) {
        mInstaPhotos = instaPhotos;
    }

    @Override
    public PhotoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPhotoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_photo, parent, false);

        return new PhotoItemHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(PhotoItemHolder holder, int position) {
        InstaPhoto instaPhoto = mInstaPhotos.get(position);

        Glide.with(holder.mBinding.photoImageView.getContext())
                .load(instaPhoto.mImageUrl)
                .crossFade()
                .into(holder.mBinding.photoImageView);
    }

    @Override
    public int getItemCount() {
        return mInstaPhotos.size();
    }

    public String getLastItemId() {
        if (getItemCount() > 0) {
            return mInstaPhotos.get(getItemCount() - 1).mId;
        }
        return DEFUALT_ID;
    }

    public static class PhotoItemHolder extends RecyclerView.ViewHolder {

        ItemPhotoBinding mBinding;

        public PhotoItemHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.findBinding(itemView);
        }
    }
}
