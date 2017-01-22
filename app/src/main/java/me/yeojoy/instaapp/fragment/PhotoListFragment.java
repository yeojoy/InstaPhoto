package me.yeojoy.instaapp.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import me.yeojoy.instaapp.R;
import me.yeojoy.instaapp.adapter.PhotoListAdapter;
import me.yeojoy.instaapp.databinding.FragmentListBinding;
import me.yeojoy.instaapp.model.service.InstaPhoto;
import me.yeojoy.instaapp.network.NetworkManager;
import me.yeojoy.instaapp.utils.SoftkeyboardUtils;
import me.yeojoy.instaapp.utils.Validator;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class PhotoListFragment extends Fragment {
    private static final String TAG = PhotoListFragment.class.getSimpleName();

    private FragmentListBinding mBinding;

    private LinearLayoutManager mLinearLayoutManager;

    private String mUserName;

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
        mBinding.editTextQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mBinding.buttonSearch.performClick();
                return true;
            }
            return false;
        });

        mBinding.buttonSearch.setOnClickListener(this::onClickSearchButton);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_line));

        mBinding.recyclerViewPhotoList.setLayoutManager(mLinearLayoutManager);
        mBinding.recyclerViewPhotoList.addOnScrollListener(mOnScrollListener);
        mBinding.recyclerViewPhotoList.addItemDecoration(dividerItemDecoration);
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
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(maxId)) return;

        try {
            NetworkManager.getInstance().requestPhotos(getActivity(), userName, maxId, this::onGetPhotos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onGetPhotos(List<InstaPhoto> instaPhotos) {

        if (!isResumed()) return;

        if (instaPhotos != null && instaPhotos.size() > 0) {
            PhotoListAdapter photoListAdapter = new PhotoListAdapter(instaPhotos);
            mBinding.recyclerViewPhotoList.setAdapter(photoListAdapter);
        } else {
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
            Log.i(TAG, "index : " + mLinearLayoutManager.findLastVisibleItemPosition());
            if (mBinding.recyclerViewPhotoList.getAdapter() != null &&
                    (mBinding.recyclerViewPhotoList.getAdapter().getItemCount() - 1) ==
                            mLinearLayoutManager.findLastVisibleItemPosition()) {
                String lastItemId = ((PhotoListAdapter) mBinding.recyclerViewPhotoList.getAdapter())
                        .getLastItemId();
                requestPhotos(mUserName, lastItemId);
            }
        }
    };
}
