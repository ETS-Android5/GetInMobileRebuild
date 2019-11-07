package org.odk.collect.android.provider.mappedgirltable;

import java.util.Date;

import android.database.Cursor;

import androidx.annotation.Nullable;

import org.odk.collect.android.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code mappedgirltable} table.
 */
public class MappedgirltableCursor extends AbstractCursor implements MappedgirltableModel {
    public MappedgirltableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MappedgirltableColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code firstname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getFirstname() {
        String res = getStringOrNull(MappedgirltableColumns.FIRSTNAME);
        return res;
    }

    /**
     * Get the {@code lastname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getLastname() {
        String res = getStringOrNull(MappedgirltableColumns.LASTNAME);
        return res;
    }

    /**
     * Get the {@code phonenumber} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPhonenumber() {
        String res = getStringOrNull(MappedgirltableColumns.PHONENUMBER);
        return res;
    }

    /**
     * Get the {@code nextofkinlastname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getNextofkinlastname() {
        String res = getStringOrNull(MappedgirltableColumns.NEXTOFKINLASTNAME);
        return res;
    }

    /**
     * Get the {@code nextofkinfirstname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getNextofkinfirstname() {
        String res = getStringOrNull(MappedgirltableColumns.NEXTOFKINFIRSTNAME);
        return res;
    }

    /**
     * Get the {@code nextofkinphonenumber} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getNextofkinphonenumber() {
        String res = getStringOrNull(MappedgirltableColumns.NEXTOFKINPHONENUMBER);
        return res;
    }

    /**
     * Get the {@code educationlevel} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getEducationlevel() {
        String res = getStringOrNull(MappedgirltableColumns.EDUCATIONLEVEL);
        return res;
    }

    /**
     * Get the {@code maritalstatus} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMaritalstatus() {
        String res = getStringOrNull(MappedgirltableColumns.MARITALSTATUS);
        return res;
    }

    /**
     * Get the {@code age} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getAge() {
        Integer res = getIntegerOrNull(MappedgirltableColumns.AGE);
        return res;
    }

    /**
     * Get the {@code user} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getUser() {
        String res = getStringOrNull(MappedgirltableColumns.USER);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(MappedgirltableColumns.CREATED_AT);
        return res;
    }

    /**
     * Get the {@code completed_all_visits} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getCompletedAllVisits() {
        Boolean res = getBooleanOrNull(MappedgirltableColumns.COMPLETED_ALL_VISITS);
        return res;
    }

    /**
     * Get the {@code pending_visits} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getPendingVisits() {
        Integer res = getIntegerOrNull(MappedgirltableColumns.PENDING_VISITS);
        return res;
    }

    /**
     * Get the {@code missed_visits} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getMissedVisits() {
        Integer res = getIntegerOrNull(MappedgirltableColumns.MISSED_VISITS);
        return res;
    }
}
