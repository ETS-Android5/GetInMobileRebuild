package org.odk.getin.android.activities;

import android.os.Bundle;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.ui.vieweditmappedgirls.AmbulanceViewFragment;


public class AmbulanceViewActivity extends CollectAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ambulace_view_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AmbulanceViewFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
