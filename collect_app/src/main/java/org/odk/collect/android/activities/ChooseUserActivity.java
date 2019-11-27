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

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.ui.login.LoginActivity;

import static org.odk.collect.android.utilities.ApplicationConstants.USER_LOGGED_IN;

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
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(getString(R.string.choose_role));
        setSupportActionBar(toolbar);
    }
}
