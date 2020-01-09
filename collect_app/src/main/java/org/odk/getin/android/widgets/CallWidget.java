/*
 * Copyright (C) 2013 Nafundi LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.odk.getin.android.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;
import org.odk.getin.android.R;
import org.odk.getin.android.utilities.CustomTabHelper;
import org.odk.getin.android.widgets.interfaces.ButtonWidget;

/**
 * Widget that allows user to open EmergencyCallActivity and call the midwife throught the calling app
 *
 * @author Phillip Kigenyi (codephillip@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
@SuppressLint("ViewConstructor")
public class CallWidget extends QuestionWidget implements ButtonWidget {

    private Uri uri;
    private final Button openUrlButton;
    private final CustomTabHelper customTabHelper;

    public CallWidget(Context context, FormEntryPrompt prompt) {
        super(context, prompt);

        openUrlButton = getSimpleButton(context.getString(R.string.call_midwife));
        openUrlButton.setBackground(getResources().getDrawable(R.drawable.round_button_blue));
        openUrlButton.setTextColor(Color.WHITE);

        TableLayout.LayoutParams params = new TableLayout.LayoutParams();
        params.setMargins(10, 20, 10, 2);
        openUrlButton.setLayoutParams(params);

        // finish complex layout
        LinearLayout answerLayout = new LinearLayout(getContext());
        answerLayout.setOrientation(LinearLayout.VERTICAL);
        answerLayout.addView(openUrlButton);
        addAnswerView(answerLayout);

        customTabHelper = new CustomTabHelper();
    }

    private boolean isUrlEmpty(TextView stringAnswer) {
        return stringAnswer == null || stringAnswer.getText() == null
                || stringAnswer.getText().toString().isEmpty();
    }

    @Override
    public void clearAnswer() {
        Toast.makeText(getContext(), "URL is readonly", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IAnswerData getAnswer() {
        return null;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (customTabHelper.getServiceConnection() != null) {
            getContext().unbindService(customTabHelper.getServiceConnection());
        }
    }

    @Override
    public void onButtonClick(int buttonId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("getin://android.getincall.app"));
        getContext().startActivity(intent);
    }
}
