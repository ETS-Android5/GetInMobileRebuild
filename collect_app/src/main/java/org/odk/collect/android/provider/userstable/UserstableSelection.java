package org.odk.collect.android.provider.userstable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import org.odk.collect.android.provider.base.AbstractSelection;

/**
 * Selection for the {@code userstable} table.
 */
public class UserstableSelection extends AbstractSelection<UserstableSelection> {
    @Override
    protected Uri baseUri() {
        return UserstableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code UserstableCursor} object, which is positioned before the first entry, or null.
     */
    public UserstableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new UserstableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public UserstableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code UserstableCursor} object, which is positioned before the first entry, or null.
     */
    public UserstableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new UserstableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public UserstableCursor query(Context context) {
        return query(context, null);
    }


    public UserstableSelection id(long... value) {
        addEquals("userstable." + UserstableColumns._ID, toObjectArray(value));
        return this;
    }

    public UserstableSelection idNot(long... value) {
        addNotEquals("userstable." + UserstableColumns._ID, toObjectArray(value));
        return this;
    }

    public UserstableSelection orderById(boolean desc) {
        orderBy("userstable." + UserstableColumns._ID, desc);
        return this;
    }

    public UserstableSelection orderById() {
        return orderById(false);
    }

    public UserstableSelection userid(String... value) {
        addEquals(UserstableColumns.USERID, value);
        return this;
    }

    public UserstableSelection useridNot(String... value) {
        addNotEquals(UserstableColumns.USERID, value);
        return this;
    }

    public UserstableSelection useridLike(String... value) {
        addLike(UserstableColumns.USERID, value);
        return this;
    }

    public UserstableSelection useridContains(String... value) {
        addContains(UserstableColumns.USERID, value);
        return this;
    }

    public UserstableSelection useridStartsWith(String... value) {
        addStartsWith(UserstableColumns.USERID, value);
        return this;
    }

    public UserstableSelection useridEndsWith(String... value) {
        addEndsWith(UserstableColumns.USERID, value);
        return this;
    }

    public UserstableSelection orderByUserid(boolean desc) {
        orderBy(UserstableColumns.USERID, desc);
        return this;
    }

    public UserstableSelection orderByUserid() {
        orderBy(UserstableColumns.USERID, false);
        return this;
    }

    public UserstableSelection firstname(String... value) {
        addEquals(UserstableColumns.FIRSTNAME, value);
        return this;
    }

    public UserstableSelection firstnameNot(String... value) {
        addNotEquals(UserstableColumns.FIRSTNAME, value);
        return this;
    }

    public UserstableSelection firstnameLike(String... value) {
        addLike(UserstableColumns.FIRSTNAME, value);
        return this;
    }

    public UserstableSelection firstnameContains(String... value) {
        addContains(UserstableColumns.FIRSTNAME, value);
        return this;
    }

    public UserstableSelection firstnameStartsWith(String... value) {
        addStartsWith(UserstableColumns.FIRSTNAME, value);
        return this;
    }

    public UserstableSelection firstnameEndsWith(String... value) {
        addEndsWith(UserstableColumns.FIRSTNAME, value);
        return this;
    }

    public UserstableSelection orderByFirstname(boolean desc) {
        orderBy(UserstableColumns.FIRSTNAME, desc);
        return this;
    }

    public UserstableSelection orderByFirstname() {
        orderBy(UserstableColumns.FIRSTNAME, false);
        return this;
    }

    public UserstableSelection lastname(String... value) {
        addEquals(UserstableColumns.LASTNAME, value);
        return this;
    }

    public UserstableSelection lastnameNot(String... value) {
        addNotEquals(UserstableColumns.LASTNAME, value);
        return this;
    }

    public UserstableSelection lastnameLike(String... value) {
        addLike(UserstableColumns.LASTNAME, value);
        return this;
    }

    public UserstableSelection lastnameContains(String... value) {
        addContains(UserstableColumns.LASTNAME, value);
        return this;
    }

    public UserstableSelection lastnameStartsWith(String... value) {
        addStartsWith(UserstableColumns.LASTNAME, value);
        return this;
    }

    public UserstableSelection lastnameEndsWith(String... value) {
        addEndsWith(UserstableColumns.LASTNAME, value);
        return this;
    }

    public UserstableSelection orderByLastname(boolean desc) {
        orderBy(UserstableColumns.LASTNAME, desc);
        return this;
    }

    public UserstableSelection orderByLastname() {
        orderBy(UserstableColumns.LASTNAME, false);
        return this;
    }

    public UserstableSelection phonenumber(String... value) {
        addEquals(UserstableColumns.PHONENUMBER, value);
        return this;
    }

    public UserstableSelection phonenumberNot(String... value) {
        addNotEquals(UserstableColumns.PHONENUMBER, value);
        return this;
    }

    public UserstableSelection phonenumberLike(String... value) {
        addLike(UserstableColumns.PHONENUMBER, value);
        return this;
    }

    public UserstableSelection phonenumberContains(String... value) {
        addContains(UserstableColumns.PHONENUMBER, value);
        return this;
    }

    public UserstableSelection phonenumberStartsWith(String... value) {
        addStartsWith(UserstableColumns.PHONENUMBER, value);
        return this;
    }

    public UserstableSelection phonenumberEndsWith(String... value) {
        addEndsWith(UserstableColumns.PHONENUMBER, value);
        return this;
    }

    public UserstableSelection orderByPhonenumber(boolean desc) {
        orderBy(UserstableColumns.PHONENUMBER, desc);
        return this;
    }

    public UserstableSelection orderByPhonenumber() {
        orderBy(UserstableColumns.PHONENUMBER, false);
        return this;
    }

    public UserstableSelection createdAt(Date... value) {
        addEquals(UserstableColumns.CREATED_AT, value);
        return this;
    }

    public UserstableSelection createdAtNot(Date... value) {
        addNotEquals(UserstableColumns.CREATED_AT, value);
        return this;
    }

    public UserstableSelection createdAt(Long... value) {
        addEquals(UserstableColumns.CREATED_AT, value);
        return this;
    }

    public UserstableSelection createdAtAfter(Date value) {
        addGreaterThan(UserstableColumns.CREATED_AT, value);
        return this;
    }

    public UserstableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(UserstableColumns.CREATED_AT, value);
        return this;
    }

    public UserstableSelection createdAtBefore(Date value) {
        addLessThan(UserstableColumns.CREATED_AT, value);
        return this;
    }

    public UserstableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(UserstableColumns.CREATED_AT, value);
        return this;
    }

    public UserstableSelection orderByCreatedAt(boolean desc) {
        orderBy(UserstableColumns.CREATED_AT, desc);
        return this;
    }

    public UserstableSelection orderByCreatedAt() {
        orderBy(UserstableColumns.CREATED_AT, false);
        return this;
    }

    public UserstableSelection numberPlate(String... value) {
        addEquals(UserstableColumns.NUMBER_PLATE, value);
        return this;
    }

    public UserstableSelection numberPlateNot(String... value) {
        addNotEquals(UserstableColumns.NUMBER_PLATE, value);
        return this;
    }

    public UserstableSelection numberPlateLike(String... value) {
        addLike(UserstableColumns.NUMBER_PLATE, value);
        return this;
    }

    public UserstableSelection numberPlateContains(String... value) {
        addContains(UserstableColumns.NUMBER_PLATE, value);
        return this;
    }

    public UserstableSelection numberPlateStartsWith(String... value) {
        addStartsWith(UserstableColumns.NUMBER_PLATE, value);
        return this;
    }

    public UserstableSelection numberPlateEndsWith(String... value) {
        addEndsWith(UserstableColumns.NUMBER_PLATE, value);
        return this;
    }

    public UserstableSelection orderByNumberPlate(boolean desc) {
        orderBy(UserstableColumns.NUMBER_PLATE, desc);
        return this;
    }

    public UserstableSelection orderByNumberPlate() {
        orderBy(UserstableColumns.NUMBER_PLATE, false);
        return this;
    }

    public UserstableSelection role(String... value) {
        addEquals(UserstableColumns.ROLE, value);
        return this;
    }

    public UserstableSelection roleNot(String... value) {
        addNotEquals(UserstableColumns.ROLE, value);
        return this;
    }

    public UserstableSelection roleLike(String... value) {
        addLike(UserstableColumns.ROLE, value);
        return this;
    }

    public UserstableSelection roleContains(String... value) {
        addContains(UserstableColumns.ROLE, value);
        return this;
    }

    public UserstableSelection roleStartsWith(String... value) {
        addStartsWith(UserstableColumns.ROLE, value);
        return this;
    }

    public UserstableSelection roleEndsWith(String... value) {
        addEndsWith(UserstableColumns.ROLE, value);
        return this;
    }

    public UserstableSelection orderByRole(boolean desc) {
        orderBy(UserstableColumns.ROLE, desc);
        return this;
    }

    public UserstableSelection orderByRole() {
        orderBy(UserstableColumns.ROLE, false);
        return this;
    }

    public UserstableSelection midwifeid(String... value) {
        addEquals(UserstableColumns.MIDWIFEID, value);
        return this;
    }

    public UserstableSelection midwifeidNot(String... value) {
        addNotEquals(UserstableColumns.MIDWIFEID, value);
        return this;
    }

    public UserstableSelection midwifeidLike(String... value) {
        addLike(UserstableColumns.MIDWIFEID, value);
        return this;
    }

    public UserstableSelection midwifeidContains(String... value) {
        addContains(UserstableColumns.MIDWIFEID, value);
        return this;
    }

    public UserstableSelection midwifeidStartsWith(String... value) {
        addStartsWith(UserstableColumns.MIDWIFEID, value);
        return this;
    }

    public UserstableSelection midwifeidEndsWith(String... value) {
        addEndsWith(UserstableColumns.MIDWIFEID, value);
        return this;
    }

    public UserstableSelection orderByMidwifeid(boolean desc) {
        orderBy(UserstableColumns.MIDWIFEID, desc);
        return this;
    }

    public UserstableSelection orderByMidwifeid() {
        orderBy(UserstableColumns.MIDWIFEID, false);
        return this;
    }

    public UserstableSelection village(String... value) {
        addEquals(UserstableColumns.VILLAGE, value);
        return this;
    }

    public UserstableSelection villageNot(String... value) {
        addNotEquals(UserstableColumns.VILLAGE, value);
        return this;
    }

    public UserstableSelection villageLike(String... value) {
        addLike(UserstableColumns.VILLAGE, value);
        return this;
    }

    public UserstableSelection villageContains(String... value) {
        addContains(UserstableColumns.VILLAGE, value);
        return this;
    }

    public UserstableSelection villageStartsWith(String... value) {
        addStartsWith(UserstableColumns.VILLAGE, value);
        return this;
    }

    public UserstableSelection villageEndsWith(String... value) {
        addEndsWith(UserstableColumns.VILLAGE, value);
        return this;
    }

    public UserstableSelection orderByVillage(boolean desc) {
        orderBy(UserstableColumns.VILLAGE, desc);
        return this;
    }

    public UserstableSelection orderByVillage() {
        orderBy(UserstableColumns.VILLAGE, false);
        return this;
    }
}
