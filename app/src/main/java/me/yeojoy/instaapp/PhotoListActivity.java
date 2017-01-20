package me.yeojoy.instaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.yeojoy.instaapp.fragment.PhotoListFragment;

public class PhotoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.relative_layout_container, new PhotoListFragment())
                .commit();


    }
}
