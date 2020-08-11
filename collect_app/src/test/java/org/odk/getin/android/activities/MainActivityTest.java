package org.odk.getin.android.activities;

import android.content.ComponentName;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odk.getin.android.R;
import org.odk.getin.android.preferences.PreferencesActivity;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

/**
 * Unit test for checking {@link Button}'s behaviour  in {@link MainMenuActivity}
 */
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainMenuActivity mainMenuActivity;

    /**
     * Runs {@link Before} each test.
     */
    @Before
    public void setUp() throws Exception {
        mainMenuActivity = Robolectric.setupActivity(MainMenuActivity.class);
    }

    /**
     * {@link Test} to assert {@link MainMenuActivity} for not null.
     */
    @Test
    public void nullActivityTest() throws Exception {
        assertNotNull(mainMenuActivity);
    }

    /**
     * {@link Test} to assert title of {@link MainMenuActivity} for not null.
     */
    @Test
    public void titleTest() throws Exception {
        Toolbar toolbar = mainMenuActivity.findViewById(R.id.toolbar);
        assertEquals(mainMenuActivity.getString(R.string.app_name), toolbar.getTitle());
    }

    /**
     * {@link Test} to assert dataButton's functioning.
     */
    @Test
    public void dataButtonTest() throws Exception {
        Button mapGirlButton = mainMenuActivity.findViewById(R.id.chew_button);

        assertNotNull(mapGirlButton);
        assertEquals(View.VISIBLE, mapGirlButton.getVisibility());
        mapGirlButton.performClick();
    }

    /**
     * {@link Test} to assert reviewDataButton's functioning.
     */
    @Test
    public void reviewDataButtonTest() throws Exception {
        Button mappedGirlsButton = mainMenuActivity.findViewById(R.id.view_edit_mapped_girls);

        assertNotNull(mappedGirlsButton);
        assertEquals(View.VISIBLE, mappedGirlsButton.getVisibility());
        mappedGirlsButton.performClick();
    }

    /**
     * {@link Test} to assert sendDataButton's functioning.
     */
    @Test
    public void upComingAppointmentsButtonTest() throws Exception {
        Button sendDataButton = mainMenuActivity.findViewById(R.id.upcoming_appointments_button);

        assertNotNull(sendDataButton);
        assertEquals(View.VISIBLE, sendDataButton.getVisibility());
        sendDataButton.performClick();
    }
}