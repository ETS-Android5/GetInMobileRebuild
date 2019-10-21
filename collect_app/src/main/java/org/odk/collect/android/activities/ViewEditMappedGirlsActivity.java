package org.odk.collect.android.activities;

import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.ui.vieweditmappedgirls.ViewEditMappedGirlsFragment;


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
}
