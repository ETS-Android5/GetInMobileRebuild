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

package org.odk.collect.android.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.listeners.PermissionListener;
import org.odk.collect.android.preferences.GeneralKeys;
import org.odk.collect.android.tasks.ServerPollingJob;
import org.odk.collect.android.utilities.DialogUtils;
import org.odk.collect.android.utilities.PermissionUtils;

import timber.log.Timber;

public class SplashScreenActivity extends CollectAbstractActivity {

    private static final int SPLASH_TIMEOUT = 2000; // milliseconds
    private static final boolean EXIT = true;
    private boolean firstRun;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        firstRun = Prefs.getBoolean(GeneralKeys.KEY_FIRST_RUN, true);

        new PermissionUtils().requestStoragePermissions(this, new PermissionListener() {
            @Override
            public void granted() {
                // must be at the beginning of any activity that can be called from an external intent
                try {
                    Collect.createODKDirs();
                    if (firstRun)
                        // download all empty forms from the server. this is required before user can fill in the form
                        ServerPollingJob.startJobImmediately();
                } catch (RuntimeException e) {
                    DialogUtils.showDialog(DialogUtils.createErrorDialog(SplashScreenActivity.this,
                            e.getMessage(), EXIT), SplashScreenActivity.this);
                    return;
                }
                endSplashScreen();
            }

            @Override
            public void denied() {
                // The activity has to finish because ODK Collect cannot function without these permissions.
                endSplashScreen();
                finish();
            }
        });
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);



        PackageInfo packageInfo = null;
        try {
            packageInfo =
                    getPackageManager().getPackageInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "Unable to get package info");
        }

        startSplashScreenTimer();
    }

    private void endSplashScreen() {
        //todo navigate directly to home screen if already logged in
        startActivity(new Intent(this, ChooseUserActivity.class));
        finish();
    }

    private void startSplashScreenTimer() {
        // create a thread that counts up to the timeout
        Thread t = new Thread() {
            int count;

            @Override
            public void run() {
                try {
                    super.run();
                    while (count < SPLASH_TIMEOUT) {
                        sleep(100);
                        count += 100;
                    }
                } catch (Exception e) {
                    Timber.e(e);
                } finally {
                    if (!firstRun)
                        endSplashScreen();
                }
            }
        };
        t.start();
    }
}
