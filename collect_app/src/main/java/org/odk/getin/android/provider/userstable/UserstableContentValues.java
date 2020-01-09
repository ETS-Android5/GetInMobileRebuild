package org.odk.getin.android.provider.userstable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import androidx.annotation.Nullable;
import org.odk.getin.android.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code userstable} table.
 */
public class UserstableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return UserstableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable UserstableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable UserstableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public UserstableContentValues putUserid(@Nullable String value) {
        mContentValues.put(UserstableColumns.USERID, value);
        return this;
    }

    public UserstableContentValues putUseridNull() {
        mContentValues.putNull(UserstableColumns.USERID);
        return this;
    }

    public UserstableContentValues putFirstname(@Nullable String value) {
        mContentValues.put(UserstableColumns.FIRSTNAME, value);
        return this;
    }

    public UserstableContentValues putFirstnameNull() {
        mContentValues.putNull(UserstableColumns.FIRSTNAME);
        return this;
    }

    public UserstableContentValues putLastname(@Nullable String value) {
        mContentValues.put(UserstableColumns.LASTNAME, value);
        return this;
    }

    public UserstableContentValues putLastnameNull() {
        mContentValues.putNull(UserstableColumns.LASTNAME);
        return this;
    }

    public UserstableContentValues putPhonenumber(@Nullable String value) {
        mContentValues.put(UserstableColumns.PHONENUMBER, value);
        return this;
    }

    public UserstableContentValues putPhonenumberNull() {
        mContentValues.putNull(UserstableColumns.PHONENUMBER);
        return this;
    }

    public UserstableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(UserstableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public UserstableContentValues putCreatedAtNull() {
        mContentValues.putNull(UserstableColumns.CREATED_AT);
        return this;
    }

    public UserstableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(UserstableColumns.CREATED_AT, value);
        return this;
    }

    public UserstableContentValues putNumberPlate(@Nullable String value) {
        mContentValues.put(UserstableColumns.NUMBER_PLATE, value);
        return this;
    }

    public UserstableContentValues putNumberPlateNull() {
        mContentValues.putNull(UserstableColumns.NUMBER_PLATE);
        return this;
    }

    public UserstableContentValues putRole(@Nullable String value) {
        mContentValues.put(UserstableColumns.ROLE, value);
        return this;
    }

    public UserstableContentValues putRoleNull() {
        mContentValues.putNull(UserstableColumns.ROLE);
        return this;
    }

    public UserstableContentValues putMidwifeid(@Nullable String value) {
        mContentValues.put(UserstableColumns.MIDWIFEID, value);
        return this;
    }

    public UserstableContentValues putMidwifeidNull() {
        mContentValues.putNull(UserstableColumns.MIDWIFEID);
        return this;
    }

    public UserstableContentValues putVillage(@Nullable String value) {
        mContentValues.put(UserstableColumns.VILLAGE, value);
        return this;
    }

    public UserstableContentValues putVillageNull() {
        mContentValues.putNull(UserstableColumns.VILLAGE);
        return this;
    }
}
