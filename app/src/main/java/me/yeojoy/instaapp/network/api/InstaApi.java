package me.yeojoy.instaapp.network.api;

import me.yeojoy.instaapp.model.api.Photos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yeojoy on 2017. 1. 20..
 */

public interface InstaApi {
    @GET("{userName}/media/")
    Call<Photos> getPhotos(@Path("userName") String userName, @Query("max_id") String maxId);
}
