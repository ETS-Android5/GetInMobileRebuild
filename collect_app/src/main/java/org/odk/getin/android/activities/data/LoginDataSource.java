package org.odk.getin.android.activities.data;

import android.os.StrictMode;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.getin.android.activities.data.model.LoggedInUser;
import org.odk.getin.android.retrofit.APIClient;
import org.odk.getin.android.retrofit.APIInterface;
import org.odk.getin.android.retrofitmodels.AuthModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

import static org.odk.getin.android.utilities.ApplicationConstants.SERVER_TOKEN;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_LOGGED_IN;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static final String TAG = LoginDataSource.class.getSimpleName();
    private boolean isSuccessful = true;

    public Result<LoggedInUser> login(String username, String password) {

        // TODO: Replace this with asyncTask
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Log.d(TAG, "login: started");
            Prefs.putBoolean(USER_LOGGED_IN, false);

            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<AuthModel> call = apiInterface.loginUser(username, password);
            Timber.d("getMappedGirlsList: made server request");


            Response<AuthModel> response = call.execute();
            Log.d(TAG, "login: finished request " + response.isSuccessful());

            if (response.isSuccessful()) {
                AuthModel authModel = response.body();
                String authToken = authModel.getAuthToken();
                Prefs.putString(SERVER_TOKEN, authToken);
                Timber.d(authModel.getAuthToken());

                LoggedInUser loggedInUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "Jane Doe", authToken);
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
