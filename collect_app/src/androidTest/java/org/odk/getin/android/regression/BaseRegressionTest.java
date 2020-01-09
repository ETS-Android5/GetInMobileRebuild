package org.odk.getin.android.regression;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.odk.getin.android.activities.MainMenuActivity;

public class BaseRegressionTest {

    @Rule
    public ActivityTestRule<MainMenuActivity> main = new ActivityTestRule<>(MainMenuActivity.class);
}