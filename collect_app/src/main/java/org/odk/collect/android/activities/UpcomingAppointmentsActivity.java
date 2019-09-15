package org.odk.collect.android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.ui.upcomingappointments.UpcomingAppointmentsFragment;

public class UpcomingAppointmentsActivity extends CollectAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_appointments_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, UpcomingAppointmentsFragment.newInstance())
                    .commitNow();
        }
    }
}
