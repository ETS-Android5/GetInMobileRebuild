/*
 * Copyright (C) 2018 Nafundi
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

package org.odk.collect.android.upload;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.MainMenuActivity;
import org.odk.collect.android.activities.NotificationActivity;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.dto.Instance;
import org.odk.collect.android.http.openrosa.OpenRosaHttpInterface;
import org.odk.collect.android.logic.PropertyManager;
import org.odk.collect.android.preferences.GeneralKeys;
import org.odk.collect.android.preferences.GeneralSharedPreferences;
import org.odk.collect.android.provider.InstanceProviderAPI.InstanceColumns;
import org.odk.collect.android.utilities.InstanceUploaderUtils;
import org.odk.collect.android.utilities.NotificationUtils;
import org.odk.collect.android.utilities.PermissionUtils;
import org.odk.collect.android.utilities.WebCredentialsUtils;
import org.odk.collect.android.utilities.gdrive.GoogleAccountsManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

import static org.odk.collect.android.utilities.ApplicationConstants.RequestCodes.FORMS_UPLOADED_NOTIFICATION;
import static org.odk.collect.android.utilities.InstanceUploaderUtils.SPREADSHEET_UPLOADED_TO_GOOGLE_DRIVE;

public class AutoSendWorker extends Worker {
    private static final int AUTO_SEND_RESULT_NOTIFICATION_ID = 1328974928;

    public AutoSendWorker(@NonNull Context c, @NonNull WorkerParameters parameters) {
        super(c, parameters);
    }

    /**
     * If the app-level auto-send setting is enabled, send all finalized forms that don't specify not
     * to auto-send at the form level. If the app-level auto-send setting is disabled, send all
     * finalized forms that specify to send at the form level.
     *
     * Fails immediately if:
     *   - storage isn't ready
     *   - the network type that toggled on is not the desired type AND no form specifies auto-send
     *
     * If the network type doesn't match the auto-send settings, retry next time a connection is
     * available.
     */
    @NonNull
    @Override
    @SuppressLint("WrongThread")
    public Result doWork() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Result.FAILURE;
        }

        List<Instance> toUpload = getInstancesToAutoSend();
        Timber.d("doWork: toUpload size " + toUpload.size());

        if (toUpload.isEmpty()) {
            return Result.SUCCESS;
        }

        GeneralSharedPreferences settings = GeneralSharedPreferences.getInstance();
        String protocol = (String) settings.get(GeneralKeys.KEY_PROTOCOL);

        InstanceUploader uploader;
        Map<String, String> resultMessagesByInstanceId = new HashMap<>();
        String deviceId = null;
        boolean anyFailure = false;

        if (protocol.equals(getApplicationContext().getString(R.string.protocol_google_sheets))) {
            if (PermissionUtils.isGetAccountsPermissionGranted(getApplicationContext())) {
                GoogleAccountsManager accountsManager = new GoogleAccountsManager(Collect.getInstance());
                String googleUsername = accountsManager.getLastSelectedAccountIfValid();
                if (googleUsername.isEmpty()) {
                    showUploadStatusNotification(true, Collect.getInstance().getString(R.string.google_set_account));
                    return Result.FAILURE;
                }
                accountsManager.selectAccount(googleUsername);
                uploader = new InstanceGoogleSheetsUploader(accountsManager);
            } else {
                showUploadStatusNotification(true, Collect.getInstance().getString(R.string.odk_permissions_fail));
                return Result.FAILURE;
            }
        } else {
            OpenRosaHttpInterface httpInterface = Collect.getInstance().getComponent().openRosaHttpInterface();
            uploader = new InstanceServerUploader(httpInterface,
                    new WebCredentialsUtils(), new HashMap<>());
            deviceId = new PropertyManager(Collect.getInstance().getApplicationContext())
                    .getSingularProperty(PropertyManager.withUri(PropertyManager.PROPMGR_DEVICE_ID));
        }

        for (Instance instance : toUpload) {
            try {
                String destinationUrl = uploader.getUrlToSubmitTo(instance, deviceId, null);
                if (protocol.equals(getApplicationContext().getString(R.string.protocol_google_sheets))
                        && !InstanceUploaderUtils.doesUrlRefersToGoogleSheetsFile(destinationUrl)) {
                    anyFailure = true;
                    resultMessagesByInstanceId.put(instance.getDatabaseId().toString(), SPREADSHEET_UPLOADED_TO_GOOGLE_DRIVE);
                    continue;
                }
                String customMessage = uploader.uploadOneSubmission(instance, destinationUrl);
                resultMessagesByInstanceId.put(instance.getDatabaseId().toString(),
                        customMessage != null ? customMessage : Collect.getInstance().getString(R.string.success));

                // If the submission was successful, delete the instance if either the app-level
                // delete preference is set or the form definition requests auto-deletion.
                // TODO: this could take some time so might be better to do in a separate process,
                // perhaps another worker. It also feels like this could fail and if so should be
                // communicated to the user. Maybe successful delete should also be communicated?
                if (InstanceUploader.formShouldBeAutoDeleted(instance.getJrFormId(),
                        (boolean) GeneralSharedPreferences.getInstance().get(GeneralKeys.KEY_DELETE_AFTER_SEND))) {
                    Uri deleteForm = Uri.withAppendedPath(InstanceColumns.CONTENT_URI, instance.getDatabaseId().toString());
                    Collect.getInstance().getContentResolver().delete(deleteForm, null, null);
                }

                String action = protocol.equals(getApplicationContext().getString(R.string.protocol_google_sheets)) ?
                        "HTTP-Sheets auto" : "HTTP auto";
                String label = Collect.getFormIdentifierHash(instance.getJrFormId(), instance.getJrVersion());
                Collect.getInstance().logRemoteAnalytics("Submission", action, label);
            } catch (UploadException e) {
                Timber.d(e);
                anyFailure = true;
                resultMessagesByInstanceId.put(instance.getDatabaseId().toString(),
                        e.getDisplayMessage());
            }
        }

        String message = formatOverallResultMessage(resultMessagesByInstanceId);
        showUploadStatusNotification(anyFailure, message);

        return Result.SUCCESS;
    }

    /**
     * Returns instances that need to be auto-sent.
     */
    @NonNull
    private List<Instance> getInstancesToAutoSend() {
        InstancesDao dao = new InstancesDao();
        Cursor c = dao.getFinalizedInstancesCursor();
        List<Instance> allFinalized = dao.getInstancesFromCursor(c);
        return allFinalized;
    }

    private String formatOverallResultMessage(Map<String, String> resultMessagesByInstanceId) {
        String message = "";

        if (resultMessagesByInstanceId != null) {
            StringBuilder selection = new StringBuilder();
            Set<String> keys = resultMessagesByInstanceId.keySet();
            Iterator<String> it = keys.iterator();

            String[] selectionArgs = new String[keys.size()];
            int i = 0;
            while (it.hasNext()) {
                String id = it.next();
                selection.append(InstanceColumns._ID + "=?");
                selectionArgs[i++] = id;
                if (i != keys.size()) {
                    selection.append(" or ");
                }
            }

            Cursor cursor = new InstancesDao().getInstancesCursor(selection.toString(), selectionArgs);
            message = InstanceUploaderUtils.getUploadResultMessage(cursor, resultMessagesByInstanceId);
        }
        return message;
    }

    private void showUploadStatusNotification(boolean anyFailure, String message) {
        Intent notifyIntent = new Intent(Collect.getInstance(), MainMenuActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyIntent.putExtra(NotificationActivity.NOTIFICATION_TITLE, Collect.getInstance().getString(R.string.upload_results));
        notifyIntent.putExtra(NotificationActivity.NOTIFICATION_MESSAGE, R.string.girls_mapped_success);

        PendingIntent pendingNotify = PendingIntent.getActivity(Collect.getInstance(), FORMS_UPLOADED_NOTIFICATION,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationUtils.showNotification(
                pendingNotify,
                AUTO_SEND_RESULT_NOTIFICATION_ID,
                R.string.getin_auto_send_note,
                Collect.getInstance().getString(R.string.success));

        // download data from django server; mapped girls
        Intent intent = new Intent(Collect.getInstance(), SetupIntentService.class);
        Collect.getInstance().startService(intent);

    }
}