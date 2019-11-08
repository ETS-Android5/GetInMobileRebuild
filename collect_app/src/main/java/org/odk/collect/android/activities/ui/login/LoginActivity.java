package org.odk.collect.android.activities.ui.login;

import android.app.Activity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;
import org.odk.collect.android.R;
import org.odk.collect.android.activities.CollectAbstractActivity;
import org.odk.collect.android.activities.MainMenuActivity;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.UserModel;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

import static org.odk.collect.android.utilities.ApplicationConstants.APP_USER_URL;
import static org.odk.collect.android.utilities.ApplicationConstants.CHEW_ROLE;
import static org.odk.collect.android.utilities.ApplicationConstants.DJANGO_BACKEND_URL;
import static org.odk.collect.android.utilities.ApplicationConstants.MIDWIFE_ROLE;
import static org.odk.collect.android.utilities.ApplicationConstants.SERVER_TOKEN;
import static org.odk.collect.android.utilities.ApplicationConstants.USER_FIRST_NAME;
import static org.odk.collect.android.utilities.ApplicationConstants.USER_ID;
import static org.odk.collect.android.utilities.ApplicationConstants.USER_LAST_NAME;
import static org.odk.collect.android.utilities.ApplicationConstants.USER_NAME;
import static org.odk.collect.android.utilities.ApplicationConstants.USER_ROLE;

public class LoginActivity extends CollectAbstractActivity {

    private LoginViewModel loginViewModel;
    private String userType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();

        userType = getIntent().getStringExtra("user_type");

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final TextView userTypeTextView = findViewById(R.id.user_type);
        userTypeTextView.setText(userType);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
                loginButton.setEnabled(false);
                try {
                    Timber.d("start post request");
                    postRequest(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        Intent i = new Intent(getApplicationContext(),
                MainMenuActivity.class);
        startActivity(i);
        //Complete and destroy login activity once successful
        finish();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void postRequest(String username, String password) throws IOException {
        Timber.d("postrequest started");

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = DJANGO_BACKEND_URL + "auth/login/";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("username", username);
            postdata.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Timber.e("failure Response login" + mMessage);
                runOnUiThread(() -> Toast.makeText(LoginActivity.this,
                        "Login failed. Wrong username or password", Toast.LENGTH_SHORT).show());
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseBody = response.body().string();
                Timber.d("onResponse: " + responseBody);
                Timber.d("onResponse: login" + response.code());
                try {
                    if (response.code() == 200) {
                        JSONObject responseJsonObject = new JSONObject(responseBody);
                        String authToken = responseJsonObject.getString("auth_token");

                        JSONObject userObject = responseJsonObject.getJSONObject("user");
                        Timber.d(userObject.toString());
                        String firstName = userObject.getString("first_name");
                        Timber.d(firstName);
                        String lastName = userObject.getString("last_name");
                        String loggedInUserName = userObject.getString("username");
                        String userId = userObject.getString("id");
                        int role = userObject.getInt("role");

                        String userLoggedInType = role == 3 ? CHEW_ROLE : MIDWIFE_ROLE;

                        if (!userType.equals(userLoggedInType)) {
                            throw new IllegalAccessException("User is not a " + userType);
                        }

                        Prefs.putString(SERVER_TOKEN, authToken);
                        Prefs.putString(USER_FIRST_NAME, firstName);
                        Prefs.putString(USER_LAST_NAME, lastName);
                        Prefs.putString(USER_NAME, loggedInUserName);
                        Prefs.putString(USER_ROLE, userLoggedInType);
                        Prefs.putString(USER_ID, userId);
                        Timber.d(authToken + firstName + lastName + role);

                        //TODO GET ODK CENTRAL APP USER URL
                        Prefs.putString("APP_USER_URL", APP_USER_URL);

                        Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this,
                                "Login failed. Wrong username or password", Toast.LENGTH_SHORT).show());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Timber.e("ERROR " + e.getMessage());
                    Timber.e(e);
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this,
                            "Login failed. Wrong username or password. Try again", Toast.LENGTH_SHORT).show());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this,
                            "Login failed. User is not a " + userType, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getLoggedInUserDetails() {
        Timber.d("get mapped girls list started");
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        retrofit2.Call<UserModel> call = apiInterface.getLoggedInUserDetails();
        Log.d("Server", "getMappedGirlsList: made server request");

        call.enqueue(new retrofit2.Callback<UserModel>() {

            @Override
            public void onResponse(retrofit2.Call<UserModel> call, retrofit2.Response<UserModel> response) {
                Timber.d("onResponse() -> " + response.code());
                Timber.d("onResponse() -> " + response.body());
                Prefs.putString("USER_NAME", response.body().getUsername());
                Prefs.putString(USER_ID, response.body().getId());
            }

            @Override
            public void onFailure(retrofit2.Call<UserModel> call, Throwable t) {
                Timber.e("Failed to get user details ");
                Timber.e(t);
            }
        });
    }

}
