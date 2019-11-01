package org.odk.collect.android.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static org.odk.collect.android.utilities.ApplicationConstants.SERVER_TOKEN;

public class APIClient {
    public static Retrofit getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    String token = Prefs.getString(SERVER_TOKEN, "");
                    Timber.d("token: " + token);
//                    token = "9da70b0c9dc95cfb5277b25d45c90d4de9fbfba4";
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Token " + token).build();
                    return chain.proceed(request);
                }).build();

        return new Retrofit.Builder()
                //todo#
//                .baseUrl("https://central.getinmobile.org/v1/")
                .baseUrl("https://getin-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
