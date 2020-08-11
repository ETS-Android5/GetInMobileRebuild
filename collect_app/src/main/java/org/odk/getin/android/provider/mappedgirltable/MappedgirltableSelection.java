package org.odk.getin.android.provider.mappedgirltable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import org.odk.getin.android.provider.base.AbstractSelection;

/**
 * Selection for the {@code mappedgirltable} table.
 */
public class MappedgirltableSelection extends AbstractSelection<MappedgirltableSelection> {
    @Override
    protected Uri baseUri() {
        return MappedgirltableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MappedgirltableCursor} object, which is positioned before the first entry, or null.
     */
    public MappedgirltableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MappedgirltableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MappedgirltableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MappedgirltableCursor} object, which is positioned before the first entry, or null.
     */
    public MappedgirltableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MappedgirltableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MappedgirltableCursor query(Context context) {
        return query(context, null);
    }


    public MappedgirltableSelection id(long... value) {
        addEquals("mappedgirltable." + MappedgirltableColumns._ID, toObjectArray(value));
        return this;
    }

    public MappedgirltableSelection idNot(long... value) {
        addNotEquals("mappedgirltable." + MappedgirltableColumns._ID, toObjectArray(value));
        return this;
    }

    public MappedgirltableSelection orderById(boolean desc) {
        orderBy("mappedgirltable." + MappedgirltableColumns._ID, desc);
        return this;
    }

    public MappedgirltableSelection orderById() {
        return orderById(false);
    }

    public MappedgirltableSelection serverid(String... value) {
        addEquals(MappedgirltableColumns.SERVERID, value);
        return this;
    }

    public MappedgirltableSelection serveridNot(String... value) {
        addNotEquals(MappedgirltableColumns.SERVERID, value);
        return this;
    }

    public MappedgirltableSelection serveridLike(String... value) {
        addLike(MappedgirltableColumns.SERVERID, value);
        return this;
    }

    public MappedgirltableSelection serveridContains(String... value) {
        addContains(MappedgirltableColumns.SERVERID, value);
        return this;
    }

    public MappedgirltableSelection serveridStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.SERVERID, value);
        return this;
    }

    public MappedgirltableSelection serveridEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.SERVERID, value);
        return this;
    }

    public MappedgirltableSelection orderByServerid(boolean desc) {
        orderBy(MappedgirltableColumns.SERVERID, desc);
        return this;
    }

    public MappedgirltableSelection orderByServerid() {
        orderBy(MappedgirltableColumns.SERVERID, false);
        return this;
    }

    public MappedgirltableSelection firstname(String... value) {
        addEquals(MappedgirltableColumns.FIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection firstnameNot(String... value) {
        addNotEquals(MappedgirltableColumns.FIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection firstnameLike(String... value) {
        addLike(MappedgirltableColumns.FIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection firstnameContains(String... value) {
        addContains(MappedgirltableColumns.FIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection firstnameStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.FIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection firstnameEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.FIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection orderByFirstname(boolean desc) {
        orderBy(MappedgirltableColumns.FIRSTNAME, desc);
        return this;
    }

    public MappedgirltableSelection orderByFirstname() {
        orderBy(MappedgirltableColumns.FIRSTNAME, false);
        return this;
    }

    public MappedgirltableSelection lastname(String... value) {
        addEquals(MappedgirltableColumns.LASTNAME, value);
        return this;
    }

    public MappedgirltableSelection lastnameNot(String... value) {
        addNotEquals(MappedgirltableColumns.LASTNAME, value);
        return this;
    }

    public MappedgirltableSelection lastnameLike(String... value) {
        addLike(MappedgirltableColumns.LASTNAME, value);
        return this;
    }

    public MappedgirltableSelection lastnameContains(String... value) {
        addContains(MappedgirltableColumns.LASTNAME, value);
        return this;
    }

    public MappedgirltableSelection lastnameStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.LASTNAME, value);
        return this;
    }

    public MappedgirltableSelection lastnameEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.LASTNAME, value);
        return this;
    }

    public MappedgirltableSelection orderByLastname(boolean desc) {
        orderBy(MappedgirltableColumns.LASTNAME, desc);
        return this;
    }

    public MappedgirltableSelection orderByLastname() {
        orderBy(MappedgirltableColumns.LASTNAME, false);
        return this;
    }

    public MappedgirltableSelection phonenumber(String... value) {
        addEquals(MappedgirltableColumns.PHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection phonenumberNot(String... value) {
        addNotEquals(MappedgirltableColumns.PHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection phonenumberLike(String... value) {
        addLike(MappedgirltableColumns.PHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection phonenumberContains(String... value) {
        addContains(MappedgirltableColumns.PHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection phonenumberStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.PHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection phonenumberEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.PHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection orderByPhonenumber(boolean desc) {
        orderBy(MappedgirltableColumns.PHONENUMBER, desc);
        return this;
    }

    public MappedgirltableSelection orderByPhonenumber() {
        orderBy(MappedgirltableColumns.PHONENUMBER, false);
        return this;
    }

    public MappedgirltableSelection nextofkinlastname(String... value) {
        addEquals(MappedgirltableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinlastnameNot(String... value) {
        addNotEquals(MappedgirltableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinlastnameLike(String... value) {
        addLike(MappedgirltableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinlastnameContains(String... value) {
        addContains(MappedgirltableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinlastnameStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinlastnameEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public MappedgirltableSelection orderByNextofkinlastname(boolean desc) {
        orderBy(MappedgirltableColumns.NEXTOFKINLASTNAME, desc);
        return this;
    }

    public MappedgirltableSelection orderByNextofkinlastname() {
        orderBy(MappedgirltableColumns.NEXTOFKINLASTNAME, false);
        return this;
    }

    public MappedgirltableSelection nextofkinfirstname(String... value) {
        addEquals(MappedgirltableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinfirstnameNot(String... value) {
        addNotEquals(MappedgirltableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinfirstnameLike(String... value) {
        addLike(MappedgirltableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinfirstnameContains(String... value) {
        addContains(MappedgirltableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinfirstnameStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection nextofkinfirstnameEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public MappedgirltableSelection orderByNextofkinfirstname(boolean desc) {
        orderBy(MappedgirltableColumns.NEXTOFKINFIRSTNAME, desc);
        return this;
    }

    public MappedgirltableSelection orderByNextofkinfirstname() {
        orderBy(MappedgirltableColumns.NEXTOFKINFIRSTNAME, false);
        return this;
    }

    public MappedgirltableSelection nextofkinphonenumber(String... value) {
        addEquals(MappedgirltableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection nextofkinphonenumberNot(String... value) {
        addNotEquals(MappedgirltableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection nextofkinphonenumberLike(String... value) {
        addLike(MappedgirltableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection nextofkinphonenumberContains(String... value) {
        addContains(MappedgirltableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection nextofkinphonenumberStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection nextofkinphonenumberEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public MappedgirltableSelection orderByNextofkinphonenumber(boolean desc) {
        orderBy(MappedgirltableColumns.NEXTOFKINPHONENUMBER, desc);
        return this;
    }

    public MappedgirltableSelection orderByNextofkinphonenumber() {
        orderBy(MappedgirltableColumns.NEXTOFKINPHONENUMBER, false);
        return this;
    }

    public MappedgirltableSelection educationlevel(String... value) {
        addEquals(MappedgirltableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public MappedgirltableSelection educationlevelNot(String... value) {
        addNotEquals(MappedgirltableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public MappedgirltableSelection educationlevelLike(String... value) {
        addLike(MappedgirltableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public MappedgirltableSelection educationlevelContains(String... value) {
        addContains(MappedgirltableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public MappedgirltableSelection educationlevelStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public MappedgirltableSelection educationlevelEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public MappedgirltableSelection orderByEducationlevel(boolean desc) {
        orderBy(MappedgirltableColumns.EDUCATIONLEVEL, desc);
        return this;
    }

    public MappedgirltableSelection orderByEducationlevel() {
        orderBy(MappedgirltableColumns.EDUCATIONLEVEL, false);
        return this;
    }

    public MappedgirltableSelection maritalstatus(String... value) {
        addEquals(MappedgirltableColumns.MARITALSTATUS, value);
        return this;
    }

    public MappedgirltableSelection maritalstatusNot(String... value) {
        addNotEquals(MappedgirltableColumns.MARITALSTATUS, value);
        return this;
    }

    public MappedgirltableSelection maritalstatusLike(String... value) {
        addLike(MappedgirltableColumns.MARITALSTATUS, value);
        return this;
    }

    public MappedgirltableSelection maritalstatusContains(String... value) {
        addContains(MappedgirltableColumns.MARITALSTATUS, value);
        return this;
    }

    public MappedgirltableSelection maritalstatusStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.MARITALSTATUS, value);
        return this;
    }

    public MappedgirltableSelection maritalstatusEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.MARITALSTATUS, value);
        return this;
    }

    public MappedgirltableSelection orderByMaritalstatus(boolean desc) {
        orderBy(MappedgirltableColumns.MARITALSTATUS, desc);
        return this;
    }

    public MappedgirltableSelection orderByMaritalstatus() {
        orderBy(MappedgirltableColumns.MARITALSTATUS, false);
        return this;
    }

    public MappedgirltableSelection age(Integer... value) {
        addEquals(MappedgirltableColumns.AGE, value);
        return this;
    }

    public MappedgirltableSelection ageNot(Integer... value) {
        addNotEquals(MappedgirltableColumns.AGE, value);
        return this;
    }

    public MappedgirltableSelection ageGt(int value) {
        addGreaterThan(MappedgirltableColumns.AGE, value);
        return this;
    }

    public MappedgirltableSelection ageGtEq(int value) {
        addGreaterThanOrEquals(MappedgirltableColumns.AGE, value);
        return this;
    }

    public MappedgirltableSelection ageLt(int value) {
        addLessThan(MappedgirltableColumns.AGE, value);
        return this;
    }

    public MappedgirltableSelection ageLtEq(int value) {
        addLessThanOrEquals(MappedgirltableColumns.AGE, value);
        return this;
    }

    public MappedgirltableSelection orderByAge(boolean desc) {
        orderBy(MappedgirltableColumns.AGE, desc);
        return this;
    }

    public MappedgirltableSelection orderByAge() {
        orderBy(MappedgirltableColumns.AGE, false);
        return this;
    }

    public MappedgirltableSelection user(String... value) {
        addEquals(MappedgirltableColumns.USER, value);
        return this;
    }

    public MappedgirltableSelection userNot(String... value) {
        addNotEquals(MappedgirltableColumns.USER, value);
        return this;
    }

    public MappedgirltableSelection userLike(String... value) {
        addLike(MappedgirltableColumns.USER, value);
        return this;
    }

    public MappedgirltableSelection userContains(String... value) {
        addContains(MappedgirltableColumns.USER, value);
        return this;
    }

    public MappedgirltableSelection userStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.USER, value);
        return this;
    }

    public MappedgirltableSelection userEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.USER, value);
        return this;
    }

    public MappedgirltableSelection orderByUser(boolean desc) {
        orderBy(MappedgirltableColumns.USER, desc);
        return this;
    }

    public MappedgirltableSelection orderByUser() {
        orderBy(MappedgirltableColumns.USER, false);
        return this;
    }

    public MappedgirltableSelection createdAt(Date... value) {
        addEquals(MappedgirltableColumns.CREATED_AT, value);
        return this;
    }

    public MappedgirltableSelection createdAtNot(Date... value) {
        addNotEquals(MappedgirltableColumns.CREATED_AT, value);
        return this;
    }

    public MappedgirltableSelection createdAt(Long... value) {
        addEquals(MappedgirltableColumns.CREATED_AT, value);
        return this;
    }

    public MappedgirltableSelection createdAtAfter(Date value) {
        addGreaterThan(MappedgirltableColumns.CREATED_AT, value);
        return this;
    }

    public MappedgirltableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(MappedgirltableColumns.CREATED_AT, value);
        return this;
    }

    public MappedgirltableSelection createdAtBefore(Date value) {
        addLessThan(MappedgirltableColumns.CREATED_AT, value);
        return this;
    }

    public MappedgirltableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(MappedgirltableColumns.CREATED_AT, value);
        return this;
    }

    public MappedgirltableSelection orderByCreatedAt(boolean desc) {
        orderBy(MappedgirltableColumns.CREATED_AT, desc);
        return this;
    }

    public MappedgirltableSelection orderByCreatedAt() {
        orderBy(MappedgirltableColumns.CREATED_AT, false);
        return this;
    }

    public MappedgirltableSelection completedAllVisits(Boolean value) {
        addEquals(MappedgirltableColumns.COMPLETED_ALL_VISITS, toObjectArray(value));
        return this;
    }

    public MappedgirltableSelection orderByCompletedAllVisits(boolean desc) {
        orderBy(MappedgirltableColumns.COMPLETED_ALL_VISITS, desc);
        return this;
    }

    public MappedgirltableSelection orderByCompletedAllVisits() {
        orderBy(MappedgirltableColumns.COMPLETED_ALL_VISITS, false);
        return this;
    }

    public MappedgirltableSelection pendingVisits(Integer... value) {
        addEquals(MappedgirltableColumns.PENDING_VISITS, value);
        return this;
    }

    public MappedgirltableSelection pendingVisitsNot(Integer... value) {
        addNotEquals(MappedgirltableColumns.PENDING_VISITS, value);
        return this;
    }

    public MappedgirltableSelection pendingVisitsGt(int value) {
        addGreaterThan(MappedgirltableColumns.PENDING_VISITS, value);
        return this;
    }

    public MappedgirltableSelection pendingVisitsGtEq(int value) {
        addGreaterThanOrEquals(MappedgirltableColumns.PENDING_VISITS, value);
        return this;
    }

    public MappedgirltableSelection pendingVisitsLt(int value) {
        addLessThan(MappedgirltableColumns.PENDING_VISITS, value);
        return this;
    }

    public MappedgirltableSelection pendingVisitsLtEq(int value) {
        addLessThanOrEquals(MappedgirltableColumns.PENDING_VISITS, value);
        return this;
    }

    public MappedgirltableSelection orderByPendingVisits(boolean desc) {
        orderBy(MappedgirltableColumns.PENDING_VISITS, desc);
        return this;
    }

    public MappedgirltableSelection orderByPendingVisits() {
        orderBy(MappedgirltableColumns.PENDING_VISITS, false);
        return this;
    }

    public MappedgirltableSelection missedVisits(Integer... value) {
        addEquals(MappedgirltableColumns.MISSED_VISITS, value);
        return this;
    }

    public MappedgirltableSelection missedVisitsNot(Integer... value) {
        addNotEquals(MappedgirltableColumns.MISSED_VISITS, value);
        return this;
    }

    public MappedgirltableSelection missedVisitsGt(int value) {
        addGreaterThan(MappedgirltableColumns.MISSED_VISITS, value);
        return this;
    }

    public MappedgirltableSelection missedVisitsGtEq(int value) {
        addGreaterThanOrEquals(MappedgirltableColumns.MISSED_VISITS, value);
        return this;
    }

    public MappedgirltableSelection missedVisitsLt(int value) {
        addLessThan(MappedgirltableColumns.MISSED_VISITS, value);
        return this;
    }

    public MappedgirltableSelection missedVisitsLtEq(int value) {
        addLessThanOrEquals(MappedgirltableColumns.MISSED_VISITS, value);
        return this;
    }

    public MappedgirltableSelection orderByMissedVisits(boolean desc) {
        orderBy(MappedgirltableColumns.MISSED_VISITS, desc);
        return this;
    }

    public MappedgirltableSelection orderByMissedVisits() {
        orderBy(MappedgirltableColumns.MISSED_VISITS, false);
        return this;
    }

    public MappedgirltableSelection village(String... value) {
        addEquals(MappedgirltableColumns.VILLAGE, value);
        return this;
    }

    public MappedgirltableSelection villageNot(String... value) {
        addNotEquals(MappedgirltableColumns.VILLAGE, value);
        return this;
    }

    public MappedgirltableSelection villageLike(String... value) {
        addLike(MappedgirltableColumns.VILLAGE, value);
        return this;
    }

    public MappedgirltableSelection villageContains(String... value) {
        addContains(MappedgirltableColumns.VILLAGE, value);
        return this;
    }

    public MappedgirltableSelection villageStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.VILLAGE, value);
        return this;
    }

    public MappedgirltableSelection villageEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.VILLAGE, value);
        return this;
    }

    public MappedgirltableSelection orderByVillage(boolean desc) {
        orderBy(MappedgirltableColumns.VILLAGE, desc);
        return this;
    }

    public MappedgirltableSelection orderByVillage() {
        orderBy(MappedgirltableColumns.VILLAGE, false);
        return this;
    }

    public MappedgirltableSelection voucherNumber(String... value) {
        addEquals(MappedgirltableColumns.VOUCHER_NUMBER, value);
        return this;
    }

    public MappedgirltableSelection voucherNumberNot(String... value) {
        addNotEquals(MappedgirltableColumns.VOUCHER_NUMBER, value);
        return this;
    }

    public MappedgirltableSelection voucherNumberLike(String... value) {
        addLike(MappedgirltableColumns.VOUCHER_NUMBER, value);
        return this;
    }

    public MappedgirltableSelection voucherNumberContains(String... value) {
        addContains(MappedgirltableColumns.VOUCHER_NUMBER, value);
        return this;
    }

    public MappedgirltableSelection voucherNumberStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.VOUCHER_NUMBER, value);
        return this;
    }

    public MappedgirltableSelection voucherNumberEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.VOUCHER_NUMBER, value);
        return this;
    }

    public MappedgirltableSelection orderByVoucherNumber(boolean desc) {
        orderBy(MappedgirltableColumns.VOUCHER_NUMBER, desc);
        return this;
    }

    public MappedgirltableSelection orderByVoucherNumber() {
        orderBy(MappedgirltableColumns.VOUCHER_NUMBER, false);
        return this;
    }

    public MappedgirltableSelection servicesReceived(String... value) {
        addEquals(MappedgirltableColumns.SERVICES_RECEIVED, value);
        return this;
    }

    public MappedgirltableSelection servicesReceivedNot(String... value) {
        addNotEquals(MappedgirltableColumns.SERVICES_RECEIVED, value);
        return this;
    }

    public MappedgirltableSelection servicesReceivedLike(String... value) {
        addLike(MappedgirltableColumns.SERVICES_RECEIVED, value);
        return this;
    }

    public MappedgirltableSelection servicesReceivedContains(String... value) {
        addContains(MappedgirltableColumns.SERVICES_RECEIVED, value);
        return this;
    }

    public MappedgirltableSelection servicesReceivedStartsWith(String... value) {
        addStartsWith(MappedgirltableColumns.SERVICES_RECEIVED, value);
        return this;
    }

    public MappedgirltableSelection servicesReceivedEndsWith(String... value) {
        addEndsWith(MappedgirltableColumns.SERVICES_RECEIVED, value);
        return this;
    }

    public MappedgirltableSelection orderByServicesReceived(boolean desc) {
        orderBy(MappedgirltableColumns.SERVICES_RECEIVED, desc);
        return this;
    }

    public MappedgirltableSelection orderByServicesReceived() {
        orderBy(MappedgirltableColumns.SERVICES_RECEIVED, false);
        return this;
    }
}
