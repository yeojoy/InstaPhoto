package me.yeojoy.instaapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.yeojoy.instaapp.R;
import me.yeojoy.instaapp.databinding.ItemPhotoListBinding;
import me.yeojoy.instaapp.fragment.view.PhotoListView;
import me.yeojoy.instaapp.model.service.InstaPhoto;

/**
 * Created by yeojoy on 2017. 1. 21..
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoItemHolder> {
    private static final String TAG = PhotoListAdapter.class.getSimpleName();

    private PhotoListView mPhotoListView;
    private List<InstaPhoto> mInstaPhotos;

    private static final String DEFUALT_ID = "0";

    public PhotoListAdapter(PhotoListView photoListView, List<InstaPhoto> instaPhotos) {
        mPhotoListView = photoListView;

        if (instaPhotos != null) {
            mInstaPhotos = instaPhotos;
        } else {
            mInstaPhotos = new ArrayList<>();
        }
    }

    @Override
    public PhotoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPhotoListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_photo_list, parent, false);
        return new PhotoItemHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(PhotoItemHolder holder, int position) {
        InstaPhoto instaPhoto = mInstaPhotos.get(position);

        Glide.with(holder.mBinding.imageView.getContext())
                .load(instaPhoto.mImageUrl)
                // .diskCacheStrategy(DiskCacheStrategy.RESULT) /* Disk cache도 사용 */
                .placeholder(R.drawable.no_image)
                .crossFade()
                .into(holder.mBinding.imageView);

        holder.mBinding.getRoot().setOnClickListener(v ->
                mPhotoListView.onSelectPhoto(holder.mBinding.imageView, instaPhoto));
    }

    @Override
    public int getItemCount() {
        return mInstaPhotos.size();
    }

    @Deprecated
    public String getLastItemId() {
        if (getItemCount() > 0) {
            return mInstaPhotos.get(getItemCount() - 1).mId;
        }
        return DEFUALT_ID;
    }

    public List<InstaPhoto> getInstaPhotos() {
        return mInstaPhotos;
    }

    public void addPhotos(List<InstaPhoto> photos) {
        Log.i(TAG, "addPhotos()");
        if (photos == null) return;

        mInstaPhotos.addAll(photos);

        Log.d(TAG, "=================================================================================");
        for (InstaPhoto photo : mInstaPhotos) {
            Log.d(TAG, photo.toString());
        }
        Log.d(TAG, "=================================================================================");
        notifyDataSetChanged();
    }

    public static class PhotoItemHolder extends RecyclerView.ViewHolder {

        ItemPhotoListBinding mBinding;

        public PhotoItemHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.findBinding(itemView);
        }
    }
}
