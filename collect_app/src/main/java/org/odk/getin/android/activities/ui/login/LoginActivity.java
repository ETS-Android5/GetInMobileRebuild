package org.odk.getin.android.activities.ui.login;

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
import org.odk.getin.android.BuildConfig;
import org.odk.getin.android.R;
import org.odk.getin.android.activities.CollectAbstractActivity;
import org.odk.getin.android.activities.MainMenuActivity;
import org.odk.getin.android.retrofit.APIClient;
import org.odk.getin.android.retrofit.APIInterface;
import org.odk.getin.android.retrofitmodels.UserModel;
import org.odk.getin.android.utilities.TextUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

import static org.odk.getin.android.utilities.ApplicationConstants.CHEW_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.SERVER_TOKEN;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_DISTRICT;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_FIRST_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_LAST_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_LOGGED_IN;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.VHT_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.VHT_MIDWIFE_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.VHT_MIDWIFE_PHONE;

public class LoginActivity extends CollectAbstractActivity {

    private LoginViewModel loginViewModel;
    private String userType;
    ProgressBar loadingProgressBar;

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
        if (userType.equals("chew"))
            userTypeTextView.setText("VHT");
        else
            userTypeTextView.setText(TextUtils.toCapitalize(userType));

        loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
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
                    loginButton.setEnabled(false);
                    try {
                        Timber.d("start post request");
                        postRequest(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginButton.setEnabled(false);
            try {
                Timber.d("start post request");
                postRequest(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
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
        String url = BuildConfig.DJANGO_BACKEND_URL + "auth/login/";

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
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this,
                            "Login failed. Wrong username or password", Toast.LENGTH_SHORT).show();
                    loadingProgressBar.setVisibility(View.GONE);
                });
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
                        String district = userObject.getJSONObject("village").getJSONObject("parish").getJSONObject("sub_county")
                                .getJSONObject("county").getJSONObject("district").getString("name");
                        String loggedInUserName = userObject.getString("username");
                        String userId = userObject.getString("id");
                        String role = userObject.getString("role");
                        Timber.d("Role# " + role);

                        if (!userType.equals(role)) {
                            throw new IllegalAccessException("User is not a " + userType);
                        }

                        Prefs.putString(SERVER_TOKEN, authToken);
                        Prefs.putString(USER_FIRST_NAME, firstName);
                        Prefs.putString(USER_LAST_NAME, lastName);
                        Prefs.putString(USER_DISTRICT, district);
                        Prefs.putString(USER_NAME, loggedInUserName);
                        Prefs.putString(USER_ROLE, role);
                        Prefs.putBoolean(USER_LOGGED_IN, true);

                        try {
                            if (role.equals(CHEW_ROLE)) {
                                // get details of midwife attached to the vht
                                JSONObject midwifeObject = userObject.getJSONObject("midwife");
                                Timber.d("midwife phone: " + midwifeObject.getString("phone"));
                                String midwifeId = midwifeObject.getString("id");
                                Prefs.putString(VHT_MIDWIFE_ID, midwifeObject.getString("id"));
                                Prefs.putString(VHT_MIDWIFE_NAME, midwifeObject.getString("last_name")
                                        + " " + midwifeObject.getString("last_name"));
                                Timber.d("midwife phone from object " + midwifeObject.getString("id"));
                                Prefs.putString(VHT_MIDWIFE_PHONE, midwifeObject.getString("phone"));
                            } else {
                                // get details of vhts attached to the midwife
                                //todo make request to fetch vhts
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Prefs.putString(VHT_MIDWIFE_ID, "cfad4c4f-c8eb-48d9-a056-7fe4b4888921");
                        }
                        Prefs.putString(USER_ID, userId);
                        Timber.d(authToken + firstName + lastName + role);

                        //TODO GET ODK CENTRAL APP USER URL
                        Prefs.putString("APP_USER_URL", BuildConfig.APP_USER_URL);

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
                runOnUiThread(() -> {
                    loadingProgressBar.setVisibility(View.GONE);
                });
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
