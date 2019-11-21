package org.odk.collect.android.provider.appointmentstable;

import java.util.Date;

import android.database.Cursor;

import androidx.annotation.Nullable;

import org.odk.collect.android.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code appointmentstable} table.
 */
public class AppointmentstableCursor extends AbstractCursor implements AppointmentstableModel {
    public AppointmentstableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(AppointmentstableColumns._ID);
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
        String res = getStringOrNull(AppointmentstableColumns.SERVERID);
        return res;
    }

    /**
     * Get the {@code firstname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getFirstname() {
        String res = getStringOrNull(AppointmentstableColumns.FIRSTNAME);
        return res;
    }

    /**
     * Get the {@code lastname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getLastname() {
        String res = getStringOrNull(AppointmentstableColumns.LASTNAME);
        return res;
    }

    /**
     * Get the {@code phonenumber} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPhonenumber() {
        String res = getStringOrNull(AppointmentstableColumns.PHONENUMBER);
        return res;
    }

    /**
     * Get the {@code nextofkinlastname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getNextofkinlastname() {
        String res = getStringOrNull(AppointmentstableColumns.NEXTOFKINLASTNAME);
        return res;
    }

    /**
     * Get the {@code nextofkinfirstname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getNextofkinfirstname() {
        String res = getStringOrNull(AppointmentstableColumns.NEXTOFKINFIRSTNAME);
        return res;
    }

    /**
     * Get the {@code nextofkinphonenumber} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getNextofkinphonenumber() {
        String res = getStringOrNull(AppointmentstableColumns.NEXTOFKINPHONENUMBER);
        return res;
    }

    /**
     * Get the {@code educationlevel} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getEducationlevel() {
        String res = getStringOrNull(AppointmentstableColumns.EDUCATIONLEVEL);
        return res;
    }

    /**
     * Get the {@code maritalstatus} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMaritalstatus() {
        String res = getStringOrNull(AppointmentstableColumns.MARITALSTATUS);
        return res;
    }

    /**
     * Get the {@code age} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getAge() {
        Integer res = getIntegerOrNull(AppointmentstableColumns.AGE);
        return res;
    }

    /**
     * Get the {@code user} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getUser() {
        String res = getStringOrNull(AppointmentstableColumns.USER);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(AppointmentstableColumns.CREATED_AT);
        return res;
    }

    /**
     * Get the {@code completed_all_visits} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getCompletedAllVisits() {
        Boolean res = getBooleanOrNull(AppointmentstableColumns.COMPLETED_ALL_VISITS);
        return res;
    }

    /**
     * Get the {@code pending_visits} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getPendingVisits() {
        Integer res = getIntegerOrNull(AppointmentstableColumns.PENDING_VISITS);
        return res;
    }

    /**
     * Get the {@code missed_visits} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getMissedVisits() {
        Integer res = getIntegerOrNull(AppointmentstableColumns.MISSED_VISITS);
        return res;
    }

    /**
     * Get the {@code trimester} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getTrimester() {
        Integer res = getIntegerOrNull(AppointmentstableColumns.TRIMESTER);
        return res;
    }

    /**
     * Get the {@code village} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getVillage() {
        String res = getStringOrNull(AppointmentstableColumns.VILLAGE);
        return res;
    }

    /**
     * Get the {@code status} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getStatus() {
        String res = getStringOrNull(AppointmentstableColumns.STATUS);
        return res;
    }

    /**
     * Get the {@code vht_name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getVhtName() {
        String res = getStringOrNull(AppointmentstableColumns.VHT_NAME);
        return res;
    }

    /**
     * Get the {@code appointment_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getAppointmentDate() {
        Date res = getDateOrNull(AppointmentstableColumns.APPOINTMENT_DATE);
        return res;
    }
}
