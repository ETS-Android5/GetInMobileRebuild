package org.odk.getin.android.widgets;

import androidx.annotation.NonNull;

import net.bytebuddy.utility.RandomString;

import org.javarosa.core.model.data.StringData;
import org.odk.getin.android.widgets.base.QuestionWidgetTest;

/**
 * @author James Knight
 */

public class UrlWidgetTest extends QuestionWidgetTest<CallWidget, StringData> {
    @NonNull
    @Override
    public CallWidget createWidget() {
        return new CallWidget(activity, formEntryPrompt);
    }

    @NonNull
    @Override
    public StringData getNextAnswer() {
        return new StringData(RandomString.make());
    }

    @Override
    public void callingClearShouldRemoveTheExistingAnswer() {
        // The widget is ReadOnly, clear shouldn't do anything.
    }
}
