package me.yeojoy.instaapp.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import me.yeojoy.instaapp.R;
import me.yeojoy.instaapp.adapter.PhotoListAdapter;
import me.yeojoy.instaapp.databinding.FragmentListBinding;
import me.yeojoy.instaapp.model.service.InstaPhoto;
import me.yeojoy.instaapp.network.NetworkManager;
import me.yeojoy.instaapp.utils.Validator;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class PhotoListFragment extends Fragment {
    private static final String TAG = PhotoListFragment.class.getSimpleName();

    private FragmentListBinding mBinding;

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mLinearLayoutManager.findLastVisibleItemPosition() == mBinding.recyclerViewPhotoList.getAdapter().getItemCount()) {
                mBinding.buttonSearch.performClick();
            }
        }
    };

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
        mBinding.buttonSearch.setOnClickListener(this::onClickSearchButton);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        mBinding.recyclerViewPhotoList.setLayoutManager(mLinearLayoutManager);
        mBinding.recyclerViewPhotoList.addOnScrollListener(mOnScrollListener);
    }

    private void onClickSearchButton(View view) {
        if (TextUtils.isEmpty(mBinding.textInputLayoutQuery.getError())) {
            String userName = mBinding.editTextQuery.getText().toString();
            if (TextUtils.isEmpty(userName)) return;

            PhotoListAdapter adapter = (PhotoListAdapter) mBinding.recyclerViewPhotoList.getAdapter();

            requestPhotos(userName, adapter == null ? "0" : adapter.getLastItemId());
        } else {

        }
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
        try {
            NetworkManager.getInstance().requestPhotos(getActivity(), userName, maxId, this::onGetPhotos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onGetPhotos(List<InstaPhoto> instaPhotos) {
        if (instaPhotos != null) {
            PhotoListAdapter photoListAdapter = new PhotoListAdapter(instaPhotos);
            mBinding.recyclerViewPhotoList.setAdapter(photoListAdapter);
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
}
