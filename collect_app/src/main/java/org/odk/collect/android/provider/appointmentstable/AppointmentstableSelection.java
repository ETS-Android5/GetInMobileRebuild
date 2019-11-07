package org.odk.collect.android.provider.appointmentstable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import org.odk.collect.android.provider.base.AbstractSelection;

/**
 * Selection for the {@code appointmentstable} table.
 */
public class AppointmentstableSelection extends AbstractSelection<AppointmentstableSelection> {
    @Override
    protected Uri baseUri() {
        return AppointmentstableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code AppointmentstableCursor} object, which is positioned before the first entry, or null.
     */
    public AppointmentstableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new AppointmentstableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public AppointmentstableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code AppointmentstableCursor} object, which is positioned before the first entry, or null.
     */
    public AppointmentstableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new AppointmentstableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public AppointmentstableCursor query(Context context) {
        return query(context, null);
    }


    public AppointmentstableSelection id(long... value) {
        addEquals("appointmentstable." + AppointmentstableColumns._ID, toObjectArray(value));
        return this;
    }

    public AppointmentstableSelection idNot(long... value) {
        addNotEquals("appointmentstable." + AppointmentstableColumns._ID, toObjectArray(value));
        return this;
    }

    public AppointmentstableSelection orderById(boolean desc) {
        orderBy("appointmentstable." + AppointmentstableColumns._ID, desc);
        return this;
    }

    public AppointmentstableSelection orderById() {
        return orderById(false);
    }

    public AppointmentstableSelection serverid(String... value) {
        addEquals(AppointmentstableColumns.SERVERID, value);
        return this;
    }

    public AppointmentstableSelection serveridNot(String... value) {
        addNotEquals(AppointmentstableColumns.SERVERID, value);
        return this;
    }

    public AppointmentstableSelection serveridLike(String... value) {
        addLike(AppointmentstableColumns.SERVERID, value);
        return this;
    }

    public AppointmentstableSelection serveridContains(String... value) {
        addContains(AppointmentstableColumns.SERVERID, value);
        return this;
    }

    public AppointmentstableSelection serveridStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.SERVERID, value);
        return this;
    }

    public AppointmentstableSelection serveridEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.SERVERID, value);
        return this;
    }

    public AppointmentstableSelection orderByServerid(boolean desc) {
        orderBy(AppointmentstableColumns.SERVERID, desc);
        return this;
    }

    public AppointmentstableSelection orderByServerid() {
        orderBy(AppointmentstableColumns.SERVERID, false);
        return this;
    }

    public AppointmentstableSelection firstname(String... value) {
        addEquals(AppointmentstableColumns.FIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection firstnameNot(String... value) {
        addNotEquals(AppointmentstableColumns.FIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection firstnameLike(String... value) {
        addLike(AppointmentstableColumns.FIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection firstnameContains(String... value) {
        addContains(AppointmentstableColumns.FIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection firstnameStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.FIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection firstnameEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.FIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection orderByFirstname(boolean desc) {
        orderBy(AppointmentstableColumns.FIRSTNAME, desc);
        return this;
    }

    public AppointmentstableSelection orderByFirstname() {
        orderBy(AppointmentstableColumns.FIRSTNAME, false);
        return this;
    }

    public AppointmentstableSelection lastname(String... value) {
        addEquals(AppointmentstableColumns.LASTNAME, value);
        return this;
    }

    public AppointmentstableSelection lastnameNot(String... value) {
        addNotEquals(AppointmentstableColumns.LASTNAME, value);
        return this;
    }

    public AppointmentstableSelection lastnameLike(String... value) {
        addLike(AppointmentstableColumns.LASTNAME, value);
        return this;
    }

    public AppointmentstableSelection lastnameContains(String... value) {
        addContains(AppointmentstableColumns.LASTNAME, value);
        return this;
    }

    public AppointmentstableSelection lastnameStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.LASTNAME, value);
        return this;
    }

    public AppointmentstableSelection lastnameEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.LASTNAME, value);
        return this;
    }

    public AppointmentstableSelection orderByLastname(boolean desc) {
        orderBy(AppointmentstableColumns.LASTNAME, desc);
        return this;
    }

    public AppointmentstableSelection orderByLastname() {
        orderBy(AppointmentstableColumns.LASTNAME, false);
        return this;
    }

    public AppointmentstableSelection phonenumber(String... value) {
        addEquals(AppointmentstableColumns.PHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection phonenumberNot(String... value) {
        addNotEquals(AppointmentstableColumns.PHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection phonenumberLike(String... value) {
        addLike(AppointmentstableColumns.PHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection phonenumberContains(String... value) {
        addContains(AppointmentstableColumns.PHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection phonenumberStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.PHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection phonenumberEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.PHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection orderByPhonenumber(boolean desc) {
        orderBy(AppointmentstableColumns.PHONENUMBER, desc);
        return this;
    }

    public AppointmentstableSelection orderByPhonenumber() {
        orderBy(AppointmentstableColumns.PHONENUMBER, false);
        return this;
    }

    public AppointmentstableSelection nextofkinlastname(String... value) {
        addEquals(AppointmentstableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinlastnameNot(String... value) {
        addNotEquals(AppointmentstableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinlastnameLike(String... value) {
        addLike(AppointmentstableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinlastnameContains(String... value) {
        addContains(AppointmentstableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinlastnameStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinlastnameEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.NEXTOFKINLASTNAME, value);
        return this;
    }

    public AppointmentstableSelection orderByNextofkinlastname(boolean desc) {
        orderBy(AppointmentstableColumns.NEXTOFKINLASTNAME, desc);
        return this;
    }

    public AppointmentstableSelection orderByNextofkinlastname() {
        orderBy(AppointmentstableColumns.NEXTOFKINLASTNAME, false);
        return this;
    }

    public AppointmentstableSelection nextofkinfirstname(String... value) {
        addEquals(AppointmentstableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinfirstnameNot(String... value) {
        addNotEquals(AppointmentstableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinfirstnameLike(String... value) {
        addLike(AppointmentstableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinfirstnameContains(String... value) {
        addContains(AppointmentstableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinfirstnameStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection nextofkinfirstnameEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.NEXTOFKINFIRSTNAME, value);
        return this;
    }

    public AppointmentstableSelection orderByNextofkinfirstname(boolean desc) {
        orderBy(AppointmentstableColumns.NEXTOFKINFIRSTNAME, desc);
        return this;
    }

    public AppointmentstableSelection orderByNextofkinfirstname() {
        orderBy(AppointmentstableColumns.NEXTOFKINFIRSTNAME, false);
        return this;
    }

    public AppointmentstableSelection nextofkinphonenumber(String... value) {
        addEquals(AppointmentstableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection nextofkinphonenumberNot(String... value) {
        addNotEquals(AppointmentstableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection nextofkinphonenumberLike(String... value) {
        addLike(AppointmentstableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection nextofkinphonenumberContains(String... value) {
        addContains(AppointmentstableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection nextofkinphonenumberStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection nextofkinphonenumberEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.NEXTOFKINPHONENUMBER, value);
        return this;
    }

    public AppointmentstableSelection orderByNextofkinphonenumber(boolean desc) {
        orderBy(AppointmentstableColumns.NEXTOFKINPHONENUMBER, desc);
        return this;
    }

    public AppointmentstableSelection orderByNextofkinphonenumber() {
        orderBy(AppointmentstableColumns.NEXTOFKINPHONENUMBER, false);
        return this;
    }

    public AppointmentstableSelection educationlevel(String... value) {
        addEquals(AppointmentstableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public AppointmentstableSelection educationlevelNot(String... value) {
        addNotEquals(AppointmentstableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public AppointmentstableSelection educationlevelLike(String... value) {
        addLike(AppointmentstableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public AppointmentstableSelection educationlevelContains(String... value) {
        addContains(AppointmentstableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public AppointmentstableSelection educationlevelStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public AppointmentstableSelection educationlevelEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.EDUCATIONLEVEL, value);
        return this;
    }

    public AppointmentstableSelection orderByEducationlevel(boolean desc) {
        orderBy(AppointmentstableColumns.EDUCATIONLEVEL, desc);
        return this;
    }

    public AppointmentstableSelection orderByEducationlevel() {
        orderBy(AppointmentstableColumns.EDUCATIONLEVEL, false);
        return this;
    }

    public AppointmentstableSelection maritalstatus(String... value) {
        addEquals(AppointmentstableColumns.MARITALSTATUS, value);
        return this;
    }

    public AppointmentstableSelection maritalstatusNot(String... value) {
        addNotEquals(AppointmentstableColumns.MARITALSTATUS, value);
        return this;
    }

    public AppointmentstableSelection maritalstatusLike(String... value) {
        addLike(AppointmentstableColumns.MARITALSTATUS, value);
        return this;
    }

    public AppointmentstableSelection maritalstatusContains(String... value) {
        addContains(AppointmentstableColumns.MARITALSTATUS, value);
        return this;
    }

    public AppointmentstableSelection maritalstatusStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.MARITALSTATUS, value);
        return this;
    }

    public AppointmentstableSelection maritalstatusEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.MARITALSTATUS, value);
        return this;
    }

    public AppointmentstableSelection orderByMaritalstatus(boolean desc) {
        orderBy(AppointmentstableColumns.MARITALSTATUS, desc);
        return this;
    }

    public AppointmentstableSelection orderByMaritalstatus() {
        orderBy(AppointmentstableColumns.MARITALSTATUS, false);
        return this;
    }

    public AppointmentstableSelection age(Integer... value) {
        addEquals(AppointmentstableColumns.AGE, value);
        return this;
    }

    public AppointmentstableSelection ageNot(Integer... value) {
        addNotEquals(AppointmentstableColumns.AGE, value);
        return this;
    }

    public AppointmentstableSelection ageGt(int value) {
        addGreaterThan(AppointmentstableColumns.AGE, value);
        return this;
    }

    public AppointmentstableSelection ageGtEq(int value) {
        addGreaterThanOrEquals(AppointmentstableColumns.AGE, value);
        return this;
    }

    public AppointmentstableSelection ageLt(int value) {
        addLessThan(AppointmentstableColumns.AGE, value);
        return this;
    }

    public AppointmentstableSelection ageLtEq(int value) {
        addLessThanOrEquals(AppointmentstableColumns.AGE, value);
        return this;
    }

    public AppointmentstableSelection orderByAge(boolean desc) {
        orderBy(AppointmentstableColumns.AGE, desc);
        return this;
    }

    public AppointmentstableSelection orderByAge() {
        orderBy(AppointmentstableColumns.AGE, false);
        return this;
    }

    public AppointmentstableSelection user(String... value) {
        addEquals(AppointmentstableColumns.USER, value);
        return this;
    }

    public AppointmentstableSelection userNot(String... value) {
        addNotEquals(AppointmentstableColumns.USER, value);
        return this;
    }

    public AppointmentstableSelection userLike(String... value) {
        addLike(AppointmentstableColumns.USER, value);
        return this;
    }

    public AppointmentstableSelection userContains(String... value) {
        addContains(AppointmentstableColumns.USER, value);
        return this;
    }

    public AppointmentstableSelection userStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.USER, value);
        return this;
    }

    public AppointmentstableSelection userEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.USER, value);
        return this;
    }

    public AppointmentstableSelection orderByUser(boolean desc) {
        orderBy(AppointmentstableColumns.USER, desc);
        return this;
    }

    public AppointmentstableSelection orderByUser() {
        orderBy(AppointmentstableColumns.USER, false);
        return this;
    }

    public AppointmentstableSelection createdAt(Date... value) {
        addEquals(AppointmentstableColumns.CREATED_AT, value);
        return this;
    }

    public AppointmentstableSelection createdAtNot(Date... value) {
        addNotEquals(AppointmentstableColumns.CREATED_AT, value);
        return this;
    }

    public AppointmentstableSelection createdAt(Long... value) {
        addEquals(AppointmentstableColumns.CREATED_AT, value);
        return this;
    }

    public AppointmentstableSelection createdAtAfter(Date value) {
        addGreaterThan(AppointmentstableColumns.CREATED_AT, value);
        return this;
    }

    public AppointmentstableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(AppointmentstableColumns.CREATED_AT, value);
        return this;
    }

    public AppointmentstableSelection createdAtBefore(Date value) {
        addLessThan(AppointmentstableColumns.CREATED_AT, value);
        return this;
    }

    public AppointmentstableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(AppointmentstableColumns.CREATED_AT, value);
        return this;
    }

    public AppointmentstableSelection orderByCreatedAt(boolean desc) {
        orderBy(AppointmentstableColumns.CREATED_AT, desc);
        return this;
    }

    public AppointmentstableSelection orderByCreatedAt() {
        orderBy(AppointmentstableColumns.CREATED_AT, false);
        return this;
    }

    public AppointmentstableSelection completedAllVisits(Boolean value) {
        addEquals(AppointmentstableColumns.COMPLETED_ALL_VISITS, toObjectArray(value));
        return this;
    }

    public AppointmentstableSelection orderByCompletedAllVisits(boolean desc) {
        orderBy(AppointmentstableColumns.COMPLETED_ALL_VISITS, desc);
        return this;
    }

    public AppointmentstableSelection orderByCompletedAllVisits() {
        orderBy(AppointmentstableColumns.COMPLETED_ALL_VISITS, false);
        return this;
    }

    public AppointmentstableSelection pendingVisits(Integer... value) {
        addEquals(AppointmentstableColumns.PENDING_VISITS, value);
        return this;
    }

    public AppointmentstableSelection pendingVisitsNot(Integer... value) {
        addNotEquals(AppointmentstableColumns.PENDING_VISITS, value);
        return this;
    }

    public AppointmentstableSelection pendingVisitsGt(int value) {
        addGreaterThan(AppointmentstableColumns.PENDING_VISITS, value);
        return this;
    }

    public AppointmentstableSelection pendingVisitsGtEq(int value) {
        addGreaterThanOrEquals(AppointmentstableColumns.PENDING_VISITS, value);
        return this;
    }

    public AppointmentstableSelection pendingVisitsLt(int value) {
        addLessThan(AppointmentstableColumns.PENDING_VISITS, value);
        return this;
    }

    public AppointmentstableSelection pendingVisitsLtEq(int value) {
        addLessThanOrEquals(AppointmentstableColumns.PENDING_VISITS, value);
        return this;
    }

    public AppointmentstableSelection orderByPendingVisits(boolean desc) {
        orderBy(AppointmentstableColumns.PENDING_VISITS, desc);
        return this;
    }

    public AppointmentstableSelection orderByPendingVisits() {
        orderBy(AppointmentstableColumns.PENDING_VISITS, false);
        return this;
    }

    public AppointmentstableSelection missedVisits(Integer... value) {
        addEquals(AppointmentstableColumns.MISSED_VISITS, value);
        return this;
    }

    public AppointmentstableSelection missedVisitsNot(Integer... value) {
        addNotEquals(AppointmentstableColumns.MISSED_VISITS, value);
        return this;
    }

    public AppointmentstableSelection missedVisitsGt(int value) {
        addGreaterThan(AppointmentstableColumns.MISSED_VISITS, value);
        return this;
    }

    public AppointmentstableSelection missedVisitsGtEq(int value) {
        addGreaterThanOrEquals(AppointmentstableColumns.MISSED_VISITS, value);
        return this;
    }

    public AppointmentstableSelection missedVisitsLt(int value) {
        addLessThan(AppointmentstableColumns.MISSED_VISITS, value);
        return this;
    }

    public AppointmentstableSelection missedVisitsLtEq(int value) {
        addLessThanOrEquals(AppointmentstableColumns.MISSED_VISITS, value);
        return this;
    }

    public AppointmentstableSelection orderByMissedVisits(boolean desc) {
        orderBy(AppointmentstableColumns.MISSED_VISITS, desc);
        return this;
    }

    public AppointmentstableSelection orderByMissedVisits() {
        orderBy(AppointmentstableColumns.MISSED_VISITS, false);
        return this;
    }

    public AppointmentstableSelection trimester(Integer... value) {
        addEquals(AppointmentstableColumns.TRIMESTER, value);
        return this;
    }

    public AppointmentstableSelection trimesterNot(Integer... value) {
        addNotEquals(AppointmentstableColumns.TRIMESTER, value);
        return this;
    }

    public AppointmentstableSelection trimesterGt(int value) {
        addGreaterThan(AppointmentstableColumns.TRIMESTER, value);
        return this;
    }

    public AppointmentstableSelection trimesterGtEq(int value) {
        addGreaterThanOrEquals(AppointmentstableColumns.TRIMESTER, value);
        return this;
    }

    public AppointmentstableSelection trimesterLt(int value) {
        addLessThan(AppointmentstableColumns.TRIMESTER, value);
        return this;
    }

    public AppointmentstableSelection trimesterLtEq(int value) {
        addLessThanOrEquals(AppointmentstableColumns.TRIMESTER, value);
        return this;
    }

    public AppointmentstableSelection orderByTrimester(boolean desc) {
        orderBy(AppointmentstableColumns.TRIMESTER, desc);
        return this;
    }

    public AppointmentstableSelection orderByTrimester() {
        orderBy(AppointmentstableColumns.TRIMESTER, false);
        return this;
    }

    public AppointmentstableSelection village(String... value) {
        addEquals(AppointmentstableColumns.VILLAGE, value);
        return this;
    }

    public AppointmentstableSelection villageNot(String... value) {
        addNotEquals(AppointmentstableColumns.VILLAGE, value);
        return this;
    }

    public AppointmentstableSelection villageLike(String... value) {
        addLike(AppointmentstableColumns.VILLAGE, value);
        return this;
    }

    public AppointmentstableSelection villageContains(String... value) {
        addContains(AppointmentstableColumns.VILLAGE, value);
        return this;
    }

    public AppointmentstableSelection villageStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.VILLAGE, value);
        return this;
    }

    public AppointmentstableSelection villageEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.VILLAGE, value);
        return this;
    }

    public AppointmentstableSelection orderByVillage(boolean desc) {
        orderBy(AppointmentstableColumns.VILLAGE, desc);
        return this;
    }

    public AppointmentstableSelection orderByVillage() {
        orderBy(AppointmentstableColumns.VILLAGE, false);
        return this;
    }

    public AppointmentstableSelection status(String... value) {
        addEquals(AppointmentstableColumns.STATUS, value);
        return this;
    }

    public AppointmentstableSelection statusNot(String... value) {
        addNotEquals(AppointmentstableColumns.STATUS, value);
        return this;
    }

    public AppointmentstableSelection statusLike(String... value) {
        addLike(AppointmentstableColumns.STATUS, value);
        return this;
    }

    public AppointmentstableSelection statusContains(String... value) {
        addContains(AppointmentstableColumns.STATUS, value);
        return this;
    }

    public AppointmentstableSelection statusStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.STATUS, value);
        return this;
    }

    public AppointmentstableSelection statusEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.STATUS, value);
        return this;
    }

    public AppointmentstableSelection orderByStatus(boolean desc) {
        orderBy(AppointmentstableColumns.STATUS, desc);
        return this;
    }

    public AppointmentstableSelection orderByStatus() {
        orderBy(AppointmentstableColumns.STATUS, false);
        return this;
    }

    public AppointmentstableSelection vhtName(String... value) {
        addEquals(AppointmentstableColumns.VHT_NAME, value);
        return this;
    }

    public AppointmentstableSelection vhtNameNot(String... value) {
        addNotEquals(AppointmentstableColumns.VHT_NAME, value);
        return this;
    }

    public AppointmentstableSelection vhtNameLike(String... value) {
        addLike(AppointmentstableColumns.VHT_NAME, value);
        return this;
    }

    public AppointmentstableSelection vhtNameContains(String... value) {
        addContains(AppointmentstableColumns.VHT_NAME, value);
        return this;
    }

    public AppointmentstableSelection vhtNameStartsWith(String... value) {
        addStartsWith(AppointmentstableColumns.VHT_NAME, value);
        return this;
    }

    public AppointmentstableSelection vhtNameEndsWith(String... value) {
        addEndsWith(AppointmentstableColumns.VHT_NAME, value);
        return this;
    }

    public AppointmentstableSelection orderByVhtName(boolean desc) {
        orderBy(AppointmentstableColumns.VHT_NAME, desc);
        return this;
    }

    public AppointmentstableSelection orderByVhtName() {
        orderBy(AppointmentstableColumns.VHT_NAME, false);
        return this;
    }

    public AppointmentstableSelection appointmentDate(Date... value) {
        addEquals(AppointmentstableColumns.APPOINTMENT_DATE, value);
        return this;
    }

    public AppointmentstableSelection appointmentDateNot(Date... value) {
        addNotEquals(AppointmentstableColumns.APPOINTMENT_DATE, value);
        return this;
    }

    public AppointmentstableSelection appointmentDate(Long... value) {
        addEquals(AppointmentstableColumns.APPOINTMENT_DATE, value);
        return this;
    }

    public AppointmentstableSelection appointmentDateAfter(Date value) {
        addGreaterThan(AppointmentstableColumns.APPOINTMENT_DATE, value);
        return this;
    }

    public AppointmentstableSelection appointmentDateAfterEq(Date value) {
        addGreaterThanOrEquals(AppointmentstableColumns.APPOINTMENT_DATE, value);
        return this;
    }

    public AppointmentstableSelection appointmentDateBefore(Date value) {
        addLessThan(AppointmentstableColumns.APPOINTMENT_DATE, value);
        return this;
    }

    public AppointmentstableSelection appointmentDateBeforeEq(Date value) {
        addLessThanOrEquals(AppointmentstableColumns.APPOINTMENT_DATE, value);
        return this;
    }

    public AppointmentstableSelection orderByAppointmentDate(boolean desc) {
        orderBy(AppointmentstableColumns.APPOINTMENT_DATE, desc);
        return this;
    }

    public AppointmentstableSelection orderByAppointmentDate() {
        orderBy(AppointmentstableColumns.APPOINTMENT_DATE, false);
        return this;
    }
}
