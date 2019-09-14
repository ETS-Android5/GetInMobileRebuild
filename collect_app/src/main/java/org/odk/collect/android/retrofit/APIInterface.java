package org.odk.collect.android.retrofit;

import org.odk.collect.android.retrofitmodels.MappedGirls;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {
    //todo# add the production project id
    @GET("projects/2/forms/{form_name}.svc/Submissions?")
    Call<List<MappedGirls>> getMappedGirls(@Path("form_name") String type);
}
