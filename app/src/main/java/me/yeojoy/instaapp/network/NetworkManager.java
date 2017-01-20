package me.yeojoy.instaapp.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import me.yeojoy.instaapp.R;
import me.yeojoy.instaapp.model.ModelConvertor;
import me.yeojoy.instaapp.model.api.Photos;
import me.yeojoy.instaapp.model.service.InstaPhoto;
import me.yeojoy.instaapp.network.api.InstaApi;
import me.yeojoy.instaapp.utils.Validator;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public class NetworkManager implements NetworkConstants {
    private static final String TAG = NetworkManager.class.getSimpleName();

    private static NetworkManager mInstance;

    private Retrofit mRetrofit;

    public interface OnGetDataListener {
        void onGetData(List<InstaPhoto> photos);
    }

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
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public void requestPhotos(Context context, String userName, String maxId, OnGetDataListener listener) throws IOException {
        Log.i(TAG, "userName : " + userName + ", maxId : " + maxId);
        if (!Validator.isValidUserName(userName)) {
            Toast.makeText(context, R.string.toast_warning_not_validate_username, Toast.LENGTH_SHORT).show();
            return;
        }

        InstaApi instaApi = mRetrofit.create(InstaApi.class);
        // TODO: 2017. 1. 20. userName에 대한 validation 체크

        instaApi.getPhotos(userName, maxId).enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {
                Photos photos = response.body();
                if (listener != null) {
                    listener.onGetData(ModelConvertor.convertPhotoToInstaPhoto(photos.mItems));
                }
            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {

            }
        });
    }
}
