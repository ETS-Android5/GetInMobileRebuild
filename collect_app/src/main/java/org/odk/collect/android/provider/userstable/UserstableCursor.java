package org.odk.collect.android.provider.userstable;

import java.util.Date;

import android.database.Cursor;
import androidx.annotation.Nullable;

import org.odk.collect.android.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code userstable} table.
 */
public class UserstableCursor extends AbstractCursor implements UserstableModel {
    public UserstableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(UserstableColumns._ID);
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
        String res = getStringOrNull(UserstableColumns.FIRSTNAME);
        return res;
    }

    /**
     * Get the {@code lastname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getLastname() {
        String res = getStringOrNull(UserstableColumns.LASTNAME);
        return res;
    }

    /**
     * Get the {@code phonenumber} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPhonenumber() {
        String res = getStringOrNull(UserstableColumns.PHONENUMBER);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(UserstableColumns.CREATED_AT);
        return res;
    }

    /**
     * Get the {@code number_plate} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getNumberPlate() {
        String res = getStringOrNull(UserstableColumns.NUMBER_PLATE);
        return res;
    }

    /**
     * Get the {@code role} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getRole() {
        Integer res = getIntegerOrNull(UserstableColumns.ROLE);
        return res;
    }

    /**
     * Get the {@code midwifeid} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMidwifeid() {
        String res = getStringOrNull(UserstableColumns.MIDWIFEID);
        return res;
    }

    /**
     * Get the {@code village} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getVillage() {
        String res = getStringOrNull(UserstableColumns.VILLAGE);
        return res;
    }
}
