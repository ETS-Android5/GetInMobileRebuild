package org.odk.collect.android.activities.data;

import android.util.Log;

import org.odk.collect.android.activities.data.model.LoggedInUser;
import org.odk.collect.android.activities.ui.vieweditmappedgirls.ViewEditMappedGirlsFragment;
import org.odk.collect.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.LoginResult;
import org.odk.collect.android.retrofitmodels.MappedGirls;
import org.odk.collect.android.retrofitmodels.Value;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static final String TAG = LoginDataSource.class.getSimpleName();
    private String token = "";
    private boolean isSuccessful = true;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            Log.d(TAG, "login: started");
            // TODO: handle loggedInUser authentication
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<LoginResult> call = apiInterface.login(username, password);
            Log.d("Server", "getMappedGirlsList: made server request");

            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    Timber.d("onResponse() -> " + response.code());
                    Timber.d("onResponse() -> " + response.isSuccessful());
//                    token = response.body().getToken();
//                    isSuccessful = response.isSuccessful();
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    Timber.e("onFailure() -> " + t.getMessage());
                }
            });

            if (true) {
                LoggedInUser loggedInUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "Jane Doe", token);
                return new Result.Success<>(loggedInUser);
            } else {
                return new Result.Error(new Exception("Error logging in"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
