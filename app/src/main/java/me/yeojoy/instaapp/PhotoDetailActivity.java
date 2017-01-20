package me.yeojoy.instaapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import me.yeojoy.instaapp.databinding.ActivityDetailBinding;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class PhotoDetailActivity extends AppCompatActivity {

    private ActivityDetailBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
    }
}
