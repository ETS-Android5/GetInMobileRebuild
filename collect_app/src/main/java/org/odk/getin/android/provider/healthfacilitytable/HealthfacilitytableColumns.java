package org.odk.getin.android.provider.healthfacilitytable;

import android.net.Uri;
import android.provider.BaseColumns;

import org.odk.getin.android.provider.MappedGirlsProvider;
import org.odk.getin.android.provider.appointmentstable.AppointmentstableColumns;
import org.odk.getin.android.provider.healthfacilitytable.HealthfacilitytableColumns;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableColumns;
import org.odk.getin.android.provider.userstable.UserstableColumns;

/**
 * Columns for the {@code healthfacilitytable} table.
 */
public class HealthfacilitytableColumns implements BaseColumns {
    public static final String TABLE_NAME = "healthfacilitytable";
    public static final Uri CONTENT_URI = Uri.parse(MappedGirlsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String SERVERID = "serverId";

    public static final String NAME = "name";

    public static final String FACILITYLEVEL = "facilityLevel";

    public static final String DISTRICT = "district";

    public static final String SUBCOUNTY = "subcounty";

    public static final String CREATED_AT = "created_at";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            SERVERID,
            NAME,
            FACILITYLEVEL,
            DISTRICT,
            SUBCOUNTY,
            CREATED_AT
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(SERVERID) || c.contains("." + SERVERID)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(FACILITYLEVEL) || c.contains("." + FACILITYLEVEL)) return true;
            if (c.equals(DISTRICT) || c.contains("." + DISTRICT)) return true;
            if (c.equals(SUBCOUNTY) || c.contains("." + SUBCOUNTY)) return true;
            if (c.equals(CREATED_AT) || c.contains("." + CREATED_AT)) return true;
        }
        return false;
    }

}
