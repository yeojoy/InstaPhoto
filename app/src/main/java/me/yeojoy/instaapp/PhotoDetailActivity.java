package me.yeojoy.instaapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.yeojoy.instaapp.fragment.PhotoDetailFragment;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class PhotoDetailActivity extends AppCompatActivity {
    private static final String TAG = PhotoDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_detail);

        if (getIntent() == null || getIntent().getExtras() == null) {
            Log.e(TAG, "Either intent or bundle is null.");
            // Shared Element Transition을 사용하지 않아 finish()로 변경
//            supportFinishAfterTransition();
            finish();
            return;
        }

        Bundle extras = getIntent().getExtras();
        Log.d(TAG, "extras : " + extras);

        Fragment photoDetailFragment = PhotoDetailFragment.getInstance(extras);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.relative_layout_container, photoDetailFragment)
                .commit();
    }

}
