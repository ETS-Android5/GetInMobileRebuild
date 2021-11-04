package org.odk.getin.android.provider.healthfacilitytable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.Nullable;

import org.odk.getin.android.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code healthfacilitytable} table.
 */
public class HealthfacilitytableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return HealthfacilitytableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable HealthfacilitytableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable HealthfacilitytableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public HealthfacilitytableContentValues putServerid(@Nullable String value) {
        mContentValues.put(HealthfacilitytableColumns.SERVERID, value);
        return this;
    }

    public HealthfacilitytableContentValues putServeridNull() {
        mContentValues.putNull(HealthfacilitytableColumns.SERVERID);
        return this;
    }

    public HealthfacilitytableContentValues putName(@Nullable String value) {
        mContentValues.put(HealthfacilitytableColumns.NAME, value);
        return this;
    }

    public HealthfacilitytableContentValues putNameNull() {
        mContentValues.putNull(HealthfacilitytableColumns.NAME);
        return this;
    }

    public HealthfacilitytableContentValues putFacilitylevel(@Nullable String value) {
        mContentValues.put(HealthfacilitytableColumns.FACILITYLEVEL, value);
        return this;
    }

    public HealthfacilitytableContentValues putFacilitylevelNull() {
        mContentValues.putNull(HealthfacilitytableColumns.FACILITYLEVEL);
        return this;
    }

    public HealthfacilitytableContentValues putDistrict(@Nullable String value) {
        mContentValues.put(HealthfacilitytableColumns.DISTRICT, value);
        return this;
    }

    public HealthfacilitytableContentValues putDistrictNull() {
        mContentValues.putNull(HealthfacilitytableColumns.DISTRICT);
        return this;
    }

    public HealthfacilitytableContentValues putSubcounty(@Nullable String value) {
        mContentValues.put(HealthfacilitytableColumns.SUBCOUNTY, value);
        return this;
    }

    public HealthfacilitytableContentValues putSubcountyNull() {
        mContentValues.putNull(HealthfacilitytableColumns.SUBCOUNTY);
        return this;
    }

    public HealthfacilitytableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(HealthfacilitytableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public HealthfacilitytableContentValues putCreatedAtNull() {
        mContentValues.putNull(HealthfacilitytableColumns.CREATED_AT);
        return this;
    }

    public HealthfacilitytableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(HealthfacilitytableColumns.CREATED_AT, value);
        return this;
    }
}
