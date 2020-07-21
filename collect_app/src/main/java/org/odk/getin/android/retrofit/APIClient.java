package org.odk.getin.android.retrofit;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.getin.android.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static org.odk.getin.android.utilities.ApplicationConstants.SERVER_TOKEN;

public class APIClient {
    public static Retrofit getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    String token = Prefs.getString(SERVER_TOKEN, "");
                    Timber.d("token: " + token);
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Token " + token).build();
                    return chain.proceed(request);
                }).build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.DJANGO_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
