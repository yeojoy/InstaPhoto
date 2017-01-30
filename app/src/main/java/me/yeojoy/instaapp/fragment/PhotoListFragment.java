package me.yeojoy.instaapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.yeojoy.instaapp.Constants;
import me.yeojoy.instaapp.PhotoDetailActivity;
import me.yeojoy.instaapp.R;
import me.yeojoy.instaapp.adapter.PhotoListAdapter;
import me.yeojoy.instaapp.databinding.FragmentListBinding;
import me.yeojoy.instaapp.fragment.view.PhotoListView;
import me.yeojoy.instaapp.model.service.InstaPhoto;
import me.yeojoy.instaapp.network.NetworkManager;
import me.yeojoy.instaapp.utils.SoftkeyboardUtils;
import me.yeojoy.instaapp.utils.Validator;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class PhotoListFragment extends Fragment implements PhotoListView {
    private static final String TAG = PhotoListFragment.class.getSimpleName();

    private FragmentListBinding mBinding;

    private ProgressDialog mProgressDialog;
    private LinearLayoutManager mLinearLayoutManager;

    private String mUserName;

    /**
     * Insta Api에서 받아오는 more_available flag
     */
    private boolean mMoreAvailable = false;

    /**
     * Insta 데이터 중 마지막 id
     */
    private String mLastPhotoId;

    private boolean isOnline = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.editTextQuery.addTextChangedListener(mQueryTextWatcher);
        mBinding.buttonSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mBinding.buttonSearch.performClick();
                return true;
            }
            return false;
        });

        mBinding.buttonSearch.setOnClickListener(this::onClickSearchButton);
        mBinding.buttonSearch.setOnLongClickListener(this::onLongClickSearchButton);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.recyclerViewPhotoList.getContext(),
                mLinearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_line));

        mBinding.recyclerViewPhotoList.addItemDecoration(dividerItemDecoration);
        mBinding.recyclerViewPhotoList.setLayoutManager(mLinearLayoutManager);
        mBinding.recyclerViewPhotoList.addOnScrollListener(mOnScrollListener);
    }

    private boolean onLongClickSearchButton(View view) {
        mBinding.editTextQuery.setText("yeojoy");
        mBinding.buttonSearch.performClick();
        return false;
    }

    private void onClickSearchButton(View view) {
        if (TextUtils.isEmpty(mBinding.textInputLayoutQuery.getError())) {
            String userName = mBinding.editTextQuery.getText().toString();
            if (TextUtils.isEmpty(userName)) return;

            requestPhotos(userName, "0");
            // 중간에 username이 바뀔 수 있으므로 검색버튼을 누를 때 마지막 username을
            // 전역으로 저장해 둔다.
            mUserName = userName;
        } else {
            Toast.makeText(getActivity(), R.string.warning_username_check_again, Toast.LENGTH_SHORT)
                    .show();
        }

        SoftkeyboardUtils.hideSoftKeyboard(mBinding.editTextQuery);
    }

    private void checkQuery(String query) {
        Log.i(TAG, "query : " + query);
        if (!Validator.isQueryValidated(query)) {
            mBinding.textInputLayoutQuery.setError(getString(R.string.warning_username_not_valid));
        } else {
            mBinding.textInputLayoutQuery.setError(null);
        }
    }

    private void requestPhotos(String userName, String maxId) {
        Log.i(TAG, "requestPhotos()");
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(maxId)) {
            isOnline = false;
            return;
        }

        showProgressDialog();
        try {
            NetworkManager.getInstance().requestPhotos(getActivity(), userName, maxId, this::onGetPhotos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onGetPhotos(List<InstaPhoto> instaPhotos, boolean moreAvailable, String lastId) {
        hideProgressDialog();
        isOnline = false;

        if (!isResumed()) {
            return;
        }

        mMoreAvailable = moreAvailable;
        mLastPhotoId = lastId;

        if (instaPhotos != null && instaPhotos.size() > 0) {
            PhotoListAdapter photoListAdapter = (PhotoListAdapter) mBinding.recyclerViewPhotoList.getAdapter();

            if (photoListAdapter == null) {
                // 사진이 없을 때 Adapter를 생성해서 넣어 준다.
                instaPhotos.get(0).mImageUrl = "http://blogimg.ohmynews.com/attach/4355/1038322469.jpg";
                instaPhotos.get(1).mImageUrl = "http://blogimg.ohmynews.com/attach/4355/1020645369.jpg";

                photoListAdapter = new PhotoListAdapter(this, instaPhotos);
                mBinding.recyclerViewPhotoList.setAdapter(photoListAdapter);
            } else {
                // 이미 어댑터가 있고 이 후 data는 add 해준다.
                photoListAdapter.addPhotos(instaPhotos);
            }
        } else {
            mBinding.recyclerViewPhotoList.setAdapter(null);
            Toast.makeText(getActivity(), R.string.warning_username_no_photos, Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private TextWatcher mQueryTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() == 0) {
                mBinding.textInputLayoutQuery.setError(null);
                return;
            }

            checkQuery(s.toString());
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (isAllowedToLoadMore()) {
                isOnline = true;
                String lastItemId = mLastPhotoId;
                Log.d(TAG, "lastItemId >>>>> " + lastItemId);
                requestPhotos(mUserName, lastItemId);
            }
        }
    };

    // TODO: 2017. 1. 30. BaseActivty에서 보여주도록 수정
    private void showProgressDialog() {
        Log.i(TAG, "showProgressDialog()");
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.show();
    }

    // TODO: 2017. 1. 30. BaseActivty에서 보여주도록 수정
    private void hideProgressDialog() {
        Log.i(TAG, "hideProgressDialog()");
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private boolean isAllowedToLoadMore() {
        return !isOnline &&
                mMoreAvailable &&
                mBinding.recyclerViewPhotoList.getAdapter() != null &&
                (mBinding.recyclerViewPhotoList.getAdapter().getItemCount() - 1) ==
                        mLinearLayoutManager.findLastVisibleItemPosition();
    }

    @Override
    public void onSelectPhoto(ImageView imageView, InstaPhoto instaPhoto) {
        Log.i(TAG, "onSelectPhoto(), " + instaPhoto.toString());
        List<InstaPhoto> photoList =
                ((PhotoListAdapter) mBinding.recyclerViewPhotoList.getAdapter()).getInstaPhotos();
        Log.d(TAG, "photoList's size : " + photoList.size());
        int photoIndex = photoList.indexOf(instaPhoto);
        Log.d(TAG, "photoIndex : " + photoIndex);

        Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.BundleKey.KEY_INSTA_PHOTOS, (ArrayList<? extends Parcelable>) photoList);
        bundle.putInt(Constants.BundleKey.KEY_INSTA_PHOTO_INDEX, photoIndex);
        intent.putExtras(bundle);
        startActivity(intent);

        // Shared Element Transition은 ViewPager로 이동해야해서 적절하지 않아서 적용 안 함.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptionsCompat options = ActivityOptionsCompat.
//                    makeSceneTransitionAnimation(getActivity(), imageView, "insta_image");
//            startActivity(intent, options.toBundle());
//        } else {
//            startActivity(intent);
//        }
    }
}
