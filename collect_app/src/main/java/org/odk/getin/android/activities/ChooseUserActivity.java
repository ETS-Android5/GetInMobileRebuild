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

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.odk.getin.android.BuildConfig;
import org.odk.getin.android.R;
import org.odk.getin.android.activities.ui.login.LoginActivity;

/**
 * Responsible for displaying buttons to launch activities.
 * User logs in either as VHT(chew) or Midwife
 *
 * @author Phillip Kigenyi
 */
public class ChooseUserActivity extends CollectAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_user_activity);
        initToolbar();

        Button mapGirlButton = findViewById(R.id.chew_button);
        mapGirlButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),
                    LoginActivity.class);
            i.putExtra("user_type", "chew");
            startActivity(i);
            finish();
        });

        Button midwifeButton = findViewById(R.id.midwife_button);
        midwifeButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),
                    LoginActivity.class);
            i.putExtra("user_type", "midwife");
            startActivity(i);
            finish();
        });

        TextView appVersion = findViewById(R.id.app_version);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = pInfo.versionName.replace("-dirty", "");
            if (BuildConfig.DJANGO_BACKEND_URL.contains("test") || BuildConfig.APP_USER_URL.contains("test")) {
                versionName = versionName  + "-test";
            }
            appVersion.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            appVersion.setVisibility(View.GONE);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(getString(R.string.choose_role));
        setSupportActionBar(toolbar);
    }
}
