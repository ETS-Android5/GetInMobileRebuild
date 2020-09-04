package org.odk.getin.android.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.ui.vieweditmappedgirls.Searchable;
import org.odk.getin.android.activities.ui.vieweditmappedgirls.ViewEditMappedGirlsFragment;
import org.odk.getin.android.adapters.SectionsPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;


public class ViewEditMappedGirlsActivity extends CollectAbstractActivity {

    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_people_white_24dp,
            R.drawable.ic_contact_mail_white_24dp
    };
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_mapped_girls_activity);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        // reset the search view every two seconds
        try {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    resetSearch();
                }
            }, 0, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void resetSearch() {
        Fragment currentFragment = sectionsPagerAdapter.getCurrentFragment();
        if (currentFragment instanceof ViewEditMappedGirlsFragment)
            ((Searchable) currentFragment).initializeSearch();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
