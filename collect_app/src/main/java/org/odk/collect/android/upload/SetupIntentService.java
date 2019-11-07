package org.odk.collect.android.upload;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.odk.collect.android.provider.mappedgirltable.MappedgirltableColumns;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableContentValues;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.mappedgirls.MappedGirl;
import org.odk.collect.android.retrofitmodels.mappedgirls.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 *Downloads list of mapped girls
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

        //todo activate on launch
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

    private void deleteData() {
        long deleted;
        deleted = getContentResolver().delete(MappedgirltableColumns.CONTENT_URI, null, null);
        Timber.d("deleted data count %s", deleted);
    }

    private void loadMappedGirls() {
        Timber.d("get mapped girls list started");
        Call<MappedGirl> call = apiInterface.getMappedGirls();
        Log.d("Server", "loadMappedGirls: made server request");

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
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved mapped girl %s", uri);
        }
    }
}
