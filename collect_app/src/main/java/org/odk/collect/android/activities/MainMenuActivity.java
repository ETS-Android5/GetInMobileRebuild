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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.gms.analytics.GoogleAnalytics;

import org.json.JSONObject;
import org.odk.collect.android.R;
import org.odk.collect.android.activities.viewmodels.FormDownloadListViewModel;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.injection.DaggerUtils;
import org.odk.collect.android.listeners.DownloadFormsTaskListener;
import org.odk.collect.android.listeners.FormListDownloaderListener;
import org.odk.collect.android.logic.FormDetails;
import org.odk.collect.android.preferences.AdminKeys;
import org.odk.collect.android.preferences.AdminPreferencesActivity;
import org.odk.collect.android.preferences.AdminSharedPreferences;
import org.odk.collect.android.preferences.AutoSendPreferenceMigrator;
import org.odk.collect.android.preferences.GeneralSharedPreferences;
import org.odk.collect.android.preferences.GeneralKeys;
import org.odk.collect.android.preferences.PreferencesActivity;
import org.odk.collect.android.preferences.Transport;
import org.odk.collect.android.provider.InstanceProviderAPI.InstanceColumns;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.tasks.DownloadFormListTask;
import org.odk.collect.android.tasks.DownloadFormsTask;
import org.odk.collect.android.utilities.ApplicationConstants;
import org.odk.collect.android.utilities.DownloadFormListUtils;
import org.odk.collect.android.utilities.SharedPreferencesUtils;
import org.odk.collect.android.utilities.ToastUtils;
import org.odk.collect.android.utilities.WebCredentialsUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static org.odk.collect.android.preferences.GeneralKeys.KEY_SUBMISSION_TRANSPORT_TYPE;
import static org.odk.collect.android.utilities.DownloadFormListUtils.DL_AUTH_REQUIRED;
import static org.odk.collect.android.utilities.DownloadFormListUtils.DL_ERROR_MSG;

/**
 * Responsible for displaying buttons to launch the major activities. Launches
 * some activities based on returns of others.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
public class MainMenuActivity extends CollectAbstractActivity implements FormListDownloaderListener, DownloadFormsTaskListener {

    private static final int PASSWORD_DIALOG = 1;

    private static final boolean EXIT = true;
    // buttons
    private Button manageFilesButton;
    private Button sendDataButton;
    private Button viewSentFormsButton;
    private Button reviewDataButton;
    private Button getFormsButton;
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

    FormDownloadListViewModel viewModel;
    private DownloadFormListTask downloadFormListTask;
    private DownloadFormsTask downloadFormsTask;

    public static final String FORMNAME = "formname";
    private static final String FORMDETAIL_KEY = "formdetailkey";
    public static final String FORMID_DISPLAY = "formiddisplay";

    public static final String FORM_ID_KEY = "formid";
    private static final String FORM_VERSION_KEY = "formversion";
    private final ArrayList<HashMap<String, String>> filteredFormList = new ArrayList<>();
    APIInterface apiInterface;

    @Inject
    WebCredentialsUtils webCredentialsUtils;

    @Inject
    DownloadFormListUtils downloadFormListUtils;

    // private static boolean DO_NOT_EXIT = false;

    public static void startActivityAndCloseAllOthers(Activity activity) {
        activity.startActivity(new Intent(activity, MainMenuActivity.class));
        activity.overridePendingTransition(0, 0);
        activity.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        DaggerUtils.getComponent(this).inject(this);
        initToolbar();

        disableSmsIfNeeded();
        apiInterface = APIClient.getClient().create(APIInterface.class);

        //todo# load the default forms and activate the code
//        viewModel = ViewModelProviders.of(MainMenuActivity.this).get(FormDownloadListViewModel.class);
//        viewModel.clearFormList();
//        downloadFormList();

        // map girl button. expects a result.
        Button mapGirlButton = findViewById(R.id.enter_data);
        mapGirlButton.setText(getString(R.string.map_girl_button));
        mapGirlButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    Intent i = new Intent(getApplicationContext(),
                            FormChooserList.class);
                    startActivity(i);
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

//        // review data button. expects a result.
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
//
//        // send data button. expects a result.
//        sendDataButton = findViewById(R.id.send_data);
//        sendDataButton.setText(getString(R.string.send_data_button));
//        sendDataButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Collect.allowClick(getClass().getName())) {
//                    Intent i = new Intent(getApplicationContext(),
//                            InstanceUploaderListActivity.class);
//                    startActivity(i);
//                }
//            }
//        });
//
//        //View sent forms
//        viewSentFormsButton = findViewById(R.id.view_sent_forms);
//        viewSentFormsButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Collect.allowClick(getClass().getName())) {
//                    Intent i = new Intent(getApplicationContext(), InstanceChooserList.class);
//                    i.putExtra(ApplicationConstants.BundleKeys.FORM_MODE,
//                            ApplicationConstants.FormModes.VIEW_SENT);
//                    startActivity(i);
//                }
//            }
//        });
//
//        // manage forms button. no result expected.
//        getFormsButton = findViewById(R.id.get_forms);
//        getFormsButton.setText(getString(R.string.get_forms));
//        getFormsButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Collect.allowClick(getClass().getName())) {
//                    SharedPreferences sharedPreferences = PreferenceManager
//                            .getDefaultSharedPreferences(MainMenuActivity.this);
//                    String protocol = sharedPreferences.getString(
//                            GeneralKeys.KEY_PROTOCOL, getString(R.string.protocol_odk_default));
//                    Intent i = null;
//                    if (protocol.equalsIgnoreCase(getString(R.string.protocol_google_sheets))) {
//                        if (PlayServicesUtil.isGooglePlayServicesAvailable(MainMenuActivity.this)) {
//                            i = new Intent(getApplicationContext(),
//                                    GoogleDriveActivity.class);
//                        } else {
//                        ArrayList<FormDetails> filesToDownload = new ArrayList<FormDetails>();
//                        Timber.d("onClick: viewModel " + viewModel.getFormNamesAndURLs());
//                        filesToDownload.add(viewModel.getFormNamesAndURLs().get("build_GetIntest_1567416829"));
//                        Timber.d("onClick: files to download " + filesToDownload.toString());
//                        startFormsDownload(filesToDownload);
//                        downloadSelectedFiles();
//                        Toast.makeText(MainMenuActivity.this, "Finished downloading", Toast.LENGTH_SHORT).show();
//                            PlayServicesUtil.showGooglePlayServicesAvailabilityErrorDialog(MainMenuActivity.this);
//                            return;
//                        }
//                    } else {
//                        i = new Intent(getApplicationContext(),
//                                FormDownloadList.class);
//                    }
//                    startActivity(i);
//                }
//            }
//        });
//
        // manage forms button. no result expected.
        manageFilesButton = findViewById(R.id.manage_forms);
        manageFilesButton.setText(getString(R.string.manage_files));
        manageFilesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    Intent i = new Intent(getApplicationContext(), InstanceChooserList.class);
                    i.putExtra(ApplicationConstants.BundleKeys.FORM_MODE,
                            ApplicationConstants.FormModes.EDIT_SAVED);
                    startActivity(i);
                }
            }
        });

        // must be at the beginning of any activity that can be called from an
        // external intent
        Timber.i("Starting up, creating directories");
        try {
            Collect.createODKDirs();
        } catch (RuntimeException e) {
            createErrorDialog(e.getMessage(), EXIT);
            return;
        }


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

//        login();
    }

    private void downloadSelectedFiles() {
        Timber.d("downloadSelectedFiles: called");
        ArrayList<FormDetails> filesToDownload = new ArrayList<FormDetails>();
        Timber.d("downloadSelectedFiles: form names and urls " + viewModel.getFormNamesAndURLs());
        filesToDownload.add(viewModel.getFormNamesAndURLs().get(getString(R.string.default_server_url)));
        startFormsDownload(filesToDownload);
    }

    private void downloadFormList() {
        Timber.d("downloadFormList: called");
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

        if (ni == null || !ni.isConnected()) {
            ToastUtils.showShortToast(R.string.no_connection);

            if (viewModel.isDownloadOnlyMode()) {
                setReturnResult(false, getString(R.string.no_connection), viewModel.getFormResults());
                finish();
            }
        } else {
            viewModel.clearFormNamesAndURLs();

            if (downloadFormListTask != null
                    && downloadFormListTask.getStatus() != AsyncTask.Status.FINISHED) {
                return; // we are already doing the download!!!
            } else if (downloadFormListTask != null) {
                downloadFormListTask.setDownloaderListener(null);
                downloadFormListTask.cancel(true);
                downloadFormListTask = null;
            }

            Timber.d("downloadFormList: downloadFormListUtils " + downloadFormListUtils);
            downloadFormListTask = new DownloadFormListTask(downloadFormListUtils);
            downloadFormListTask.setDownloaderListener(this);

            if (viewModel.isDownloadOnlyMode()) {
                // Pass over the nulls -> They have no effect if even one of them is a null
                downloadFormListTask.setAlternateCredentials(viewModel.getUrl(), viewModel.getUsername(), viewModel.getPassword());
            }

            downloadFormListTask.execute();
        }
    }

    private void setReturnResult(boolean successful, @Nullable String message, @Nullable HashMap<String, Boolean> resultFormIds) {
        Intent intent = new Intent();
        intent.putExtra(ApplicationConstants.BundleKeys.SUCCESS_KEY, successful);
        if (message != null) {
            intent.putExtra(ApplicationConstants.BundleKeys.MESSAGE, message);
        }
        if (resultFormIds != null) {
            intent.putExtra(ApplicationConstants.BundleKeys.FORM_IDS, resultFormIds);
        }

        setResult(RESULT_OK, intent);
    }

    private void startFormsDownload(@NonNull ArrayList<FormDetails> filesToDownload) {
        Timber.d("startFormsDownload: started");
        int totalCount = filesToDownload.size();
        Timber.d("startFormsDownload: size " + totalCount);
        if (totalCount > 0) {
            // show dialog box

            downloadFormsTask = new DownloadFormsTask();
            downloadFormsTask.setDownloaderListener(this);

            if (viewModel.getUrl() != null) {
                if (viewModel.getUsername() != null && viewModel.getPassword() != null) {
                    webCredentialsUtils.saveCredentials(viewModel.getUrl(), viewModel.getUsername(), viewModel.getPassword());
                } else {
                    webCredentialsUtils.clearCredentials(viewModel.getUrl());
                }
            }
            downloadFormsTask.execute(filesToDownload);
        } else {
            ToastUtils.showShortToast(R.string.noselect_error);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(getString(R.string.main_menu));
        setSupportActionBar(toolbar);
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
            if (viewSentFormsButton != null) {
                viewSentFormsButton.setVisibility(View.GONE);
            }
        } else {
            if (viewSentFormsButton != null) {
                viewSentFormsButton.setVisibility(View.VISIBLE);
            }
        }

        boolean getBlank = sharedPreferences.getBoolean(
                AdminKeys.KEY_GET_BLANK, true);
        if (!getBlank) {
            if (getFormsButton != null) {
                getFormsButton.setVisibility(View.GONE);
            }
        } else {
            if (getFormsButton != null) {
                getFormsButton.setVisibility(View.VISIBLE);
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

    @Override
    protected void onPause() {
        super.onPause();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.menu_general_preferences:
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;
            case R.id.menu_admin_preferences:
                String pw = adminPreferences.getString(
                        AdminKeys.KEY_ADMIN_PW, "");
                if ("".equalsIgnoreCase(pw)) {
                    startActivity(new Intent(this, AdminPreferencesActivity.class));
                } else {
                    showDialog(PASSWORD_DIALOG);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void updateButtons() {
        if (finalizedCursor != null && !finalizedCursor.isClosed()) {
            finalizedCursor.requery();
            completedCount = finalizedCursor.getCount();
            if (completedCount > 0) {
                sendDataButton.setText(
                        getString(R.string.send_data_button, String.valueOf(completedCount)));
            } else {
                sendDataButton.setText(getString(R.string.send_data));
            }
        } else {
            sendDataButton.setText(getString(R.string.send_data));
            Timber.w("Cannot update \"Send Finalized\" button label since the database is closed. "
                    + "Perhaps the app is running in the background?");
        }

        if (savedCursor != null && !savedCursor.isClosed()) {
            savedCursor.requery();
            savedCount = savedCursor.getCount();
            if (savedCount > 0) {
                reviewDataButton.setText(getString(R.string.review_data_button,
                        String.valueOf(savedCount)));
            } else {
                reviewDataButton.setText(getString(R.string.review_data));
            }
        } else {
            reviewDataButton.setText(getString(R.string.review_data));
            Timber.w("Cannot update \"Edit Form\" button label since the database is closed. "
                    + "Perhaps the app is running in the background?");
        }

        if (viewSentCursor != null && !viewSentCursor.isClosed()) {
            viewSentCursor.requery();
            viewSentCount = viewSentCursor.getCount();
            if (viewSentCount > 0) {
                viewSentFormsButton.setText(
                        getString(R.string.view_sent_forms_button, String.valueOf(viewSentCount)));
            } else {
                viewSentFormsButton.setText(getString(R.string.view_sent_forms));
            }
        } else {
            viewSentFormsButton.setText(getString(R.string.view_sent_forms));
            Timber.w("Cannot update \"View Sent\" button label since the database is closed. "
                    + "Perhaps the app is running in the background?");
        }
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

    public void formListDownloadingComplete(HashMap<String, FormDetails> result) {
        Timber.d("formListDownloadingComplete: called");
//        viewModel.setProgressDialogShowing(false);
        downloadFormListTask.setDownloaderListener(null);
        downloadFormListTask = null;

        if (result == null) {
            Timber.e("Formlist Downloading returned null.  That shouldn't happen");
            // Just displayes "error occured" to the user, but this should never happen.
            if (viewModel.isDownloadOnlyMode()) {
                setReturnResult(false, "Formlist Downloading returned null.  That shouldn't happen", null);
            }

            return;
        }

        if (result.containsKey(DL_AUTH_REQUIRED)) {
            // need authorization
        } else if (result.containsKey(DL_ERROR_MSG)) {
            // Download failed
            String dialogMessage =
                    getString(R.string.list_failed_with_error,
                            result.get(DL_ERROR_MSG).getErrorStr());
            String dialogTitle = getString(R.string.load_remote_form_error);

            if (viewModel.isDownloadOnlyMode()) {
                setReturnResult(false, getString(R.string.load_remote_form_error), viewModel.getFormResults());
            }

        } else {
            // Everything worked. Clear the list and add the results.
            viewModel.setFormNamesAndURLs(result);

            viewModel.clearFormList();

            ArrayList<String> ids = new ArrayList<String>(viewModel.getFormNamesAndURLs().keySet());
            for (int i = 0; i < result.size(); i++) {
                String formDetailsKey = ids.get(i);
                FormDetails details = viewModel.getFormNamesAndURLs().get(formDetailsKey);

                if (true || (details.isNewerFormVersionAvailable() || details.areNewerMediaFilesAvailable())) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put(FORMNAME, details.getFormName());
                    item.put(FORMID_DISPLAY,
                            ((details.getFormVersion() == null) ? "" : (getString(R.string.version) + " "
                                    + details.getFormVersion() + " ")) + "ID: " + details.getFormID());
                    item.put(FORMDETAIL_KEY, formDetailsKey);
                    item.put(FORM_ID_KEY, details.getFormID());
                    item.put(FORM_VERSION_KEY, details.getFormVersion());

                    // Insert the new form in alphabetical order.
                    if (viewModel.getFormList().isEmpty()) {
                        viewModel.addForm(item);
                    } else {
                        int j;
                        for (j = 0; j < viewModel.getFormList().size(); j++) {
                            HashMap<String, String> compareMe = viewModel.getFormList().get(j);
                            String name = compareMe.get(FORMNAME);
                            if (name.compareTo(viewModel.getFormNamesAndURLs().get(ids.get(i)).getFormName()) > 0) {
                                break;
                            }
                        }
                        viewModel.addForm(j, item);
                    }
                }
            }

            filteredFormList.addAll(viewModel.getFormList());

            if (viewModel.isDownloadOnlyMode()) {
                //1. First check if all form IDS could be found on the server - Register forms that could not be found

                for (String formId: viewModel.getFormIdsToDownload()) {
                    viewModel.putFormResult(formId, false);
                }

                ArrayList<FormDetails> filesToDownload  = new ArrayList<>();

                for (FormDetails formDetails: viewModel.getFormNamesAndURLs().values()) {
                    String formId = formDetails.getFormID();

                    if (viewModel.getFormResults().containsKey(formId)) {
                        filesToDownload.add(formDetails);
                    }
                }

                //2. Select forms and start downloading
                if (!filesToDownload.isEmpty()) {
                    startFormsDownload(filesToDownload);
                } else {
                    // None of the forms was found
                    setReturnResult(false, "Forms not found on server", viewModel.getFormResults());
                    finish();
                }
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void formsDownloadingComplete(HashMap<FormDetails, String> result) {
        if (downloadFormsTask != null) {
            downloadFormsTask.setDownloaderListener(null);
        }

        cleanUpWebCredentials();

//        createAlertDialog(getString(R.string.download_forms_result), getDownloadResultMessage(result), EXIT);

        // Set result to true for forms which were downloaded
        if (viewModel.isDownloadOnlyMode()) {
            for (FormDetails formDetails: result.keySet()) {
                String successKey = result.get(formDetails);
                if (Collect.getInstance().getString(R.string.success).equals(successKey)) {
                    if (viewModel.getFormResults().containsKey(formDetails.getFormID())) {
                        viewModel.putFormResult(formDetails.getFormID(), true);
                    }
                }
            }

            setReturnResult(true, null, viewModel.getFormResults());
        }
    }

    private void cleanUpWebCredentials() {
        if (viewModel.getUrl() != null) {
            String host = Uri.parse(viewModel.getUrl())
                    .getHost();

            if (host != null) {
                webCredentialsUtils.clearCredentials(viewModel.getUrl());
            }
        }
    }

    @Override
    public void progressUpdate(String currentFile, int progress, int total) {

    }

    @Override
    public void formsDownloadingCancelled() {
        if (downloadFormsTask != null) {
            downloadFormsTask.setDownloaderListener(null);
            downloadFormsTask = null;
        }

        cleanUpWebCredentials();

        if (viewModel.isDownloadOnlyMode()) {
            setReturnResult(false, "Download cancelled", null);
            finish();
        }
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
            MainMenuActivity target = this.target.get();
            if (target != null) {
                target.updateButtons();
            }
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

    private void login(){
        Timber.d("login started");
//        todo# pick username/email and password from user
        String username = "pkigenyi@outbox.co.ug";
        String password = "Doppler25";
//
//        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
//            aq.toast("Please enter your phone number and password");
//            return;
//        }

        Call<String> call = apiInterface.login(username, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Timber.d("onResponse() -> " + response.code());
                loginResponse(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Timber.e("onFailure() -> " + t.getMessage());
            }
        });
    }

    private void loginResponse(Response<String> response){
        //todo# move this to login activity
        try{
            Timber.d("login started");
            JSONObject json;
            if(response.code() == 200 || response.code() == 201){
                json = new JSONObject(response.body());
                String token = json.optString("token");
                if(TextUtils.isEmpty(token)){
                    ToastUtils.showShortToast(json.optString("error", "Failed to login. Please check your phone number and password."));
                }else{

                    GeneralSharedPreferences.getInstance().save(GeneralKeys.KEY_TOKEN, token);

                    //todo# activate this when login is created
//                    Intent intent = new Intent(this, MainMenuActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                }
            }else{
                json = new JSONObject(response.errorBody().string());
                ToastUtils.showShortToast(json.optString("error", "Failed to login. Please check your phone number and password."));
            }
        }catch (Exception ex){
            Timber.d("Error processing loginResponse() -> " + ex.getMessage());
        }
    }
}
