package org.odk.getin.android.activities;

import android.content.ComponentName;
import android.content.Intent;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.pixplicity.easyprefs.library.Prefs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.odk.getin.android.R;
import org.odk.getin.android.preferences.PreferencesActivity;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import javax.crypto.Cipher;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_LOGGED_IN;
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
     * {@link Test} to assert Options Menu's functioning.
     */
    @Test
    public void optionsMenuTest() throws Exception {
        Menu menu = shadowOf(mainMenuActivity).getOptionsMenu();

        assertNotNull(menu);
        assertNotNull(mainMenuActivity.onCreateOptionsMenu(menu));

        //Test for help button
        mainMenuActivity.onOptionsItemSelected(menu.getItem(0));
        String menuTitle = mainMenuActivity.getResources().getString(R.string.help);
        String shadowTitle = menu.getItem(0).getTitle().toString();
        assertEquals(shadowTitle, menuTitle);

        //Test for logout
        mainMenuActivity.onOptionsItemSelected(menu.getItem(1));
        menuTitle = mainMenuActivity.getResources().getString(R.string.logout);
        shadowTitle = menu.getItem(1).getTitle().toString();
        assertEquals(shadowTitle, menuTitle);
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
     * {@link Test} to assert mapGirlButton's functioning.
     */
    @Test
    public void mapGirlButtonTest() throws Exception {
        Button mapGirlButton = mainMenuActivity.findViewById(R.id.chew_button);

        assertNotNull(mapGirlButton);
        assertEquals(View.VISIBLE, mapGirlButton.getVisibility());
        mapGirlButton.performClick();
    }

    /**
     * {@link Test} to assert mappedGirlsButton's functioning.
     */
    @Test
    public void reviewDataButtonTest() throws Exception {
        Button mappedGirlsButton = mainMenuActivity.findViewById(R.id.view_edit_mapped_girls);

        assertNotNull(mappedGirlsButton);
        assertEquals(View.VISIBLE, mappedGirlsButton.getVisibility());
        mappedGirlsButton.performClick();
    }

    /**
     * {@link Test} to assert upComingAppointmentsButton's functioning.
     */
    @Test
    public void upComingAppointmentsButtonTest() throws Exception {
        Button upComingAppointmentsButton = mainMenuActivity.findViewById(R.id.upcoming_appointments_button);

        assertNotNull(upComingAppointmentsButton);
        assertEquals(View.VISIBLE, upComingAppointmentsButton.getVisibility());
        upComingAppointmentsButton.performClick();
    }
}