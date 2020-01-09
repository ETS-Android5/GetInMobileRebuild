package org.odk.getin.android.provider.userstable;

import android.net.Uri;
import android.provider.BaseColumns;

import org.odk.getin.android.provider.MappedGirlsProvider;

/**
 * Columns for the {@code userstable} table.
 */
public class UserstableColumns implements BaseColumns {
    public static final String TABLE_NAME = "userstable";
    public static final Uri CONTENT_URI = Uri.parse(MappedGirlsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String USERID = "userId";

    public static final String FIRSTNAME = "firstName";

    public static final String LASTNAME = "lastName";

    public static final String PHONENUMBER = "phoneNumber";

    public static final String CREATED_AT = "created_at";

    public static final String NUMBER_PLATE = "number_plate";

    public static final String ROLE = "role";

    public static final String MIDWIFEID = "midwifeId";

    public static final String VILLAGE = "village";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            USERID,
            FIRSTNAME,
            LASTNAME,
            PHONENUMBER,
            CREATED_AT,
            NUMBER_PLATE,
            ROLE,
            MIDWIFEID,
            VILLAGE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(USERID) || c.contains("." + USERID)) return true;
            if (c.equals(FIRSTNAME) || c.contains("." + FIRSTNAME)) return true;
            if (c.equals(LASTNAME) || c.contains("." + LASTNAME)) return true;
            if (c.equals(PHONENUMBER) || c.contains("." + PHONENUMBER)) return true;
            if (c.equals(CREATED_AT) || c.contains("." + CREATED_AT)) return true;
            if (c.equals(NUMBER_PLATE) || c.contains("." + NUMBER_PLATE)) return true;
            if (c.equals(ROLE) || c.contains("." + ROLE)) return true;
            if (c.equals(MIDWIFEID) || c.contains("." + MIDWIFEID)) return true;
            if (c.equals(VILLAGE) || c.contains("." + VILLAGE)) return true;
        }
        return false;
    }

}
