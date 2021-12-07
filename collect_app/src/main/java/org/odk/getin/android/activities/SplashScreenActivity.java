/*
 * Copyright (C) 2011 University of Washington
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

import static org.odk.getin.android.provider.MappedGirlsDatabaseHelper.DATABASE_FILE_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_LOGGED_IN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Window;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.getin.android.R;
import org.odk.getin.android.application.Collect;
import org.odk.getin.android.listeners.PermissionListener;
import org.odk.getin.android.preferences.GeneralKeys;
import org.odk.getin.android.provider.FormsProviderAPI;
import org.odk.getin.android.provider.MappedGirlsDatabaseHelper;
import org.odk.getin.android.tasks.ServerPollingJob;
import org.odk.getin.android.utilities.DialogUtils;
import org.odk.getin.android.utilities.PermissionUtils;

import java.io.File;

import timber.log.Timber;

public class SplashScreenActivity extends CollectAbstractActivity {

    private static final int SPLASH_TIMEOUT = 2000; // milliseconds
    private static final boolean EXIT = true;
    private boolean firstRun;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void requestPermissions() {
        new PermissionUtils().requestStoragePermissions(this, new PermissionListener() {
            @Override
            public void granted() {
                // must be at the beginning of any activity that can be called from an external intent
                try {
                    SharedPreferences sharedPref = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
                    firstRun = sharedPref.getBoolean(GeneralKeys.KEY_FIRST_RUN, true);
                    Timber.d("firstrun1 %s", firstRun);
//                    if (firstRun || !Prefs.getBoolean(USER_LOGGED_IN, false)) {
                    if (firstRun) {
                        if (askForPermissions()) {
                            clearFormsFolderAndStartFormDownload();
                        }
                    } else {
                        endSplashScreen();
                    }
                } catch (RuntimeException e) {
                    DialogUtils.showDialog(DialogUtils.createErrorDialog(SplashScreenActivity.this,
                            e.getMessage(), EXIT), SplashScreenActivity.this);
                    return;
                }
            }

            @Override
            public void denied() {
                // The activity has to finish because ODK Collect cannot function without these permissions.
                endSplashScreen();
                finish();
            }
        });
    }

    private void clearFormsFolderAndStartFormDownload() {
        deleteODKFormStorageFolder();
        Collect.createODKDirs();
        // download all empty forms from the server. this is required before user can fill in the form
        ServerPollingJob.startJobImmediately();

        MappedGirlsDatabaseHelper.getInstance(getApplicationContext()).close();
        deleteDatabase(DATABASE_FILE_NAME);
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
    }

    private void endSplashScreen() {
        Timber.d("firstrun3 %s", firstRun);
        if (!firstRun) {
            // navigate the user to the main activity if they logged in once
            // this allows the user the use the app offline
            Intent i = new Intent(getApplicationContext(),
                    MainMenuActivity.class);
            startActivity(i);
            //Complete and destroy login activity once successful
            finish();
        } else {
            startActivity(new Intent(this, ChooseUserActivity.class));
            finish();
        }
    }

    public void deleteODKFormStorageFolder() {
        try {
            File dir = new File(Collect.FORMS_PATH);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
            getContentResolver().delete(FormsProviderAPI.FormsColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermissions();
    }

    public boolean askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Timber.d("askForPermissions greater than SDK 30");
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
                return false;
            }
        }
        endSplashScreen();
        return true;
    }
}
