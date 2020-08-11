/*
 * Copyright 2018 Shobhit Agarwal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.odk.getin.android.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import org.odk.getin.android.R;
import org.odk.getin.android.adapters.AboutListAdapter;
import org.odk.getin.android.application.Collect;
import org.odk.getin.android.utilities.CustomTabHelper;

import timber.log.Timber;

public class AboutActivity extends CollectAbstractActivity implements
        AboutListAdapter.AboutItemClickListener {

    private static final String LICENSES_HTML_PATH = "file:///android_asset/open_source_licenses.html";
    private static final int REQUEST_PHONE_CALL = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        initToolbar();

        int[][] items = {
                {R.drawable.information_outline_accent, R.string.app_name, R.string.app_version_number},
                {R.drawable.ic_call_accent_24px, R.string.help, R.string.help_phone},
                {R.drawable.ic_stars, R.string.all_open_source_licenses, R.string.all_open_source_licenses_msg}
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AboutListAdapter(items, this, this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(getString(R.string.about_preferences));
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(int position) {
        if (Collect.allowClick(getClass().getName())) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    callGetInHelpUser();
                    break;
                case 2:
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra(CustomTabHelper.OPEN_URL, LICENSES_HTML_PATH);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void callGetInHelpUser() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AboutActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getString(R.string.help_phone))));
            }
        } catch (ActivityNotFoundException e) {
            Timber.e(e);
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.help_phone))));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
