package org.odk.getin.android.provider.mappedgirltable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import androidx.annotation.Nullable;

import org.odk.getin.android.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code mappedgirltable} table.
 */
public class MappedgirltableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MappedgirltableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MappedgirltableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MappedgirltableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MappedgirltableContentValues putServerid(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.SERVERID, value);
        return this;
    }

    public MappedgirltableContentValues putServeridNull() {
        mContentValues.putNull(MappedgirltableColumns.SERVERID);
        return this;
    }

    public MappedgirltableContentValues putFirstname(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.FIRSTNAME, value);
        return this;
    }

    public MappedgirltableContentValues putFirstnameNull() {
        mContentValues.putNull(MappedgirltableColumns.FIRSTNAME);
        return this;
    }

    public MappedgirltableContentValues putLastname(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.LASTNAME, value);
        return this;
    }

    public MappedgirltableContentValues putLastnameNull() {
        mContentValues.putNull(MappedgirltableColumns.LASTNAME);
        return this;
    }

    public MappedgirltableContentValues putPhonenumber(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.PHONENUMBER, value);
        return this;
    }

    public MappedgirltableContentValues putPhonenumberNull() {
        mContentValues.putNull(MappedgirltableColumns.PHONENUMBER);
        return this;
    }

    public MappedgirltableContentValues putNextofkinlastname(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public MappedgirltableContentValues putNextofkinlastnameNull() {
        mContentValues.putNull(MappedgirltableColumns.NEXTOFKINLASTNAME);
        return this;
    }

    public MappedgirltableContentValues putNextofkinfirstname(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public MappedgirltableContentValues putNextofkinfirstnameNull() {
        mContentValues.putNull(MappedgirltableColumns.NEXTOFKINFIRSTNAME);
        return this;
    }

    public MappedgirltableContentValues putNextofkinphonenumber(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public MappedgirltableContentValues putNextofkinphonenumberNull() {
        mContentValues.putNull(MappedgirltableColumns.NEXTOFKINPHONENUMBER);
        return this;
    }

    public MappedgirltableContentValues putEducationlevel(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public MappedgirltableContentValues putEducationlevelNull() {
        mContentValues.putNull(MappedgirltableColumns.EDUCATIONLEVEL);
        return this;
    }

    public MappedgirltableContentValues putMaritalstatus(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.MARITALSTATUS, value);
        return this;
    }

    public MappedgirltableContentValues putMaritalstatusNull() {
        mContentValues.putNull(MappedgirltableColumns.MARITALSTATUS);
        return this;
    }

    public MappedgirltableContentValues putAge(@Nullable Integer value) {
        mContentValues.put(MappedgirltableColumns.AGE, value);
        return this;
    }

    public MappedgirltableContentValues putAgeNull() {
        mContentValues.putNull(MappedgirltableColumns.AGE);
        return this;
    }

    public MappedgirltableContentValues putUser(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.USER, value);
        return this;
    }

    public MappedgirltableContentValues putUserNull() {
        mContentValues.putNull(MappedgirltableColumns.USER);
        return this;
    }

    public MappedgirltableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(MappedgirltableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public MappedgirltableContentValues putCreatedAtNull() {
        mContentValues.putNull(MappedgirltableColumns.CREATED_AT);
        return this;
    }

    public MappedgirltableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(MappedgirltableColumns.CREATED_AT, value);
        return this;
    }

    public MappedgirltableContentValues putCompletedAllVisits(@Nullable Boolean value) {
        mContentValues.put(MappedgirltableColumns.COMPLETED_ALL_VISITS, value);
        return this;
    }

    public MappedgirltableContentValues putCompletedAllVisitsNull() {
        mContentValues.putNull(MappedgirltableColumns.COMPLETED_ALL_VISITS);
        return this;
    }

    public MappedgirltableContentValues putPendingVisits(@Nullable Integer value) {
        mContentValues.put(MappedgirltableColumns.PENDING_VISITS, value);
        return this;
    }

    public MappedgirltableContentValues putPendingVisitsNull() {
        mContentValues.putNull(MappedgirltableColumns.PENDING_VISITS);
        return this;
    }

    public MappedgirltableContentValues putMissedVisits(@Nullable Integer value) {
        mContentValues.put(MappedgirltableColumns.MISSED_VISITS, value);
        return this;
    }

    public MappedgirltableContentValues putMissedVisitsNull() {
        mContentValues.putNull(MappedgirltableColumns.MISSED_VISITS);
        return this;
    }

    public MappedgirltableContentValues putVillage(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.VILLAGE, value);
        return this;
    }

    public MappedgirltableContentValues putVillageNull() {
        mContentValues.putNull(MappedgirltableColumns.VILLAGE);
        return this;
    }

    public MappedgirltableContentValues putVoucherNumber(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.VOUCHER_NUMBER, value);
        return this;
    }

    public MappedgirltableContentValues putVoucherNumberNull() {
        mContentValues.putNull(MappedgirltableColumns.VOUCHER_NUMBER);
        return this;
    }

    public MappedgirltableContentValues putServicesReceived(@Nullable String value) {
        mContentValues.put(MappedgirltableColumns.SERVICES_RECEIVED, value);
        return this;
    }

    public MappedgirltableContentValues putServicesReceivedNull() {
        mContentValues.putNull(MappedgirltableColumns.SERVICES_RECEIVED);
        return this;
    }
}
