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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.listeners.PermissionListener;
import org.odk.collect.android.preferences.GeneralKeys;
import org.odk.collect.android.preferences.GeneralSharedPreferences;
import org.odk.collect.android.utilities.DialogUtils;
import org.odk.collect.android.utilities.PermissionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import timber.log.Timber;

import static org.odk.collect.android.preferences.GeneralKeys.KEY_SPLASH_PATH;

public class SplashScreenActivity extends CollectAbstractActivity {

    private static final int SPLASH_TIMEOUT = 2000; // milliseconds
    private static final boolean EXIT = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new PermissionUtils().requestStoragePermissions(this, new PermissionListener() {
            @Override
            public void granted() {
                // must be at the beginning of any activity that can be called from an external intent
                try {
                    Collect.createODKDirs();
                } catch (RuntimeException e) {
                    DialogUtils.showDialog(DialogUtils.createErrorDialog(SplashScreenActivity.this,
                            e.getMessage(), EXIT), SplashScreenActivity.this);
                    return;
                }

                init();
            }

            @Override
            public void denied() {
                // The activity has to finish because ODK Collect cannot function without these permissions.
                finish();
            }
        });
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);

        // get the shared preferences object
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // get the package info object with version number
        PackageInfo packageInfo = null;
        try {
            packageInfo =
                    getPackageManager().getPackageInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "Unable to get package info");
        }

        boolean firstRun = sharedPreferences.getBoolean(GeneralKeys.KEY_FIRST_RUN, true);
        boolean showSplash =
                sharedPreferences.getBoolean(GeneralKeys.KEY_SHOW_SPLASH, false);
        String splashPath = (String) GeneralSharedPreferences.getInstance().get(KEY_SPLASH_PATH);

        // if you've increased version code, then update the version number and set firstRun to true
        if (sharedPreferences.getLong(GeneralKeys.KEY_LAST_VERSION, 0)
                < packageInfo.versionCode) {
            editor.putLong(GeneralKeys.KEY_LAST_VERSION, packageInfo.versionCode);
            editor.apply();

            firstRun = true;
        }

        startSplashScreen(splashPath);
    }

    private void endSplashScreen() {
        //todo navigate directly to home screen if already logged in
        startActivity(new Intent(this, ChooseUserActivity.class));
        finish();
    }

    private void startSplashScreen(String path) {
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
                    endSplashScreen();
                }
            }
        };
        t.start();
    }
}
