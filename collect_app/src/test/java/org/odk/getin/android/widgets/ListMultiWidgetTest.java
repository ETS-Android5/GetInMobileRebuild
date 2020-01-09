package org.odk.getin.android.widgets;

import androidx.annotation.NonNull;

import org.odk.getin.android.widgets.base.GeneralSelectMultiWidgetTest;

/**
 * @author James Knight
 */

public class ListMultiWidgetTest extends GeneralSelectMultiWidgetTest<ListMultiWidget> {
    @NonNull
    @Override
    public ListMultiWidget createWidget() {
        return new ListMultiWidget(activity, formEntryPrompt, true);
    }
}
