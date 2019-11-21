package org.odk.collect.android.activities;

import android.os.Bundle;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.ui.vieweditmappedgirls.AmbulanceViewFragment;
import org.odk.collect.android.activities.ui.vieweditmappedgirls.CallUserViewFragment;


public class CallUserViewActivity extends CollectAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_call_user_view_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CallUserViewFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
