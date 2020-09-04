package org.odk.getin.android.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.getin.android.R;

import timber.log.Timber;

import static org.odk.getin.android.utilities.ApplicationConstants.VHT_MIDWIFE_PHONE;

/**
 * Responsible for launching the android call app with the given number.
 * This activity only requests for permissions and delegates the calling functionality to the call app.
 * ideally should not be shown to the user
 * It is called when the vht answers yes to all the risk assessment questions
 *
 * @author Phillip Kigenyi (codephillip@gmail.com)
 */

public class EmergencyCallActivity extends CollectAbstractActivity {

    private static final int REQUEST_PHONE_CALL = 89;
    String phoneNumber = Prefs.getString(VHT_MIDWIFE_PHONE, "0787228913");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_call);
        Timber.d("Make emergency phone call");


        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                openCallApp();
            }
        } catch (ActivityNotFoundException e) {
            Timber.e(e);
            openCallApp();
        }
    }

    private void openCallApp() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
        //exit app after opening android call app
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCallApp();
                }
                return;
            }
        }
        finish();
    }
}
