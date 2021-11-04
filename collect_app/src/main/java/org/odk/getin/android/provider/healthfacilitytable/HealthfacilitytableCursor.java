package org.odk.getin.android.provider.healthfacilitytable;

import java.util.Date;

import android.database.Cursor;

import androidx.annotation.Nullable;

import org.odk.getin.android.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code healthfacilitytable} table.
 */
public class HealthfacilitytableCursor extends AbstractCursor implements HealthfacilitytableModel {
    public HealthfacilitytableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(HealthfacilitytableColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getServerid() {
        String res = getStringOrNull(HealthfacilitytableColumns.SERVERID);
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(HealthfacilitytableColumns.NAME);
        return res;
    }

    /**
     * Get the {@code facilitylevel} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getFacilitylevel() {
        String res = getStringOrNull(HealthfacilitytableColumns.FACILITYLEVEL);
        return res;
    }

    /**
     * Get the {@code district} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDistrict() {
        String res = getStringOrNull(HealthfacilitytableColumns.DISTRICT);
        return res;
    }

    /**
     * Get the {@code subcounty} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getSubcounty() {
        String res = getStringOrNull(HealthfacilitytableColumns.SUBCOUNTY);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(HealthfacilitytableColumns.CREATED_AT);
        return res;
    }
}
