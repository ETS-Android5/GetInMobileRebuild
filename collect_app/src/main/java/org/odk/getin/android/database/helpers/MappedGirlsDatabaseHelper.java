package org.odk.getin.android.database.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import org.odk.getin.android.BuildConfig;
import org.odk.getin.android.provider.appointmentstable.AppointmentstableColumns;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableColumns;
import org.odk.getin.android.provider.userstable.UserstableColumns;

public class MappedGirlsDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = MappedGirlsDatabaseHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "mappedgirls.db";
    private static final int DATABASE_VERSION = 7;
    private static MappedGirlsDatabaseHelper sInstance;
    private final Context mContext;
    private final MappedGirlsDatabaseHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_APPOINTMENTSTABLE = "CREATE TABLE IF NOT EXISTS "
            + AppointmentstableColumns.TABLE_NAME + " ( "
            + AppointmentstableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AppointmentstableColumns.SERVERID + " TEXT, "
            + AppointmentstableColumns.FIRSTNAME + " TEXT, "
            + AppointmentstableColumns.LASTNAME + " TEXT, "
            + AppointmentstableColumns.PHONENUMBER + " TEXT, "
            + AppointmentstableColumns.NEXTOFKINLASTNAME + " TEXT, "
            + AppointmentstableColumns.NEXTOFKINFIRSTNAME + " TEXT, "
            + AppointmentstableColumns.NEXTOFKINPHONENUMBER + " TEXT, "
            + AppointmentstableColumns.EDUCATIONLEVEL + " TEXT, "
            + AppointmentstableColumns.MARITALSTATUS + " TEXT, "
            + AppointmentstableColumns.AGE + " INTEGER, "
            + AppointmentstableColumns.USER + " TEXT, "
            + AppointmentstableColumns.CREATED_AT + " INTEGER, "
            + AppointmentstableColumns.COMPLETED_ALL_VISITS + " INTEGER, "
            + AppointmentstableColumns.PENDING_VISITS + " INTEGER, "
            + AppointmentstableColumns.MISSED_VISITS + " INTEGER, "
            + AppointmentstableColumns.TRIMESTER + " INTEGER, "
            + AppointmentstableColumns.VILLAGE + " TEXT, "
            + AppointmentstableColumns.STATUS + " TEXT, "
            + AppointmentstableColumns.VHT_NAME + " TEXT, "
            + AppointmentstableColumns.APPOINTMENT_DATE + " INTEGER "
            + " );";

    public static final String SQL_CREATE_TABLE_MAPPEDGIRLTABLE = "CREATE TABLE IF NOT EXISTS "
            + MappedgirltableColumns.TABLE_NAME + " ( "
            + MappedgirltableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MappedgirltableColumns.SERVERID + " TEXT, "
            + MappedgirltableColumns.FIRSTNAME + " TEXT, "
            + MappedgirltableColumns.LASTNAME + " TEXT, "
            + MappedgirltableColumns.PHONENUMBER + " TEXT, "
            + MappedgirltableColumns.NEXTOFKINLASTNAME + " TEXT, "
            + MappedgirltableColumns.NEXTOFKINFIRSTNAME + " TEXT, "
            + MappedgirltableColumns.NEXTOFKINPHONENUMBER + " TEXT, "
            + MappedgirltableColumns.EDUCATIONLEVEL + " TEXT, "
            + MappedgirltableColumns.MARITALSTATUS + " TEXT, "
            + MappedgirltableColumns.AGE + " INTEGER, "
            + MappedgirltableColumns.USER + " TEXT, "
            + MappedgirltableColumns.CREATED_AT + " INTEGER, "
            + MappedgirltableColumns.COMPLETED_ALL_VISITS + " INTEGER, "
            + MappedgirltableColumns.PENDING_VISITS + " INTEGER, "
            + MappedgirltableColumns.MISSED_VISITS + " INTEGER, "
            + MappedgirltableColumns.VILLAGE + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_USERSTABLE = "CREATE TABLE IF NOT EXISTS "
            + UserstableColumns.TABLE_NAME + " ( "
            + UserstableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserstableColumns.USERID + " TEXT, "
            + UserstableColumns.FIRSTNAME + " TEXT, "
            + UserstableColumns.LASTNAME + " TEXT, "
            + UserstableColumns.PHONENUMBER + " TEXT, "
            + UserstableColumns.CREATED_AT + " INTEGER, "
            + UserstableColumns.NUMBER_PLATE + " TEXT, "
            + UserstableColumns.ROLE + " TEXT, "
            + UserstableColumns.MIDWIFEID + " TEXT, "
            + UserstableColumns.VILLAGE + " TEXT "
            + " );";

    // @formatter:on

    public static MappedGirlsDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static MappedGirlsDatabaseHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static MappedGirlsDatabaseHelper newInstancePreHoneycomb(Context context) {
        return new MappedGirlsDatabaseHelper(context);
    }

    private MappedGirlsDatabaseHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new MappedGirlsDatabaseHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static MappedGirlsDatabaseHelper newInstancePostHoneycomb(Context context) {
        return new MappedGirlsDatabaseHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MappedGirlsDatabaseHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new MappedGirlsDatabaseHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_MAPPEDGIRLTABLE);
        db.execSQL(SQL_CREATE_TABLE_APPOINTMENTSTABLE);
        db.execSQL(SQL_CREATE_TABLE_USERSTABLE);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
