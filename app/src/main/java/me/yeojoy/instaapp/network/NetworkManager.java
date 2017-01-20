package me.yeojoy.instaapp.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import me.yeojoy.instaapp.R;
import me.yeojoy.instaapp.model.Photos;
import me.yeojoy.instaapp.network.api.InstaApi;
import me.yeojoy.instaapp.utils.Validator;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class NetworkManager implements NetworkConstants {
    private static final String TAG = NetworkManager.class.getSimpleName();

    private static NetworkManager mInstance;

    private Retrofit mRetrofit;

    public NetworkManager() {
        initManager();
    }

    public static NetworkManager getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkManager();
        }
        return mInstance;
    }

    private void initManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public Call<Photos> requestPhotos(Context context, String userName, String maxId) {
        Log.i(TAG, "userName : " + userName + ", maxId : " + maxId);
        if (!Validator.isValidUserName(userName)) {
            Toast.makeText(context, R.string.toast_warning_not_validate_username, Toast.LENGTH_SHORT).show();
            return null;
        }

        InstaApi instaApi = mRetrofit.create(InstaApi.class);
        // TODO: 2017. 1. 20. userName에 대한 validation 체크
        return instaApi.getPhotos(userName, maxId);
    }
}
