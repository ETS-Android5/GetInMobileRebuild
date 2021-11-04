package org.odk.getin.android.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.ui.vieweditmappedgirls.AmbulanceViewFragment;
import org.odk.getin.android.activities.ui.vieweditmappedgirls.HealthFacilitiesViewFragment;
import org.odk.getin.android.adapters.AmbulanceAdapter;
import org.odk.getin.android.adapters.HealthFacilityAdapter;
import org.odk.getin.android.provider.appointmentstable.AppointmentstableCursor;
import org.odk.getin.android.provider.appointmentstable.AppointmentstableSelection;
import org.odk.getin.android.provider.userstable.UserstableCursor;
import org.odk.getin.android.provider.userstable.UserstableSelection;

public class HealthFacilityActivity extends CollectAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_facility);

//        recycler.setAdapter(new );
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HealthFacilitiesViewFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private AppointmentstableCursor queryAppointmentTable() {
        return new AppointmentstableSelection().orderByCreatedAt(true).query(getContentResolver());
    }
}