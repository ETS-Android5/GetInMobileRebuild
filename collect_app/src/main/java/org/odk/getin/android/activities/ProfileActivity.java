package org.odk.getin.android.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.odk.getin.android.R;
import org.odk.getin.android.provider.FormsProviderAPI;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableSelection;
import org.odk.getin.android.tasks.ServerPollingJob;
import org.odk.getin.android.utilities.ApplicationConstants;
import org.odk.getin.android.utilities.ToastUtils;

import java.util.Locale;

import timber.log.Timber;

import static org.odk.getin.android.utilities.ApplicationConstants.CHEW_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.EDIT_GIRL;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_FIRST_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_LAST_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_ADJUMANI_FORM_CHEW_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_ADJUMANI_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_ARUA_FORM_CHEW_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_ARUA_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_BUNDIBUGYO_FORM_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_BUNDIBUGYO_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_KAMPALA_FORM_CHEW_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_KAMPALA_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_MOYO_FORM_CHEW_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_MOYO_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_YUMBE_FORM_CHEW_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_YUMBE_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_DISTRICT;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ROLE;
import static org.odk.getin.android.utilities.TextUtils.toCapitalize;

/**
 * Responsible for displaying mapped girl details when user clicks one of the girls under the mapped girls tab
 *
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
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView name = findViewById(R.id.name);
        TextView lastName = findViewById(R.id.last_name);
        TextView phoneNumber = findViewById(R.id.phone_number);
        TextView phoneNumberKin = findViewById(R.id.kin_phone_number);
        TextView maritalStatus = findViewById(R.id.marital_status);
        TextView age = findViewById(R.id.age);
        TextView village = findViewById(R.id.village);
        TextView education = findViewById(R.id.education);
        TextView voucherNumber = findViewById(R.id.voucher_number_text_view);
        TextView redeemedServices = findViewById(R.id.redeemed_text_view);
        TextView nationality = findViewById(R.id.nationality_text_view);
        TextView disabled = findViewById(R.id.disability_text_view);
        RelativeLayout editButton = findViewById(R.id.edit_button);
        RelativeLayout voucherRelativeLayout = findViewById(R.id.voucher_relative_layout);
        RelativeLayout redeemedRelativeLayout = findViewById(R.id.redeem_services_relative_layout);
        FloatingActionButton fab = findViewById(R.id.fab);

        MappedgirltableSelection selection = new MappedgirltableSelection();
        selection.firstnameContains(Prefs.getString(GIRL_FIRST_NAME, "")).and().lastnameContains(Prefs.getString(GIRL_LAST_NAME, ""));
        cursor = selection.query(getContentResolver());
        cursor.moveToFirst();

        name.setText(cursor.getFullName());
        lastName.setText(cursor.getLastname());
        phoneNumber.setText(cursor.getPhonenumber());
        phoneNumberKin.setText(cursor.getNextofkinphonenumber());
        maritalStatus.setText(toCapitalize(cursor.getMaritalstatus()));
        age.setText(String.format(Locale.US, "%d Years", cursor.getAge()));
        village.setText(cursor.getVillage());
        education.setText(toCapitalize(cursor.getEducationlevel()));

        if (TextUtils.isEmpty(cursor.getVoucherNumber()))
            voucherRelativeLayout.setVisibility(View.GONE);
        else
            voucherNumber.setText(cursor.getVoucherNumber());

        if (TextUtils.isEmpty(cursor.getServicesReceived()))
            redeemedRelativeLayout.setVisibility(View.GONE);
        else
            redeemedServices.setText(cursor.getServicesReceived());

        nationality.setText(cursor.getNationality());
        try {
            disabled.setText(cursor.getDisabled() ? "Yes" : "No");
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        editButton.setOnClickListener(v -> {
            Prefs.putBoolean(EDIT_GIRL, true);

            if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE)) {
                if (Prefs.getString(USER_DISTRICT, "BUNDIBUGYO").equals("BUNDIBUGYO"))
                    startFormActivity(MAP_GIRL_BUNDIBUGYO_FORM_ID);
                else if (Prefs.getString(USER_DISTRICT, "Kampala").equals("Kampala"))
                    startFormActivity(MAP_GIRL_KAMPALA_FORM_CHEW_ID);
                else if (Prefs.getString(USER_DISTRICT, "Moyo").equals("Moyo"))
                    startFormActivity(MAP_GIRL_MOYO_FORM_CHEW_ID);
                else if (Prefs.getString(USER_DISTRICT, "Moyo").equals("Adjumani"))
                    startFormActivity(MAP_GIRL_ADJUMANI_FORM_CHEW_ID);
                else if (Prefs.getString(USER_DISTRICT, "Moyo").equals("Yumbe"))
                    startFormActivity(MAP_GIRL_YUMBE_FORM_CHEW_ID);
                else
                    startFormActivity(MAP_GIRL_ARUA_FORM_CHEW_ID);
            } else {
                if (Prefs.getString(USER_DISTRICT, "BUNDIBUGYO").equals("BUNDIBUGYO"))
                    startFormActivity(MAP_GIRL_BUNDIBUGYO_FORM_MIDWIFE_ID);
                else if (Prefs.getString(USER_DISTRICT, "Kampala").equals("Kampala"))
                    startFormActivity(MAP_GIRL_KAMPALA_FORM_MIDWIFE_ID);
                else if (Prefs.getString(USER_DISTRICT, "Moyo").equals("Moyo"))
                    startFormActivity(MAP_GIRL_MOYO_FORM_MIDWIFE_ID);
                else if (Prefs.getString(USER_DISTRICT, "Moyo").equals("Adjumani"))
                    startFormActivity(MAP_GIRL_ADJUMANI_FORM_MIDWIFE_ID);
                else if (Prefs.getString(USER_DISTRICT, "Moyo").equals("Yumbe"))
                    startFormActivity(MAP_GIRL_YUMBE_FORM_MIDWIFE_ID);
                else
                    startFormActivity(MAP_GIRL_ARUA_FORM_MIDWIFE_ID);
            }
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
