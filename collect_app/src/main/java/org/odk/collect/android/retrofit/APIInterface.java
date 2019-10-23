package org.odk.collect.android.retrofit;

import org.odk.collect.android.retrofitmodels.LoginResult;
import org.odk.collect.android.retrofitmodels.MappedGirls;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    //todo# add the production project id
    @GET("api/v1/girls")
    Call<MappedGirls> getMappedGirls();

    @FormUrlEncoded
    @POST("v1/sessions")
    Call<LoginResult> login(@Field("email") String email, @Field("password") String password);
}
