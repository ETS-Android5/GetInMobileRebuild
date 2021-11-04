package org.odk.getin.android.provider.mappedgirltable;

import android.net.Uri;
import android.provider.BaseColumns;

import org.odk.getin.android.provider.MappedGirlsProvider;


/**
 * Columns for the {@code mappedgirltable} table.
 */
public class MappedgirltableColumns implements BaseColumns {
    public static final String TABLE_NAME = "mappedgirltable";
    public static final Uri CONTENT_URI = Uri.parse(MappedGirlsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String SERVERID = "serverId";

    public static final String FIRSTNAME = "firstName";

    public static final String LASTNAME = "lastName";

    public static final String PHONENUMBER = "phoneNumber";

    public static final String NEXTOFKINLASTNAME = "nextOfKinLastName";

    public static final String NEXTOFKINFIRSTNAME = "nextOfKinFirstName";

    public static final String NEXTOFKINPHONENUMBER = "nextOfKinPhoneNumber";

    public static final String EDUCATIONLEVEL = "educationLevel";

    public static final String MARITALSTATUS = "maritalStatus";

    public static final String HEALTHFACILITY = "healthFacility";

    public static final String AGE = "age";

    public static final String USER = "user";

    public static final String CREATED_AT = "created_at";

    public static final String VOUCHER_EXPIRY_DATE = "voucher_expiry_date";

    public static final String COMPLETED_ALL_VISITS = "completed_all_visits";

    public static final String PENDING_VISITS = "pending_visits";

    public static final String MISSED_VISITS = "missed_visits";

    public static final String VILLAGE = "village";

    public static final String VOUCHER_NUMBER = "voucher_number";

    public static final String SERVICES_RECEIVED = "services_received";

    public static final String NATIONALITY = "nationality";

    public static final String DISABLED = "disabled";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            SERVERID,
            FIRSTNAME,
            LASTNAME,
            PHONENUMBER,
            NEXTOFKINLASTNAME,
            NEXTOFKINFIRSTNAME,
            NEXTOFKINPHONENUMBER,
            EDUCATIONLEVEL,
            MARITALSTATUS,
            HEALTHFACILITY,
            AGE,
            USER,
            CREATED_AT,
            VOUCHER_EXPIRY_DATE,
            COMPLETED_ALL_VISITS,
            PENDING_VISITS,
            MISSED_VISITS,
            VILLAGE,
            VOUCHER_NUMBER,
            SERVICES_RECEIVED,
            NATIONALITY,
            DISABLED
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(SERVERID) || c.contains("." + SERVERID)) return true;
            if (c.equals(FIRSTNAME) || c.contains("." + FIRSTNAME)) return true;
            if (c.equals(LASTNAME) || c.contains("." + LASTNAME)) return true;
            if (c.equals(PHONENUMBER) || c.contains("." + PHONENUMBER)) return true;
            if (c.equals(NEXTOFKINLASTNAME) || c.contains("." + NEXTOFKINLASTNAME)) return true;
            if (c.equals(NEXTOFKINFIRSTNAME) || c.contains("." + NEXTOFKINFIRSTNAME)) return true;
            if (c.equals(NEXTOFKINPHONENUMBER) || c.contains("." + NEXTOFKINPHONENUMBER)) return true;
            if (c.equals(EDUCATIONLEVEL) || c.contains("." + EDUCATIONLEVEL)) return true;
            if (c.equals(MARITALSTATUS) || c.contains("." + MARITALSTATUS)) return true;
            if (c.equals(HEALTHFACILITY) || c.contains("." + HEALTHFACILITY)) return true;
            if (c.equals(AGE) || c.contains("." + AGE)) return true;
            if (c.equals(USER) || c.contains("." + USER)) return true;
            if (c.equals(CREATED_AT) || c.contains("." + CREATED_AT)) return true;
            if (c.equals(VOUCHER_EXPIRY_DATE) || c.contains("." + VOUCHER_EXPIRY_DATE)) return true;
            if (c.equals(COMPLETED_ALL_VISITS) || c.contains("." + COMPLETED_ALL_VISITS)) return true;
            if (c.equals(PENDING_VISITS) || c.contains("." + PENDING_VISITS)) return true;
            if (c.equals(MISSED_VISITS) || c.contains("." + MISSED_VISITS)) return true;
            if (c.equals(VILLAGE) || c.contains("." + VILLAGE)) return true;
            if (c.equals(VOUCHER_NUMBER) || c.contains("." + VOUCHER_NUMBER)) return true;
            if (c.equals(SERVICES_RECEIVED) || c.contains("." + SERVICES_RECEIVED)) return true;
            if (c.equals(NATIONALITY) || c.contains("." + NATIONALITY)) return true;
            if (c.equals(DISABLED) || c.contains("." + DISABLED)) return true;
        }
        return false;
    }

}
