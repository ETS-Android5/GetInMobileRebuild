package org.odk.collect.android.upload;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.odk.collect.android.provider.mappedgirltable.MappedgirltableColumns;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableContentValues;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.mappedgirls.MappedGirl;
import org.odk.collect.android.retrofitmodels.mappedgirls.Result;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Downloads list of mapped girls
 */
public class SetupIntentService extends IntentService {
    private static final String TAG = SetupIntentService.class.getSimpleName();
    private APIInterface apiInterface;

    public SetupIntentService() {
        super(SetupIntentService.class.getSimpleName());
    }

    public SetupIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("onHandleIntent: started service");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        boolean isConnectedToInternet = isConnectedToInternet(this);
        Timber.d("is connected %s", isConnectedToInternet);

        if (isConnectedToInternet) {
            try {
                deleteData();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                loadMappedGirls();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteData() {
        long deleted;
        deleted = getContentResolver().delete(MappedgirltableColumns.CONTENT_URI, null, null);
        Timber.d("deleted data count %s", deleted);
    }

    private void loadMappedGirls() {
        Timber.d("get mapped girls list started");
        Call<MappedGirl> call = apiInterface.getMappedGirls();

        call.enqueue(new Callback<MappedGirl>() {
            @Override
            public void onResponse(Call<MappedGirl> call, Response<MappedGirl> response) {
                Timber.d("onResponse() -> %s", response.code());
                try {
                    if (response.code() == 200) {
                        saveMappedGirls(response.body());
                    } else {
                        Timber.e("Failed to get mapped girls");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MappedGirl> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveMappedGirls(MappedGirl mappedGirl) {
        Timber.d("INSERT: mapped girl starting");
        if (mappedGirl == null)
            throw new NullPointerException("Locations not found");
        List<Result> mappedGirls = mappedGirl.getResults();


        for (Result girl : mappedGirls) {
            MappedgirltableContentValues values = new MappedgirltableContentValues();
            values.putFirstname(girl.getFirstName());
            values.putLastname(girl.getLastName());
            values.putPhonenumber(girl.getPhoneNumber());
            values.putNextofkinfirstname(girl.getNextOfKinFirstName());
            values.putNextofkinlastname(girl.getNextOfKinLastName());
            values.putNextofkinphonenumber(girl.getNextOfKinPhoneNumber());
            values.putEducationlevel(girl.getEducationLevel());
            values.putMaritalstatus(girl.getMaritalStatus());
            values.putAge(girl.getAge());
            values.putUser(girl.getUser());
            values.putCreatedAt(girl.getCreatedAt());
            values.putCompletedAllVisits(girl.isCompletedAllVisits());
            values.putPendingVisits(girl.getPendingVisits());
            values.putMissedVisits(girl.getMissedVisits());
            values.putServerid(girl.getId());
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved mapped girl %s", uri);
        }
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        try {
                            //todo replace with async task
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com").openConnection());
                            urlc.setRequestProperty("User-Agent", "Test");
                            urlc.setRequestProperty("Connection", "close");
                            urlc.setConnectTimeout(2000);
                            urlc.setReadTimeout(2000);
                            urlc.connect();
                            boolean isConnected = (urlc.getResponseCode() == 200);
                            urlc.disconnect();
                            // making a network request on 2G network will not wield proper results
                            // instead depend on the CONNECTED state of the network
                            if (!isConnected && getNetworkClass(context) == 2) {
                                isConnected = info[i].getState() == NetworkInfo.State.CONNECTED;
                            }
                            return isConnected;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
        }
        return false;
    }

    public static int getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 2;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return 3;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return 4;
            default:
                return 3;
        }
    }
}
