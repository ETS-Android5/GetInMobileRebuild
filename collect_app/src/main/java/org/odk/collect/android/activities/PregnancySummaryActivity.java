package org.odk.collect.android.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import org.odk.collect.android.R;
import org.odk.collect.android.retrofitmodels.Value;
import org.odk.collect.android.utilities.ToastUtils;

public class PregnancySummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_summary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //todo# get phone numbers from db and populate them

        Button callGirlButton = findViewById(R.id.call_girl_button);
        callGirlButton.setOnClickListener(v -> {
            ToastUtils.showShortToast("call girl");
            callPerson("256756878433");
        });

        Button callPowerHolderButton = findViewById(R.id.call_power_holder_button);
        callPowerHolderButton.setOnClickListener(v -> {
            ToastUtils.showShortToast("call power holder");
            callPerson("256756878433");
        });

        Button previousRecordsButton = findViewById(R.id.previous_records_button);
        previousRecordsButton.setOnClickListener(v -> startActivity(new Intent(this, PreviousRecordsActivity.class)));
    }

    private void callPerson(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}
