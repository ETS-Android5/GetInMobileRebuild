package org.odk.getin.android.activities;

import android.os.Bundle;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.ui.vieweditmappedgirls.ViewEditMappedGirlsFragment;


public class ViewEditMappedGirlsActivity extends CollectAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_mapped_girls_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ViewEditMappedGirlsFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
