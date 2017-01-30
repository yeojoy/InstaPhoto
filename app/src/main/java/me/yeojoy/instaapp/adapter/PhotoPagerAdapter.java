package me.yeojoy.instaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.demono.adapter.InfinitePagerAdapter;

import java.util.List;
import java.util.Locale;

import me.yeojoy.instaapp.R;
import me.yeojoy.instaapp.fragment.view.PhotoDetailView;
import me.yeojoy.instaapp.model.service.InstaPhoto;

/**
 * Created by yeojoy on 2017. 1. 29..
 */

public class PhotoPagerAdapter extends InfinitePagerAdapter {

    private static final String TAG = PhotoPagerAdapter.class.getSimpleName();
    private static final String INDICATOR_FORMAT = "%d/%d";

    private PhotoDetailView mPhotoDetailView;
    private List<InstaPhoto> mPhotos;

    public PhotoPagerAdapter(PhotoDetailView photoDetailView, List<InstaPhoto> photos) {
        mPhotoDetailView = photoDetailView;
        mPhotos = photos;
    }

    @Override
    public int getItemCount() {
        return mPhotos == null ? 0 : mPhotos.size();
    }

    @Override
    public View getItemView(int i, View view, ViewGroup viewGroup) {
        InstaPhoto instaPhoto = mPhotos.get(i);
        View page = LayoutInflater.from(mPhotoDetailView.getContext()).inflate(R.layout.item_photo_pager, null);
        // TODO 2017.01.30 pinch zoom을 지원하는 ImageView로 변경하면 사진을 보기 더 좋음.
        ImageView imageViewPhoto = (ImageView) page.findViewById(R.id.image_view);
        // Indicator는 보통 ViewPager와 별개로 넣지만 개발 편의상 안에 넣습니다.
        TextView textViewIndicator = (TextView) page.findViewById(R.id.text_view_indicator);

        Glide.with(mPhotoDetailView.getContext())
                .load(instaPhoto.mImageUrl)
                .placeholder(R.drawable.no_image)
                .crossFade()
                .into(imageViewPhoto);

        textViewIndicator.setText(String.format(Locale.getDefault(), INDICATOR_FORMAT, (i + 1), mPhotos.size()));
        return page;
    }
}
