package org.odk.getin.android.provider.appointmentstable;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;

import org.odk.getin.android.provider.base.AbstractContentValues;

import java.util.Date;


/**
 * Content values wrapper for the {@code appointmentstable} table.
 */
public class AppointmentstableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return AppointmentstableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where           The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable AppointmentstableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where           The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable AppointmentstableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public AppointmentstableContentValues putServerid(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.SERVERID, value);
        return this;
    }

    public AppointmentstableContentValues putServeridNull() {
        mContentValues.putNull(AppointmentstableColumns.SERVERID);
        return this;
    }

    public AppointmentstableContentValues putFirstname(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.FIRSTNAME, value);
        return this;
    }

    public AppointmentstableContentValues putFirstnameNull() {
        mContentValues.putNull(AppointmentstableColumns.FIRSTNAME);
        return this;
    }

    public AppointmentstableContentValues putLastname(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.LASTNAME, value);
        return this;
    }

    public AppointmentstableContentValues putLastnameNull() {
        mContentValues.putNull(AppointmentstableColumns.LASTNAME);
        return this;
    }

    public AppointmentstableContentValues putPhonenumber(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.PHONENUMBER, value);
        return this;
    }

    public AppointmentstableContentValues putPhonenumberNull() {
        mContentValues.putNull(AppointmentstableColumns.PHONENUMBER);
        return this;
    }

    public AppointmentstableContentValues putNextofkinlastname(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public AppointmentstableContentValues putNextofkinlastnameNull() {
        mContentValues.putNull(AppointmentstableColumns.NEXTOFKINLASTNAME);
        return this;
    }

    public AppointmentstableContentValues putNextofkinfirstname(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public AppointmentstableContentValues putNextofkinfirstnameNull() {
        mContentValues.putNull(AppointmentstableColumns.NEXTOFKINFIRSTNAME);
        return this;
    }

    public AppointmentstableContentValues putNextofkinphonenumber(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public AppointmentstableContentValues putNextofkinphonenumberNull() {
        mContentValues.putNull(AppointmentstableColumns.NEXTOFKINPHONENUMBER);
        return this;
    }

    public AppointmentstableContentValues putEducationlevel(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public AppointmentstableContentValues putEducationlevelNull() {
        mContentValues.putNull(AppointmentstableColumns.EDUCATIONLEVEL);
        return this;
    }

    public AppointmentstableContentValues putMaritalstatus(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.MARITALSTATUS, value);
        return this;
    }

    public AppointmentstableContentValues putMaritalstatusNull() {
        mContentValues.putNull(AppointmentstableColumns.MARITALSTATUS);
        return this;
    }

    public AppointmentstableContentValues putAge(@Nullable Integer value) {
        mContentValues.put(AppointmentstableColumns.AGE, value);
        return this;
    }

    public AppointmentstableContentValues putAgeNull() {
        mContentValues.putNull(AppointmentstableColumns.AGE);
        return this;
    }

    public AppointmentstableContentValues putUser(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.USER, value);
        return this;
    }

    public AppointmentstableContentValues putUserNull() {
        mContentValues.putNull(AppointmentstableColumns.USER);
        return this;
    }

    public AppointmentstableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(AppointmentstableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public AppointmentstableContentValues putCreatedAtNull() {
        mContentValues.putNull(AppointmentstableColumns.CREATED_AT);
        return this;
    }

    public AppointmentstableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(AppointmentstableColumns.CREATED_AT, value);
        return this;
    }

    public AppointmentstableContentValues putCompletedAllVisits(@Nullable Boolean value) {
        mContentValues.put(AppointmentstableColumns.COMPLETED_ALL_VISITS, value);
        return this;
    }

    public AppointmentstableContentValues putCompletedAllVisitsNull() {
        mContentValues.putNull(AppointmentstableColumns.COMPLETED_ALL_VISITS);
        return this;
    }

    public AppointmentstableContentValues putPendingVisits(@Nullable Integer value) {
        mContentValues.put(AppointmentstableColumns.PENDING_VISITS, value);
        return this;
    }

    public AppointmentstableContentValues putPendingVisitsNull() {
        mContentValues.putNull(AppointmentstableColumns.PENDING_VISITS);
        return this;
    }

    public AppointmentstableContentValues putMissedVisits(@Nullable Integer value) {
        mContentValues.put(AppointmentstableColumns.MISSED_VISITS, value);
        return this;
    }

    public AppointmentstableContentValues putMissedVisitsNull() {
        mContentValues.putNull(AppointmentstableColumns.MISSED_VISITS);
        return this;
    }

    public AppointmentstableContentValues putTrimester(@Nullable Integer value) {
        mContentValues.put(AppointmentstableColumns.TRIMESTER, value);
        return this;
    }

    public AppointmentstableContentValues putTrimesterNull() {
        mContentValues.putNull(AppointmentstableColumns.TRIMESTER);
        return this;
    }

    public AppointmentstableContentValues putVillage(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.VILLAGE, value);
        return this;
    }

    public AppointmentstableContentValues putVillageNull() {
        mContentValues.putNull(AppointmentstableColumns.VILLAGE);
        return this;
    }

    public AppointmentstableContentValues putStatus(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.STATUS, value);
        return this;
    }

    public AppointmentstableContentValues putStatusNull() {
        mContentValues.putNull(AppointmentstableColumns.STATUS);
        return this;
    }

    public AppointmentstableContentValues putVhtName(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.VHT_NAME, value);
        return this;
    }

    public AppointmentstableContentValues putVhtNameNull() {
        mContentValues.putNull(AppointmentstableColumns.VHT_NAME);
        return this;
    }

    public AppointmentstableContentValues putAppointmentDate(@Nullable Date value) {
        mContentValues.put(AppointmentstableColumns.APPOINTMENT_DATE, value == null ? null : value.getTime());
        return this;
    }

    public AppointmentstableContentValues putAppointmentDateNull() {
        mContentValues.putNull(AppointmentstableColumns.APPOINTMENT_DATE);
        return this;
    }

    public AppointmentstableContentValues putAppointmentDate(@Nullable Long value) {
        mContentValues.put(AppointmentstableColumns.APPOINTMENT_DATE, value);
        return this;
    }

    public AppointmentstableContentValues putVoucherNumber(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.VOUCHER_NUMBER, value);
        return this;
    }

    public AppointmentstableContentValues putVoucherNumberNull() {
        mContentValues.putNull(AppointmentstableColumns.VOUCHER_NUMBER);
        return this;
    }

    public AppointmentstableContentValues putServicesReceived(@Nullable String value) {
        mContentValues.put(AppointmentstableColumns.SERVICES_RECEIVED, value);
        return this;
    }

    public AppointmentstableContentValues putServicesReceivedNull() {
        mContentValues.putNull(AppointmentstableColumns.SERVICES_RECEIVED);
        return this;
    }
}
