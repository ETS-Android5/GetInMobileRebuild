package org.odk.collect.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.ui.postnatal.PostNatalFragment;

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
