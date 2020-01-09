package org.odk.getin.android.regression;

import android.Manifest;

import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.odk.getin.android.R;
import org.odk.getin.android.espressoutils.FormEntry;
import org.odk.getin.android.espressoutils.MainMenu;
import org.odk.getin.android.espressoutils.Settings;
import org.odk.getin.android.support.CopyFormRule;
import org.odk.getin.android.support.ResetStateRule;

import static androidx.test.espresso.Espresso.pressBack;

//Issue NODK-415
@RunWith(AndroidJUnit4.class)
public class TriggerWidgetTest extends BaseRegressionTest {
    @Rule
    public RuleChain copyFormChain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE)
            )
            .around(new ResetStateRule())
            .around(new CopyFormRule("Automated_guidance_hint_form.xml"));

    @Test
    public void guidanceIcons_ShouldBeAlwaysShown() {
        MainMenu.clickOnMenu();
        MainMenu.clickGeneralSettings();
        Settings.openFormManagement();
        Settings.openShowGuidanceForQuestions();
        Settings.clickOnString(R.string.guidance_yes);
        pressBack();
        pressBack();
        MainMenu.startBlankForm("Guidance Form Sample");
        FormEntry.checkIsTextDisplayed("Guidance text");
        FormEntry.swipeToNextQuestion();
        FormEntry.clickSaveAndExit();

    }

    @Test
    public void guidanceIcons_ShouldBeCollapsed() {
        MainMenu.clickOnMenu();
        MainMenu.clickGeneralSettings();
        Settings.openFormManagement();
        Settings.openShowGuidanceForQuestions();
        Settings.clickOnString(R.string.guidance_yes_collapsed);
        pressBack();
        pressBack();
        MainMenu.startBlankForm("Guidance Form Sample");
        FormEntry.checkIsIdDisplayed(R.id.help_icon);
        FormEntry.clickOnText("TriggerWidget");
        FormEntry.checkIsTextDisplayed("Guidance text");
        FormEntry.swipeToNextQuestion();
        FormEntry.clickSaveAndExit();
    }
}