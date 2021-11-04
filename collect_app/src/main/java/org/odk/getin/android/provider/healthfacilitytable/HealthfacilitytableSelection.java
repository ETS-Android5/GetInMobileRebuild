package org.odk.getin.android.provider.healthfacilitytable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import org.odk.getin.android.provider.base.AbstractSelection;

/**
 * Selection for the {@code healthfacilitytable} table.
 */
public class HealthfacilitytableSelection extends AbstractSelection<HealthfacilitytableSelection> {
    @Override
    protected Uri baseUri() {
        return HealthfacilitytableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code HealthfacilitytableCursor} object, which is positioned before the first entry, or null.
     */
    public HealthfacilitytableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new HealthfacilitytableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public HealthfacilitytableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code HealthfacilitytableCursor} object, which is positioned before the first entry, or null.
     */
    public HealthfacilitytableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new HealthfacilitytableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public HealthfacilitytableCursor query(Context context) {
        return query(context, null);
    }


    public HealthfacilitytableSelection id(long... value) {
        addEquals("healthfacilitytable." + HealthfacilitytableColumns._ID, toObjectArray(value));
        return this;
    }

    public HealthfacilitytableSelection idNot(long... value) {
        addNotEquals("healthfacilitytable." + HealthfacilitytableColumns._ID, toObjectArray(value));
        return this;
    }

    public HealthfacilitytableSelection orderById(boolean desc) {
        orderBy("healthfacilitytable." + HealthfacilitytableColumns._ID, desc);
        return this;
    }

    public HealthfacilitytableSelection orderById() {
        return orderById(false);
    }

    public HealthfacilitytableSelection serverid(String... value) {
        addEquals(HealthfacilitytableColumns.SERVERID, value);
        return this;
    }

    public HealthfacilitytableSelection serveridNot(String... value) {
        addNotEquals(HealthfacilitytableColumns.SERVERID, value);
        return this;
    }

    public HealthfacilitytableSelection serveridLike(String... value) {
        addLike(HealthfacilitytableColumns.SERVERID, value);
        return this;
    }

    public HealthfacilitytableSelection serveridContains(String... value) {
        addContains(HealthfacilitytableColumns.SERVERID, value);
        return this;
    }

    public HealthfacilitytableSelection serveridStartsWith(String... value) {
        addStartsWith(HealthfacilitytableColumns.SERVERID, value);
        return this;
    }

    public HealthfacilitytableSelection serveridEndsWith(String... value) {
        addEndsWith(HealthfacilitytableColumns.SERVERID, value);
        return this;
    }

    public HealthfacilitytableSelection orderByServerid(boolean desc) {
        orderBy(HealthfacilitytableColumns.SERVERID, desc);
        return this;
    }

    public HealthfacilitytableSelection orderByServerid() {
        orderBy(HealthfacilitytableColumns.SERVERID, false);
        return this;
    }

    public HealthfacilitytableSelection name(String... value) {
        addEquals(HealthfacilitytableColumns.NAME, value);
        return this;
    }

    public HealthfacilitytableSelection nameNot(String... value) {
        addNotEquals(HealthfacilitytableColumns.NAME, value);
        return this;
    }

    public HealthfacilitytableSelection nameLike(String... value) {
        addLike(HealthfacilitytableColumns.NAME, value);
        return this;
    }

    public HealthfacilitytableSelection nameContains(String... value) {
        addContains(HealthfacilitytableColumns.NAME, value);
        return this;
    }

    public HealthfacilitytableSelection nameStartsWith(String... value) {
        addStartsWith(HealthfacilitytableColumns.NAME, value);
        return this;
    }

    public HealthfacilitytableSelection nameEndsWith(String... value) {
        addEndsWith(HealthfacilitytableColumns.NAME, value);
        return this;
    }

    public HealthfacilitytableSelection orderByName(boolean desc) {
        orderBy(HealthfacilitytableColumns.NAME, desc);
        return this;
    }

    public HealthfacilitytableSelection orderByName() {
        orderBy(HealthfacilitytableColumns.NAME, false);
        return this;
    }

    public HealthfacilitytableSelection facilitylevel(String... value) {
        addEquals(HealthfacilitytableColumns.FACILITYLEVEL, value);
        return this;
    }

    public HealthfacilitytableSelection facilitylevelNot(String... value) {
        addNotEquals(HealthfacilitytableColumns.FACILITYLEVEL, value);
        return this;
    }

    public HealthfacilitytableSelection facilitylevelLike(String... value) {
        addLike(HealthfacilitytableColumns.FACILITYLEVEL, value);
        return this;
    }

    public HealthfacilitytableSelection facilitylevelContains(String... value) {
        addContains(HealthfacilitytableColumns.FACILITYLEVEL, value);
        return this;
    }

    public HealthfacilitytableSelection facilitylevelStartsWith(String... value) {
        addStartsWith(HealthfacilitytableColumns.FACILITYLEVEL, value);
        return this;
    }

    public HealthfacilitytableSelection facilitylevelEndsWith(String... value) {
        addEndsWith(HealthfacilitytableColumns.FACILITYLEVEL, value);
        return this;
    }

    public HealthfacilitytableSelection orderByFacilitylevel(boolean desc) {
        orderBy(HealthfacilitytableColumns.FACILITYLEVEL, desc);
        return this;
    }

    public HealthfacilitytableSelection orderByFacilitylevel() {
        orderBy(HealthfacilitytableColumns.FACILITYLEVEL, false);
        return this;
    }

    public HealthfacilitytableSelection district(String... value) {
        addEquals(HealthfacilitytableColumns.DISTRICT, value);
        return this;
    }

    public HealthfacilitytableSelection districtNot(String... value) {
        addNotEquals(HealthfacilitytableColumns.DISTRICT, value);
        return this;
    }

    public HealthfacilitytableSelection districtLike(String... value) {
        addLike(HealthfacilitytableColumns.DISTRICT, value);
        return this;
    }

    public HealthfacilitytableSelection districtContains(String... value) {
        addContains(HealthfacilitytableColumns.DISTRICT, value);
        return this;
    }

    public HealthfacilitytableSelection districtStartsWith(String... value) {
        addStartsWith(HealthfacilitytableColumns.DISTRICT, value);
        return this;
    }

    public HealthfacilitytableSelection districtEndsWith(String... value) {
        addEndsWith(HealthfacilitytableColumns.DISTRICT, value);
        return this;
    }

    public HealthfacilitytableSelection orderByDistrict(boolean desc) {
        orderBy(HealthfacilitytableColumns.DISTRICT, desc);
        return this;
    }

    public HealthfacilitytableSelection orderByDistrict() {
        orderBy(HealthfacilitytableColumns.DISTRICT, false);
        return this;
    }

    public HealthfacilitytableSelection subcounty(String... value) {
        addEquals(HealthfacilitytableColumns.SUBCOUNTY, value);
        return this;
    }

    public HealthfacilitytableSelection subcountyNot(String... value) {
        addNotEquals(HealthfacilitytableColumns.SUBCOUNTY, value);
        return this;
    }

    public HealthfacilitytableSelection subcountyLike(String... value) {
        addLike(HealthfacilitytableColumns.SUBCOUNTY, value);
        return this;
    }

    public HealthfacilitytableSelection subcountyContains(String... value) {
        addContains(HealthfacilitytableColumns.SUBCOUNTY, value);
        return this;
    }

    public HealthfacilitytableSelection subcountyStartsWith(String... value) {
        addStartsWith(HealthfacilitytableColumns.SUBCOUNTY, value);
        return this;
    }

    public HealthfacilitytableSelection subcountyEndsWith(String... value) {
        addEndsWith(HealthfacilitytableColumns.SUBCOUNTY, value);
        return this;
    }

    public HealthfacilitytableSelection orderBySubcounty(boolean desc) {
        orderBy(HealthfacilitytableColumns.SUBCOUNTY, desc);
        return this;
    }

    public HealthfacilitytableSelection orderBySubcounty() {
        orderBy(HealthfacilitytableColumns.SUBCOUNTY, false);
        return this;
    }

    public HealthfacilitytableSelection createdAt(Date... value) {
        addEquals(HealthfacilitytableColumns.CREATED_AT, value);
        return this;
    }

    public HealthfacilitytableSelection createdAtNot(Date... value) {
        addNotEquals(HealthfacilitytableColumns.CREATED_AT, value);
        return this;
    }

    public HealthfacilitytableSelection createdAt(Long... value) {
        addEquals(HealthfacilitytableColumns.CREATED_AT, value);
        return this;
    }

    public HealthfacilitytableSelection createdAtAfter(Date value) {
        addGreaterThan(HealthfacilitytableColumns.CREATED_AT, value);
        return this;
    }

    public HealthfacilitytableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(HealthfacilitytableColumns.CREATED_AT, value);
        return this;
    }

    public HealthfacilitytableSelection createdAtBefore(Date value) {
        addLessThan(HealthfacilitytableColumns.CREATED_AT, value);
        return this;
    }

    public HealthfacilitytableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(HealthfacilitytableColumns.CREATED_AT, value);
        return this;
    }

    public HealthfacilitytableSelection orderByCreatedAt(boolean desc) {
        orderBy(HealthfacilitytableColumns.CREATED_AT, desc);
        return this;
    }

    public HealthfacilitytableSelection orderByCreatedAt() {
        orderBy(HealthfacilitytableColumns.CREATED_AT, false);
        return this;
    }
}
