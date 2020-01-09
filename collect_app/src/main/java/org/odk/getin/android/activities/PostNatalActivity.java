package org.odk.getin.android.activities;

import android.os.Bundle;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.ui.postnatal.PostNatalFragment;

public class PostNatalActivity extends CollectAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_natal_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PostNatalFragment.newInstance())
                    .commitNow();
        }
    }
}
