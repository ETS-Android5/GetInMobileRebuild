package org.odk.getin.android.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.getin.android.R;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableSelection;

import timber.log.Timber;

import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_FIRST_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_LAST_NAME;
import static org.odk.getin.android.utilities.TextUtils.toCapitalize;

/**
 * Responsible for displaying mapped girl details when user clicks one of the girls under the mapped girls tab
 * @author Phillip Kigenyi (codephillip@gmail.com)
 */

public class ProfileActivity extends AppCompatActivity {
    private MappedgirltableCursor cursor;
    private static final int REQUEST_PHONE_CALL = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView name = findViewById(R.id.name);
        TextView lastName = findViewById(R.id.last_name);
        TextView phoneNumber = findViewById(R.id.phone_number);
        TextView phoneNumberKin = findViewById(R.id.kin_phone_number);
        TextView maritalStatus = findViewById(R.id.marital_status);
        TextView age = findViewById(R.id.age);
        TextView village = findViewById(R.id.village);
        TextView education = findViewById(R.id.education);
        RelativeLayout backButton = findViewById(R.id.back_button);
        FloatingActionButton fab = findViewById(R.id.fab);

        MappedgirltableSelection selection = new MappedgirltableSelection();
        selection.firstnameContains(Prefs.getString(GIRL_FIRST_NAME, "")).and().lastnameContains(Prefs.getString(GIRL_LAST_NAME, ""));
        cursor = selection.query(getContentResolver());
        cursor.moveToFirst();

        name.setText(cursor.getFirstname() + " " + cursor.getLastname());
        lastName.setText(cursor.getLastname());
        phoneNumber.setText(cursor.getPhonenumber());
        phoneNumberKin.setText(cursor.getNextofkinphonenumber());
        maritalStatus.setText(toCapitalize(cursor.getMaritalstatus()));
        age.setText(cursor.getAge() + " Years");
        village.setText(cursor.getVillage());
        education.setText(toCapitalize(cursor.getEducationlevel()));

        fab.setOnClickListener(view -> {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cursor.getPhonenumber())));
                }
            } catch (ActivityNotFoundException e) {
                Timber.e(e);
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cursor.getPhonenumber())));
            }
        });

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String phoneNumber = cursor.getPhonenumber();
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                }
                return;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
