/*
 * Copyright (C) 2012 University of Washington
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

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pixplicity.easyprefs.library.Prefs;

import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;
import org.odk.getin.android.BuildConfig;
import org.odk.getin.android.R;
import org.odk.getin.android.activities.FormEntryActivity;
import org.odk.getin.android.utilities.ToastUtils;
import org.odk.getin.android.widgets.interfaces.BinaryWidget;

import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_REDEEM_SERVICE_SELECTED;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_VOUCHER_NUMBER;

/**
 * <p>Use the ODK Sensors framework to print data to a connected printer.</p>
 * <p>
 * <p>The default button text is "Print Label"
 * <p>
 * <p>You may override the button text and the error text that is
 * displayed when the app is missing by using jr:itext() values. The
 * special itext form values are 'buttonText' and 'noPrinterErrorString',
 * respectively.</p>
 * <p>
 * <p>To use via XLSForm, specify a 'note' type with a 'calculation' that defines
 * the data to be printed and with an 'appearance' as described below.
 * <p>
 * <p>Within the XForms XML, to use this widget, define an appearance on the
 * &lt;input/&gt; tag that begins "printer:" and then contains the intent
 * action to launch. That intent starts the printer app. The data to print
 * is sent via a broadcast intent to intentname.data The printer then pops
 * a UI to initiate the actual printing (or change the destination printer).
 * </p>
 * <p>
 * <p>Implementation-wise, this widget is an ExStringWidget that is read-only.</p>
 * <p>
 * <p>The ODK Sensors Zebra printer uses this appearance (intent):</p>
 * <pre>
 * "printer:org.opendatakit.sensors.ZebraPrinter"
 * </pre>
 * <p>
 * <p>The data that is printed should be defined in the calculate attribute
 * of the bind. The structure of that string is a &lt;br&gt; separated list
 * of values consisting of:</p>
 * <ul><li>numeric barcode to emit (optional)</li>
 * <li>string qrcode to emit (optional)</li>
 * <li>text line 1 (optional)</li>
 * <li>additional text line (repeat as needed)</li></ul>
 * <p>
 * <p>E.g., if you wanted to emit a barcode of 123, a qrcode of "mycode" and
 * two text lines of "line 1" and "line 2", you would define the calculate
 * as:</p>
 * <p>
 * <pre>
 *  &lt;bind nodeset="/printerForm/printme" type="string" readonly="true()"
 *     calculate="concat('123','&lt;br&gt;','mycode','&lt;br&gt;','line 1','&lt;br&gt;','line 2')"
 * /&gt;
 * </pre>
 * <p>
 * <p>Depending upon what you supply, the printer may print just a
 * barcode, just a qrcode, just text, or some combination of all 3.</p>
 * <p>
 * <p>Despite using &lt;br&gt; as a separator, the supplied Zebra
 * printer does not recognize html.</p>
 * <p>
 * <pre>
 * &lt;input appearance="ex:change.uw.android.TEXTANSWER" ref="/printerForm/printme" &gt;
 * </pre>
 * <p>or, to customize the button text and error strings with itext:
 * <pre>
 *      ...
 *      &lt;bind nodeset="/printerForm/printme" type="string" readonly="true()"
 * calculate="concat('&lt;br&gt;',
 *       /printerForm/some_text ,'&lt;br&gt;Text: ', /printerForm/shortened_text
 * ,'&lt;br&gt;Integer: ',
 *       /printerForm/a_integer ,'&lt;br&gt;Decimal: ', /printerForm/a_decimal )"/&gt;
 *      ...
 *      &lt;itext&gt;
 *        &lt;translation lang="English"&gt;
 *          &lt;text id="printAnswer"&gt;
 *            &lt;value form="short"&gt;Print your label&lt;/value&gt;
 *            &lt;value form="long"&gt;Print your label&lt;/value&gt;
 *            &lt;value form="buttonText"&gt;Print now&lt;/value&gt;
 *            &lt;value form="noPrinterErrorString"&gt;ODK Sensors Zebra Printer is not installed!
 *             Please install ODK Sensors Framework and ODK Sensors Zebra Printer from Google
 * Play.&lt;/value&gt;
 *          &lt;/text&gt;
 *        &lt;/translation&gt;
 *      &lt;/itext&gt;
 *    ...
 *    &lt;input appearance="printer:org.opendatakit.sensors.ZebraPrinter" ref="/form/printme"&gt;
 *      &lt;label ref="jr:itext('printAnswer')"/&gt;
 *    &lt;/input&gt;
 * </pre>
 *
 * @author mitchellsundt@gmail.com
 */
public class ExPrinterWidget extends QuestionWidget implements BinaryWidget {

    private final Button launchIntentButton;

    public ExPrinterWidget(Context context, FormEntryPrompt prompt) {
        super(context, prompt);

        String v = getFormEntryPrompt().getSpecialFormQuestionText("buttonText");
        String buttonText = (v != null) ? v : "Click";
        launchIntentButton = getSimpleButton(buttonText);

        // finish complex layout
        LinearLayout printLayout = new LinearLayout(getContext());
        printLayout.setOrientation(LinearLayout.VERTICAL);
        printLayout.addView(launchIntentButton);
        addAnswerView(printLayout);
    }

    @Override
    public void clearAnswer() {
    }

    @Override
    public IAnswerData getAnswer() {
        return getFormEntryPrompt().getAnswerValue();
    }

    /**
     * Allows answer to be set externally in {@link FormEntryActivity}.
     */
    @Override
    public void setBinaryData(Object answer) {
    }

    @Override
    public void setFocus(Context context) {
        // focus on launch button
        launchIntentButton.requestFocus();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return !event.isAltPressed() && super.onKeyDown(keyCode, event);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        launchIntentButton.setOnLongClickListener(l);
    }

    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        launchIntentButton.cancelLongPress();
    }

    @Override
    public void onButtonClick(int buttonId) {
        SmsManager smsManager = SmsManager.getDefault();

        if (questionMediaLayout.getView_Text().getText().toString().contains("validate")) {
            smsManager.sendTextMessage(BuildConfig.MSI_PHONE_NUMBER, null,
                    String.format(getResources().getString(R.string.validate_voucher_sms_format),
                            Prefs.getString(GIRL_VOUCHER_NUMBER, "123-ABC"),
                            BuildConfig.MSI_HEALTH_FACILITY_ID), null, null);
        } else {
            String redeemServiceSelected = Prefs.getString(GIRL_REDEEM_SERVICE_SELECTED, "AN1");
            if (redeemServiceSelected.contains(getContext().getString(R.string.select_one))){
                ToastUtils.showShortToast("Please select a service to redeem!");
            } else {
                smsManager.sendTextMessage(BuildConfig.MSI_PHONE_NUMBER, null,
                        String.format(getResources().getString(R.string.redeem_voucher_sms_format),
                                Prefs.getString(GIRL_VOUCHER_NUMBER, "123-ABC"), redeemServiceSelected,
                                BuildConfig.MSI_HEALTH_FACILITY_ID), null, null);
            }
        }
    }
}
