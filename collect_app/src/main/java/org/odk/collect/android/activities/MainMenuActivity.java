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

package org.odk.collect.android.activities;

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
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;
import org.odk.collect.android.NotificationAlarmTrigger;
import org.odk.collect.android.NotifyWorker;
import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.preferences.AdminKeys;
import org.odk.collect.android.preferences.AdminPreferencesActivity;
import org.odk.collect.android.preferences.AdminSharedPreferences;
import org.odk.collect.android.preferences.AutoSendPreferenceMigrator;
import org.odk.collect.android.preferences.GeneralSharedPreferences;
import org.odk.collect.android.preferences.GeneralKeys;
import org.odk.collect.android.preferences.Transport;
import org.odk.collect.android.provider.FormsProviderAPI;
import org.odk.collect.android.provider.InstanceProviderAPI.InstanceColumns;
import org.odk.collect.android.tasks.ServerPollingJob;
import org.odk.collect.android.upload.SetupIntentService;
import org.odk.collect.android.utilities.ApplicationConstants;
import org.odk.collect.android.utilities.SharedPreferencesUtils;
import org.odk.collect.android.utilities.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.ref.WeakReference;
import java.util.Calendar;
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

import static org.odk.collect.android.preferences.GeneralKeys.KEY_SUBMISSION_TRANSPORT_TYPE;
import static org.odk.collect.android.utilities.ApplicationConstants.CHEW_ROLE;
import static org.odk.collect.android.utilities.ApplicationConstants.DJANGO_BACKEND_URL;
import static org.odk.collect.android.utilities.ApplicationConstants.MAP_GIRL_ARUA_FORM_CHEW_ID;
import static org.odk.collect.android.utilities.ApplicationConstants.MAP_GIRL_ARUA_FORM_MIDWIFE_ID;
import static org.odk.collect.android.utilities.ApplicationConstants.MAP_GIRL_BUNDIBUGYO_FORM_ID;
import static org.odk.collect.android.utilities.ApplicationConstants.MAP_GIRL_BUNDIBUGYO_FORM_MIDWIFE_ID;
import static org.odk.collect.android.utilities.ApplicationConstants.USER_DISTRICT;
import static org.odk.collect.android.utilities.ApplicationConstants.USER_ID;
import static org.odk.collect.android.utilities.ApplicationConstants.USER_ROLE;
import static org.odk.collect.android.utilities.ApplicationConstants.VHT_MIDWIFE_ID;

/**
 * Responsible for displaying buttons to launch the major activities. Launches
 * some activities based on returns of others.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
public class MainMenuActivity extends CollectAbstractActivity {

    private static final int PASSWORD_DIALOG = 1;

    private static final boolean EXIT = true;
    private static final String TASK_ID = "NotificationWorker";
    // buttons
    private Button manageFilesButton;
    private Button sendDataButton;
    private Button callMidwifeOrChewButton;
    private Button reviewDataButton;
    private Button callAmbulanceButton;
    private AlertDialog alertDialog;
    private SharedPreferences adminPreferences;
    private int completedCount;
    private int savedCount;
    private int viewSentCount;
    private Cursor finalizedCursor;
    private Cursor savedCursor;
    private Cursor viewSentCursor;
    private final IncomingHandler handler = new IncomingHandler(this);
    private final MyContentObserver contentObserver = new MyContentObserver();
    private static final int REQUEST_PHONE_CALL = 34;

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
        Timber.d("User role");
        Timber.d(Prefs.getString(USER_ROLE, CHEW_ROLE));
        Timber.d("Midwife id: " + Prefs.getString(VHT_MIDWIFE_ID, ""));
        Timber.d(String.valueOf(Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE)));

        // download data from django server; mapped girls
        Intent intent = new Intent(this, SetupIntentService.class);
        startService(intent);

        // map girl button. expects a result.
        Button mapGirlButton = findViewById(R.id.chew_button);
        mapGirlButton.setText(getString(R.string.map_girl_button));
        mapGirlButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    // get form depending on loggin user district
                    if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
                        if (Prefs.getString(USER_DISTRICT, "BUNDIBUGYO").equals("BUNDIBUGYO"))
                            startFormActivity(MAP_GIRL_BUNDIBUGYO_FORM_ID);
                        else
                            startFormActivity(MAP_GIRL_ARUA_FORM_CHEW_ID);
                    else
                        if (Prefs.getString(USER_DISTRICT, "BUNDIBUGYO").equals("BUNDIBUGYO"))
                            startFormActivity(MAP_GIRL_BUNDIBUGYO_FORM_MIDWIFE_ID);
                        else
                            startFormActivity(MAP_GIRL_ARUA_FORM_MIDWIFE_ID);
                }
            }
        });

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

        // must be at the beginning of any activity that can be called from an
        // external intent
        Timber.i("Starting up, creating directories");
        try {
            Collect.createODKDirs();
        } catch (RuntimeException e) {
            createErrorDialog(e.getMessage(), EXIT);
            return;
        }


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
        completedCount = finalizedCursor != null ? finalizedCursor.getCount() : 0;
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
        savedCount = savedCursor != null ? savedCursor.getCount() : 0;

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

//        updateButtons();
        setupGoogleAnalytics();
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        Button button = toolbar.findViewById(R.id.help_button);
        button.setOnClickListener(v -> {
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        SharedPreferences sharedPreferences = this.getSharedPreferences(
                AdminPreferencesActivity.ADMIN_PREFERENCES, 0);

        boolean edit = sharedPreferences.getBoolean(
                AdminKeys.KEY_EDIT_SAVED, true);
        if (!edit) {
            if (reviewDataButton != null) {
                reviewDataButton.setVisibility(View.GONE);
            }
        } else {
            if (reviewDataButton != null) {
                reviewDataButton.setVisibility(View.VISIBLE);
            }
        }

        boolean send = sharedPreferences.getBoolean(
                AdminKeys.KEY_SEND_FINALIZED, true);
        if (!send) {
            if (sendDataButton != null) {
                sendDataButton.setVisibility(View.GONE);
            }
        } else {
            if (sendDataButton != null) {
                sendDataButton.setVisibility(View.VISIBLE);
            }
        }

        boolean viewSent = sharedPreferences.getBoolean(
                AdminKeys.KEY_VIEW_SENT, true);
        if (!viewSent) {
            if (callMidwifeOrChewButton != null) {
                callMidwifeOrChewButton.setVisibility(View.GONE);
            }
        } else {
            if (callMidwifeOrChewButton != null) {
                callMidwifeOrChewButton.setVisibility(View.VISIBLE);
            }
        }

        boolean getBlank = sharedPreferences.getBoolean(
                AdminKeys.KEY_GET_BLANK, true);
        if (!getBlank) {
            if (callAmbulanceButton != null) {
                callAmbulanceButton.setVisibility(View.GONE);
            }
        } else {
            if (callAmbulanceButton != null) {
                callAmbulanceButton.setVisibility(View.VISIBLE);
            }
        }

        boolean deleteSaved = sharedPreferences.getBoolean(
                AdminKeys.KEY_DELETE_SAVED, true);
        if (!deleteSaved) {
            if (manageFilesButton != null) {
                manageFilesButton.setVisibility(View.GONE);
            }
        } else {
            if (manageFilesButton != null) {
                manageFilesButton.setVisibility(View.VISIBLE);
            }
        }

        ((Collect) getApplication())
                .getDefaultTracker()
                .enableAutoActivityTracking(true);
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
            ToastUtils.showLongToast("Please connect to Internet and try again");
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
        switch (id) {
            case PASSWORD_DIALOG:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final AlertDialog passwordDialog = builder.create();
                passwordDialog.setTitle(getString(R.string.enter_admin_password));
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialogbox_layout, null);
                passwordDialog.setView(dialogView, 20, 10, 20, 10);
                final CheckBox checkBox = dialogView.findViewById(R.id.checkBox);
                final EditText input = dialogView.findViewById(R.id.editText);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (!checkBox.isChecked()) {
                            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        } else {
                            input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                    }
                });
                passwordDialog.setButton(AlertDialog.BUTTON_POSITIVE,
                        getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                String value = input.getText().toString();
                                String pw = adminPreferences.getString(
                                        AdminKeys.KEY_ADMIN_PW, "");
                                if (pw.compareTo(value) == 0) {
                                    Intent i = new Intent(getApplicationContext(),
                                            AdminPreferencesActivity.class);
                                    startActivity(i);
                                    input.setText("");
                                    passwordDialog.dismiss();
                                } else {
                                    ToastUtils.showShortToast(R.string.admin_password_incorrect);
                                }
                            }
                        });

                passwordDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
                        getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                input.setText("");
                            }
                        });

                passwordDialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return passwordDialog;

        }
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
        String url = DJANGO_BACKEND_URL + "api/v1/notifier";

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

}
