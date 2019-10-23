package org.odk.collect.android.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.odk.collect.android.preferences.GeneralKeys;
import org.odk.collect.android.preferences.GeneralSharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class APIClient {
    public static Retrofit getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
//                    String token = (String) GeneralSharedPreferences.getInstance().get(GeneralKeys.KEY_TOKEN);
                    String token = "PMNFdsQKIDdwLmJrlTWXCYW8EHebpRqOsr!fxaRHiG!ao2ihon1sdwtuAixGamCk";
                    Timber.d(token);
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();
                    return chain.proceed(request);
                }).build();

//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Customer.class, (JsonDeserializer<Customer>) (json, typeOfT, context) -> {
//            JsonObject j = json.getAsJsonObject();
//            Gson g = new Gson();
//            Customer c = g.fromJson(j, Customer.class);
//            if(j.has("customer_id")){
//                c.setId(j.get("customer_id").getAsLong());
//            }
//
//            return c;
//        });

        return new Retrofit.Builder()
                //todo#
//                .baseUrl("https://central.getinmobile.org/v1/")
                .baseUrl("https://getin-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
