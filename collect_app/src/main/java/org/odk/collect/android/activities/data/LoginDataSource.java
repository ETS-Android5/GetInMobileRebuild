package org.odk.collect.android.activities.data;

import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.collect.android.activities.data.model.LoggedInUser;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.AuthModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static org.odk.collect.android.utilities.ApplicationConstants.SERVER_TOKEN;

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
            Call<AuthModel> call = apiInterface.loginUser(username, password);
            Log.d("Server", "getMappedGirlsList: made server request");

            call.enqueue(new Callback<AuthModel>() {
                @Override
                public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                    Timber.d("onResponse() -> " + response.code());
                    Timber.d("onResponse() -> " + response.body().toString());
                    token = response.body().getAuthToken();
                    Prefs.putString(SERVER_TOKEN, token);
//                    isSuccessful = response.isSuccessful();
                }

                @Override
                public void onFailure(Call<AuthModel> call, Throwable t) {
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
