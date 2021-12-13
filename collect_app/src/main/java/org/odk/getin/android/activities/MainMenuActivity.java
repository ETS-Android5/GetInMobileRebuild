/*
 * Copyright (C) 2009 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.odk.getin.android.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;
import org.odk.getin.android.BuildConfig;
import org.odk.getin.android.NotificationAlarmTrigger;
import org.odk.getin.android.NotifyWorker;
import org.odk.getin.android.R;
import org.odk.getin.android.application.Collect;
import org.odk.getin.android.dao.InstancesDao;
import org.odk.getin.android.preferences.AdminPreferencesActivity;
import org.odk.getin.android.preferences.AdminSharedPreferences;
import org.odk.getin.android.preferences.AutoSendPreferenceMigrator;
import org.odk.getin.android.preferences.GeneralSharedPreferences;
import org.odk.getin.android.preferences.GeneralKeys;
import org.odk.getin.android.preferences.Transport;
import org.odk.getin.android.provider.FormsProviderAPI;
import org.odk.getin.android.provider.InstanceProviderAPI.InstanceColumns;
import org.odk.getin.android.retrofit.APIClient;
import org.odk.getin.android.retrofit.APIInterface;
import org.odk.getin.android.retrofitmodels.AuthModel;
import org.odk.getin.android.tasks.ServerPollingJob;
import org.odk.getin.android.upload.SetupIntentService;
import org.odk.getin.android.utilities.ApplicationConstants;
import org.odk.getin.android.utilities.AudioPlay;
import org.odk.getin.android.utilities.GeneralUtils;
import org.odk.getin.android.utilities.SharedPreferencesUtils;
import org.odk.getin.android.utilities.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

import static org.odk.getin.android.preferences.GeneralKeys.KEY_SUBMISSION_TRANSPORT_TYPE;
import static org.odk.getin.android.utilities.ApplicationConstants.CHEW_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.MIDWIFE_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_DISTRICT;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_LOGGED_IN;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.getChewUserMappingForm;
import static org.odk.getin.android.utilities.ApplicationConstants.getMidwifeUserMappingForm;

/**
 * Responsible for displaying buttons to launch the major activities. Launches
 * some activities based on returns of others.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 * @author Phillip Kigenyi (codephillip@gmail.com)
 */
public class MainMenuActivity extends CollectAbstractActivity {
    private static final boolean EXIT = true;
    private static final String TASK_ID = "NotificationWorker";
    // buttons
    private Button callMidwifeOrChewButton;
    private Button callAmbulanceButton;
    private AlertDialog alertDialog;
    private SharedPreferences adminPreferences;
    private Cursor finalizedCursor;
    private Cursor savedCursor;
    private Cursor viewSentCursor;
    private final IncomingHandler handler = new IncomingHandler(this);
    private final MyContentObserver contentObserver = new MyContentObserver();
    private static final int REQUEST_PHONE_CALL = 34;
    private TextView networkStatusTextView;
    private CountDownTimer countDownTimer;
    private int viewSentCount;

    public static void startActivityAndCloseAllOthers(Activity activity) {
        activity.startActivity(new Intent(activity, MainMenuActivity.class));
        activity.overridePendingTransition(0, 0);
        activity.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        initToolbar();

        Timber.d("Login status %s", Prefs.getBoolean(USER_LOGGED_IN, false));

        // must be at the beginning of any activity that can be called from an
        // external intent
        Timber.i("Starting up, creating directories");
        try {
            Collect.createODKDirs();
        } catch (RuntimeException e) {
            createErrorDialog(e.getMessage(), EXIT);
            return;
        }

        networkStatusTextView = findViewById(R.id.network_status);
        networkStatusCheckTimer();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Timber.w("getInstanceId failed" + task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    sendRegistrationToServer(token);

                    // Log and toast
                    String msg = getString(R.string.msg_token_fmt, token);
                    Timber.d(msg);
                });

        disableSmsIfNeeded();

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        boolean firstRun = sharedPref.getBoolean(GeneralKeys.KEY_FIRST_RUN, true);

        if (firstRun) {
            // download district mapping forms
            ServerPollingJob.startJobImmediately();
            // disable all procedures that are dependant on first launch on app like downloading forms
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(GeneralKeys.KEY_FIRST_RUN, false);
            editor.apply();
        }

        // map girl button. expects a result.
        Button mapGirlButton = findViewById(R.id.chew_button);
        mapGirlButton.setText(getString(R.string.map_girl_button));
        mapGirlButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    // get form depending on loggin user district


                    if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE)) {
                        startFormActivity(getChewUserMappingForm(Prefs.getString(USER_DISTRICT, "BUNDIBUGYO").toUpperCase(Locale.ROOT)));
                    } else {
                        startFormActivity(getMidwifeUserMappingForm(Prefs.getString(USER_DISTRICT, "BUNDIBUGYO").toUpperCase(Locale.ROOT)));
                    }
                }
            }
        });


        // disable mapping feature for midwives
        if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(MIDWIFE_ROLE))
            mapGirlButton.setVisibility(View.GONE);

        Button viewEditMappedGirlsButton = findViewById(R.id.view_edit_mapped_girls);
        viewEditMappedGirlsButton.setText(getString(R.string.view_edit_girls));
        viewEditMappedGirlsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    Intent i = new Intent(getApplicationContext(),
                            ViewEditMappedGirlsActivity.class);
                    startActivity(i);
                }
            }
        });

        Button upComingAppointments = findViewById(R.id.upcoming_appointments_button);
        upComingAppointments.setText(getString(R.string.upcoming_appointments));
        upComingAppointments.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    Intent i = new Intent(getApplicationContext(), UpcomingAppointmentsActivity.class);
                    startActivity(i);
                }
            }
        });

        callMidwifeOrChewButton = findViewById(R.id.call_midwife_or_chew);
        if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE)) {
            callMidwifeOrChewButton.setText(R.string.call_midwive);
        } else {
            callMidwifeOrChewButton.setText(R.string.call_vhts);
        }


        callMidwifeOrChewButton.setOnClickListener(v -> {
            if (Collect.allowClick(getClass().getName())) {
                Intent i = new Intent(getApplicationContext(), CallUserViewActivity.class);
                startActivity(i);
            }
        });

        callAmbulanceButton = findViewById(R.id.call_ambulance);
        callAmbulanceButton.setText(getString(R.string.ambulance));
        callAmbulanceButton.setOnClickListener(v -> startActivity(new Intent(v.getContext(), AmbulanceViewActivity.class)));

        Button healthFacilityButton = findViewById(R.id.health_facility_button);
        healthFacilityButton.setText(getString(R.string.health_facilities));
        healthFacilityButton.setOnClickListener(v -> startActivity(new Intent(v.getContext(), HealthFacilityActivity.class)));

        // trigger periodic worker and alarm manager to re enforce the success of the notifications
        // since some phone manufactures kill background services
        triggerAppUsageReminderWorkManager();
        triggerAppUsageReminderNotificationAlarm(this);

        File f = new File(Collect.ODK_ROOT + "/collect.settings");
        File j = new File(Collect.ODK_ROOT + "/collect.settings.json");
        // Give JSON file preference
        if (j.exists()) {
            boolean success = SharedPreferencesUtils.loadSharedPreferencesFromJSONFile(j);
            if (success) {
                ToastUtils.showLongToast(R.string.settings_successfully_loaded_file_notification);
                j.delete();
                recreate();

                // Delete settings file to prevent overwrite of settings from JSON file on next startup
                if (f.exists()) {
                    f.delete();
                }
            } else {
                ToastUtils.showLongToast(R.string.corrupt_settings_file_notification);
            }
        } else if (f.exists()) {
            boolean success = loadSharedPreferencesFromFile(f);
            if (success) {
                ToastUtils.showLongToast(R.string.settings_successfully_loaded_file_notification);
                f.delete();
                recreate();
            } else {
                ToastUtils.showLongToast(R.string.corrupt_settings_file_notification);
            }
        }

        adminPreferences = this.getSharedPreferences(
                AdminPreferencesActivity.ADMIN_PREFERENCES, 0);

        InstancesDao instancesDao = new InstancesDao();

        // count for finalized instances
        try {
            finalizedCursor = instancesDao.getFinalizedInstancesCursor();
        } catch (Exception e) {
            createErrorDialog(e.getMessage(), EXIT);
            return;
        }

        if (finalizedCursor != null) {
            startManagingCursor(finalizedCursor);
        }
        getContentResolver().registerContentObserver(InstanceColumns.CONTENT_URI, true,
                contentObserver);
        // finalizedCursor.registerContentObserver(contentObserver);

        // count for saved instances
        try {
            savedCursor = instancesDao.getUnsentInstancesCursor();
        } catch (Exception e) {
            createErrorDialog(e.getMessage(), EXIT);
            return;
        }

        if (savedCursor != null) {
            startManagingCursor(savedCursor);
        }

        //count for view sent form
        try {
            viewSentCursor = instancesDao.getSentInstancesCursor();
        } catch (Exception e) {
            createErrorDialog(e.getMessage(), EXIT);
            return;
        }
        if (viewSentCursor != null) {
            startManagingCursor(viewSentCursor);
        }
        viewSentCount = viewSentCursor != null ? viewSentCursor.getCount() : 0;
        setupGoogleAnalytics();
        stopNotificationSoundAndVibration();
    }

    public void networkStatusCheckTimer() {
        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 10000) {

            // This is called after every 10 sec interval.
            public void onTick(long millisUntilFinished) {
                boolean isConnected = GeneralUtils.isConnectedToInternet(getApplicationContext());
                Timber.d("onTick: internet connection status " + isConnected);
                /*
                 * the chances of online status appearing are much lower than offline
                 * prioritise online status and only change incase the user is online.
                 * */
                changeNetworkStatusIndicatorText(isConnected);
            }

            public void onFinish() {
                start();
            }
        }.start();
    }

    private void changeNetworkStatusIndicatorText(boolean isConnected) {
        String dataStatus = isConnected ? "ONLINE" : "OFFLINE";
        networkStatusTextView.setText(dataStatus);
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ((Collect) getApplication())
                .getDefaultTracker()
                .enableAutoActivityTracking(true);

        // download data from django server; mapped girls
        Intent intent = new Intent(this, SetupIntentService.class);
        startService(intent);
    }

    private void startFormActivity(String formId) {
        try {
            String selectionClause = FormsProviderAPI.FormsColumns.DISPLAY_NAME + " LIKE ?";
            String[] selectionArgs = {formId + "%"};

            Cursor c = getContentResolver().query(
                    FormsProviderAPI.FormsColumns.CONTENT_URI,  // The content URI of the words table
                    null,                       // The columns to return for each row
                    selectionClause,                  // Either null, or the word the user entered
                    selectionArgs,                    // Either empty, or the string the user entered
                    null);

            c.moveToFirst();

            Uri formUri =
                    ContentUris.withAppendedId(FormsProviderAPI.FormsColumns.CONTENT_URI,
                            c.getLong(c.getColumnIndex(FormsProviderAPI.FormsColumns._ID)));

            String action = getIntent().getAction();
            if (Intent.ACTION_PICK.equals(action)) {
                // caller is waiting on a picked form
                setResult(RESULT_OK, new Intent().setData(formUri));
            } else {
                // caller wants to view/edit a form, so launch formentryactivity
                Intent intent = new Intent(Intent.ACTION_EDIT, formUri);
                intent.putExtra(ApplicationConstants.BundleKeys.FORM_MODE, ApplicationConstants.FormModes.EDIT_SAVED);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showLongToast("Please wait for the ODK forms to download and try again");
            // Incase user did not download the forms in the beginning. Reinitiate the form download
            // download all empty forms from the server. this is required before user can fill in the form
            ServerPollingJob.startJobImmediately();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    private void createErrorDialog(String errorMsg, final boolean shouldExit) {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        alertDialog.setMessage(errorMsg);
        DialogInterface.OnClickListener errorListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        if (shouldExit) {
                            finish();
                        }
                        break;
                }
            }
        };
        alertDialog.setCancelable(false);
        alertDialog.setButton(getString(R.string.ok), errorListener);
        alertDialog.show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return null;
    }

    // This flag must be set each time the app starts up
    private void setupGoogleAnalytics() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Collect
                .getInstance());
        boolean isAnalyticsEnabled = settings.getBoolean(GeneralKeys.KEY_ANALYTICS, true);
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(getApplicationContext());
        googleAnalytics.setAppOptOut(!isAnalyticsEnabled);
    }

    private boolean loadSharedPreferencesFromFile(File src) {
        // this should probably be in a thread if it ever gets big
        boolean res = false;
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(new FileInputStream(src));
            GeneralSharedPreferences.getInstance().clear();

            // first object is preferences
            Map<String, ?> entries = (Map<String, ?>) input.readObject();

            AutoSendPreferenceMigrator.migrate(entries);

            for (Entry<String, ?> entry : entries.entrySet()) {
                GeneralSharedPreferences.getInstance().save(entry.getKey(), entry.getValue());
            }

            AdminSharedPreferences.getInstance().clear();

            // second object is admin options
            Map<String, ?> adminEntries = (Map<String, ?>) input.readObject();
            for (Entry<String, ?> entry : adminEntries.entrySet()) {
                AdminSharedPreferences.getInstance().save(entry.getKey(), entry.getValue());
            }
            Collect.getInstance().initializeJavaRosa();
            res = true;
        } catch (IOException | ClassNotFoundException e) {
            Timber.e(e, "Exception while loading preferences from file due to : %s ", e.getMessage());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                Timber.e(ex, "Exception thrown while closing an input stream due to: %s ", ex.getMessage());
            }
        }
        return res;
    }

    /*
     * Used to prevent memory leaks
     */
    static class IncomingHandler extends Handler {
        private final WeakReference<MainMenuActivity> target;

        IncomingHandler(MainMenuActivity target) {
            this.target = new WeakReference<MainMenuActivity>(target);
        }

        @Override
        public void handleMessage(Message msg) {
        }
    }

    /**
     * notifies us that something changed
     */
    private class MyContentObserver extends ContentObserver {

        MyContentObserver() {
            super(null);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            handler.sendEmptyMessage(0);
        }
    }

    private void disableSmsIfNeeded() {
        if (Transport.Internet != Transport.fromPreference(GeneralSharedPreferences.getInstance().get(KEY_SUBMISSION_TRANSPORT_TYPE))) {
            GeneralSharedPreferences.getInstance().save(KEY_SUBMISSION_TRANSPORT_TYPE, getString(R.string.transport_type_value_internet));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle(R.string.sms_feature_disabled_dialog_title)
                    .setMessage(R.string.sms_feature_disabled_dialog_message)
                    .setPositiveButton(R.string.read_details, (dialog, which) -> {
                        Intent intent = new Intent(this, WebViewActivity.class);
                        intent.putExtra("url", "https://forum.opendatakit.org/t/17973");
                        startActivity(intent);
                    })
                    .setNegativeButton(R.string.ok, (dialog, which) -> dialog.dismiss());

            builder
                    .create()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getString(R.string.help_phone))));
                }
                return;
            }
        }
    }

    private void triggerAppUsageReminderWorkManager() {
        WorkManager workManager = WorkManager.getInstance();
        PeriodicWorkRequest notifyAlarmRequest = new PeriodicWorkRequest.Builder(NotifyWorker.class, 1, TimeUnit.HOURS).build();
        workManager.enqueueUniquePeriodicWork(TASK_ID, ExistingPeriodicWorkPolicy.REPLACE, notifyAlarmRequest);
    }

    private void triggerAppUsageReminderNotificationAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        Intent alarmIntent = new Intent(context, NotificationAlarmTrigger.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void sendRegistrationToServer(String firebase_device_id) {
        Timber.d("postrequest started");

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = BuildConfig.DJANGO_BACKEND_URL + "api/v1/notifier";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefs.getString(USER_ID, "0"));
            postdata.put("firebase_device_id", firebase_device_id);
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
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Timber.d("onResponse: notifier" + responseBody);
                Timber.d("onResponse: notifier" + response.code());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_help:
                callGetInHelpUser();
                return true;
            case R.id.menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.menu_logout:
                logoutDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callGetInHelpUser() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainMenuActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getString(R.string.help_phone))));
            }
        } catch (ActivityNotFoundException e) {
            Timber.e(e);
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.help_phone))));
        }
    }

    public void logoutDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainMenuActivity.this);
        alertDialog.setTitle("Confirm Logout");
        alertDialog.setMessage("Are you sure you want to logout? Please make sure you have login credentials");

        alertDialog.setIcon(R.drawable.information_outline);
        alertDialog.setPositiveButton("YES", (dialog, which) -> {
            try {
                Prefs.putBoolean(USER_LOGGED_IN, false);
                Prefs.putBoolean(GeneralKeys.KEY_FIRST_RUN, true);
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                retrofit2.Call<AuthModel> call = apiInterface.logOutUser();
                retrofit2.Response<AuthModel> response = call.execute();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                finish();
                startActivity(new Intent(this, SplashScreenActivity.class));
            }
        });

        alertDialog.setNegativeButton("NO", (dialog, which) -> {
            dialog.cancel();
        });
        alertDialog.show();
    }

    private void stopNotificationSoundAndVibration() {
        try {
            AudioPlay.stopAudio(this);
            Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            rr.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
